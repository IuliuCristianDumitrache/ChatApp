package com.example.app.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.app.R
import com.example.app.databinding.FragmentLoginBinding
import com.example.app.extensions.navigateSafely
import com.example.app.extensions.observe
import com.example.app.utils.AlertDialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()
    private var views: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = FragmentLoginBinding.bind(view)


        observe(viewModel.loginInTheApp) { loginInTheApp ->
            if (loginInTheApp) {
                navigateToModelsScreen()
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

        observe(viewModel.showInvalidLogin) { messageId ->
            AlertDialogUtil.createCustomAlertDialog(
                context = requireContext(),
                alertTitle = getString(messageId),
                null,
                positiveButton = Pair(getString(R.string.lbl_ok)) { dialog, _ ->
                    dialog.dismiss()
                }, null
            ).show()
        }

        initButtons()
    }

    override fun onResume() {
        if (viewModel.loggedInAlreadyInTheApp.value == true) {
            navigateToModelsScreen()
        }
        super.onResume()
    }

    private fun navigateToModelsScreen() {
        val loginDirections = LoginFragmentDirections.actionLoginFragmentToModelsFragment()
        findNavController().navigateSafely(
            loginDirections
        )
    }

    private fun initButtons() {
        views?.btnLogin?.setOnClickListener {
            viewModel.login(
                views?.inputEmail?.text.toString(),
                views?.inputPassword?.text.toString()
            )
        }
    }
}