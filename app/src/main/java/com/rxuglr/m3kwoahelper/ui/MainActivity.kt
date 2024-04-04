package com.rxuglr.m3kwoahelper.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rxuglr.m3kwoahelper.R
import com.rxuglr.m3kwoahelper.ui.templates.*
import com.rxuglr.m3kwoahelper.ui.templates.Cards.InfoCard
import com.rxuglr.m3kwoahelper.ui.templates.Cards.pxtodp
import com.rxuglr.m3kwoahelper.ui.templates.Images.DeviceImage
import com.rxuglr.m3kwoahelper.ui.theme.WOAHelperTheme
import com.rxuglr.m3kwoahelper.util.Commands.checksensors
import com.rxuglr.m3kwoahelper.util.Commands.dumpmodem
import com.rxuglr.m3kwoahelper.util.Commands.dumpsensors
import com.rxuglr.m3kwoahelper.util.Variables.Codename
import com.rxuglr.m3kwoahelper.util.Variables.NoModem
import com.rxuglr.m3kwoahelper.util.Variables.Unsupported
import com.rxuglr.m3kwoahelper.woahApp
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
            WOAHelperTheme {
                if (Shell.isAppGrantedRoot() == true) {
                    WOAHelper()
                } else {
                    AlertDialog(
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        title = {},
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = getString(R.string.no_root),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 35.sp,
                                fontSize = 25.sp
                            )
                        },
                        onDismissRequest = {},
                        dismissButton = {},
                        confirmButton = {}
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WOAHelper() {
        val navController = rememberNavController()
        val home = remember { mutableStateOf(true) }
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            imageVector = ImageVector.vectorResource(R.drawable.ic_windows),
                            contentDescription = null
                        )
                    },
                    title = {
                        Text(
                            text = "M3K WOA Helper",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        //    Text(
                        //        modifier = Modifier.fillMaxWidth(),
                        //        textAlign = TextAlign.Right,
                        //        text = "v1.5",
                        //        fontSize = 15.sp,
                        //        color = MaterialTheme.colorScheme.inverseSurface
                        //    )
                    },
                    actions = {
                        //    if (home.value) {
                        //        IconButton(onClick = {
                        //            navController.navigate("settings") {
                        //                navController.graph.startDestinationRoute?.let { route ->
                        //                    popUpTo(route) {
                        //                        saveState = true
                        //                    }
                        //                }
                        //                launchSingleTop = true
                        //                restoreState = true
                        //            }
                        //        }) {
                        //            Icon(
                        //                imageVector = Icons.Outlined.Settings,
                        //                contentDescription = null,
                        //                tint = MaterialTheme.colorScheme.primary
                        //            )
                        //        }
                        //    }
                        //    if (!home.value) {
                        //        IconButton(onClick = {
                        //            navController.navigate("main") {
                        //                navController.graph.startDestinationRoute?.let { route ->
                        //                    popUpTo(route) {
                        //                        saveState = true
                        //                    }
                        //                }
                        //                launchSingleTop = true
                        //                restoreState = true
                        //            }
                        //        }) {
                        //            Icon(
                        //                imageVector = Icons.Outlined.Home,
                        //                contentDescription = null,
                        //                tint = MaterialTheme.colorScheme.primary
                        //            )
                        //        }
                        //    }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp),
                        titleContentColor = MaterialTheme.colorScheme.inverseSurface,
                    )
                )
            }
        ) { innerPadding ->
            NavHost(navController, startDestination = "main", Modifier.padding(innerPadding)) {
                composable(
                    route = "settings",
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() }
                ) {
                    home.value = false
                    SettingsScreen()
                }
                composable(
                    route = "main",
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() }
                ) {
                    home.value = true
                    MainScreen()
                }
            }
        }
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun SettingsScreen() {
        Scaffold {
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MainScreen() {
        Scaffold {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
            ) {
                when {
                    Unsupported.value -> {
                        AlertDialog(
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .size(40.dp),
                                    imageVector = Icons.Outlined.Warning,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            title = {},
                            text = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "YOUR DEVICE ISN'T SUPPORTED!\n USING THIS APP CAN RESULT IN A BRICK",
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 35.sp,
                                    fontSize = 25.sp
                                )
                            },
                            onDismissRequest = { Unsupported.value = false },
                            dismissButton = {},
                            confirmButton = {
                                AssistChip(
                                    onClick = {
                                        Unsupported.value = false
                                    },
                                    label = {
                                        Text(
                                            modifier = Modifier.padding(
                                                top = 2.dp,
                                                bottom = 2.dp
                                            ),
                                            text = "I'm fine with that",
                                            color = MaterialTheme.colorScheme.inverseSurface
                                        )
                                    }
                                )
                            }
                        )
                    }
                }
                if (woahApp.resources.configuration.orientation != Configuration.ORIENTATION_PORTRAIT) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        ) {
                            InfoCard(
                                Modifier
                                    .height(200.dp)
                                    .width((pxtodp(625f)).dp), LocalUriHandler.current
                            )
                            DeviceImage(Modifier.width((pxtodp(625f)).dp))
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        ) {
                            Buttons.BackupButton()
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
                            }
                            Buttons.UEFIButton()
                            Buttons.QuickbootButton()
                        }
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            if (Codename == "nabu") {
                                10.dp
                            } else 0.dp
                        )
                    ) {
                        DeviceImage(Modifier.width((pxtodp(625f)).dp))
                        InfoCard(Modifier.height((pxtodp(743f)).dp), LocalUriHandler.current)
                    }
                    Buttons.BackupButton()
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
                    }
                    Buttons.UEFIButton()
                    Buttons.QuickbootButton()
                }
            }
        }
    }
}
