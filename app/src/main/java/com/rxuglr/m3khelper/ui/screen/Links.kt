package com.rxuglr.m3khelper.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.rxuglr.m3khelper.M3KApp
import com.rxuglr.m3khelper.R
import com.rxuglr.m3khelper.ui.component.LinkButton
import com.rxuglr.m3khelper.util.Variables.CurrentDeviceCard
import com.rxuglr.m3khelper.util.Variables.FontSize
import com.rxuglr.m3khelper.util.Variables.PaddingValue
import com.rxuglr.m3khelper.util.sdp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination<RootGraph>()
@Composable
fun LinksScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = M3KApp.getString(R.string.links),
                        fontSize = FontSize,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {},
            )
        })
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
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.sdp()),
        modifier = Modifier
            .padding(vertical = PaddingValue)
            .padding(horizontal = PaddingValue)
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.sdp()),
            modifier = Modifier
                .padding(vertical = PaddingValue)
                .width(500.sdp())
        ) {
            if (CurrentDeviceCard.unifiedDriversUEFI == true &&
                !(CurrentDeviceCard.noDrivers == true || CurrentDeviceCard.noUEFI == true)
            ) {
                LinkButton(CurrentDeviceCard.deviceName + " " + M3KApp.getString(R.string.driversuefi), null, CurrentDeviceCard.deviceDrivers, R.drawable.ic_drivers, LocalUriHandler.current)
            } else {
                when {
                    CurrentDeviceCard.noDrivers == false -> {
                        LinkButton(CurrentDeviceCard.deviceName + " " + M3KApp.getString(R.string.drivers), M3KApp.getString(R.string.drivers), CurrentDeviceCard.deviceDrivers, R.drawable.ic_drivers, LocalUriHandler.current)
                    }
                }
                when {
                    CurrentDeviceCard.noUEFI == false -> {
                        LinkButton(CurrentDeviceCard.deviceName + " " + M3KApp.getString(R.string.uefi), M3KApp.getString(R.string.uefi), CurrentDeviceCard.deviceUEFI, R.drawable.ic_uefi, LocalUriHandler.current)
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.sdp()),
            modifier = Modifier
                .padding(vertical = PaddingValue)
                .width(500.sdp())
        ) {
            when {
                CurrentDeviceCard.noGroup == false -> {
                    LinkButton(CurrentDeviceCard.deviceName + " " + M3KApp.getString(R.string.group), null, CurrentDeviceCard.deviceGroup, Icons.AutoMirrored.Filled.Message, LocalUriHandler.current)
                }
            }
            when {
                CurrentDeviceCard.noGuide == false -> {
                    LinkButton(CurrentDeviceCard.deviceName + " " + M3KApp.getString(R.string.guide), null, CurrentDeviceCard.deviceGuide, Icons.Filled.Book, LocalUriHandler.current)
                }
            }
        }
    }
}

@Composable
private fun Portrait() {
    if (CurrentDeviceCard.unifiedDriversUEFI == true &&
        !(CurrentDeviceCard.noDrivers == true || CurrentDeviceCard.noUEFI == true)
    ) {
        LinkButton(CurrentDeviceCard.deviceName + " " + M3KApp.getString(R.string.driversuefi), null, CurrentDeviceCard.deviceDrivers, R.drawable.ic_drivers, LocalUriHandler.current)
    } else {
        when {
            CurrentDeviceCard.noDrivers == false -> {
                LinkButton(CurrentDeviceCard.deviceName + " " + M3KApp.getString(R.string.drivers), M3KApp.getString(R.string.drivers), CurrentDeviceCard.deviceDrivers, R.drawable.ic_drivers, LocalUriHandler.current)
            }
        }
        when {
            CurrentDeviceCard.noUEFI == false -> {
                LinkButton(CurrentDeviceCard.deviceName + " " + M3KApp.getString(R.string.uefi), M3KApp.getString(R.string.uefi), CurrentDeviceCard.deviceUEFI, R.drawable.ic_uefi, LocalUriHandler.current)
            }
        }
    }
    when {
        CurrentDeviceCard.noGroup == false -> {
            LinkButton(CurrentDeviceCard.deviceName + " " + M3KApp.getString(R.string.group), null, CurrentDeviceCard.deviceGroup, Icons.AutoMirrored.Filled.Message, LocalUriHandler.current)
        }
    }
    when {
        CurrentDeviceCard.noGuide == false -> {
            LinkButton(CurrentDeviceCard.deviceName + " " + M3KApp.getString(R.string.guide), null, CurrentDeviceCard.deviceGuide, Icons.Filled.Book, LocalUriHandler.current)
        }
    }

}