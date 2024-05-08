package com.example.keystorepractice.data.repository

import android.content.SharedPreferences
import com.example.keystorepractice.di.Preference
import com.example.keystorepractice.di.PreferenceType
import com.example.keystorepractice.domain.PreferenceRepository
import javax.inject.Inject

class SharedPreferenceRepository @Inject constructor(
    @Preference(PreferenceType.ENCRYPTED) private val sharedPreference: SharedPreferences
) : PreferenceRepository {
    override suspend fun saveToken(token: String) = with(sharedPreference.edit()) {
        putString("token", token)
        apply()
    }
}