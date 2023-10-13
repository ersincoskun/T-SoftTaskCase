package com.tsoft.taskcase.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tsoft.taskcase.base.BaseActivity
import com.tsoft.taskcase.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            startAppProcess()
        }, 2000)
        return super.onCreateView(name, context, attrs)
    }

    private fun startAppProcess() {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        currentUser?.let {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } ?: kotlin.run {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}