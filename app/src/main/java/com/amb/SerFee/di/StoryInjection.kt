package com.amb.SerFee.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.amb.SerFee.data.networking.api.ApiConfig
import com.amb.SerFee.data.preference.UserPreferences
import com.amb.SerFee.data.repository.StoryRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("DailySnap2")

object StoryInjection {
    fun provideRepository(context: Context): StoryRepository {
        val dataStore = context.dataStore
        val preferences = UserPreferences.getInstance(dataStore)
        val apiService = ApiConfig.getApiClient()
        return StoryRepository.getInstance(preferences, apiService)
    }
}