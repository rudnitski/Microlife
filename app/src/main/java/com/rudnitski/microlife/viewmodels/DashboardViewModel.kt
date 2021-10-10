package com.rudnitski.microlife.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rudnitski.microlife.db.HabitsJournalRepository
import com.rudnitski.microlife.db.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val habitsJournalRepository: HabitsJournalRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _selected = MutableLiveData<LocalDate>().apply {
        value = LocalDate.now()
    }
    val selected: MutableLiveData<LocalDate> = _selected

}