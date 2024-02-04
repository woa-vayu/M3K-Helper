package com.rxuglr.m3kwoahelper.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rxuglr.m3kwoahelper.R
import com.rxuglr.m3kwoahelper.ui.templates.*
import com.rxuglr.m3kwoahelper.ui.templates.Cards.InfoCard
import com.rxuglr.m3kwoahelper.ui.templates.Cards.LandScapeInfoCard
import com.rxuglr.m3kwoahelper.ui.templates.Images.DeviceImage
import com.rxuglr.m3kwoahelper.ui.templates.Images.LandScapeDeviceImage
import com.rxuglr.m3kwoahelper.ui.theme.WOAHelperTheme
import com.rxuglr.m3kwoahelper.util.Commands
import com.rxuglr.m3kwoahelper.util.Commands.nomodem
import com.rxuglr.m3kwoahelper.util.RAM
import com.topjohnwu.superuser.ShellUtils


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            requestedOrientation = if (Build.DEVICE == "nabu") {
                SCREEN_ORIENTATION_USER_LANDSCAPE
            } else {
                SCREEN_ORIENTATION_UNSPECIFIED
            }
            WOAHelperTheme {
                // main variables
                val ram = RAM().getMemory(applicationContext)
                val devicename = Commands.devicename()
                val slot = String.format("%S", ShellUtils.fastCmd("getprop ro.boot.slot_suffix")).drop(1)
                // unsupported device warning
                val unsupported = remember { mutableStateOf(false) }
                if ((devicename == "Unknown")) {
                    unsupported.value = true
                }

                var textSize = 10.sp
                var paddingValue = 10.dp

                if (Build.DEVICE == "nabu") {
                    textSize = 20.sp
                    paddingValue = 20.dp
                }
                WOAHelper(ram, slot, textSize, paddingValue, devicename, unsupported)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WOAHelper(
        ram: String,
        slot: String,
        textSize: TextUnit,
        paddingValue: Dp,
        devicename: String,
        unsupported: MutableState<Boolean>
    ) {
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            imageVector = ImageVector.vectorResource(R.drawable.ic_windows),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
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
                        val home = remember { mutableStateOf(true) }
                        if (home.value) {
                            IconButton(onClick = {
                                navController.navigate("settings") {
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                home.value = false
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Settings,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        if (!home.value) {
                            IconButton(onClick = {
                                navController.navigate("main") {
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                home.value = true
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
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
                    enterTransition = { slideInVertically { height -> height } + fadeIn() },
                    exitTransition = { slideOutVertically { height -> height } + fadeOut() }
                ) {
                    SettingsScreen()
                }
                composable(
                    route = "main",
                    enterTransition = { slideInVertically { height -> -height } + fadeIn() },
                    exitTransition = { slideOutVertically { height -> -height } + fadeOut() }
                ) {
                    MainScreen(ram, slot, textSize, paddingValue, devicename, unsupported)
                }
            }
        }
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun SettingsScreen() {
        Scaffold {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
                    ),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        text = "M3K Windows on Android Helper",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.size(3.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp),
                        text = "Version: 1.0",
                        textAlign = TextAlign.Center
                    )
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
                    )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        text = "Original WOA Helper",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(3.dp))
                    Card(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .align(Alignment.CenterHorizontally),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                                12.dp
                            )
                        )
                    ) {
                        Column {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp),
                                text = "Made by",
                                textAlign = TextAlign.Center,
                            )
                            Row(
                                Modifier
                                    .align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically),
                                    text = "KuatoDEV"
                                )
                                Card {
                                    Image(
                                        modifier = Modifier.size(35.dp),
                                        painter = painterResource(id = R.drawable.kuato),
                                        contentDescription = null
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Row(
                                Modifier
                                    .align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = "halal-beef"
                                )
                                Card {
                                    Image(
                                        modifier = Modifier.size(35.dp),
                                        painter = painterResource(id = R.drawable.halal_beef),
                                        contentDescription = null
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Row(
                                Modifier
                                    .align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = "mobxprjkt"
                                )
                                Card {
                                    Image(
                                        modifier = Modifier.size(35.dp),
                                        painter = painterResource(id = R.drawable.mobxprjkt),
                                        contentDescription = null
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Row(
                                Modifier
                                    .align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = "Nabil ABA"
                                )
                                Card {
                                    Image(
                                        modifier = Modifier.size(35.dp),
                                        painter = painterResource(id = R.drawable.nabil_aba),
                                        contentDescription = null
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(5.dp))
                            Row(
                                Modifier
                                    .align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = "Otang45  "
                                )
                                Card {
                                    Image(
                                        modifier = Modifier.size(35.dp),
                                        painter = painterResource(id = R.drawable.otang45),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
                    )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        text = "M3K WOA Helper",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Card(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .align(Alignment.CenterHorizontally),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                                12.dp
                            )
                        )
                    ) {
                        Column {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp),
                                text = "Made by",
                                textAlign = TextAlign.Center,
                            )
                            Row(
                                Modifier
                                    .align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically),
                                    text = "rxuglr       "
                                )
                                Card {
                                    Image(
                                        modifier = Modifier.size(35.dp),
                                        painter = painterResource(id = R.drawable.rxuglr),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MainScreen(
        ram: String,
        slot: String,
        textSize: TextUnit,
        paddingValue: Dp,
        devicename: String,
        unsupported: MutableState<Boolean>
    ) {
        Scaffold {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
            ) {
                when {
                    unsupported.value -> {
                        AlertDialog(
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.Warning,
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
                                    fontSize = 20.sp
                                )
                            },
                            onDismissRequest = { unsupported.value = false },
                            dismissButton = {},
                            confirmButton = {
                                AssistChip(
                                    onClick = {
                                        unsupported.value = false
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
                if (Build.DEVICE != "nabu") {
                    Row {
                        DeviceImage()
                        InfoCard(devicename, ram)
                    }
                    Buttons.BackupButton(textSize, paddingValue)
                    Buttons.MountButton(textSize, paddingValue)
                    if (!nomodem.contains(Build.DEVICE)) {
                        Buttons.ModemButton(textSize, paddingValue)
                    }
                    Buttons.UEFIButton(textSize, paddingValue)
                    Buttons.QuickbootButton(textSize, paddingValue)
                }
                    else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        ) {
                            LandScapeInfoCard(devicename, ram, slot, textSize)
                            LandScapeDeviceImage()
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        ) {
                            Buttons.BackupButton(textSize, paddingValue)
                            Buttons.MountButton(textSize, paddingValue)
                            Buttons.UEFIButton(textSize, paddingValue)
                            Buttons.QuickbootButton(textSize, paddingValue)
                        }
                    }
                }
            }
        }
    }
}
