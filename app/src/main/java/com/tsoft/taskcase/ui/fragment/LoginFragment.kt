package com.tsoft.taskcase.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.tsoft.taskcase.R
import com.tsoft.taskcase.base.BaseFragment
import com.tsoft.taskcase.databinding.FragmentLoginBinding
import com.tsoft.taskcase.helpers.PreferencesHelper
import com.tsoft.taskcase.ui.activity.MainActivity
import com.tsoft.taskcase.utils.*
import com.tsoft.taskcase.viewmodel.LoginFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var preferencesHelper: PreferencesHelper
    private val viewModel: LoginFragmentViewModel by viewModels()
    private lateinit var passwordEyeIconAction: PasswordEyeIconAction

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subLiveData()
        prepareUI()
    }

    private fun prepareUI() {
        binding.apply {
            passwordEyeIconAction = PasswordEyeIconAction(etLoginPassword, ivLoginPasswordEyeIcon)
            setListeners()
            cbRememberMe.isChecked = preferencesHelper.isRememberMeChecked
            if (preferencesHelper.isRememberMeChecked && preferencesHelper.rememberMeMail.isNotEmpty()) {
                etLoginMail.setText(preferencesHelper.rememberMeMail)
            }
            btnLogin.setClickEffect()
            tvSignUp.setClickEffect()
            tvForgotPassword.setClickEffect()
        }
    }

    private fun subLiveData() {
        viewModel.loginResponse.observe(viewLifecycleOwner) { responseResource ->
            hideProgressBar()
            when (responseResource) {
                is Resource.Error -> {
                    when (responseResource.message) {
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
                if (etLoginMail.text.isBlank() || etLoginPassword.text.isBlank()) {
                    context.showErrorToasty(getString(R.string.login_fragment_email_or_password_empty_error_message))
                } else if (!etLoginMail.text.toString().isValidEmail()) {
                    context.showErrorToasty(getString(R.string.login_fragment_mail_not_valid))
                } else {
                    showProgressBar()
                    if (preferencesHelper.isRememberMeChecked) {
                        preferencesHelper.rememberMeMail = etLoginMail.text.toString()
                    }
                    viewModel.login(binding.etLoginMail.text.toString(), binding.etLoginPassword.text.toString())
                }
            }
            tvSignUp.onSingleClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                navigate(navDirections = action)
            }

            tvForgotPassword.onSingleClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment()
                navigate(navDirections = action)
            }

            cbRememberMe.setOnCheckedChangeListener { _, isChecked ->
                preferencesHelper.isRememberMeChecked = isChecked
            }
        }
    }

}