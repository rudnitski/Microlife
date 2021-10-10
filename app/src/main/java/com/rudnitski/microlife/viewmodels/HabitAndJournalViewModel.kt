package com.rudnitski.microlife.viewmodels

import com.rudnitski.microlife.db.HabitAndJournal

class HabitAndJournalViewModel(journal: HabitAndJournal) {
    private val habit = checkNotNull(journal.habit)
    private val journalEntry = journal.habitsJournal[0]

    val habitName
        get() = habit.title
    val habitImage
        get() = habit.resourceUrl
    val habitId
        get() = journalEntry.habitId
    val microlifes
        get() = journalEntry.microlifes
}