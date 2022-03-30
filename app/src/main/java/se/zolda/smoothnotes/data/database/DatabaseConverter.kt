package se.zolda.smoothnotes.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import se.zolda.smoothnotes.data.model.NoteTodo
import java.util.*

class DatabaseConverter {
    @TypeConverter
    fun toCalendar(time: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = time }

    @TypeConverter
    fun fromCalendar(cal: Calendar): Long = cal.time.time

    @TypeConverter
    fun fromTodoString(value: String?): List<NoteTodo> {
        val listType = object :
            TypeToken<ArrayList<NoteTodo?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromTodoList(list: List<NoteTodo?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}