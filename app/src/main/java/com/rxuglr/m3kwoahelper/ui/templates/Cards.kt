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
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
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
import com.rxuglr.m3kwoahelper.util.Variables.codename
import com.rxuglr.m3kwoahelper.util.Variables.codenames
import com.rxuglr.m3kwoahelper.util.Variables.fontSize
import com.rxuglr.m3kwoahelper.util.Variables.lineHeight
import com.rxuglr.m3kwoahelper.util.Variables.name
import com.rxuglr.m3kwoahelper.util.Variables.ram
import com.rxuglr.m3kwoahelper.util.Variables.slot
import com.rxuglr.m3kwoahelper.woahApp

object Cards {

    fun pxtodp(px: Float): Float {
        return px / (woahApp.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    @Composable
    fun InfoCard(modifier: Modifier, localUriHandler: UriHandler) {
        ElevatedCard(
            modifier =
            if (codename == "nabu") {
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
                    fontSize = fontSize,
                    lineHeight = lineHeight
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    text = name,
                    fontSize = fontSize,
                    lineHeight = lineHeight
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    text = LocalContext.current.getString(R.string.ramvalue, ram),
                    fontSize = fontSize,
                    lineHeight = lineHeight
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    text = LocalContext.current.getString(R.string.paneltype, displaytype()),
                    fontSize = fontSize,
                    lineHeight = lineHeight
                )
                if (slot.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        text = LocalContext.current.getString(R.string.slot, slot),
                        fontSize = fontSize,
                        lineHeight = lineHeight
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    if ((codename != "cepheus") && (name != "Unknown")) {
                        AssistChip(
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Book,
                                    contentDescription = null,
                                    Modifier.size(AssistChipDefaults.IconSize),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            onClick = {
                                localUriHandler.openUri(
                                    when (codename) {
                                        codenames[0], codenames[1] -> "https://github.com/woa-vayu/Port-Windows-11-Poco-X3-pro"
                                        codenames[2] -> "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5"
                                        codenames[3], codenames[4], codenames[6] -> "https://github.com/graphiks/woa-raphael"
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
                    if (name != "Unknown") {
                        AssistChip(
                            leadingIcon = {
                                Icon(
                                    Icons.AutoMirrored.Filled.Message,
                                    contentDescription = null,
                                    Modifier.size(AssistChipDefaults.IconSize),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            onClick = {
                                localUriHandler.openUri(
                                    when (codename) {
                                        codenames[0], codenames[1] -> "https://t.me/winonvayualt"
                                        codenames[2] -> "https://t.me/nabuwoa"
                                        codenames[3], codenames[4], codenames[6] -> "https://t.me/woaraphael"
                                        codenames[5] -> "https://t.me/WinOnMi9/"
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