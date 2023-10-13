package com.tsoft.taskcase.repo

import android.app.Activity
import com.tsoft.taskcase.utils.Resource


interface UserRepository {
    suspend fun login(email: String, password: String): Resource
}