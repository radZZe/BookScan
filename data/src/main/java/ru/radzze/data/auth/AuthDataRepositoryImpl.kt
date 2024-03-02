package ru.radzze.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import ru.radzze.bookscan.glue.auth.AuthDataRepository
import javax.inject.Inject

class AuthDataRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AuthDataRepository {

    companion object PreferenceKeys {
        val isAuthenticated : Preferences.Key<Boolean> = booleanPreferencesKey("is_authenticated")
    }

    override suspend fun saveAuthState() {
        dataStore.edit {
            it[isAuthenticated] = true
        }
    }

    override suspend fun getAuthState(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[isAuthenticated] ?: false
    }
}