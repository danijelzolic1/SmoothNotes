package se.zolda.smoothnotes.navigation

sealed class Screen(val route: String) {
    object NotesList: Screen("notes_list")
    object WriteNote: Screen("write_note"){
        fun getNoteRoute(noteId: String?) = noteId?.let { route.plus("?write_note_id=$noteId") } ?: route
    }
}