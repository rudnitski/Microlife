package com.rudnitski.microlife.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepository @Inject constructor(private val habitDao: HabitDao) {

    fun getAllHabits() = habitDao.getAll()
//    val allHabits: Flow<List<Habit>> = habitDao.getAll()

    @WorkerThread
    suspend fun insertAll(habits: List<Habit>) {
        habitDao.insertAll(habits)
    }
}