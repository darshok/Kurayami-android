package com.kurayami.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

fun <T> DataStore<Preferences>.getValue(key: Preferences.Key<T>) = data.map { it[key] }

fun <T> DataStore<Preferences>.getValueOrDefault(
    key: Preferences.Key<T>,
    default: T,
) = data.map { it[key] ?: default }

suspend fun <T> DataStore<Preferences>.setValue(
    key: Preferences.Key<T>,
    value: T?
) = edit { mutablePreference ->
    value?.let {
        mutablePreference[key] = it
    }
}
