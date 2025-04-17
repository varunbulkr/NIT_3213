package com.project.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.project.myapplication.ApiService
import com.project.myapplication.R
import com.project.myapplication.adaptor.DashboardAdapter
import com.project.myapplication.models.DashboardResponse
import com.project.myapplication.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Set progress bar
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        //Set toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbar.title = "Dashboard"
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        progressBar.visibility = View.VISIBLE

        // Fetch keypass from login screen
        val keypass = intent.getStringExtra("keypass")

        var keypassValue = "";
        keypassValue = keypass ?: "error"
        val call = ApiClient.buildService(ApiService::class.java).getDataByKey(keypassValue)
        call.enqueue(object : Callback<DashboardResponse> {
            override fun onResponse(call: Call<DashboardResponse>, response: Response<DashboardResponse>) {
                progressBar.visibility = View.GONE
                handleResponse(response, recyclerView)
            }

            // Handle API failure
            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                // Handle failure
                progressBar.visibility = View.GONE

                // Log the error message
                Log.e("NetworkError", "Request failed: ${t.message}")

                // Check if the error is related to network connectivity
                if (t is IOException) {
                    // Handle network connectivity issues
                    Log.e("NetworkError", "Network connectivity issue: ${t.localizedMessage}")
                } else {
                    // Handle other types of errors
                    Log.e("NetworkError", "Conversion or unexpected error: ${t.localizedMessage}")
                }
            }
        })
    }

    // Handle response from API
    fun handleResponse(response: Response<DashboardResponse>, recyclerView: RecyclerView) {
        if (response.isSuccessful) {
            val items = response.body()!!.entities;

            val adapter = DashboardAdapter(items) { selectedItem ->
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("item", selectedItem)
                startActivity(intent)
            }

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }else{
            Toast.makeText(this, "Some error occurred.", Toast.LENGTH_SHORT).show()
        }
    }
}
