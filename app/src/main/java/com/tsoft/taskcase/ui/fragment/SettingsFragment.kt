package com.tsoft.taskcase.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.tsoft.taskcase.R
import com.tsoft.taskcase.base.BaseFragment
import com.tsoft.taskcase.databinding.FragmentSettingsBinding
import com.tsoft.taskcase.ui.activity.LoginActivity
import com.tsoft.taskcase.ui.dialog.SureToDoAlertDialog
import com.tsoft.taskcase.utils.*
import com.tsoft.taskcase.viewmodel.SettingsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val viewModel: SettingsFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBottomNavView()
        subLiveData()
        setListeners()
        prepareUI()
    }

    private fun subLiveData() {
        viewModel.deleteAccountResponse.observe(viewLifecycleOwner) { responseResource ->
            when (responseResource) {
                is Resource.Error -> {
                    when (responseResource.message) {
                        /* task successful olmamasının sebebi kullanıcı uzun süre login olmadığında
                         firebase account delete işlemi için kullanıcının tekrar login olmasını istiyor
                         bunu kullanıcı verileri güvenliği için yapıyormuş, bu yüzden login sayfasına yönelndirip
                         bilgilendirme yapıyorum*/
                        "TASK_NOT_SUCCESSFUL" -> {
                            activity?.let { safeActivity ->
                                context.showInfoToasty(getString(R.string.settings_fragment_relogin_before_delete_account_text))
                                hideProgressBar()
                                FirebaseAuth.getInstance().signOut()
                                val intent = Intent(safeActivity, LoginActivity::class.java)
                                startActivity(intent)
                                safeActivity.finish()
                            }

                        }
                        else -> context.showErrorToasty(getString(R.string.default_error_text))
                    }
                }
                is Resource.Success<*> -> {
                    activity?.let { safeActivity ->
                        context.showSuccessToasty(getString(R.string.settings_fragment_delete_account_process_successful_text))
                        hideProgressBar()
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(safeActivity, LoginActivity::class.java)
                        startActivity(intent)
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
            clLogoutContainer.onSingleClickListener {
                context?.let { safeContext ->
                    SureToDoAlertDialog(safeContext).setParameters {
                        activity?.let { safeActivity ->
                            FirebaseAuth.getInstance().signOut()
                            val intent = Intent(safeActivity, LoginActivity::class.java)
                            startActivity(intent)
                            safeActivity.finish()
                        }
                    }.showDialog()
                }
            }

            clDeleteAccountContainer.onSingleClickListener {
                context?.let { safeContext ->
                    SureToDoAlertDialog(safeContext).setParameters(title = getString(R.string.settings_fragment_sure_to_delete_account_title)) {
                        showProgressBar()
                        viewModel.deleteAccount()
                    }.showDialog()
                }
            }

            clPrivacyPolicyContainer.onSingleClickListener {
                val mUrl = "https://www.tsoft.com.tr/Eticaret/GizlilikPolitikasi.php"
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mUrl))
                startActivity(browserIntent)
            }

            clTermsOfServiceContainer.onSingleClickListener {
                val mUrl = "https://www.tsoft.com.tr/Eticaret/KVKK-Aydinlatma-Metni.php"
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mUrl))
                startActivity(browserIntent)
            }

            ivBackIcon.onSingleClickListener {
                navigateBackStack()
            }
        }
    }

    private fun prepareUI() {
        binding.apply {
            ivDeleteIcon.setColorFilter(Color.WHITE)
            context?.let { safeContext ->
                ivLogoutIcon.setColorFilter(ContextCompat.getColor(safeContext, R.color.red))
            }
            clLogoutContainer.setClickEffect()
            clDeleteAccountContainer.setClickEffect()
            clPrivacyPolicyContainer.setClickEffect()
            clTermsOfServiceContainer.setClickEffect()
            ivBackIcon.setClickEffect()
        }
    }

}