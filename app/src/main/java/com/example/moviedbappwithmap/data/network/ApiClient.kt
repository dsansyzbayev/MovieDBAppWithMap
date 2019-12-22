package com.example.moviedbappwithmap.data.network

import android.util.Log
import com.example.moviedbappwithmap.utils.AppConstants
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val apiClient: MovieApi by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttp = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(interceptor)
            .addInterceptor {chain ->
                val newUrl = chain.request().url
                    .newBuilder()
                    .addQueryParameter("api_key",
                        AppConstants.API_KEY
                    )
                    .build()
                val newRequest = chain.request()
                    .newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return@lazy retrofit.create(MovieApi::class.java)
    }
}