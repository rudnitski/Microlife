package com.rudnitski.microlife.db

import androidx.annotation.WorkerThread
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitsJournalRepository @Inject constructor(private val habitsJournalDao: HabitsJournalDao) {

    fun getAllJournalRecords() = habitsJournalDao.getJournalAndHabits()

    @WorkerThread
    suspend fun insertRecord(habitsJournal: HabitsJournal) {
        habitsJournalDao.insertJournalRecord(habitsJournal)
    }
}