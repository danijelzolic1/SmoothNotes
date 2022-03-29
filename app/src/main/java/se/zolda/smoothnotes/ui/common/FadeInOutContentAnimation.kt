package se.zolda.smoothnotes.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FadeInOutContentAnimation(
    modifier: Modifier = Modifier,
    visible: Boolean,
    content: @Composable () -> Unit
){
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        content()
    }
}