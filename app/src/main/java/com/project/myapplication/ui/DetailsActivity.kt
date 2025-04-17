package com.project.myapplication.ui

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.project.myapplication.R
import com.project.myapplication.models.Entity
import com.project.myapplication.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val dashboardViewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Show toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbar.title = "Details"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Fetch selected item from dashboard
        val item: Entity? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("item", Entity::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Entity>("item")
        }

        item?.let {
            // Populate the item data to populate your UI
            findViewById<TextView>(R.id.tvArtistName).text = item.artistName
            findViewById<TextView>(R.id.tvAlbumTitle).text = item.albumTitle
            findViewById<TextView>(R.id.tvReleaseYear).text = item.releaseYear.toString()
            findViewById<TextView>(R.id.tvGenre).text = item.genre
            findViewById<TextView>(R.id.tvTrackCount).text = item.trackCount.toString()
            findViewById<TextView>(R.id.tvDescription).text = item.description
            findViewById<TextView>(R.id.tvPopularTrack).text = item.popularTrack
        }
    }
}

