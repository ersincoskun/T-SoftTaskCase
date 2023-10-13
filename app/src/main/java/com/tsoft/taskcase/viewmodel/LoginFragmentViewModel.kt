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
class LoginFragmentViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginResponse = SingleLiveEvent<Resource>()
    val loginResponse: LiveData<Resource>
        get() = _loginResponse

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResponse.value = userRepository.login(email, password)
        }
    }

}