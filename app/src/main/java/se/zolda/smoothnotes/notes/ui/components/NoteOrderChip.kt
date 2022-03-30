package se.zolda.smoothnotes.notes.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import se.zolda.smoothnotes.R
import se.zolda.smoothnotes.notes.util.NoteOrder
import se.zolda.smoothnotes.notes.util.OrderType
import se.zolda.smoothnotes.ui.theme.Color_Dark_Text
import se.zolda.smoothnotes.ui.theme.Color_Unselected
import se.zolda.smoothnotes.util.midMargin
import se.zolda.smoothnotes.util.miniMargin
import se.zolda.smoothnotes.util.smallMargin

@ExperimentalMaterialApi
@Composable
fun NoteOrderChip(
    noteOrder: NoteOrder,
    isSelected: Boolean,
    onSelectionChanged: (NoteOrder) -> Unit,
) {
    val shape = CircleShape
    Surface(
        shape =
            shape,
        color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant,
        onClick = {
            onSelectionChanged(noteOrder)
        }
    ) {
        Text(
            text = when (noteOrder) {
                is NoteOrder.Title -> stringResource(id = R.string.sort_order_title)
                is NoteOrder.DateCreated -> stringResource(id = R.string.sort_order_date_created)
                is NoteOrder.DateChanged -> stringResource(id = R.string.sort_order_date_changed)
            }.uppercase(),
            style = MaterialTheme.typography.overline.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical = smallMargin, horizontal = midMargin),
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun NoteOrderTypeChip(
    orderType: OrderType,
    onSelectionChanged: (OrderType) -> Unit,
) {
    val shape = CircleShape
    Surface(
        shape = shape,
        color = MaterialTheme.colors.primary,
        onClick = {
            onSelectionChanged(
                when (orderType) {
                    OrderType.Descending -> OrderType.Ascending
                    OrderType.Ascending -> OrderType.Descending
                }
            )
        }
    ){
        Icon(
            when (orderType) {
                OrderType.Descending -> Icons.Filled.ArrowDownward
                OrderType.Ascending -> Icons.Filled.ArrowUpward
            },
            "contentDescription",
            modifier = Modifier
                .size(28.dp)
                .padding(miniMargin),
        )
    }
}