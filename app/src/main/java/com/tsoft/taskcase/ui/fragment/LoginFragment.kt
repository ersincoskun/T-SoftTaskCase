package com.tsoft.taskcase.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tsoft.taskcase.R
import com.tsoft.taskcase.base.BaseFragment
import com.tsoft.taskcase.databinding.FragmentLoginBinding
import com.tsoft.taskcase.ui.activity.MainActivity
import com.tsoft.taskcase.utils.*
import com.tsoft.taskcase.viewmodel.LoginFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginFragmentViewModel by viewModels()
    private lateinit var passwordEyeIconAction: PasswordEyeIconAction

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subLiveData()
        passwordEyeIconAction = PasswordEyeIconAction(binding.etLoginPassword, binding.ivLoginPasswordEyeIcon)
        setListeners()
    }

    private fun subLiveData() {
        viewModel.loginResponse.observe(viewLifecycleOwner) { responseResource ->
            when (responseResource) {
                is Resource.Error -> {
                    when (responseResource.message) {
                        "MAIL_NOT_VERIFIED" -> context.showErrorToasty(getString(R.string.login_fragment_before_login_verify_error))
                        "USER_NULL" -> context.showDefaultError()
                        "LOGIN_NOT_SUCCESSFUL" -> context.showDefaultError()
                        "ERROR_INVALID_EMAIL" -> context.showErrorToasty(getString(R.string.login_fragment_invalid_email))
                        "ERROR_WRONG_PASSWORD" -> context.showErrorToasty(getString(R.string.login_fragment_wrong_password))
                        "ERROR_USER_NOT_FOUND" -> context.showErrorToasty(getString(R.string.login_fragment_user_not_found))
                        else -> context.showErrorToasty(getString(R.string.default_error_text))
                    }
                }
                is Resource.Success<*> -> {
                    activity?.let { safeActivity ->
                        startActivity(Intent(safeActivity, MainActivity::class.java))
                        safeActivity.finish()
                    } ?: kotlin.run {
                        context.showErrorToasty(getString(R.string.default_error_text))
                    }
                }
                is Resource.Empty -> {
                    context.showDefaultError()
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            btnLogin.onSingleClickListener {
                if (etLoginMail.text.isBlank() || etLoginMail.text.isBlank()) {
                    context.showErrorToasty(getString(R.string.login_fragment_email_or_password_empty_error_message))
                } else if (!etLoginMail.text.toString().isValidEmail()) {
                    context.showErrorToasty(getString(R.string.login_fragment_mail_not_valid))
                } else {
                    showProgressBar()
                    viewModel.login(binding.etLoginMail.text.toString(), binding.etLoginPassword.text.toString())
                }
            }
            tvSignUp.onSingleClickListener {
                //navigate signup
            }

            tvForgotPassword.onSingleClickListener {
                //navigate forgot password
            }
        }
    }

}