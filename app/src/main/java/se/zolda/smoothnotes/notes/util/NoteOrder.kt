package se.zolda.smoothnotes.notes.util

import se.zolda.smoothnotes.data.model.Note

sealed class NoteOrder(val id: Int, var orderType: OrderType) {
    class DateChanged(id: Int = 0, orderType: OrderType = OrderType.Descending): NoteOrder(id, orderType)
    class DateCreated(id: Int = 1, orderType: OrderType = OrderType.Descending): NoteOrder(id, orderType)
    class Title(id: Int = 2, orderType: OrderType = OrderType.Descending): NoteOrder(id, orderType)

    fun copy(orderType: OrderType): NoteOrder {
        return when(this) {
            is Title -> Title(orderType = orderType)
            is DateCreated -> DateCreated(orderType = orderType)
            is DateChanged -> DateChanged(orderType = orderType)
        }
    }
}

sealed class NoteEvent {
    data class Order(val noteOrder: NoteOrder): NoteEvent()
    data class DeleteNote(val note: Note): NoteEvent()
    object RestoreNote: NoteEvent()
}

fun getAllOrders() = listOf(
    NoteOrder.DateChanged(orderType = OrderType.Descending),
    NoteOrder.DateCreated(orderType = OrderType.Descending),
    NoteOrder.Title(orderType = OrderType.Descending),
)

fun getOrderWhere(id: Int): NoteOrder = when(id){
    1 -> NoteOrder.DateCreated()
    2 -> NoteOrder.Title()
    else -> NoteOrder.DateChanged()
}