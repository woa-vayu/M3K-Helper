package com.rxuglr.m3khelper.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.rxuglr.m3khelper.R

enum class Destinations(
    val direction: String,
    @StringRes val label: Int,
    val iconSelected: ImageVector,
) {
    Home("main", R.string.home, Icons.Filled.Home),
    Links("links", R.string.links, Icons.Filled.Info),
}