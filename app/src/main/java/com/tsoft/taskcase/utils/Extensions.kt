package com.tsoft.taskcase.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tsoft.taskcase.R
import es.dmoral.toasty.Toasty

fun View.onSingleClickListener(listener: View.OnClickListener) {
    this.setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(view: View?) {
            listener.onClick(view)
        }
    })
}

fun Context?.showErrorToasty(message: String) {
    this?.let { safeContext ->
        Toasty.error(safeContext, message, Toasty.LENGTH_LONG).show()
    }
}

fun Context?.showSuccessToasty(message: String) {
    this?.let { safeContext ->
        Toasty.success(safeContext, message, Toasty.LENGTH_LONG).show()
    }
}

fun Context?.showInfoToasty(message: String) {
    this?.let { safeContext ->
        Toasty.info(safeContext, message, Toasty.LENGTH_LONG).show()
    }
}

fun ImageView.loadGlideImage(url: String) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.load_image_place_holder_icon)
        .error(R.drawable.load_image_error_place_holder_icon)
    Glide.with(this.context).load(url).apply(options).into(this)
}

fun Context.showSoftKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.applicationWindowToken, 0)
}


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.remove() {
    this.visibility = View.GONE
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

@SuppressLint("ClickableViewAccessibility")
fun View.setClickEffect() {
    this.setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.alpha = 0.2f
                v.animate()
                    .scaleX(1.3f)  // 1.1 katına büyüt
                    .scaleY(1.3f)
                    .setDuration(200)  // Bu animasyon için süre (milisaniye cinsinden)
                    .start()
                false
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                v.alpha = 1f
                v.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(200)
                    .start()
                false
            }
            else -> false
        }
    }
}