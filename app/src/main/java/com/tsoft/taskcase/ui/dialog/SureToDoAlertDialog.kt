package com.tsoft.taskcase.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import com.tsoft.taskcase.R
import com.tsoft.taskcase.databinding.DialogSureToDoBinding
import com.tsoft.taskcase.utils.onSingleClickListener

class SureToDoAlertDialog(private val context: Context) {

    private lateinit var yesButtonAction: () -> Unit
    private var mTitle: String? = null

    fun showDialog() {
        val builder = AlertDialog.Builder(context)

        val binding = DialogSureToDoBinding.inflate(LayoutInflater.from(context))

        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(0))

        mTitle?.let {
            binding.tvSureToDoTitle.text = it
        }

        binding.btnYes.onSingleClickListener {
            dialog.cancel()
            if (this::yesButtonAction.isInitialized) yesButtonAction.invoke()
        }

        binding.btnNo.onSingleClickListener {
            dialog.cancel()
        }

        val window = dialog.window
        window?.setBackgroundDrawable(ColorDrawable(0))
        dialog.show()
        val layoutParams = window?.attributes
        layoutParams?.let {
            val marginSize = context.resources.getDimensionPixelSize(R.dimen._24sdp)
            it.width = context.resources.displayMetrics.widthPixels - (2 * marginSize)
            it.gravity = Gravity.CENTER
            window.attributes = it
        }
    }

    fun setParameters(title: String? = null, callback: () -> Unit): SureToDoAlertDialog {
        yesButtonAction = callback
        mTitle = title
        return this
    }
}
