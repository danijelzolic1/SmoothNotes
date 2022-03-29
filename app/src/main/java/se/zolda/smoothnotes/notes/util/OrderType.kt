package se.zolda.smoothnotes.notes.util

sealed class OrderType(
    val id: Int
) {
    object Descending: OrderType(0)
    object Ascending: OrderType(1)
}

fun getAllOrderTypes() = listOf(
    OrderType.Descending,
    OrderType.Ascending
)

fun getOrderTypeWhere(id: Int): OrderType = when(id){
    1 -> OrderType.Ascending
    else -> OrderType.Descending
}