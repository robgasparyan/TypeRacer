package com.example.typeracer.viewModel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.typeracer.util.FirebaseAuthManager
import com.example.typeracer.util.TextHelper

class ProfileVM(app: Application) : BaseVM(app) {

    val profileName = ObservableField(getDisplayName())
    val profileEmail = ObservableField(getEmail())

    private val _logOutLiveData = MutableLiveData<Boolean>()
    val logOutLiveData: LiveData<Boolean>
        get() = _logOutLiveData

    fun logOut() {
        FirebaseAuthManager.logOut()
        postLogOutEvent()
    }

    private fun postLogOutEvent() {
        _logOutLiveData.value = true
    }

    private fun getDisplayName() = TextHelper.emailToDisplayName(getEmail())

    private fun getEmail() = FirebaseAuthManager.getCurrentUser()?.email

}