package com.rxuglr.m3khelper.ui.templates

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
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.rxuglr.m3khelper.R
import com.rxuglr.m3khelper.util.Commands.displaytype
import com.rxuglr.m3khelper.util.Variables.Codename
import com.rxuglr.m3khelper.util.Variables.FontSize
import com.rxuglr.m3khelper.util.Variables.GroupLink
import com.rxuglr.m3khelper.util.Variables.GuideLink
import com.rxuglr.m3khelper.util.Variables.LineHeight
import com.rxuglr.m3khelper.util.Variables.Name
import com.rxuglr.m3khelper.util.Variables.NoGroup
import com.rxuglr.m3khelper.util.Variables.NoGuide
import com.rxuglr.m3khelper.util.Variables.PaddingValue
import com.rxuglr.m3khelper.util.Variables.Ram
import com.rxuglr.m3khelper.util.Variables.Slot
import com.rxuglr.m3khelper.util.sdp
import com.rxuglr.m3khelper.M3KApp
import com.rxuglr.m3khelper.util.Commands.bootstate
import com.rxuglr.m3khelper.util.Variables.BootIsPresent

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
                    text = M3KApp.getString(R.string.model, Name),
                    fontSize = FontSize,
                    lineHeight = LineHeight
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.sdp()),
                    text = M3KApp.getString(R.string.ramvalue, Ram),
                    fontSize = FontSize,
                    lineHeight = LineHeight
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.sdp()),
                    text = M3KApp.getString(R.string.paneltype, displaytype()),
                    fontSize = FontSize,
                    lineHeight = LineHeight
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.sdp()),
                    text = M3KApp.getString(R.string.backup_boot_state, bootstate()),
                    fontSize = FontSize,
                    lineHeight = LineHeight
                )
                if (Slot.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.sdp()),
                        text = M3KApp.getString(R.string.slot, Slot),
                        fontSize = FontSize,
                        lineHeight = LineHeight
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = PaddingValue),
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
                                        GuideLink
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
                                        GroupLink
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
