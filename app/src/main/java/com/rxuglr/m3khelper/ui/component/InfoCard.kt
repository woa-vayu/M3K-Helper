package com.rxuglr.m3khelper.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.rxuglr.m3khelper.M3KApp
import com.rxuglr.m3khelper.R
import com.rxuglr.m3khelper.util.Variables.BootIsPresent
import com.rxuglr.m3khelper.util.Variables.CurrentDeviceCard
import com.rxuglr.m3khelper.util.Variables.FontSize
import com.rxuglr.m3khelper.util.Variables.LineHeight
import com.rxuglr.m3khelper.util.Variables.PaddingValue
import com.rxuglr.m3khelper.util.Variables.PanelType
import com.rxuglr.m3khelper.util.Variables.Ram
import com.rxuglr.m3khelper.util.Variables.Slot
import com.rxuglr.m3khelper.util.Variables.WindowsIsPresent
import com.rxuglr.m3khelper.util.Variables.specialDeviceCardsArray
import com.rxuglr.m3khelper.util.sdp

@Composable
fun InfoCard(modifier: Modifier) {
    ElevatedCard(
        modifier =
        if (specialDeviceCardsArray.contains(CurrentDeviceCard)) {
            modifier
        } else {
            Modifier
                .height(300.sdp())
        },
        shape = RoundedCornerShape(8.sdp()),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(3.sdp())) {
            Text(
                modifier = Modifier
                    .padding(top = PaddingValue)
                    .fillMaxWidth(),
                text = M3KApp.getString(R.string.woa),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = FontSize,
                lineHeight = LineHeight
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = PaddingValue),
                text = M3KApp.getString(R.string.model, CurrentDeviceCard.deviceName),
                fontSize = FontSize,
                lineHeight = LineHeight
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = PaddingValue),
                text = M3KApp.getString(R.string.ramvalue, Ram),
                fontSize = FontSize,
                lineHeight = LineHeight
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = PaddingValue),
                text = M3KApp.getString(R.string.paneltype) + PanelType,
                fontSize = FontSize,
                lineHeight = LineHeight
            )
            when {
                !CurrentDeviceCard.noBoot && !CurrentDeviceCard.noMount -> {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = PaddingValue),
                        text = M3KApp.getString(R.string.backup_boot_state) + M3KApp.getString(BootIsPresent),
                        fontSize = FontSize,
                        lineHeight = LineHeight
                    )
                }
            }
            when {
                Slot.isNotEmpty() -> {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = PaddingValue),
                        text = M3KApp.getString(R.string.slot, Slot),
                        fontSize = FontSize,
                        lineHeight = LineHeight
                    )
                }
            }
            when {
                !CurrentDeviceCard.noMount -> {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.sdp()),
                        text = M3KApp.getString(R.string.windows_status) + M3KApp.getString(WindowsIsPresent),
                        fontSize = FontSize,
                        lineHeight = LineHeight
                    )
                }
            }
        }
    }
}