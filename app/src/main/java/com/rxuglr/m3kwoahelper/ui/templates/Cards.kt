package com.rxuglr.m3kwoahelper.ui.templates

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.rxuglr.m3kwoahelper.R
import com.rxuglr.m3kwoahelper.util.Commands
import com.rxuglr.m3kwoahelper.woahApp

object Cards {

    fun pxtodp(px: Float): Float {
        return px / (woahApp.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
    @Composable
    fun InfoCard(devicename: String, ram: String) {
        Card(
            Modifier
                .height(200.dp),
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
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    text = devicename
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    text = LocalContext.current.getString(R.string.ramvalue, ram)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    text = LocalContext.current.getString(R.string.paneltype, Commands.displaytype())
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    if (Build.DEVICE != "cepheus") {
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
                                woahApp.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(
                                            when (Build.DEVICE) {
                                                Commands.codenames[0] -> "https://github.com/woa-vayu/Port-Windows-11-Poco-X3-pro"
                                                Commands.codenames[1] -> "https://github.com/woa-vayu/Port-Windows-11-Poco-X3-pro"
                                                Commands.codenames[2] -> "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5"
                                                Commands.codenames[3] -> "https://github.com/graphiks/woa-raphael"
                                                Commands.codenames[4] -> "https://github.com/graphiks/woa-raphael"
                                                else -> "Unknown"
                                            }
                                        )
                                    )
                                )
                            },
                            label = {
                                Text(
                                    "Guide",
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        )
                    }
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
                            woahApp.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(
                                        when (Build.DEVICE) {
                                            Commands.codenames[0] -> "https://t.me/winonvayualt"
                                            Commands.codenames[1] -> "https://t.me/winonvayualt"
                                            Commands.codenames[2] -> "https://t.me/nabuwoa"
                                            Commands.codenames[3] -> "https://t.me/woaraphael"
                                            Commands.codenames[4] -> "https://t.me/woaraphael"
                                            Commands.codenames[5] -> "https://t.me/WinOnMi9/"
                                            else -> "Unknown"
                                        }
                                    )
                                )
                            )
                        },
                        label = {
                            Text(
                                "Group",
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun LandScapeInfoCard(devicename: String, ram: String, slot: String, textSize: TextUnit) {
        Card(
            Modifier
                .height(200.dp)
                .width((pxtodp(625f)).dp),
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
                    fontSize = textSize
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    text = devicename,
                    fontSize = textSize
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    text = LocalContext.current.getString(R.string.ramvalue, ram),
                    fontSize = textSize
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    text = LocalContext.current.getString(R.string.paneltype, Commands.displaytype()),
                    fontSize = textSize
                )
                if (slot.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        text = LocalContext.current.getString(R.string.slot, slot),
                        fontSize = textSize
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    Modifier.align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    if (Build.DEVICE != "cepheus") {
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
                                woahApp.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(
                                            when (Build.DEVICE) {
                                                Commands.codenames[0] -> "https://github.com/woa-vayu/Port-Windows-11-Poco-X3-pro"
                                                Commands.codenames[1] -> "https://github.com/woa-vayu/Port-Windows-11-Poco-X3-pro"
                                                Commands.codenames[2] -> "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5"
                                                Commands.codenames[3] -> "https://github.com/graphiks/woa-raphael"
                                                Commands.codenames[4] -> "https://github.com/graphiks/woa-raphael"
                                                else -> "Unknown"
                                            }
                                        )
                                    )
                                )
                            },
                            label = {
                                Text(
                                    "Guide",
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        )
                    }
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
                            woahApp.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(
                                        when (Build.DEVICE) {
                                            Commands.codenames[0] -> "https://t.me/winonvayualt"
                                            Commands.codenames[1] -> "https://t.me/winonvayualt"
                                            Commands.codenames[2] -> "https://t.me/nabuwoa"
                                            Commands.codenames[3] -> "https://t.me/woaraphael"
                                            Commands.codenames[4] -> "https://t.me/woaraphael"
                                            Commands.codenames[5] -> "https://t.me/WinOnMi9/"
                                            else -> "Unknown"
                                        }
                                    )
                                )
                            )
                        },
                        label = {
                            Text(
                                "Group",
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    )
                }
            }
        }
    }
}