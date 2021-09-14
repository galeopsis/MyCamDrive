package com.galeopsis.mycamdrive.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galeopsis.mycamdrive.model.repository.CamDriveRepository
import com.galeopsis.mycamdrive.utils.LoadingState
import kotlinx.coroutines.launch

class MainViewModel(
    private val camDriveRepository: CamDriveRepository
) : ViewModel() {
    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    val data = camDriveRepository.data

    fun fetchData() {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                camDriveRepository.refresh()
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }

    fun login(login: String, password: String) {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                camDriveRepository.userLogin(login, password)
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }
}