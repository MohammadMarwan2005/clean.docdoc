package com.alaishat.mohammad.clean.docdoc.data.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alaishat.mohammad.clean.docdoc.domain.repo.UserLocalDataRepo
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

/**
 * Created by Mohammad Al-Aishat on Apr/13/2025.
 * Clean DocDoc Project.
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")
private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
private val HAS_ONBOARDED_KEY = booleanPreferencesKey("has_onboarded")

class UserLocalDataRepoImpl(
    private val context: Context
): UserLocalDataRepo {
    override fun getTokenFlow(): Flow<String?> {
        return context.dataStore.data.map { it[ACCESS_TOKEN_KEY] }
    }

    override suspend fun getTokenAsString(): String? {
        return context.dataStore.data.firstOrNull()?.get(ACCESS_TOKEN_KEY)
    }

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { it[ACCESS_TOKEN_KEY] = token }
    }

    override suspend fun setOnboarded() {
        context.dataStore.edit { it[HAS_ONBOARDED_KEY] = true }
    }

    override suspend fun getHasOnboarded(): Boolean? {
        return context.dataStore.data.firstOrNull()?.get(HAS_ONBOARDED_KEY)
    }
}
