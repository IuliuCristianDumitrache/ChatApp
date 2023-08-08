package com.example.app.ui.modelsdetails

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.app.R
import com.example.app.databinding.FragmentItemDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModelDetailsFragment : Fragment() {

    private var views: FragmentItemDetailBinding? = null

    private val args by navArgs<ModelDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = FragmentItemDetailBinding.bind(view)

        initUi()
        initListeners()
    }

    private fun initListeners() {
        views?.btnClose?.setOnClickListener {
            popBackStack()
        }
    }

    private fun popBackStack() {
        findNavController().popBackStack()
    }

    private fun initUi() {
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

        views?.tvName?.text = args.model.name
    }
}