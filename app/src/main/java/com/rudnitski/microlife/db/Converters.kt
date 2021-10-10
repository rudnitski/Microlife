package com.rudnitski.microlife.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.util.*

class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar) : Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long) : Calendar = Calendar.getInstance().apply { timeInMillis = value }
}