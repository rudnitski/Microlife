package com.rudnitski.microlife.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(entity = Habit::class, parentColumns = ["id"], childColumns = ["habit_id"])])
class HabitsJournal (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "habit_id") val habitId: Int,
    @ColumnInfo(name = "userId") val userId: Int,
    @ColumnInfo(name = "habitUseDate") val habitUseDate: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "microlifes") val microlifes: Int
)