package com.example.myapplication22.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {


    const val BASE_URL =  "http://10.0.2.2:3000/"
    @Synchronized
    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val EVENTSERVICE: EventService by lazy {
        retrofit().create(EventService::class.java)
    }



}