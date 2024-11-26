package com.rxuglr.m3khelper.ui.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.rxuglr.m3khelper.util.Variables.CurrentDeviceCard
import com.rxuglr.m3khelper.util.sdp

object Images {

    @Composable
    fun DeviceImage(modifier: Modifier) {
        Image(
            alignment = Alignment.TopStart,
            modifier = if (CurrentDeviceCard.deviceCodename == "nabu" || CurrentDeviceCard.deviceCodename == "emu64x") {
                Modifier
                    .padding(top = 20.sdp())
                    .height(160.sdp())
                    .width(140.sdp())
            } else {
                modifier
            },
            painter = painterResource(id = CurrentDeviceCard.deviceImage),
            contentDescription = null,
        )
    }
}