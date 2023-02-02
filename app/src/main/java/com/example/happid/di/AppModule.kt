package com.example.happid.di

import com.example.happid.data.remote.RemoteDataSource
import com.example.happid.data.remote.RemoteService
import com.example.happid.data.repository.RemoteRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRemoteService(retrofit: Retrofit): RemoteService = retrofit.create(RemoteService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(remoteService: RemoteService) = RemoteDataSource(remoteService)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource) = RemoteRepository(remoteDataSource)
}