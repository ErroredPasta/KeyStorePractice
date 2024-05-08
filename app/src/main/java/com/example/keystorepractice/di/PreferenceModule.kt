package com.example.keystorepractice.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme
import androidx.security.crypto.MasterKeys
import com.example.keystorepractice.data.repository.SharedPreferenceRepository
import com.example.keystorepractice.domain.PreferenceRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PreferenceModule {
    @Binds
    fun bindPreferenceRepository(impl: SharedPreferenceRepository): PreferenceRepository

    companion object {
        @Provides
        @Singleton
        @Preference(PreferenceType.PLAIN)
        fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
            context.getSharedPreferences("shared_preference", Context.MODE_PRIVATE)

        @Provides
        @MasterKey
        fun provideMasterKey() = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        @Provides
        @Singleton
        @Preference(PreferenceType.ENCRYPTED)
        fun provideEncryptedSharedPreference(
            @MasterKey masterKey: String,
            @ApplicationContext context: Context,
        ) = EncryptedSharedPreferences.create(
            "encrypted_shared_preference",
            masterKey,
            context,
            PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MasterKey

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Preference(val type: PreferenceType)

enum class PreferenceType { PLAIN, ENCRYPTED }