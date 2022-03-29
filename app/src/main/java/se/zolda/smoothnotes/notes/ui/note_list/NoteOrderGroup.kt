package se.zolda.smoothnotes.notes.ui.note_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import se.zolda.smoothnotes.notes.ui.components.NoteOrderChip
import se.zolda.smoothnotes.notes.ui.components.NoteOrderTypeChip
import se.zolda.smoothnotes.notes.util.NoteOrder
import se.zolda.smoothnotes.notes.util.OrderType
import se.zolda.smoothnotes.ui.theme.Color_Separator
import se.zolda.smoothnotes.util.smallMargin

@ExperimentalMaterialApi
@Composable
fun NoteOrderChipGroup(
    modifier: Modifier,
    orders: List<NoteOrder>,
    selectedOrder: NoteOrder,
    selectedOrderType: OrderType,
    onSelectionNoteOrder: (NoteOrder) -> Unit,
    onSelectionOrderType: (OrderType) -> Unit,
) {
    Row(
        modifier = modifier.padding(smallMargin),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(smallMargin)
        ) {
            items(orders) { noteOrder ->
                NoteOrderChip(
                    noteOrder = noteOrder,
                    isSelected = selectedOrder.id == noteOrder.id,
                    onSelectionChanged = {
                        onSelectionNoteOrder(noteOrder)
                    },
                )
            }
        }
        Spacer(
            modifier = Modifier
                .padding(start = smallMargin)
                .width(1.dp)
                .height(20.dp)
                .background(color = Color_Separator)
        )
        NoteOrderTypeChip(
            orderType = selectedOrderType,
            onSelectionChanged = {
            onSelectionOrderType(it)
        })
    }
}