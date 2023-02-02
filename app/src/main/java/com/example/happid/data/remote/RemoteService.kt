package com.example.happid.data.remote

import com.example.happid.data.entities.Info
import com.example.happid.data.entities.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RemoteService {
    @POST("userinfo")
    suspend fun submitUserInfo(@Body info: Info) : Response<UserResponse>
}