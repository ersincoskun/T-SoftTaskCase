package com.tsoft.taskcase.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseTemplateActivity<T : ViewBinding> : FragmentActivity() {

    val binding get() = mBinding!!

    private var vgRoot: ViewGroup? = null

    private var mBinding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBinding()
    }

    final override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    private fun prepareBinding() {
        var genericSuperclass = javaClass.genericSuperclass
        while ((genericSuperclass is ParameterizedType).not()) {
            genericSuperclass = (genericSuperclass as Class<*>).genericSuperclass
        }
        val viewBindingClassType = (genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val viewBindingClass = viewBindingClassType as Class<T>
        val inflateMethod = viewBindingClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        val layoutInflater = LayoutInflater.from(this)
        mBinding = inflateMethod.invoke(null, layoutInflater, null, false) as T
        vgRoot = mBinding?.root as ViewGroup
        setContentView(vgRoot)
    }

}