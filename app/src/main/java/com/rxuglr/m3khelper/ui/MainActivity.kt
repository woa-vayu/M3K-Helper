package com.rxuglr.m3khelper.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rxuglr.m3khelper.R
import com.rxuglr.m3khelper.ui.templates.Buttons
import com.rxuglr.m3khelper.ui.templates.Cards.InfoCard
import com.rxuglr.m3khelper.ui.templates.Images.DeviceImage
import com.rxuglr.m3khelper.ui.theme.M3KHelperTheme
import com.rxuglr.m3khelper.util.Commands.checksensors
import com.rxuglr.m3khelper.util.Commands.dumpmodem
import com.rxuglr.m3khelper.util.Commands.dumpsensors
import com.rxuglr.m3khelper.util.Variables.vars
import com.rxuglr.m3khelper.util.Variables.Codename
import com.rxuglr.m3khelper.util.Variables.FontSize
import com.rxuglr.m3khelper.util.Variables.LineHeight
import com.rxuglr.m3khelper.util.Variables.NoBoot
import com.rxuglr.m3khelper.util.Variables.NoFlash
import com.rxuglr.m3khelper.util.Variables.NoModem
import com.rxuglr.m3khelper.util.Variables.PaddingValue
import com.rxuglr.m3khelper.util.Variables.Warning
import com.rxuglr.m3khelper.util.Variables.Unsupported
import com.rxuglr.m3khelper.util.sdp
import com.rxuglr.m3khelper.util.ssp
import com.rxuglr.m3khelper.M3KApp
import com.rxuglr.m3khelper.ui.templates.PopupDialogs.NoRoot
import com.rxuglr.m3khelper.ui.templates.PopupDialogs.UnknownDevice
import com.rxuglr.m3khelper.ui.templates.PopupDialogs.UnsupportedDevice
import com.topjohnwu.superuser.Shell

class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            if (Codename != "nabu") {
                requestedOrientation = SCREEN_ORIENTATION_USER_PORTRAIT
            }
            M3KHelperTheme {
                if (Shell.isAppGrantedRoot() == true) {
                    vars(); Helper()
                } else NoRoot()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Helper() {
        LineHeight = 20.ssp()
        FontSize = 15.ssp()
        PaddingValue = 10.sdp()
        val navController = rememberNavController()
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
                            text = getString(R.string.app_name),
                            fontSize = FontSize,
                            fontWeight = FontWeight.Bold
                        )
                        /*
                        Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right,
                        text = "v1.5",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.inverseSurface
                        )
                        */
                    },
                    actions = {},
                )
            },
            /* bottomBar = {
                BottomAppBar {
                    NavigationBar(tonalElevation = 12.dp) {
                        Destinations.entries.forEach { destination ->
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true,
                                onClick = {

                                    navController.navigate(destination.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        destination.iconSelected,
                                        stringResource(destination.label)
                                    )
                                },
                                label = { Text(stringResource(destination.label)) },
                                alwaysShowLabel = false
                            )
                        }
                    }
                }
            } */
        ) { innerPadding ->
            NavHost(navController, startDestination = "main", Modifier.padding(innerPadding)) {
                composable(
                    route = "links",
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() }
                ) {
                    LinksScreen()
                }
                composable(
                    route = "main",
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() }
                ) {
                    MainScreen()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun LinksScreen() {
        Scaffold {
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MainScreen() {
        Scaffold {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.sdp()),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 10.sdp())
                    .padding(horizontal = 10.sdp())
                    .fillMaxWidth(),
            ) {
                when {
                    Warning.value -> {
                        UnknownDevice()
                    }
                }
                when {
                    Unsupported.value -> {
                        UnsupportedDevice()
                    }
                }
                if (M3KApp.resources.configuration.orientation != Configuration.ORIENTATION_PORTRAIT) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.sdp()),
                        modifier = Modifier
                            .padding(vertical = 10.sdp())
                            .padding(horizontal = 10.sdp())
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.sdp()),
                            modifier = Modifier
                                .padding(vertical = 10.sdp())
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
                        ) {
                            when {
                                !NoBoot.value -> {
                                    Buttons.BackupButton()
                                }
                            }
                            Buttons.MountButton()
                            when {
                                NoModem.value -> {
                                    if (!checksensors()) {
                                        Buttons.Button(
                                            R.string.dump_modemAsensors_title,
                                            R.string.dump_modemAsensors_subtitle,
                                            R.string.dump_modemAsensors_question,
                                            { dumpmodem(); dumpsensors() },
                                            R.drawable.ic_modem
                                        )
                                    } else {
                                        Buttons.Button(
                                            R.string.dump_modem_title,
                                            R.string.dump_modem_subtitle,
                                            R.string.dump_modem_question,
                                            { dumpmodem() },
                                            R.drawable.ic_modem
                                        )
                                    }
                                }

                                else -> {
                                    if (!checksensors()) {
                                        Buttons.Button(
                                            R.string.dump_sensors_title,
                                            R.string.dump_sensors_subtitle,
                                            R.string.dump_sensors_question,
                                            { dumpsensors() },
                                            R.drawable.ic_sensor
                                        )
                                    }
                                }
                            }
                            when {
                                !NoFlash.value -> {
                                    Buttons.UEFIButton(); Buttons.QuickbootButton()
                                }
                            }
                        }
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            if (Codename != "nabu") {
                                0.dp
                            } else 10.sdp()
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
                    Buttons.MountButton()
                    when {
                        !NoModem.value -> {
                            if (!checksensors()) {
                                Buttons.Button(
                                    R.string.dump_modemAsensors_title,
                                    R.string.dump_modemAsensors_subtitle,
                                    R.string.dump_modemAsensors_question,
                                    { dumpmodem(); dumpsensors() },
                                    R.drawable.ic_modem
                                )
                            } else {
                                Buttons.Button(
                                    R.string.dump_modem_title,
                                    R.string.dump_modem_subtitle,
                                    R.string.dump_modem_question,
                                    { dumpmodem() },
                                    R.drawable.ic_modem
                                )
                            }
                        }

                        else -> {
                            if (!checksensors()) {
                                Buttons.Button(
                                    R.string.dump_sensors_title,
                                    R.string.dump_sensors_subtitle,
                                    R.string.dump_sensors_question,
                                    { dumpsensors() },
                                    R.drawable.ic_sensor
                                )
                            }
                        }
                    }
                    when {
                        !NoFlash.value -> {
                            Buttons.UEFIButton(); Buttons.QuickbootButton()
                        }
                    }
                }
            }
        }
    }
}
