package com.tsoft.taskcase.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.tsoft.taskcase.utils.printErrorLog
import java.lang.reflect.ParameterizedType

open class BaseTemplateFragment<VB : ViewBinding?> : Fragment() {

    val binding get() = mBinding!!
    private var mBinding: VB? = null
    private var vgRoot: ViewGroup? = null
    private var vgFragmentContainer: ViewGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        printErrorLog("current fragment: $this")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vgFragmentContainer = container
        prepareBinding()
        return vgRoot
    }

    private fun prepareBinding() {
        var genericSuperclass = javaClass.genericSuperclass
        while ((genericSuperclass is ParameterizedType).not()) {
            genericSuperclass = (genericSuperclass as Class<*>).genericSuperclass
        }
        val viewBindingClassType = (genericSuperclass as ParameterizedType).actualTypeArguments[0]
        val viewBindingClass = viewBindingClassType as Class<VB>
        val inflateMethod = viewBindingClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val layoutInflater = LayoutInflater.from(context)
        mBinding = inflateMethod.invoke(null, layoutInflater, vgFragmentContainer, false) as VB
        vgRoot = mBinding?.root as ViewGroup
    }

}