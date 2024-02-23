package ru.radzze.data.onboarding

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class OnboardingDataRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
):OnboardingDataRepository {

    companion object PreferenceKeys {
        val isOnboardingChecked: Preferences.Key<Boolean> = booleanPreferencesKey("is_tutor_checked")
    }

    override suspend fun saveOnboardingState() {
        dataStore.edit {
            it[isOnboardingChecked] = true
        }
    }

    override suspend fun getOnboardingState(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[isOnboardingChecked] ?: false
    }
}