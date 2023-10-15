package com.tsoft.taskcase.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tsoft.taskcase.R
import com.tsoft.taskcase.base.BaseFragment
import com.tsoft.taskcase.databinding.FragmentRegisterBinding
import com.tsoft.taskcase.ui.activity.MainActivity
import com.tsoft.taskcase.utils.*
import com.tsoft.taskcase.viewmodel.RegisterFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterFragmentViewModel by viewModels()
    private lateinit var passwordEyeIconAction: PasswordEyeIconAction
    private lateinit var passwordAgainEyeIconAction: PasswordEyeIconAction

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subLiveData()
        passwordEyeIconAction = PasswordEyeIconAction(binding.etRegisterPassword, binding.ivRegisterPasswordEyeIcon)
        passwordAgainEyeIconAction = PasswordEyeIconAction(binding.etRegisterPasswordAgain, binding.ivRegisterPasswordAgainEyeIcon)
        setListeners()
        prepareUI()
    }

    private fun subLiveData() {
        viewModel.registerResponse.observe(viewLifecycleOwner) { responseResource ->
            hideProgressBar()
            when (responseResource) {
                is Resource.Error -> {
                    when (responseResource.message) {
                        "USER_NULL" -> context.showDefaultError()
                        "REGISTER_NOT_SUCCESSFUL" -> context.showDefaultError()
                        "ERROR_INVALID_EMAIL" -> getString(R.string.login_fragment_invalid_email)
                        "ERROR_EMAIL_ALREADY_IN_USE" -> getString(R.string.register_fragment_email_already_in_use_error_message)
                        "ERROR_WEAK_PASSWORD" -> getString(R.string.register_fragment_weak_password_error_message)
                        "ERROR_TOO_MANY_REQUESTS" -> getString(R.string.register_fragment_too_many_requests_error_message)
                        else -> context.showErrorToasty(getString(R.string.default_error_text))
                    }
                }
                is Resource.Success<*> -> {
                    activity?.let { safeActivity ->
                        startActivity(Intent(safeActivity, MainActivity::class.java))
                        safeActivity.finish()
                        safeActivity.overridePendingTransition(R.anim.enter_animation, R.anim.exit_animation)
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

    private fun prepareUI(){
        binding.apply {
            btnRegister.setClickEffect()
            tvSignIn.setClickEffect()
            ivBackIcon.setClickEffect()
        }
    }

    private fun setListeners() {
        binding.apply {
            btnRegister.onSingleClickListener {
                if (binding.etRegisterMail.text.isEmpty() || binding.etRegisterPassword.text.isEmpty() || binding.etRegisterPasswordAgain.text.isEmpty()) {
                    context.showErrorToasty(getString(R.string.login_fragment_email_or_password_empty_error_message))
                } else if (binding.etRegisterPassword.text.toString() != binding.etRegisterPasswordAgain.text.toString()) {
                    context.showErrorToasty(getString(R.string.register_fragment_password_not_match))
                } else if (!binding.etRegisterMail.text.toString().isValidEmail()) {
                    context.showErrorToasty(getString(R.string.login_fragment_mail_not_valid))
                } else {
                    showProgressBar()
                    viewModel.register(etRegisterMail.text.toString(), etRegisterPassword.text.toString())
                }
            }
            tvSignIn.onSingleClickListener {
                navigateBackStack()
            }
            ivBackIcon.onSingleClickListener {
                navigateBackStack()
            }
        }
    }

}