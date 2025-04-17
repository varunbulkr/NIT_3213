package com.project.myapplication
import com.project.myapplication.models.DashboardResponse
import com.project.myapplication.models.LoginRequest
import com.project.myapplication.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("sydney/auth")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("dashboard/{keypass}")
    fun getDataByKey(@Path("keypass") keypass: String): Call<DashboardResponse>
}