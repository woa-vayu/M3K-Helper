package com.rxuglr.m3khelper.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rxuglr.m3khelper.util.SdpScreenDimensionValues.smallestWidth

object SdpScreenDimensionValues {
    var smallestWidth: Int = 0
}

// Assigns values to the variables above
@Composable
fun AssValsToTheSdpObject() {
    val config = LocalConfiguration.current
    smallestWidth = config.smallestScreenWidthDp
}

// Will return the smallestWidth approximated to nearest 30 to improve performance
fun ApproximateWidth(value: Int): Int {
    val remainder = value % 30
    return if (remainder <= 15) {
        value - remainder
    } else {
        value + (30 - remainder)
    }
}

@Composable
fun Int.sdp(): Dp {
    if (smallestWidth == 0) {
        AssValsToTheSdpObject()
    }
    val ratio = if (smallestWidth <= 400) {
        ApproximateWidth(smallestWidth) / 440.0
    } else if (smallestWidth <= 450) {
        ApproximateWidth(smallestWidth) / 450.0
    } else if (smallestWidth <= 550) {
        ApproximateWidth(smallestWidth) / 450.0
    } else ApproximateWidth(smallestWidth) / 650.0
    val final = this * ratio
    return final.dp
}

@Composable
fun Int.ssp(): TextUnit {
    if (smallestWidth == 0) {
        AssValsToTheSdpObject()
    }
    val ratio = if (smallestWidth <= 400) {
        ApproximateWidth(smallestWidth) / 500.0
    } else if (smallestWidth <= 450) {
        ApproximateWidth(smallestWidth) / 450.0
    } else if (smallestWidth <= 550) {
        ApproximateWidth(smallestWidth) / 500.0
    } else ApproximateWidth(smallestWidth) / 650.0
    val final = this * ratio
    return final.sp
}