package com.tsoft.taskcase.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tsoft.taskcase.R
import com.tsoft.taskcase.base.BaseFragment
import com.tsoft.taskcase.databinding.FragmentResetPasswordBinding
import com.tsoft.taskcase.utils.*
import com.tsoft.taskcase.viewmodel.ResetPasswordFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : BaseFragment<FragmentResetPasswordBinding>() {

    private val viewModel: ResetPasswordFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subLiveData()
        setListeners()
    }

    private fun subLiveData() {
        viewModel.sendResetPasswordMailResponse.observe(viewLifecycleOwner) { responseResource ->
            when (responseResource) {
                is Resource.Error -> {
                    context.showDefaultError()
                }
                is Resource.Success<*> -> {
                    context.showSuccessToasty(getString(R.string.reset_password_fragment_mail_sent_message))
                    navigateBackStack()
                }
                is Resource.Empty -> {
                    context.showDefaultError()
                }
            }
        }
    }

    private fun setListeners() {
        binding.btnSendResetPassword.onSingleClickListener {
            if (binding.etResetPasswordMail.text.isEmpty()) {
                context.showErrorToasty(getString(R.string.reset_password_fragment_email_empty_error_message))
            } else if (!binding.etResetPasswordMail.text.toString().isValidEmail()) {
                context.showErrorToasty(getString(R.string.login_fragment_mail_not_valid))
            } else {
                viewModel.sendPasswordResetEmail(binding.etResetPasswordMail.text.toString())
            }
        }
    }

}