package com.example.popularmoviesapp.service

import com.example.popularmoviesapp.repository.network.ApiConstants.IMG_BASE_URL
import com.example.popularmoviesapp.repository.network.ApiConstants.IMG_SIZE_ORG
import java.text.NumberFormat
import java.util.*

object Helpers {
    fun imgUrlBuilder(imgPath: String?, size: String? = IMG_SIZE_ORG): String? {
        imgPath?.let {
            return IMG_BASE_URL + (size ?: "") + it
        }
        return null
    }

    fun toCurrencyString(value: Double?): String {
        val format = NumberFormat.getCurrencyInstance(Locale.US)
        format.maximumFractionDigits = 0
        return if (value != null) {
            "${format.format(value)} USD"
        } else {
            "-"
        }
    }
}