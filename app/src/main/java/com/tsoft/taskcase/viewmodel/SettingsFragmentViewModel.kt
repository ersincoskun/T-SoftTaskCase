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
class SettingsFragmentViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _deleteAccountResponse = SingleLiveEvent<Resource>()
    val deleteAccountResponse: LiveData<Resource>
        get() = _deleteAccountResponse

    fun deleteAccount() {
        viewModelScope.launch {
            _deleteAccountResponse.value = userRepository.deleteUser()
        }
    }

}