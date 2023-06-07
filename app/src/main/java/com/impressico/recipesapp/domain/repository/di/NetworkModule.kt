package com.impressico.recipesapp.domain.repository.di

import com.google.gson.GsonBuilder
import com.impressico.recipesapp.data.AuthInterceptor
import com.impressico.recipesapp.data.NetworkConstants.BASE_URL
import com.impressico.recipesapp.data.NetworkConstants.CONNECT_TIMEOUT
import com.impressico.recipesapp.data.NetworkConstants.READ_TIMEOUT
import com.impressico.recipesapp.data.NetworkConstants.WRITE_TIMEOUT
import com.impressico.recipesapp.data.remote.apiservice.TMDBMovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(authInterceptor: AuthInterceptor):OkHttpClient{
        val loggingInterceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient().newBuilder().addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit.Builder {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
    }
    @Provides
    @Singleton
    fun provideMovieAPI(retrofit: Retrofit.Builder): TMDBMovieApiService {
        return retrofit.build().create(TMDBMovieApiService::class.java)
    }

}