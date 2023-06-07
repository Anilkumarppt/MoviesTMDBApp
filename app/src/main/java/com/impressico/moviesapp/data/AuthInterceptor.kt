package com.impressico.moviesapp.data

import com.impressico.recipesapp.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor():Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original:Request=chain.request()
        val originalHttpURL:HttpUrl=original.url

        /*val url=originalHttpURL.newBuilder().
        addQueryParameter(NetworkConstants.API_PARAM, BuildConfig.API_KEY)
            .build()*/
        val newRequest=original.newBuilder().
        header(NetworkConstants.API_PARAM, BuildConfig.API_KEY)
            .header("accept", "application/json")
        return chain.proceed(newRequest.build())


    }
}