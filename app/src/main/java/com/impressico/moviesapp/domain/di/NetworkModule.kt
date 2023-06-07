package com.impressico.moviesapp.domain.di

import com.google.gson.GsonBuilder
import com.impressico.moviesapp.data.AuthInterceptor
import com.impressico.moviesapp.data.NetworkConstants.BASE_URL
import com.impressico.moviesapp.data.NetworkConstants.CONNECT_TIMEOUT
import com.impressico.moviesapp.data.NetworkConstants.READ_TIMEOUT
import com.impressico.moviesapp.data.NetworkConstants.WRITE_TIMEOUT
import com.impressico.moviesapp.data.PopularRemoteDataSource
import com.impressico.moviesapp.data.remote.PopularRemoteDataSourceImpl
import com.impressico.moviesapp.data.remote.RemoteDataSource
import com.impressico.moviesapp.data.remote.apiservice.TMDBMovieApiService
import com.impressico.moviesapp.data.repositoryImpl.PopularMovieRepoImpl
import com.impressico.moviesapp.domain.repository.PopularMovieRepo
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
    @Provides
    @Singleton
    fun provideRemoteSource(): RemoteDataSource = RemoteDataSource()

    @Provides
    @Singleton
    fun providesPopularRemoteDataSource(tmdbMovieApiService: TMDBMovieApiService,remoteDataSource:RemoteDataSource):PopularRemoteDataSource=PopularRemoteDataSourceImpl(tmdbMovieApiService, remoteDataSource)
    @Provides
    @Singleton
    fun providePopularRemoteDataSourceImpl(popularRemoteData: PopularRemoteDataSource):PopularMovieRepo=PopularMovieRepoImpl(popularRemoteData)
}