package com.tsoft.taskcase.base

import android.view.View
import android.widget.ProgressBar
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.tsoft.taskcase.LoginActivity
import com.tsoft.taskcase.MainActivity
import com.tsoft.taskcase.R
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

    fun showProgressBar() {
        if (activity is LoginActivity) {
            activity?.findViewById<ProgressBar>(R.id.loginProgressBar)?.show()
            activity?.findViewById<View>(R.id.loginViewOpaqueBg)?.show()
        } else if (activity is MainActivity) {
            activity?.findViewById<ProgressBar>(R.id.mainProgressBar)?.show()
            activity?.findViewById<View>(R.id.mainViewOpaqueBg)?.show()
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