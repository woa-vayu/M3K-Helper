package com.rxuglr.m3khelper.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rxuglr.m3khelper.M3KApp
import com.rxuglr.m3khelper.R
import com.rxuglr.m3khelper.ui.templates.Buttons
import com.rxuglr.m3khelper.ui.templates.Images.DeviceImage
import com.rxuglr.m3khelper.ui.templates.PopupDialogs.UnknownDevice
import com.rxuglr.m3khelper.ui.templates.PopupDialogs.UnsupportedDevice
import com.rxuglr.m3khelper.util.Commands.checkSensors
import com.rxuglr.m3khelper.util.Commands.dumpModem
import com.rxuglr.m3khelper.util.Commands.dumpSensors
import com.rxuglr.m3khelper.util.Variables.BootIsPresent
import com.rxuglr.m3khelper.util.Variables.Codename
import com.rxuglr.m3khelper.util.Variables.FontSize
import com.rxuglr.m3khelper.util.Variables.GroupLink
import com.rxuglr.m3khelper.util.Variables.GuideLink
import com.rxuglr.m3khelper.util.Variables.LineHeight
import com.rxuglr.m3khelper.util.Variables.Name
import com.rxuglr.m3khelper.util.Variables.NoBoot
import com.rxuglr.m3khelper.util.Variables.NoFlash
import com.rxuglr.m3khelper.util.Variables.NoGroup
import com.rxuglr.m3khelper.util.Variables.NoGuide
import com.rxuglr.m3khelper.util.Variables.NoModem
import com.rxuglr.m3khelper.util.Variables.NoMount
import com.rxuglr.m3khelper.util.Variables.PaddingValue
import com.rxuglr.m3khelper.util.Variables.PanelType
import com.rxuglr.m3khelper.util.Variables.Ram
import com.rxuglr.m3khelper.util.Variables.Slot
import com.rxuglr.m3khelper.util.Variables.Unsupported
import com.rxuglr.m3khelper.util.Variables.Warning
import com.rxuglr.m3khelper.util.Variables.WindowsIsPresent
import com.rxuglr.m3khelper.util.sdp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination<RootGraph>(start = true)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .size(30.sdp()),
                        tint = MaterialTheme.colorScheme.primary,
                        imageVector = ImageVector.vectorResource(R.drawable.ic_windows),
                        contentDescription = null
                    )
                },
                title = {
                    Text(
                        text = M3KApp.getString(R.string.app_name),
                        fontSize = FontSize,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {},
            )
        }
    )
    { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(10.sdp()),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 10.sdp())
                .fillMaxWidth(),
        ) {
            if (M3KApp.resources.configuration.orientation != Configuration.ORIENTATION_PORTRAIT) Landscape()
            else Portrait()
        }
    }
}

@Composable
private fun Landscape() {
    when {
        Warning.value -> {
            UnknownDevice()
        }

        Unsupported.value -> {
            UnsupportedDevice()
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.sdp()),
        modifier = Modifier
            .padding(vertical = 10.sdp())
            .padding(horizontal = 10.sdp())
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.sdp()),
            modifier = Modifier
                .padding(vertical = 10.sdp())
                .width(350.sdp())
        ) {
            InfoCard(
                Modifier
                    .height(200.sdp())
                    .width(350.sdp()), LocalUriHandler.current
            )
            DeviceImage(Modifier.width(350.sdp()))
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.sdp()),
            modifier = Modifier
                .padding(vertical = 10.sdp())
                .fillMaxWidth()
        ) {
            when {
                !NoBoot.value -> {
                    Buttons.BackupButton()
                }
            }
            when {
                !NoMount.value -> {
                    Buttons.MountButton()
                }
            }
            /*when {
                !NoModem.value -> {
                    if (!checkSensors()) {
                        Buttons.Button(
                            R.string.dump_modemAsensors_title,
                            R.string.dump_modemAsensors_subtitle,
                            R.string.dump_modemAsensors_question,
                            { dumpModem(); dumpSensors() },
                            R.drawable.ic_modem
                        )
                    } else {
                        Buttons.Button(
                            R.string.dump_modem_title,
                            R.string.dump_modem_subtitle,
                            R.string.dump_modem_question,
                            { dumpModem() },
                            R.drawable.ic_modem
                        )
                    }
                }

                else -> {
                    if (!checkSensors()) {
                        Buttons.Button(
                            R.string.dump_sensors_title,
                            R.string.dump_sensors_subtitle,
                            R.string.dump_sensors_question,
                            { dumpSensors() },
                            R.drawable.ic_sensor
                        )
                    }
                }
            }*/
            when {
                !NoFlash.value -> {
                    Buttons.QuickbootButton()
                }
            }
        }
    }
}

@Composable
private fun Portrait() {
    when {
        Warning.value -> {
            UnknownDevice()
        }

        Unsupported.value -> {
            UnsupportedDevice()
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            if (Codename == "nabu" || Codename == "emu64xa") {
                10.sdp()
            } else 0.dp
        )
    ) {
        DeviceImage(Modifier.width(350.sdp()))
        InfoCard(Modifier.height(416.sdp()), LocalUriHandler.current)
    }
    when {
        !NoBoot.value -> {
            Buttons.BackupButton()
        }
    }

    when {
        !NoMount.value -> {
            Buttons.MountButton()
        }
    }
    /*when {
        !NoModem.value -> {
            if (!checkSensors()) {
                Buttons.Button(
                    R.string.dump_modemAsensors_title,
                    R.string.dump_modemAsensors_subtitle,
                    R.string.dump_modemAsensors_question,
                    { dumpModem(); dumpSensors() },
                    R.drawable.ic_modem
                )
            } else {
                Buttons.Button(
                    R.string.dump_modem_title,
                    R.string.dump_modem_subtitle,
                    R.string.dump_modem_question,
                    { dumpModem() },
                    R.drawable.ic_modem
                )
            }
        }

        else -> {
            if (!checkSensors()) {
                Buttons.Button(
                    R.string.dump_sensors_title,
                    R.string.dump_sensors_subtitle,
                    R.string.dump_sensors_question,
                    { dumpSensors() },
                    R.drawable.ic_sensor
                )
            }
        }
    }*/
    when {
        !NoFlash.value -> {
            Buttons.QuickbootButton()
        }
    }
}

@Composable
fun InfoCard(modifier: Modifier, localUriHandler: UriHandler) {
    ElevatedCard(
        modifier =
        if (Codename == "nabu" || Codename == "emu64xa") {
            modifier
        } else {
            Modifier
                .height(200.sdp())
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
                text = M3KApp.getString(R.string.paneltype) + PanelType,
                fontSize = FontSize,
                lineHeight = LineHeight
            )
            when {
                !NoBoot.value -> {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.sdp()),
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
                            .padding(start = 10.sdp()),
                        text = M3KApp.getString(R.string.slot, Slot),
                        fontSize = FontSize,
                        lineHeight = LineHeight
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.sdp()),
                text = M3KApp.getString(R.string.windows_status) + M3KApp.getString(WindowsIsPresent),
                fontSize = FontSize,
                lineHeight = LineHeight
            )
            Spacer(modifier = Modifier.weight(1f))
            /*Row(
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
            }*/
        }
    }
}