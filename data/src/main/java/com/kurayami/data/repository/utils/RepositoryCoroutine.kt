package com.kurayami.data.repository.utils

import kotlinx.coroutines.Dispatchers

abstract class RepositoryCoroutine {
    protected val dispatcher = Dispatchers.IO
}