package com.example.popularmoviesapp.repository.data.movie

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieDataRefreshManagerImpl @Inject constructor(context: Context) : DataRefreshManager {
    // popular movies updates daily
    private val refreshInterval: Long = TimeUnit.DAYS.toMillis(1)
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val lastDataFetchTimeKey = "lastDataFetchTime"

    override fun shouldRefresh(): Boolean {
        val lastDataFetchTime: Long = sharedPreferences.getLong(lastDataFetchTimeKey, 0)
        val currentTime = System.currentTimeMillis()
        return currentTime - lastDataFetchTime > refreshInterval
    }

    override fun updateLastRefreshTime() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val today = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()

        editor.putLong(
            lastDataFetchTimeKey,
            today
        )
        editor.apply()
    }

}
