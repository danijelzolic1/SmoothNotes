package se.zolda.smoothnotes.data.store

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun putString(key: String, value: String)
    suspend fun putInt(key: String, value: Int)
    suspend fun getString(key: String): String?
    suspend fun getInt(key: String): Int?

    val noteOrderSelectedFlow: Flow<Int>
    val noteOrderTypeSelectedFlow: Flow<Int>
}

object DataStorePreferenceKey{
    const val STORE_NAME_KEY = "smooth_notes_data_storage"
    const val NOTE_ORDER_SELECTED_KEY = "note_order_selected"
    const val NOTE_ORDER_TYPE_SELECTED_KEY = "note_order_type_selected"
}