package com.tsoft.taskcase.utils

import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import com.tsoft.taskcase.R

class PasswordEyeIconAction(private val passwordET: EditText, private val clickAreaView: ImageView) {
    private var isShowingPassword = false

    init {
        clickAreaView.setImageResource(R.drawable.ic_open_eye)
        passwordET.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        clickAreaView.setOnClickListener {
            eyeClicked()
        }
    }

    private fun eyeClicked() {
        if (isShowingPassword) {
            //close password
            clickAreaView.setImageResource(R.drawable.ic_open_eye)
            isShowingPassword = !isShowingPassword
            passwordET.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            passwordET.setSelection(passwordET.length())
        } else {
            //open password
            clickAreaView.setImageResource(R.drawable.ic_closed_eye)
            isShowingPassword = !isShowingPassword
            passwordET.inputType =
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            passwordET.setSelection(passwordET.length())
        }
    }

}