package se.zolda.smoothnotes.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import se.zolda.smoothnotes.notes.ui.new_note.WriteNoteScreen
import se.zolda.smoothnotes.notes.ui.note_list.NotesScreen

@ExperimentalMaterialApi
@Composable
fun SmoothNotesNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.NotesList.route) {
        composable(
            route = Screen.NotesList.route
        ) {
            NotesScreen(navController = navController)
        }
        composable(
            route = Screen.WriteNote.route.plus("?write_note_id={write_note_id}"),
            arguments = listOf(navArgument("write_note_id") {
                nullable = true
                defaultValue = null
                type = NavType.StringType
            })
        ) {
            WriteNoteScreen(navController = navController)
        }
    }
}