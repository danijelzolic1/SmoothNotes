package se.zolda.smoothnotes.data.database

import androidx.room.TypeConverter
import java.util.*

class DatabaseConverter {
    @TypeConverter
    fun toCalendar(time: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = time }

    @TypeConverter
    fun fromCalendar(cal: Calendar): Long = cal.time.time
}