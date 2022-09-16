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
        val lastDataFetchTime: Long = sharedPreferences.getLong(lastDataFetchTimeKey, 0)
        val currentTime = System.currentTimeMillis()

        return if (currentTime - lastDataFetchTime > refreshInterval) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putLong(
                lastDataFetchTimeKey,
                LocalDate.now().atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000
            )
            editor.apply()
            true
        } else {
            false
        }
    }

}
