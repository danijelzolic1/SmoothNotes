package se.zolda.smoothnotes.notes.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.data.store.DataStorePreferenceKey
import se.zolda.smoothnotes.data.store.DataStoreRepository
import se.zolda.smoothnotes.data.use_case.NoteUseCases
import se.zolda.smoothnotes.notes.ui.note_list.NoteListState
import se.zolda.smoothnotes.notes.ui.note_list.NoteState
import se.zolda.smoothnotes.notes.util.*
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {
    private val _state = mutableStateOf(NoteListState())
    val state = _state

    private val noteOrder = combine(
        dataStoreRepository.noteOrderSelectedFlow,
        dataStoreRepository.noteOrderTypeSelectedFlow
    ){ noteOrder, noteOrderType ->
        return@combine getOrderWhere(noteOrder).copy(
            orderType = getOrderTypeWhere(noteOrderType)
        )
    }

    init {
        viewModelScope.launch {
            noteOrder.collect {
                fetchNotes(it)
            }
        }
    }

    private var fetchNotesJob: Job? = null
        set(value) {
            field?.cancel()
            field = value
        }

    private fun fetchNotes(noteOrder: NoteOrder){
        fetchNotesJob = viewModelScope.launch {
            noteUseCases.getNotes(noteOrder = noteOrder).collect { list ->
                _state.value = _state.value.copy(
                    state = NoteState.Data(list),
                    noteOrder = noteOrder
                )
            }
        }
    }

    fun saveNoteOrder(noteOrder: NoteOrder){
        viewModelScope.launch {
            dataStoreRepository.putInt(DataStorePreferenceKey.NOTE_ORDER_SELECTED_KEY, noteOrder.id)
        }
    }

    fun saveNoteOrderType(orderType: OrderType){
        viewModelScope.launch {
            dataStoreRepository.putInt(DataStorePreferenceKey.NOTE_ORDER_TYPE_SELECTED_KEY, orderType.id)
        }
    }
}