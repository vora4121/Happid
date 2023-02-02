package com.example.happid.data.remote

import com.example.happid.data.entities.Info
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val remoteService: RemoteService
) : BaseDataSource() {

    suspend fun submitUserInfo(info: Info) = getResult {
        remoteService.submitUserInfo(info)
    }
}