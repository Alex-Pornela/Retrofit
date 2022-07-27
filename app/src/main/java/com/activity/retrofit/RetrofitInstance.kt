package com.activity.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object{
        //base url of the api
        val BASE_URL = "https://jsonplaceholder.typicode.com/"

        //http logging interceptor show logs of network operations
        val interceptor = HttpLoggingInterceptor().apply {
            //logs request and response lines
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        //okHttp client instance
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            //time period in which app should establish a connection to a server
                .connectTimeout(30,TimeUnit.SECONDS)
            //maximum time gap between arrivals of two data packets when waiting for the server's response
                .readTimeout(20,TimeUnit.SECONDS)
            //maximum time gap between two data packets when sending to the server
                .writeTimeout(25,TimeUnit.SECONDS)

        }.build()

        //function that return retrofit instance
        fun getRetrofitInstance():Retrofit{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                    //add http client to the retrofit instance
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}