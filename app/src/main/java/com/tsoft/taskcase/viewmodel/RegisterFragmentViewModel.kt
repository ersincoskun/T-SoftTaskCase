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
class RegisterFragmentViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registerResponse = SingleLiveEvent<Resource>()
    val registerResponse: LiveData<Resource>
        get() = _registerResponse

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _registerResponse.value = userRepository.register(email, password)
        }
    }

}