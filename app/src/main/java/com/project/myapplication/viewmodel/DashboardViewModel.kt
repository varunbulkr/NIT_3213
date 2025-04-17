package com.project.myapplication.viewmodel

import ApiClient
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.myapplication.ApiService
import com.project.myapplication.models.DashboardResponse
import com.project.myapplication.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _dashboardData = MutableLiveData<List<DashboardResponse>>()
    val dashboardData: LiveData<List<DashboardResponse>> get() = _dashboardData

    fun fetchDataByKeypass(id: String) {
        repository.getDataByKey(id) { result ->
            result?.let {
                _dashboardData.postValue(listOf(it))
            }
        }
    }
}