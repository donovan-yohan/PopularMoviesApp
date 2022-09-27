package com.example.popularmoviesapp.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DataRefreshManagerImpl @Inject constructor(context: Context) : DataRefreshManager {
    // popular movies updates daily
    private val refreshInterval: Long = TimeUnit.DAYS.toMillis(1)
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val lastDataFetchTimeKey = "lastDataFetchTime"

    override fun shouldRefresh(): Boolean {
        val lastDataFetchTime: Long =
            sharedPreferences.getLong(lastDataFetchTimeKey, Long.MAX_VALUE)
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
