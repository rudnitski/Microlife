package com.rudnitski.microlife.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Habit(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "resourceUrl") val resourceUrl: String?,
    @ColumnInfo(name = "men_microlives_per_day") val men_microlives_per_day: Int,
    @ColumnInfo(name = "women_microlives_per_day") val women_microlives_per_day: Int
)