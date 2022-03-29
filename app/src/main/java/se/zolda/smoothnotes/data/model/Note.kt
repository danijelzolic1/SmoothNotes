package se.zolda.smoothnotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import se.zolda.smoothnotes.ui.theme.*
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String?,
    val content: String,
    val dateCreated: Calendar,
    val dateChanged: Calendar,
    val colorIndex: Int = 0
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

class InvalidNoteException(message: String): Exception(message)