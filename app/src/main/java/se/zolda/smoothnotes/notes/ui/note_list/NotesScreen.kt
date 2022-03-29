package se.zolda.smoothnotes.notes.ui.note_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import se.zolda.smoothnotes.R
import se.zolda.smoothnotes.navigation.Screen
import se.zolda.smoothnotes.navigation.SmoothNotesNavigation
import se.zolda.smoothnotes.notes.ui.components.NoteListItem
import se.zolda.smoothnotes.notes.util.NoteOrder
import se.zolda.smoothnotes.notes.util.OrderType
import se.zolda.smoothnotes.notes.util.getAllOrders
import se.zolda.smoothnotes.notes.viewmodel.NoteListViewModel
import se.zolda.smoothnotes.ui.common.LoadingIndicator
import se.zolda.smoothnotes.util.defaultMargin
import se.zolda.smoothnotes.util.smallMargin

@ExperimentalMaterialApi
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.WriteNote.getNoteRoute(null))
                }
            ) {
                Icon(Icons.Filled.Add,"")
            }
        }
    ) {
        val state by viewModel.state
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            AnimatedVisibility(
                visible = state.state is NoteState.Loading,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                LoadingIndicator(modifier = Modifier.fillMaxSize())
            }
            AnimatedVisibility(
                visible = state.state is NoteState.Data,
                enter = fadeIn(animationSpec = tween(durationMillis = 2000)),
                exit = fadeOut(animationSpec = tween(durationMillis = 1000)),
            ) {
                NoteList(
                    navController = navController,
                    state = state,
                    onSelectionOrder = {
                        viewModel.saveNoteOrder(it)
                    },
                    onSelectionOrderType = {
                        viewModel.saveNoteOrderType(it)
                    }
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun NoteList(
    navController: NavController,
    state: NoteListState,
    onSelectionOrder: (NoteOrder) -> Unit,
    onSelectionOrderType: (OrderType) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        val items = (state.state as NoteState.Data).items
        val listState = rememberLazyGridState()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(defaultMargin),
            verticalArrangement = Arrangement.spacedBy(defaultMargin),
            contentPadding = PaddingValues(bottom = 76.dp, top = 82.dp),
            modifier = Modifier.padding(horizontal = defaultMargin),
            state = listState
        ) {
            items(items) { note ->
                NoteListItem(note = note,
                    noteOrder = state.noteOrder,
                    onClick = {
                        navController.navigate(Screen.WriteNote.getNoteRoute(it.id))
                    })
            }
        }
        AnimatedVisibility(
            visible = listState.firstVisibleItemIndex != 0,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            ScrollArrow(
                icon = Icons.Filled.ArrowUpward,
                onClick = {
                    coroutineScope.launch { listState.animateScrollToItem(0) }
                }
            )
        }
        NoteOrderChipGroup(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colors.background.copy(alpha = 0.95f)),
            orders = getAllOrders(),
            selectedOrder = state.noteOrder,
            selectedOrderType = state.noteOrder.orderType,
            onSelectionNoteOrder = {
                onSelectionOrder(it)
            },
            onSelectionOrderType = {
                onSelectionOrderType(it)
            }
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun ScrollArrow(
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .padding(top = 72.dp)
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.TopCenter),
        shape = CircleShape,
        color = MaterialTheme.colors.primary,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = defaultMargin, vertical = smallMargin),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(smallMargin)
        ) {
            Icon(
                imageVector = icon,
                "contentDescription",
                modifier = Modifier
                    .size(22.dp)
            )
            Text(
                text = stringResource(id = R.string.scroll_to_top).uppercase(),
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}