package com.example.psychoapplication.di

import android.content.SharedPreferences
import com.example.psychoapplication.data.repository.AuthRepository
import com.example.psychoapplication.data.repository.AuthRepositoryImp
import com.example.psychoapplication.data.repository.HomeRepository
import com.example.psychoapplication.data.repository.HomeRepositoryImp
import com.example.psychoapplication.data.repository.SettingsRepository
import com.example.psychoapplication.data.repository.SettingsRepositoryImp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        appPreferences: SharedPreferences,
        gson: Gson
    ): AuthRepository {
        return AuthRepositoryImp(auth,database,appPreferences,gson)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        database: FirebaseFirestore,
    ): HomeRepository {
        return HomeRepositoryImp(database)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        appPreferences: SharedPreferences,
        gson: Gson
    ): SettingsRepository {
        return SettingsRepositoryImp(auth,database,appPreferences,gson)
    }
}