package com.tsoft.taskcase.base

import android.view.View
import android.widget.ProgressBar
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tsoft.taskcase.R
import com.tsoft.taskcase.ui.activity.LoginActivity
import com.tsoft.taskcase.ui.activity.MainActivity
import com.tsoft.taskcase.utils.remove
import com.tsoft.taskcase.utils.show

abstract class BaseFragment<VB : ViewBinding?> : BaseTemplateFragment<VB>() {

    fun navigateBackStack() {
        Navigation.findNavController(binding.root).popBackStack()
    }

    fun navigate(action: Int? = null, navDirections: NavDirections? = null) {
        action?.let {
            Navigation.findNavController(binding.root).navigate(it)
        }
        navDirections?.let {
            Navigation.findNavController(binding.root).navigate(it)
        }
    }

    fun showBottomSheet() {
        activity?.let { safeActivity ->
            if (safeActivity is MainActivity) {
                safeActivity.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.show()
            }
        }
    }

    fun removeBottomSheet() {
        activity?.let { safeActivity ->
            if (safeActivity is MainActivity) {
                safeActivity.findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.remove()
            }
        }
    }

    fun showProgressBar() {
        activity?.let { safeActivity ->
            when (safeActivity) {
                is LoginActivity -> {
                    safeActivity.findViewById<ProgressBar>(R.id.loginProgressBar)?.show()
                    safeActivity.findViewById<View>(R.id.loginViewOpaqueBg)?.show()
                }
                is MainActivity -> {
                    safeActivity.findViewById<ProgressBar>(R.id.mainProgressBar)?.show()
                    safeActivity.findViewById<View>(R.id.mainViewOpaqueBg)?.show()
                }
                else -> {}
            }
        }
    }

    fun hideProgressBar() {
        if (activity is LoginActivity) {
            activity?.findViewById<ProgressBar>(R.id.loginProgressBar)?.remove()
            activity?.findViewById<View>(R.id.loginViewOpaqueBg)?.remove()
        } else if (activity is MainActivity) {
            activity?.findViewById<ProgressBar>(R.id.mainProgressBar)?.remove()
            activity?.findViewById<View>(R.id.mainViewOpaqueBg)?.remove()
        }

    }

}