package com.project.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.myapplication.models.LoginRequest
import com.project.myapplication.models.LoginResponse
import com.project.myapplication.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> get() = _loginResponse

    fun login(request: LoginRequest) {
        repository.Login(request) { response ->
            _loginResponse.postValue(response)
        }
    }
}