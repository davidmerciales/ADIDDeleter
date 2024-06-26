package com.example.adiddeleter.data.di

import com.example.adiddeleter.data.remote.ApiService
import com.example.adiddeleter.data.repository.DeleteAdidRepositoryImpl
import com.example.adiddeleter.data.repository.GetAdidRepositoryImpl
import com.example.adiddeleter.domain.repository.DeleteAdidRepository
import com.example.adiddeleter.domain.repository.GetAdidRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesGetAdidRepository(apiService: ApiService): GetAdidRepository{
        return GetAdidRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesDeleteAdidRepository(apiService: ApiService): DeleteAdidRepository{
        return DeleteAdidRepositoryImpl(apiService)
    }
}