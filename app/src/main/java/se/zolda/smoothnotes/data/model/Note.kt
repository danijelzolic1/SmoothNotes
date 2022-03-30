package se.zolda.smoothnotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import se.zolda.smoothnotes.ui.theme.*
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val textContent: String = "",
    val todoContent: List<NoteTodo> = listOf(),
    val dateCreated: Calendar,
    val dateChanged: Calendar,
    val colorIndex: Int = 0,
    val noteType: NoteType = NoteType.DEFAULT
){
    companion object{
        val colors = listOf(
            Color_Note_Purple,
            Color_Note_Red,
            Color_Note_Blue,
            Color_Note_Mint,
            Color_Note_Orange,
            Color_Note_Yellow,
        )
    }
}

data class NoteTodo(
    val id: String = UUID.randomUUID().toString(),
    val content: String = "",
    val isChecked: Boolean = false
)

enum class NoteType{
    DEFAULT, TODO
}