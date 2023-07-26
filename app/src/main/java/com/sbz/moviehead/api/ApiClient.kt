package com.sbz.moviehead.api

import com.sbz.moviehead.utils.Constants.API_KEY
import com.sbz.moviehead.utils.Constants.BASEURL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    private val interceptorClient = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptorClient)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    val api by lazy {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}