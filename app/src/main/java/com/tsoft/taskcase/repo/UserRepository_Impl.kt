package com.tsoft.taskcase.repo

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tsoft.taskcase.utils.Resource
import com.tsoft.taskcase.utils.await
import javax.inject.Inject

class UserRepository_Impl @Inject constructor() : UserRepository {

    private val mAuth = Firebase.auth

    override suspend fun login(email: String, password: String): Resource {
        return try {
            mAuth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun register(email: String, password: String): Resource {
        return try {
            mAuth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            Resource.Error("REGISTER_NOT_SUCCESSFUL")
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Resource {
        return try {
            mAuth.sendPasswordResetEmail(email).await()
            Resource.Success(Any())
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

}

