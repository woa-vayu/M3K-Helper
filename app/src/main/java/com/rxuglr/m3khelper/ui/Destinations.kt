package com.rxuglr.m3khelper.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.rxuglr.m3khelper.R

enum class Destinations(
    val route: String,
    @StringRes val label: Int,
    val iconSelected: ImageVector,
) {
    Home("main", R.string.home, Icons.Filled.Home),
    Links("links", R.string.links, Icons.Filled.Info),
}