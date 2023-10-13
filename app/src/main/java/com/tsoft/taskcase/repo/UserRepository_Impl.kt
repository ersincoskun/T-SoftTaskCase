package com.tsoft.taskcase.repo

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tsoft.taskcase.utils.Resource
import com.tsoft.taskcase.utils.await
import javax.inject.Inject


class UserRepository_Impl @Inject constructor() : UserRepository {

    private val mAuth = Firebase.auth

    override suspend fun login(email: String, password: String): Resource {
        return try {
            val authResult = mAuth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user
            if (user != null) {
                if (user.isEmailVerified) {
                    Resource.Success(Any())
                } else {
                    Resource.Error("MAIL_NOT_VERIFIED")
                }
            } else {
                Resource.Error("USER_NULL")
            }
        } catch (e: FirebaseAuthException) {
            Resource.Error(e.errorCode)
        } catch (e: Exception) {
            Resource.Error("LOGIN_NOT_SUCCESSFUL")
        }
    }

}

