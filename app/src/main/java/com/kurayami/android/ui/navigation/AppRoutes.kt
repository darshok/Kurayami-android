package com.kurayami.android.ui.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoutes {
    @Serializable
    data object MyList : AppRoutes

    @Serializable
    data object TopCharts : AppRoutes
}
