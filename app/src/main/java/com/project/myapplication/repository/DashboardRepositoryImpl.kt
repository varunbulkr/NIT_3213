package com.project.myapplication.repository

import com.project.myapplication.ApiService
import com.project.myapplication.models.DashboardResponse
import com.project.myapplication.models.LoginRequest
import com.project.myapplication.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DashboardRepository {

    override fun getDataByKey(
        keypass: String,
        callback: (DashboardResponse?) -> Unit
    ) {
        apiService.getDataByKey(keypass).enqueue(object : Callback<DashboardResponse> {
            override fun onResponse(
                call: Call<DashboardResponse>,
                response: Response<DashboardResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                callback(null)
            }
        })


    }

    override fun Login(loginRequest: LoginRequest, callback: (LoginResponse?) -> Unit) {
        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            private fun callback(body: LoginResponse?) {

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}


