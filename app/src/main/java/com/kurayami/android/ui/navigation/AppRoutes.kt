package com.kurayami.android.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface AppRoutes : NavKey {
    @Serializable
    data object MyList : AppRoutes

    @Serializable
    data object TopCharts : AppRoutes

    @Serializable
    data class MediaDetails(val id: Int) : AppRoutes
}
