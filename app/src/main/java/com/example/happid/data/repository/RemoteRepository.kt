package com.example.happid.data.repository

import com.example.happid.data.entities.Info
import com.example.happid.data.remote.RemoteDataSource
import com.example.happid.utils.performGetOperation
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun submitUserInfo(info: Info) = performGetOperation(networkCall = { remoteDataSource.submitUserInfo(info) })
}