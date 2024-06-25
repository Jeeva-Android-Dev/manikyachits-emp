package com.mazenet.mani.gurugubera.Retrofit

import com.mazenet.mani.gurugubera.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

  // private const val URL = "http://13.233.231.26/boney-belgium/backend/api/"
   private const val URL =   BuildConfig.server_url
     // private const val URL = "http://bestgroups.in/bestchit/backend/api/"
//    private const val URL = "http://13.233.231.26/svarnadri/backend/api/"
//    private const val URL = "http://10.0.2.2:8283/Seating/"
//http://bestgroups.in/bestchit
    val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val okhttp = OkHttpClient.Builder().readTimeout(50,TimeUnit.SECONDS).connectTimeout(100,TimeUnit.SECONDS).addInterceptor(logger)


    val builder = Retrofit.Builder()

    val retrofit = builder.baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(
        okhttp.build()
    ).build()

    fun <T> buildservice(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}