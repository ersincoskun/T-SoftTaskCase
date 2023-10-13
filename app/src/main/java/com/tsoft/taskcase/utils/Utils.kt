package com.tsoft.taskcase.utils

import android.util.Log
import com.tsoft.taskcase.utils.Constants.MEET_NOW_LOG_TAG

fun printLog(logText: String) {
    Log.d(MEET_NOW_LOG_TAG, logText)
}

fun printErrorLog(logText: String) {
    Log.e(MEET_NOW_LOG_TAG, logText)
}