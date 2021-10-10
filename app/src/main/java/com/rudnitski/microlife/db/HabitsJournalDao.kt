package com.rudnitski.microlife.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitsJournalDao {
    @Query("SELECT * FROM habitsjournal ORDER BY habitUseDate")
    fun getAllRecords(): Flow<List<HabitsJournal>>

    @Transaction
    @Query("SELECT * FROM habit WHERE id IN (SELECT DISTINCT(habit_id) FROM habitsjournal)")
    fun getJournalAndHabits() : Flow<List<HabitAndJournal>>

    @Insert
    suspend fun insertJournalRecord(habitsJournal: HabitsJournal)
}