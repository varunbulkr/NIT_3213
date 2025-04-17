package com.project.myapplication.ui

import ApiClient
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.myapplication.ApiService
import com.project.myapplication.R
import com.project.myapplication.models.LoginRequest
import com.project.myapplication.models.LoginResponse
import com.project.myapplication.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val dashboardViewModel: DashboardViewModel by viewModels()
    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//Progress bar
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

//find UI controls
        val loginButton = findViewById<Button>(R.id.loginButton)
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val usernameLayout =
            findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.usernameLayout)
        val passwordLayout =
            findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.passwordLayout)

//Login click event
        loginButton.setOnClickListener {

            // Username and password should not be empty
            if (usernameInput.text.isEmpty()) {
                usernameLayout.error = "Username cannot be empty"
            } else {
                usernameLayout.error = null
            }

            if (passwordInput.text.isEmpty()) {
                passwordLayout.error = "Password cannot be empty"
            } else {
                passwordLayout.error = null
            }

            // Username and password should not be empty
            if (usernameInput.text.isNotEmpty() && passwordInput.text.isNotEmpty()) {
                if (isInternetAvailable(this)) {
                    // Internet is available
                    progressBar.visibility = View.VISIBLE

                    val request =
                        LoginRequest(usernameInput.text.toString(), passwordInput.text.toString());
                    val call = ApiClient.buildService(ApiService::class.java).login(request)
                    call.enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            progressBar.visibility = View.GONE
                            handleResponse(response)
                        }

                        // Handle API failure
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            // Handle failure
                            progressBar.visibility = View.GONE
                            val dd = call
                            val f = 0

                            // Log the error message
                            Log.e("NetworkError", "Request failed: ${t.message}")

                            // Check if the error is related to network connectivity
                            if (t is IOException) {
                                // Handle network connectivity issues
                                Log.e(
                                    "NetworkError",
                                    "Network connectivity issue: ${t.localizedMessage}"
                                )
                            } else {
                                // Handle other types of errors
                                Log.e(
                                    "NetworkError",
                                    "Conversion or unexpected error: ${t.localizedMessage}"
                                )
                            }
                        }
                    })
                } else {
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Navigate to dashboard
    fun handleResponse(response: Response<LoginResponse>) {
        if (response.isSuccessful) {
            // Login successful, navigate to dashboard
            val data = response.body()
            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
            intent.putExtra("keypass", data?.keypass)
            startActivity(intent)
        } else {
            // Handle the error
            Toast.makeText(this, "Username or Password incorrect.", Toast.LENGTH_SHORT).show()
        }
    }

    // Check status of internet
    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}