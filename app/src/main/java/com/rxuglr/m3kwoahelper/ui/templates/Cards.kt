package com.rxuglr.m3kwoahelper.ui.templates

import android.util.DisplayMetrics
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.Book
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
import com.rxuglr.m3kwoahelper.util.Variables.Ram
import com.rxuglr.m3kwoahelper.util.Variables.Slot
import com.rxuglr.m3kwoahelper.woahApp

object Cards {

    fun pxtodp(px: Float): Float {
        return px / (woahApp.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    @Composable
    fun InfoCard(modifier: Modifier, localUriHandler: UriHandler) {
        ElevatedCard(
            modifier =
            if (Codename == "nabu") {
                modifier
            } else {
                Modifier
                    .height(200.dp)
            },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    12.dp
                )
            )
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp)
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
                        .padding(start = 10.dp),
                    text = Name,
                    fontSize = FontSize,
                    lineHeight = LineHeight
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    text = LocalContext.current.getString(R.string.ramvalue, Ram),
                    fontSize = FontSize,
                    lineHeight = LineHeight
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    text = LocalContext.current.getString(R.string.paneltype, displaytype()),
                    fontSize = FontSize,
                    lineHeight = LineHeight
                )
                if (Slot.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        text = LocalContext.current.getString(R.string.slot, Slot),
                        fontSize = FontSize,
                        lineHeight = LineHeight
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    if (Name != "Unknown") {
                        if (Codename != "cepheus") {
                            AssistChip(
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Book,
                                        contentDescription = null,
                                        Modifier.size(AssistChipDefaults.IconSize),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                onClick = {
                                    localUriHandler.openUri(
                                        when (Codename) {
                                            Codenames[0], Codenames[1] -> "https://github.com/woa-vayu/Port-Windows-11-Poco-X3-pro"
                                            Codenames[2] -> "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5"
                                            Codenames[3], Codenames[4], Codenames[6] -> "https://github.com/graphiks/woa-raphael"
                                            else -> ""
                                        }
                                    )
                                },
                                label = {
                                    Text(
                                        LocalContext.current.getString(R.string.guide),
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            )
                        }
                        AssistChip(
                            leadingIcon = {
                                Icon(
                                    Icons.AutoMirrored.Outlined.Message,
                                    contentDescription = null,
                                    Modifier.size(AssistChipDefaults.IconSize),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            onClick = {
                                localUriHandler.openUri(
                                    when (Codename) {
                                        Codenames[0], Codenames[1] -> "https://t.me/winonvayualt"
                                        Codenames[2] -> "https://t.me/nabuwoa"
                                        Codenames[3], Codenames[4], Codenames[6] -> "https://t.me/woaraphael"
                                        Codenames[5] -> "https://t.me/WinOnMi9/"
                                        else -> ""
                                    }
                                )
                            },
                            label = {
                                Text(
                                    LocalContext.current.getString(R.string.group),
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
