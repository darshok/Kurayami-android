package com.kurayami.data.repository.utils

import kotlinx.coroutines.Dispatchers

abstract class RepositoryCoroutine {
    private val dispatcher = Dispatchers.IO
}