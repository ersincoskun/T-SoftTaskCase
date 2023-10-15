package com.tsoft.taskcase.utils

import android.util.Log
import com.tsoft.taskcase.utils.Constants.TSOFT_TASK_LOG_TAG

fun printLog(logText: String) {
    Log.d(TSOFT_TASK_LOG_TAG, logText)
}

fun printErrorLog(logText: String) {
    Log.e(TSOFT_TASK_LOG_TAG, logText)
}