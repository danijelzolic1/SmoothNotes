package se.zolda.smoothnotes.notes.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import se.zolda.smoothnotes.data.model.Note
import se.zolda.smoothnotes.data.model.NoteTodo
import se.zolda.smoothnotes.data.model.NoteType
import se.zolda.smoothnotes.data.store.DataStorePreferenceKey
import se.zolda.smoothnotes.data.store.DataStoreRepository
import se.zolda.smoothnotes.data.use_case.NoteUseCases
import se.zolda.smoothnotes.notes.ui.note_list.NoteListState
import se.zolda.smoothnotes.notes.ui.note_list.NoteState
import se.zolda.smoothnotes.notes.util.NoteOrder
import se.zolda.smoothnotes.notes.util.OrderType
import se.zolda.smoothnotes.notes.util.getOrderTypeWhere
import se.zolda.smoothnotes.notes.util.getOrderWhere
import java.util.*
import javax.inject.Inject

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

                if(list.isEmpty()){
                    delay(3000)
                    val mutableList = mutableListOf<Note>()
                    var calendar = Calendar.getInstance()
                    calendar.set(Calendar.DAY_OF_YEAR, (1..365).random())
                    calendar.set(Calendar.YEAR, 2021)
                    var note = Note(
                        title = "Shopping list",
                        dateChanged = calendar,
                        dateCreated = calendar,
                        colorIndex = 0,
                        todoContent = listOf(
                            NoteTodo(
                                content = "Apple",
                                isChecked = true
                            ),
                            NoteTodo(
                                content = "Eggs",
                                isChecked = true
                            ),
                            NoteTodo(
                                content = "Milk",
                                isChecked = false
                            ),
                            NoteTodo(
                                content = "Bread",
                                isChecked = false
                            ),
                            NoteTodo(
                                content = "Juice",
                                isChecked = true
                            ),
                            NoteTodo(
                                content = "Banana",
                                isChecked = false
                            ),
                            NoteTodo(
                                content = "Jam",
                                isChecked = false
                            ),
                            NoteTodo(
                                content = "Oats",
                                isChecked = true
                            )
                        ),
                        noteType = NoteType.TODO,
                    )
                    mutableList.add(note)

                    calendar = Calendar.getInstance()
                    calendar.set(Calendar.DAY_OF_YEAR, (1..365).random())
                    calendar.set(Calendar.YEAR, 2021)
                    note = Note(
                        title = "Pancake Recipe",
                        textContent = "Step 1\nIn a large bowl, sift together the flour, baking powder, salt and sugar. Make a well in the center and pour in the milk, egg and melted butter; mix until smooth.\nStep 2\nHeat a lightly oiled griddle or frying pan over medium-high heat. Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake. Brown on both sides and serve hot.",
                        dateChanged = calendar,
                        dateCreated = calendar,
                        colorIndex = 2,
                    )
                    mutableList.add(note)

                    calendar = Calendar.getInstance()
                    calendar.set(Calendar.DAY_OF_YEAR, (1..365).random())
                    calendar.set(Calendar.YEAR, 2021)
                    note = Note(
                        title = "School",
                        dateChanged = calendar,
                        dateCreated = calendar,
                        colorIndex = 5,
                        noteType = NoteType.TODO,
                        todoContent = listOf(
                            NoteTodo(
                                content = "Homework",
                                isChecked = true
                            ),
                            NoteTodo(
                                content = "Prepare PowerPoint",
                                isChecked = false
                            ),
                            NoteTodo(
                                content = "Math exercise",
                                isChecked = false
                            ),
                        )
                    )
                    mutableList.add(note)
                    noteUseCases.insertNotes(mutableList)
                }
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