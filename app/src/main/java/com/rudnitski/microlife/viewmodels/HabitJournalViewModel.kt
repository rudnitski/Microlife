package com.rudnitski.microlife.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rudnitski.microlife.db.HabitAndJournal
import com.rudnitski.microlife.db.HabitsJournal
import com.rudnitski.microlife.db.HabitsJournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HabitJournalViewModel @Inject constructor(
    private val habitsJournalRepository: HabitsJournalRepository
): ViewModel() {

    val habitList: LiveData<List<HabitAndJournal>> = habitsJournalRepository.getAllJournalRecords().asLiveData()
}