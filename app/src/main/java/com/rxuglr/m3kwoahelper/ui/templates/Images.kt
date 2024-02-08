package com.rxuglr.m3kwoahelper.ui.templates

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rxuglr.m3kwoahelper.R
import com.rxuglr.m3kwoahelper.util.Commands.codenames

object Images {

    @Composable
    fun DeviceImage(modifier: Modifier) {
        Image(
            alignment = Alignment.TopStart,
            modifier = if (Build.DEVICE == "nabu") {
                modifier
            }
            else {
                Modifier
                    .padding(top = 20.dp)
                    .height(160.dp)
                    .width(140.dp)
            },
            painter = painterResource(
                id = when (Build.DEVICE) {
                    codenames[0] -> R.drawable.vayu
                    codenames[1] -> R.drawable.vayu
                    codenames[2] -> R.drawable.nabu
                    codenames[3] -> R.drawable.raphael
                    codenames[4] -> R.drawable.raphael
                    else -> R.drawable.nabu
                }
            ),
            contentDescription = null
        )
    }
}