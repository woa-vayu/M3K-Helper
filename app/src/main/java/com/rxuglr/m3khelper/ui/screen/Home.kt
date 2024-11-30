package com.rxuglr.m3khelper.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rxuglr.m3khelper.M3KApp
import com.rxuglr.m3khelper.R
import com.rxuglr.m3khelper.ui.component.AboutCard
import com.rxuglr.m3khelper.ui.component.BackupButton
import com.rxuglr.m3khelper.ui.component.DeviceImage
import com.rxuglr.m3khelper.ui.component.InfoCard
import com.rxuglr.m3khelper.ui.component.MountButton
import com.rxuglr.m3khelper.ui.component.QuickbootButton
import com.rxuglr.m3khelper.util.Variables.CurrentDeviceCard
import com.rxuglr.m3khelper.util.Variables.FontSize
import com.rxuglr.m3khelper.util.Variables.PaddingValue
import com.rxuglr.m3khelper.util.Variables.showAboutCard
import com.rxuglr.m3khelper.util.sdp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Destination<RootGraph>(start = true)
@Composable
fun HomeScreen(navigator: DestinationsNavigator) {
    when {
        showAboutCard.value -> {
            AboutCard()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = M3KApp.getString(R.string.app_name),
                        fontSize = FontSize,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            showAboutCard.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null
                        )
                    }
                    /*IconButton(
                        onClick = { navigator.navigate(SettingsScreenDestination) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null
                        )
                    }*/
                },
            )
        }
    )
    { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(10.sdp()),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = PaddingValue)
                .fillMaxWidth(),
        ) {
            if (M3KApp.resources.configuration.orientation != Configuration.ORIENTATION_PORTRAIT) Landscape()
            else Portrait()
        }
    }
}

@Composable
private fun Landscape() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.sdp()),
        modifier = Modifier
            .padding(vertical = PaddingValue)
            .padding(horizontal = PaddingValue)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.sdp()),
            modifier = Modifier
                .padding(vertical = PaddingValue)
                .width(220.sdp())
        ) {
            InfoCard(
                Modifier
                    .height(200.sdp())
                    .width(220.sdp()), LocalUriHandler.current
            )
            DeviceImage(
                Modifier
                    .height(200.sdp())
                    .width(220.sdp())
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.sdp()),
            modifier = Modifier
                .padding(vertical = PaddingValue)
                .fillMaxWidth()
        ) {
            when {
                CurrentDeviceCard.noBoot == false -> {
                    BackupButton()
                }
            }
            when {
                CurrentDeviceCard.noMount == false -> {
                    MountButton()
                }
            }
            /*when {
                CurrentDeviceCard.noModem == false -> {
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
                CurrentDeviceCard.noFlash == false -> {
                    QuickbootButton()
                }
            }
        }
    }
}

@Composable
private fun Portrait() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.sdp())
    ) {
        DeviceImage(Modifier.height(416.sdp()))
        InfoCard(Modifier.height(416.sdp()), LocalUriHandler.current)
    }
    when {
        CurrentDeviceCard.noBoot == false -> {
            BackupButton()
        }
    }

    when {
        CurrentDeviceCard.noMount == false -> {
            MountButton()
        }
    }
    /*when {
        CurrentDeviceCard.noModem == false -> {
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
        CurrentDeviceCard.noFlash == false -> {
            QuickbootButton()
        }
    }
}