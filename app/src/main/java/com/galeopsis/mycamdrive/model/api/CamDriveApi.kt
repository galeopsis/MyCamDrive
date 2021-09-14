package com.galeopsis.mycamdrive.model.api

import com.galeopsis.mycamdrive.model.data.CameraData
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CamDriveApi {

    @GET("mobile/api_native/login")
    fun loginAsync(
        @Query("username") username: String,
        @Query("password") password: String
    ): Deferred<Response<CameraData>>

    @GET("mobile/api_native/cameras")
    fun getCameras(): Deferred<Response<CameraData>>

    @GET("mobile/api_native/logout")
    fun logout()
}