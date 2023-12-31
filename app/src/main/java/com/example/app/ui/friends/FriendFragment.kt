package com.example.app.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R
import com.example.app.databinding.FragmentListBinding
import com.example.app.databinding.FriendItemBinding
import com.example.app.extensions.disposeIfNotAlready
import com.example.app.extensions.navigateSafely
import com.example.app.extensions.observe
import com.example.app.models.FriendModel
import com.example.app.network.RxBus
import com.example.app.network.rxmessages.ReloadList
import com.example.app.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.Disposable

@AndroidEntryPoint
class FriendFragment : Fragment(), FriendAdapter.OnModelItemListener {

    private val viewModel: FriendViewModel by viewModels()
    private var views: FragmentListBinding? = null
    private val friendAdapter = FriendAdapter(this)

    private var reloadSubscriber: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = FragmentListBinding.bind(view)

        initRecyclerView()

        observe(viewModel.friendList) { listOfFriends ->
            friendAdapter.submitList(listOfFriends)
            if (listOfFriends.isNotEmpty()) {
                views?.recyclerView?.scrollToPosition(listOfFriends.size - 1)
            }
        }

        observe(viewModel.showProgress) { showProgress ->
            views!!.progress.isVisible = showProgress
        }

        observe(viewModel.showError) { message ->
            Toast.makeText(
                context, message,
                Toast.LENGTH_LONG
            ).show()
        }

        initSubscriber()
        initButtons()
        (requireActivity() as MainActivity).setSupportActionBar(views!!.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.title = ""
    }

    override fun onResume() {
        viewModel.getModelsList()
        super.onResume()
    }

    private fun initButtons() {
        views?.btnAddFriend?.setOnClickListener {
            viewModel.addFriend(views?.etFriendName?.text.toString())
        }
    }

    private fun initSubscriber() {
        reloadSubscriber = RxBus.listen(ReloadList::class.java).subscribe {
            if (it.reload) {
                viewModel.getModelsList()
            }
        }
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        views?.recyclerView?.layoutManager = linearLayoutManager
        views?.recyclerView?.adapter = friendAdapter
    }

    override fun onDestroy() {
        reloadSubscriber?.disposeIfNotAlready()
        super.onDestroy()
    }

    override fun onFriendItemClicked(binding: FriendItemBinding, model: FriendModel) {
        val extras = FragmentNavigatorExtras(
            binding.tvName to getString(R.string.name_transition),
            binding.root to getString(R.string.root_transition)
        )

        val modelDirection = FriendFragmentDirections.actionModelsFragmentToChatFragment(
            model = model
        )
        findNavController().navigateSafely(
            modelDirection, extras
        )
    }
}