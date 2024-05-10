package com.rxuglr.m3kwoahelper.ui.templates

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
import com.rxuglr.m3kwoahelper.util.Variables.Codename
import com.rxuglr.m3kwoahelper.util.Variables.Codenames
import com.rxuglr.m3kwoahelper.util.sdp

object Images {

    @Composable
    fun DeviceImage(modifier: Modifier) {
        Image(
            alignment = Alignment.TopStart,
            modifier = if (Codename != "nabu") {
                Modifier
                    .padding(top = 20.sdp())
                    .height(160.sdp())
                    .width(140.sdp())
            } else {
                modifier
            },
            painter = painterResource(
                id = when (Codename) {
                    Codenames[0], Codenames[1] -> R.drawable.vayu
                    Codenames[2] -> R.drawable.nabu
                    Codenames[3], Codenames[4] -> R.drawable.raphael
                    else -> R.drawable.ic_device_unknown
                },
            ),

            contentDescription = null,
        )
    }
}