package com.mou.cloudmusic_md3t.api.data

import retrofit2.http.GET
import retrofit2.http.Query

// todo: return type waiting for Dto

interface LoginApiService {
    // Guest login
    @GET("register/anonimous")
    suspend fun guestLogin():String

    // Normal Login
    /**
     * Login by phone number and password
     * @param phone String phone number
     * @param password String password
     * @param countrycode String country code
     */
    @GET("login/cellphone")
    suspend fun loginByPhonePwd(
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Query("countrycode") countrycode: String = "86"
    )

    /**
     * Login by email and password
     * @param email String email
     * @param password String password
     */
    @GET("login")
    suspend fun loginByMailPwd(
        @Query("email") email: String,
        @Query("password") password: String
    )

    // Captcha Login
    /**
     * Get captcha by phone number, you can verify captcha by phone number with "captchaVerify()"
     * @param phone String
     * @param countrycode String
     */
    @GET("captcha/sent")
    suspend fun captchaByPhone(
        @Query("phone") phone: String,
        @Query("ctcode") countrycode: String = "86"
    )

    /**
     * Verify captcha by phone number
     * @param phone String phone number
     * @param captcha String captcha
     * @param countrycode String country code
     */
    @GET("captcha/verify")
    suspend fun captchaVerify(
        @Query("phone") phone: String,
        @Query("captcha") captcha: String,
        @Query("ctcode") countrycode: String = "86"
    )

    /**
     * Login by phone number and captcha, at first you need to get captcha by phone number with "captchaByPhone()"
     * @param phone String
     * @param captcha String
     * @param countrycode String
     */
    @GET("login/cellphone")
    suspend fun loginByCaptcha(
        @Query("phone") phone: String,
        @Query("captcha") captcha: String,
        @Query("countrycode") countrycode: String = "86"
    )

    // QR Code Login
    /**
     * Get QR Code Key
     */
    @GET("login/qr/key")
    suspend fun getQrKey()

    /**
     * Get QR Code Image
     * @param key String QR Code Key from "getQrKey()"
     * @return String QR Code Image with Base 64
     */
    @GET("login/qr/create")
    suspend fun qrKeyCreate(
        @Query("key") key: String,
    ) : String

    /**
     * Check QR Key is scan of login Status
     * @param key String
     */
    @GET("login/qr/check")
    suspend fun qrCheck(
        @Query("key") key: String
    )

}