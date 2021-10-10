package com.rudnitski.microlife.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getAll(): Flow<List<Habit>>

    @Query("SELECT * FROM habit WHERE id IN (:habitIds)")
    fun loadAllByIds(habitIds: IntArray): List<Habit>

    @Insert
    suspend fun insertAll(habits: List<Habit>)
}