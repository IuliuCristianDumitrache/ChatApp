package com.example.app.ui.friendconversation

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R
import com.example.app.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private val viewModel: ChatViewModel by activityViewModels()

    private var views: FragmentChatBinding? = null

    private val args by navArgs<ChatFragmentArgs>()

    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = FragmentChatBinding.bind(view)

        initUi()
        initListeners()
        observeLiveData()
        viewModel.fetchHistory(args.model.id)
    }

    private fun initListeners() {
        val friendId = args.model.id
        val friendName = args.model.name

        views?.sendMessageButton?.setOnClickListener {
            viewModel.sendMessage(friendId, friendName, views?.editTextMessage?.text.toString())
            views?.editTextMessage?.setText("")
        }
    }

    private fun initUi() {
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

        views?.btnClose?.setOnClickListener {
            findNavController().popBackStack()
        }

        views?.tvName?.text = args.model.name

        chatAdapter = ChatAdapter()

        views?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        views?.recyclerView?.adapter = chatAdapter
    }

    override fun onDestroy() {
        viewModel.chatLiveData.postValue(null)
        super.onDestroy()
    }

    private fun observeLiveData() {
        viewModel.chatLiveData.observe(this) {
            chatAdapter.submitList(it)
            chatAdapter.notifyDataSetChanged()
        }

        viewModel.isFriendTyping.observe(this) {
            views?.isFriendTypingTextView?.isVisible = it
        }
    }
}