package com.tsoft.taskcase.repo

import com.tsoft.taskcase.utils.Resource


interface UserRepository {
    suspend fun login(email: String, password: String): Resource
    suspend fun register(email: String, password: String): Resource
    suspend fun sendPasswordResetEmail(email: String): Resource
    suspend fun deleteUser(): Resource
}