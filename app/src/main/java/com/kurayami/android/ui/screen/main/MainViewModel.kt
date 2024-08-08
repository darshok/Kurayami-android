package com.kurayami.android.ui.screen.main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    fun manageIntentData(data: Uri?) = viewModelScope.launch {
        if (data?.scheme == "kurayami") {
            // Store token
        }
    }
}