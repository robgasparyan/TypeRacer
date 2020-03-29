package com.example.typeracer.viewModel

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel

open class BaseVM(app: Application) : AndroidViewModel(app) {

    private val resources = lazy { getApplication<Application>().resources }

    fun getString(@StringRes resource: Int): String? = resources.value.getString(resource)

}