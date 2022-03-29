package se.zolda.smoothnotes.ui.common

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier){
    CircularProgressIndicator(
        modifier = modifier.wrapContentSize(
            align = Alignment.Center
        ),
        color = MaterialTheme.colors.primary
    )

}