package com.rxuglr.m3kwoahelper.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.rxuglr.m3kwoahelper.ui.theme.WOAHelperTheme
import com.rxuglr.m3kwoahelper.util.Commands
import com.rxuglr.m3kwoahelper.util.Commands.codenames
import com.rxuglr.m3kwoahelper.util.Commands.nomodem
import com.rxuglr.m3kwoahelper.util.RAM
import com.rxuglr.m3kwoahelper.woahApp
import com.topjohnwu.superuser.ShellUtils


class MainActivity : ComponentActivity() {

    private fun pxtodp(px: Float): Float {
        return px / (woahApp.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

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
                WOAHelper()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WOAHelper() {
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
                    MainScreen()
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
    fun MainScreen() {
        // main variables
        val ram = RAM().getMemory(applicationContext)
        val devicename = Commands.devicename()
        val slot = String.format("%S", ShellUtils.fastCmd("getprop ro.boot.slot_suffix")).drop(1)
        // unsupported device warning
        val unsupported = remember { mutableStateOf(false) }
        if ((devicename == "Unknown")) {
            unsupported.value = true
        }
        
        if (Build.DEVICE != "nabu") {
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
                    Row {
                        Image(
                            alignment = Alignment.TopStart,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .height(160.dp)
                                .width(140.dp),
                            painter = painterResource(
                                id = when (Build.DEVICE) {
                                    codenames[0] -> R.drawable.vayu
                                    codenames[1] -> R.drawable.vayu
                                    codenames[2] -> R.drawable.nabu
                                    codenames[3] -> R.drawable.raphael
                                    codenames[4] -> R.drawable.raphael
                                    else -> R.drawable.nabu
                                }
                            ),
                            contentDescription = null
                        )
                        Card(
                            Modifier
                                .height(200.dp)
                                .fillMaxWidth(),
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
                                    text = getString(R.string.ramvalue, ram)
                                )
                                // Works but only once when app is starting.. Comment out for now
                                //if (commands.mountstatus()) { Text(
                                //    modifier = Modifier
                                //        .fillMaxWidth()
                                //        .padding(start = 10.dp),
                                //    text = "Windows mounted: No"
                                //) } else { Text(
                                //    modifier = Modifier
                                //        .fillMaxWidth()
                                //        .padding(start = 10.dp),
                                //    text = "Windows mounted: Yes"
                                //) }

                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp),
                                    text = getString(R.string.paneltype, Commands.displaytype())
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                // What's the point of displaying name of backup image?
                                //Text(modifier = Modifier
                                //    .fillMaxWidth()
                                //    .padding(start = 10.dp),
                                //    text = "Boot backup: ")
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
                                                startActivity(
                                                    Intent(
                                                        Intent.ACTION_VIEW,
                                                        Uri.parse(
                                                            when (Build.DEVICE) {
                                                                codenames[0] -> "https://github.com/woa-vayu/Port-Windows-11-Poco-X3-pro"
                                                                codenames[1] -> "https://github.com/woa-vayu/Port-Windows-11-Poco-X3-pro"
                                                                codenames[2] -> "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5"
                                                                codenames[3] -> "https://github.com/graphiks/woa-raphael"
                                                                codenames[4] -> "https://github.com/graphiks/woa-raphael"
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
                                            startActivity(
                                                Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse(
                                                        when (Build.DEVICE) {
                                                            codenames[0] -> "https://t.me/winonvayualt"
                                                            codenames[1] -> "https://t.me/winonvayualt"
                                                            codenames[2] -> "https://t.me/nabuwoa"
                                                            codenames[3] -> "https://t.me/woaraphael"
                                                            codenames[4] -> "https://t.me/woaraphael"
                                                            codenames[5] -> "https://t.me/WinOnMi9/"
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
                    val textSize = 10.sp
                    val paddingValue = 10.dp
                    Buttons.BackupButton(textSize, paddingValue)
                    Buttons.MountButton(textSize, paddingValue)
                    if (!nomodem.contains(Build.DEVICE)) {
                        Buttons.ModemButton(textSize, paddingValue)
                    }
                    Buttons.UEFIButton(textSize, paddingValue)
                    Buttons.QuickbootButton(textSize, paddingValue)
                }
            }
        }
        else {
            Scaffold {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
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
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                        ){
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
                                    fontSize = 20.sp
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp),
                                    text = devicename,
                                    fontSize = 20.sp
                                )

                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp),
                                    text = getString(R.string.ramvalue, ram),
                                    fontSize = 20.sp
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp),
                                    text = getString(R.string.paneltype, Commands.displaytype()),
                                    fontSize = 20.sp
                                )
                                if (slot.isNotEmpty()) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 10.dp),
                                        text = getString(R.string.slot, slot),
                                        fontSize = 20.sp
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
                                                startActivity(
                                                    Intent(
                                                        Intent.ACTION_VIEW,
                                                        Uri.parse(
                                                            when (Build.DEVICE) {
                                                                codenames[0] -> "https://github.com/woa-vayu/Port-Windows-11-Poco-X3-pro"
                                                                codenames[1] -> "https://github.com/woa-vayu/Port-Windows-11-Poco-X3-pro"
                                                                codenames[2] -> "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5"
                                                                codenames[3] -> "https://github.com/graphiks/woa-raphael"
                                                                codenames[4] -> "https://github.com/graphiks/woa-raphael"
                                                                else -> "Unknown"
                                                            }
                                                        )
                                                    )
                                                )
                                            },
                                            label = {
                                                Text(
                                                    "Guide",
                                                    fontWeight = FontWeight.Bold
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
                                            startActivity(
                                                Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse(
                                                        when (Build.DEVICE) {
                                                            codenames[0] -> "https://t.me/winonvayualt"
                                                            codenames[1] -> "https://t.me/winonvayualt"
                                                            codenames[2] -> "https://t.me/nabuwoa"
                                                            codenames[3] -> "https://t.me/woaraphael"
                                                            codenames[4] -> "https://t.me/woaraphael"
                                                            codenames[5] -> "https://t.me/WinOnMi9/"
                                                            else -> "Unknown"
                                                        }
                                                    )
                                                )
                                            )
                                        },
                                        label = {
                                            Text(
                                                "Group",
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    )
                                }
                            }
                        }
                        Image(
                            alignment = Alignment.TopStart,
                            modifier = Modifier
                                .width((pxtodp(625f)).dp),
                            painter = painterResource(
                                id = when (Build.DEVICE) {
                                    codenames[0] -> R.drawable.vayu
                                    codenames[1] -> R.drawable.vayu
                                    codenames[2] -> R.drawable.nabu
                                    codenames[3] -> R.drawable.raphael
                                    codenames[4] -> R.drawable.raphael
                                    else -> R.drawable.nabu
                                }
                            ),
                            contentDescription = null
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                        ) {
                        val textSize = 20.sp
                        val paddingValue = 20.dp
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