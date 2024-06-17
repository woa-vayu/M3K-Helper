package com.rxuglr.m3kwoahelper.ui.templates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rxuglr.m3kwoahelper.R
import com.rxuglr.m3kwoahelper.util.Commands.displaytype
import com.rxuglr.m3kwoahelper.util.Variables.Codename
import com.rxuglr.m3kwoahelper.util.Variables.Codenames
import com.rxuglr.m3kwoahelper.util.Variables.FontSize
import com.rxuglr.m3kwoahelper.util.Variables.LineHeight
import com.rxuglr.m3kwoahelper.util.Variables.Name
import com.rxuglr.m3kwoahelper.util.Variables.NoGroup
import com.rxuglr.m3kwoahelper.util.Variables.NoGuide
import com.rxuglr.m3kwoahelper.util.Variables.PaddingValue
import com.rxuglr.m3kwoahelper.util.Variables.Ram
import com.rxuglr.m3kwoahelper.util.Variables.Slot
import com.rxuglr.m3kwoahelper.util.sdp

object Cards {

    @Composable
    fun InfoCard(modifier: Modifier, localUriHandler: UriHandler) {
        ElevatedCard(
            modifier =
            if (Codename != "nabu") {
                Modifier
                    .height(200.sdp())
            } else {
                modifier
            },
            shape = RoundedCornerShape(8.sdp()),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    12.dp
                )
            )
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(3.sdp())) {
                Text(
                    modifier = Modifier
                        .padding(top = PaddingValue)
                        .fillMaxWidth(),
                    text = "Windows on ARM",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = FontSize,
                    lineHeight = LineHeight
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.sdp()),
                    text = Name,
                    fontSize = FontSize,
                    lineHeight = LineHeight
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.sdp()),
                    text = LocalContext.current.getString(R.string.ramvalue, Ram),
                    fontSize = FontSize,
                    lineHeight = LineHeight
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.sdp()),
                    text = LocalContext.current.getString(R.string.paneltype, displaytype()),
                    fontSize = FontSize,
                    lineHeight = LineHeight
                )
                if (Slot.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.sdp()),
                        text = LocalContext.current.getString(R.string.slot, Slot),
                        fontSize = FontSize,
                        lineHeight = LineHeight
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    Modifier.align(Alignment.CenterHorizontally).padding(bottom = PaddingValue),
                    horizontalArrangement = Arrangement.spacedBy(5.sdp())
                ) {
                    when {
                        !NoGuide.value -> {
                            AssistChip(
                                modifier = Modifier
                                    .width(110.sdp())
                                    .height(35.sdp()),
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Book,
                                        contentDescription = null,
                                        Modifier.size(20.sdp()),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                onClick = {
                                    localUriHandler.openUri(
                                        when (Codename) {
                                            Codenames[0], Codenames[1] -> "https://github.com/woa-vayu/Port-Windows-11-Poco-X3-pro"
                                            Codenames[2] -> "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5"
                                            Codenames[3], Codenames[4], Codenames[6] -> "https://github.com/graphiks/woa-raphael"
                                            Codenames[5] -> "https://github.com/woacepheus/Port-Windows-11-Xiaomi-Mi-9"
                                            Codenames[7] -> "https://github.com/n00b69/woa-beryllium"
                                            Codenames[8] -> "https://github.com/Rubanoxd/Port-Windows-11-redmi-note-9_pro"
                                            Codenames[11], Codenames[13], Codenames[14] -> "https://github.com/Icesito68/Port-Windows-11-Lge-devices"
                                            else -> ""
                                        }
                                    )
                                },
                                label = {
                                    Text(
                                        LocalContext.current.getString(R.string.guide),
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        fontSize = FontSize
                                    )
                                }
                            )
                        }
                    }
                    when {
                        !NoGroup.value -> {
                            AssistChip(
                                modifier = Modifier
                                    .width(110.sdp())
                                    .height(35.sdp()),
                                leadingIcon = {
                                    Icon(
                                        Icons.AutoMirrored.Filled.Message,
                                        contentDescription = null,
                                        Modifier.size(20.sdp()),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                onClick = {
                                    localUriHandler.openUri(
                                        when (Codename) {
                                            Codenames[0], Codenames[1] -> "https://t.me/winonvayualt"
                                            Codenames[2] -> "https://t.me/nabuwoa"
                                            Codenames[3], Codenames[4], Codenames[6] -> "https://t.me/woaraphael"
                                            Codenames[5] -> "https://t.me/woacepheus"
                                            Codenames[7] -> "https://t.me/WinOnF1"
                                            Codenames[8] -> "http://t.me/woamiatoll"
                                            Codenames[9], Codenames[10] -> "https://t.me/onepluswoachat"
                                            Codenames[11], Codenames[12], Codenames[13], Codenames[14] -> "https://t.me/winong8x"
                                            else -> ""
                                        }
                                    )
                                },
                                label = {
                                    Text(
                                        LocalContext.current.getString(R.string.group),
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        fontSize = FontSize
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
