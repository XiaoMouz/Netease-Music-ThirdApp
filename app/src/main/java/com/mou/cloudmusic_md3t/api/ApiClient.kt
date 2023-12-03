package com.mou.cloudmusic_md3t.api

import com.mou.cloudmusic_md3t.api.data.LoginApiService
import com.mou.cloudmusic_md3t.api.data.NeteaseMusicApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object ApiClient {
    private const val BASE_URL = "http://"
    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }


    val neteaseMusicApiService: NeteaseMusicApi by lazy {
        retrofit.create(NeteaseMusicApi::class.java)
    }

    val loginApiService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }
}