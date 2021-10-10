package com.rudnitski.microlife.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rudnitski.microlife.db.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HabitRepository,
    private val userRepository: UserRepository,
    private val habitsJournalRepository: HabitsJournalRepository
) : ViewModel() {

    val allHabits: LiveData<List<Habit>> = repository.getAllHabits().asLiveData()
    val user: LiveData<User> = userRepository.getUser().asLiveData()

    fun setGenderToUser(gender: Gender) {
        viewModelScope.launch {
        }
    }

    fun addHabitToRepo(habitsJournal: HabitsJournal) {
        viewModelScope.launch {
            habitsJournalRepository.insertRecord(habitsJournal)
        }
    }
}