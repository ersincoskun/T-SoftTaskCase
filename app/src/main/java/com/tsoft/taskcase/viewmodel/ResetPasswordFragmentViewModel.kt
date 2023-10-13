package com.tsoft.taskcase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsoft.taskcase.repo.UserRepository
import com.tsoft.taskcase.utils.Resource
import com.tsoft.taskcase.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordFragmentViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _sendResetPasswordMailResponse = SingleLiveEvent<Resource>()
    val sendResetPasswordMailResponse: LiveData<Resource>
        get() = _sendResetPasswordMailResponse

    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            _sendResetPasswordMailResponse.value = userRepository.sendPasswordResetEmail(email)
        }
    }

}