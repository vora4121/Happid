package com.example.happid.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.happid.data.entities.Info
import com.example.happid.data.entities.UserResponse
import com.example.happid.data.repository.RemoteRepository
import com.example.happid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: RemoteRepository
) : ViewModel() {

    private val _id = MutableLiveData<Info>()

    private val _info = _id.switchMap { info ->
        repository.submitUserInfo(info)
    }

    val userResponse: LiveData<Resource<UserResponse>> = _info

    fun start(info: Info) {
        _id.value = info
    }

}
