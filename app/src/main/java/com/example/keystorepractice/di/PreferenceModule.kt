package com.example.keystorepractice.di

import android.content.Context
import android.content.SharedPreferences
import com.example.keystorepractice.data.repository.SharedPreferenceRepository
import com.example.keystorepractice.domain.PreferenceRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PreferenceModule {
    @Binds
    fun bindPreferenceRepository(impl: SharedPreferenceRepository): PreferenceRepository

    companion object {
        @Provides
        @Singleton
        fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
            context.getSharedPreferences("shared_preference", Context.MODE_PRIVATE)
    }
}