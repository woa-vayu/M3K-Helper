package com.rxuglr.m3kwoahelper.ui.templates

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.rxuglr.m3kwoahelper.R
import com.rxuglr.m3kwoahelper.util.Variables.fontSize
import com.rxuglr.m3kwoahelper.util.Variables.lineHeight

object PopupDialogs {

    @Composable
    fun Dialog(
        icon: Painter,
        title: String?,
        description: String?,
        showDialog: Boolean,
        onDismiss: () -> Unit,
        onConfirm: () -> Unit,
    ) {
        if (showDialog) {
            AlertDialog(
                icon = {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp)
                    )
                },
                title = {
                    if (title != null) {
                        Text(
                            text = title,
                            textAlign = TextAlign.Center,
                            fontSize = fontSize,
                            lineHeight = lineHeight,
                        )
                    }
                },
                text = {
                    if (description != null) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = description,
                            textAlign = TextAlign.Center,
                            lineHeight = lineHeight,
                            fontSize = fontSize
                        )
                    }
                },
                onDismissRequest = onDismiss,
                dismissButton = {
                    AssistChip(
                        onClick = onConfirm,
                        label = {
                            Text(
                                modifier = Modifier.padding(top = 2.dp, bottom = 2.dp),
                                text = LocalContext.current.getString(R.string.yes),
                                color = MaterialTheme.colorScheme.inverseSurface,
                                fontSize = fontSize
                            )
                        }
                    )
                },
                confirmButton = {
                    AssistChip(
                        onClick = onDismiss,
                        label = {
                            Text(
                                modifier = Modifier.padding(top = 2.dp, bottom = 2.dp),
                                text = LocalContext.current.getString(R.string.no),
                                color = MaterialTheme.colorScheme.inverseSurface,
                                fontSize = fontSize
                            )
                        }
                    )
                }
            )
        }
    }

    @Composable
    fun SpinnerDialog(
        icon: Painter,
        title: Int,
        showDialog: Boolean,
        fontSize: TextUnit,
        lineHeight: TextUnit
    ) {
        if (showDialog) {
            AlertDialog(
                icon = {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp)
                    )
                },
                title = {
                    Text(
                        text = LocalContext.current.getString(title),
                        textAlign = TextAlign.Center,
                        fontSize = fontSize,
                        lineHeight = lineHeight
                    )
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(32.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                },
                onDismissRequest = {},
                dismissButton = {},
                confirmButton = {}
            )
        }
    }
}