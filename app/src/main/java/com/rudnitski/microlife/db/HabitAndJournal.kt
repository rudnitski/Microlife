package com.rudnitski.microlife.db

import androidx.room.Embedded
import androidx.room.Relation

data class HabitAndJournal(
    @Embedded
    val habit: Habit,

    @Relation(parentColumn = "id", entityColumn = "habit_id")
    val habitsJournal: List<HabitsJournal> = emptyList()
)
