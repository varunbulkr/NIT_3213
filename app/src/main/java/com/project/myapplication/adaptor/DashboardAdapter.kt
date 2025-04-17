package com.project.myapplication.adaptor


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.myapplication.R
import com.project.myapplication.models.Entity
import javax.inject.Inject

class DashboardAdapter @Inject constructor(
    private val items: List<Entity>,
    private val onItemClick: (Entity) -> Unit
) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val artistName: TextView = view.findViewById(R.id.artistName)
        private val albumTitle: TextView = view.findViewById(R.id.albumTitle)
        private val releaseYear: TextView = view.findViewById(R.id.releaseYear)
        private val genre: TextView = view.findViewById(R.id.genre)
        private val trackCount: TextView = view.findViewById(R.id.trackCount)
        private val popularTrack: TextView = view.findViewById(R.id.popularTrack)

        // Bind data
        fun bind(item: Entity) {
            artistName.text = item.artistName
            albumTitle.text = item.albumTitle
            releaseYear.text = item.releaseYear.toString()
            genre.text = item.genre
            trackCount.text = item.trackCount.toString()
            popularTrack.text = item.popularTrack

// add click event
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
