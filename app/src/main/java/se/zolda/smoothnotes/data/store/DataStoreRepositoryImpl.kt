package se.zolda.smoothnotes.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import se.zolda.smoothnotes.data.store.DataStorePreferenceKey.NOTE_ORDER_SELECTED_KEY
import se.zolda.smoothnotes.data.store.DataStorePreferenceKey.NOTE_ORDER_TYPE_SELECTED_KEY
import se.zolda.smoothnotes.data.store.DataStorePreferenceKey.STORE_NAME_KEY
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME_KEY)

class DataStoreRepositoryImpl @Inject constructor(
    private val context: Context
) : DataStoreRepository {

    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey]
    }

    override suspend fun getInt(key: String): Int? {
        val preferencesKey = intPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey]
    }

    private val noteOrderSelected = intPreferencesKey(NOTE_ORDER_SELECTED_KEY)
    override val noteOrderSelectedFlow: Flow<Int>
        get() = context.dataStore.data
            .map { preferences ->
                preferences[noteOrderSelected] ?: 0
            }

    private val noteOrderTypeSelected = intPreferencesKey(NOTE_ORDER_TYPE_SELECTED_KEY)
    override val noteOrderTypeSelectedFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[noteOrderTypeSelected] ?: 0
        }
}