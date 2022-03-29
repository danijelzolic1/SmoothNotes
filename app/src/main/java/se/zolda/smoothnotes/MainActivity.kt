package se.zolda.smoothnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import se.zolda.smoothnotes.navigation.SmoothNotesNavigation
import se.zolda.smoothnotes.ui.theme.SmoothNotesTheme
import se.zolda.smoothnotes.util.defaultMargin
import se.zolda.smoothnotes.util.smallMargin

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmoothNotesTheme {
                SmoothNotesNavigation()
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SmoothNotesTheme {
        Scaffold(
            backgroundColor = MaterialTheme.colors.background
        ) {
            SmoothNotesNavigation()
        }
    }
}