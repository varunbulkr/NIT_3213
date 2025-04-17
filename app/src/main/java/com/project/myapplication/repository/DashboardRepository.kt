package com.project.myapplication.repository

import com.project.myapplication.models.DashboardResponse
import com.project.myapplication.models.LoginRequest
import com.project.myapplication.models.LoginResponse

interface DashboardRepository {
    fun getDataByKey(keypass: String, callback: (DashboardResponse?) -> Unit)
    fun Login(loginRequest: LoginRequest, callback: (LoginResponse?) -> Unit)
}

