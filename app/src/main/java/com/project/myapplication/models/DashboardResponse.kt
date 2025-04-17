package com.project.myapplication.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class DashboardResponse(
    val entities: List<Entity>,
    val entityTotal: Long,
)
@Parcelize
data class Entity(
    val artistName: String,
    val albumTitle: String,
    val releaseYear: Long,
    val genre: String,
    val trackCount: Long,
    val description: String,
    val popularTrack: String,
): Parcelable