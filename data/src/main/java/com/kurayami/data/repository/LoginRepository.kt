package com.kurayami.data.repository

import android.net.Uri

interface LoginRepository {

    suspend fun manageLoginData(uri: Uri)
    suspend fun manageLogout()
}