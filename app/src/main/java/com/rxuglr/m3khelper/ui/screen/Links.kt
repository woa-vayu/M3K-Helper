package com.rxuglr.m3khelper.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.rxuglr.m3khelper.M3KApp
import com.rxuglr.m3khelper.R
import com.rxuglr.m3khelper.ui.templates.Buttons
import com.rxuglr.m3khelper.util.Variables.FontSize
import com.rxuglr.m3khelper.util.Variables.GroupLink
import com.rxuglr.m3khelper.util.Variables.GuideLink
import com.rxuglr.m3khelper.util.Variables.NoGroup
import com.rxuglr.m3khelper.util.Variables.NoGuide
import com.rxuglr.m3khelper.util.sdp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination<RootGraph>()
@Composable
fun LinksScreen() {
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
            .padding(vertical = 10.sdp())
            .padding(horizontal = 10.sdp())
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.sdp()),
            modifier = Modifier
                .padding(vertical = 10.sdp())
                .width(500.sdp())
        ) {
            Buttons.LinkButton("Drivers", "Drivers", "google.com", R.drawable.ic_drivers, LocalUriHandler.current)
            Buttons.LinkButton("UEFI", "UEFI", "google.com", R.drawable.ic_uefi, LocalUriHandler.current)
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.sdp()),
            modifier = Modifier
                .padding(vertical = 10.sdp())
                .width(500.sdp())
        ) {
            when {
                !NoGroup.value -> {
                    Buttons.GuideGroupLinkButton("Group", "Group", GroupLink, Icons.AutoMirrored.Filled.Message, LocalUriHandler.current)
                }
            }
            when {
                !NoGuide.value -> {
                    Buttons.GuideGroupLinkButton("Guide", "Guide", GuideLink, Icons.Filled.Book, LocalUriHandler.current)
                }
            }
        }
    }
}

@Composable
private fun Portrait() {
    Buttons.LinkButton("Drivers", "Drivers", "google.com", R.drawable.ic_drivers, LocalUriHandler.current)
    Buttons.LinkButton("UEFI", "UEFI", "google.com", R.drawable.ic_uefi, LocalUriHandler.current)
    when {
        !NoGroup.value -> {
            Buttons.GuideGroupLinkButton("Group", "Group", GroupLink, Icons.AutoMirrored.Filled.Message, LocalUriHandler.current)
        }
    }
    when {
        !NoGuide.value -> {
            Buttons.GuideGroupLinkButton("Guide", "Guide", GuideLink, Icons.Filled.Book, LocalUriHandler.current)
        }
    }

}