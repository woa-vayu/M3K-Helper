package com.rxuglr.m3kwoahelper.ui.templates

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rxuglr.m3kwoahelper.R
import com.rxuglr.m3kwoahelper.util.Commands.backup
import com.rxuglr.m3kwoahelper.util.Commands.flashuefi
import com.rxuglr.m3kwoahelper.util.Commands.mountstatus
import com.rxuglr.m3kwoahelper.util.Commands.mountwin
import com.rxuglr.m3kwoahelper.util.Commands.quickboot
import com.rxuglr.m3kwoahelper.util.Commands.umountwin
import com.rxuglr.m3kwoahelper.util.Variables.codename
import com.rxuglr.m3kwoahelper.util.Variables.fontSize
import com.rxuglr.m3kwoahelper.util.Variables.lineHeight
import com.rxuglr.m3kwoahelper.util.Variables.name
import com.rxuglr.m3kwoahelper.util.Variables.paddingValue
import com.rxuglr.m3kwoahelper.util.Variables.uefilist

object Buttons {

    @Composable
    fun Button(
        title: Int,
        subtitle: Int,
        question: Int,
        command: () -> Unit,
        icon: Int
    ) {
        val showDialog = remember { mutableStateOf(false) }
        val showSpinner = remember { mutableStateOf(false) }
        Card(
            onClick = { showDialog.value = true },
            Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp)
            )
        ) {
            when {
                showSpinner.value -> {
                    PopupDialogs.SpinnerDialog(
                        icon = painterResource(id = icon),
                        title = R.string.please_wait,
                        showDialog = showSpinner.value,
                        fontSize = fontSize,
                        lineHeight = lineHeight
                    )
                }
            }
            when {
                showDialog.value -> {
                    PopupDialogs.Dialog(
                        icon = painterResource(id = icon),
                        title = null,
                        description = LocalContext.current.getString(question),
                        showDialog = showDialog.value,
                        onDismiss = { showDialog.value = false },
                        onConfirm = ({
                            Thread {
                                showDialog.value = false
                                showSpinner.value = true
                                run { command }
                                showSpinner.value = false
                            }.start()
                        })
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(paddingValue),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column {
                    Text(
                        LocalContext.current.getString(title),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = fontSize,
                        lineHeight = lineHeight,
                    )
                    Text(
                        LocalContext.current.getString(subtitle),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        lineHeight = lineHeight,
                        fontSize = fontSize
                    )
                }
            }
        }
    }

    @Composable
    fun BackupButton() {
        val showBackupDialog = remember { mutableStateOf(false) }
        val showBackupSpinner = remember { mutableStateOf(false) }
        Card(
            onClick = { showBackupDialog.value = true },
            Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp)
            )
        ) {
            when {
                showBackupSpinner.value -> {
                    PopupDialogs.SpinnerDialog(
                        icon = painterResource(id = R.drawable.ic_disk),
                        title = R.string.please_wait,
                        showDialog = showBackupSpinner.value,
                        fontSize = fontSize,
                        lineHeight = lineHeight
                    )
                }
            }
            when {
                showBackupDialog.value -> {
                    AlertDialog(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_disk),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        title = {
                        },
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = LocalContext.current.getString(R.string.backup_boot_question),
                                textAlign = TextAlign.Center,
                                fontSize = fontSize,
                                lineHeight = lineHeight
                            )
                        },
                        onDismissRequest = ({ showBackupDialog.value = false; }),
                        dismissButton = {
                            Row(
                                Modifier.align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.spacedBy(15.dp)
                            ) {
                                AssistChip(
                                    onClick = {
                                        Thread {
                                            showBackupDialog.value = false
                                            showBackupSpinner.value = true
                                            backup(2)
                                            showBackupSpinner.value = false
                                        }.start()
                                    },
                                    label = {
                                        Text(
                                            modifier = Modifier.padding(
                                                top = 2.dp,
                                                bottom = 2.dp
                                            ),
                                            text = "Android",
                                            color = MaterialTheme.colorScheme.inverseSurface,
                                            fontSize = fontSize
                                        )
                                    }
                                )
                                AssistChip(
                                    onClick = {
                                        Thread {
                                            showBackupDialog.value = false
                                            showBackupSpinner.value = true
                                            backup(1)
                                            showBackupSpinner.value = false
                                        }.start()
                                    },
                                    label = {
                                        Text(
                                            modifier = Modifier.padding(
                                                top = 2.dp,
                                                bottom = 2.dp
                                            ),
                                            text = "Windows",
                                            color = MaterialTheme.colorScheme.inverseSurface,
                                            fontSize = fontSize
                                        )
                                    }
                                )
                                AssistChip(
                                    onClick = ({ showBackupDialog.value = false; }),
                                    label = {
                                        Text(
                                            modifier = Modifier.padding(
                                                top = 2.dp,
                                                bottom = 2.dp
                                            ),
                                            text = LocalContext.current.getString(R.string.no),
                                            color = MaterialTheme.colorScheme.inverseSurface,
                                            fontSize = fontSize
                                        )
                                    }
                                )
                            }
                        },
                        confirmButton = {
                        }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(paddingValue),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    painter = painterResource(id = R.drawable.ic_disk),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column {
                    Text(
                        LocalContext.current.getString(R.string.backup_boot_title),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = fontSize,
                        lineHeight = lineHeight,
                    )
                    Text(
                        LocalContext.current.getString(R.string.backup_boot_subtitle),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        lineHeight = lineHeight,
                        fontSize = fontSize
                    )
                }
            }
        }
    }

    @Composable
    fun MountButton() {
        val showMountDialog = remember { mutableStateOf(false) }
        Card(
            onClick = { showMountDialog.value = true },
            Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp)
            )
        ) {
            when {
                showMountDialog.value -> {
                    if (mountstatus()) {
                        PopupDialogs.Dialog(
                            icon = painterResource(id = R.drawable.ic_windows),
                            title = null,
                            description = LocalContext.current.getString(R.string.mnt_question),
                            showDialog = showMountDialog.value,
                            onDismiss = { showMountDialog.value = false },
                            onConfirm = ({
                                mountwin()
                                showMountDialog.value = false
                            })
                        )
                    } else {
                        PopupDialogs.Dialog(
                            painterResource(id = R.drawable.ic_windows),
                            null,
                            LocalContext.current.getString(R.string.umnt_question),
                            showMountDialog.value,
                            onDismiss = ({ showMountDialog.value = false; }),
                            onConfirm = ({
                                umountwin()
                                showMountDialog.value = false
                            })
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(paddingValue),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    painter = painterResource(id = R.drawable.ic_windows),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column {
                    val mounted: Int =
                        if (mountstatus()) {
                            R.string.mnt_title
                        } else {
                            R.string.umnt_title
                        }
                    Text(
                        LocalContext.current.getString(mounted),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = fontSize
                    )
                    Text(
                        LocalContext.current.getString(R.string.mnt_subtitle),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        lineHeight = lineHeight,
                        fontSize = fontSize
                    )
                }
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    @Composable
    fun UEFIButton() {
        val showUEFIDialog = remember { mutableStateOf(false) }
        val showUEFISpinner = remember { mutableStateOf(false) }
        Card(
            onClick = {
                if (!uefilist.contains(99)) {
                    showUEFIDialog.value = true
                }
            },
            Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp)
            )
        ) {
            when {
                showUEFISpinner.value -> {
                    PopupDialogs.SpinnerDialog(
                        icon = painterResource(id = R.drawable.ic_uefi),
                        title = R.string.please_wait,
                        showDialog = showUEFISpinner.value,
                        lineHeight = lineHeight,
                        fontSize = fontSize
                    )
                }
            }
            when {
                showUEFIDialog.value -> {
                    AlertDialog(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_uefi),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(40.dp)
                            )
                        },
                        title = {
                        },
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = LocalContext.current.getString(R.string.flash_uefi_question),
                                textAlign = TextAlign.Center,
                                fontSize = fontSize
                            )
                        },
                        onDismissRequest = ({ showUEFIDialog.value = false; }),
                        dismissButton = {
                            Row(
                                Modifier.align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                if (uefilist.contains(120)) {
                                    AssistChip(
                                        onClick = {
                                            Thread {
                                                showUEFIDialog.value = false
                                                showUEFISpinner.value = true
                                                flashuefi(0)
                                                showUEFISpinner.value = false
                                            }.start()
                                        },
                                        label = {
                                            Text(
                                                modifier = Modifier.padding(
                                                    top = 2.dp,
                                                    bottom = 2.dp
                                                ),
                                                text = "120Hz",
                                                color = MaterialTheme.colorScheme.inverseSurface,
                                                fontSize = fontSize
                                            )
                                        }
                                    )
                                }
                                if (uefilist.contains(60)) {
                                    AssistChip(
                                        onClick = {
                                            Thread {
                                                showUEFIDialog.value = false
                                                showUEFISpinner.value = true
                                                flashuefi(1)
                                                showUEFISpinner.value = false
                                            }.start()
                                        },
                                        label = {
                                            Text(
                                                modifier = Modifier.padding(
                                                    top = 2.dp,
                                                    bottom = 2.dp
                                                ),
                                                text = "60Hz",
                                                color = MaterialTheme.colorScheme.inverseSurface,
                                                fontSize = fontSize
                                            )
                                        }
                                    )
                                }
                                if (uefilist
                                        .contains(1) and (!uefilist.contains(60) or !uefilist.contains(
                                        120
                                    ))
                                ) {
                                    AssistChip(
                                        onClick = {
                                            Thread {
                                                showUEFIDialog.value = false
                                                showUEFISpinner.value = true
                                                flashuefi(2)
                                                showUEFISpinner.value = false
                                            }.start()
                                        },
                                        label = {
                                            Text(
                                                modifier = Modifier.padding(
                                                    top = 2.dp,
                                                    bottom = 2.dp
                                                ),
                                                text = LocalContext.current.getString(R.string.yes),
                                                color = MaterialTheme.colorScheme.inverseSurface,
                                                fontSize = fontSize
                                            )
                                        }
                                    )
                                }
                                AssistChip(
                                    onClick = ({ showUEFIDialog.value = false; }),
                                    label = {
                                        Text(
                                            modifier = Modifier.padding(
                                                top = 2.dp,
                                                bottom = 2.dp
                                            ),
                                            text = LocalContext.current.getString(R.string.no),
                                            color = MaterialTheme.colorScheme.inverseSurface,
                                            fontSize = fontSize
                                        )
                                    }
                                )
                            }
                        },
                        confirmButton = {
                        }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(paddingValue),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    painter = painterResource(id = R.drawable.ic_uefi),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                val title: Int
                val subtitle: Int
                if (!uefilist.contains(99)) {
                    title = R.string.flash_uefi_title
                    subtitle = R.string.flash_uefi_subtitle
                } else {
                    title = R.string.uefi_not_found_title
                    subtitle = R.string.uefi_not_found_subtitle
                }
                Column {
                    Text(
                        LocalContext.current.getString(title),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.Bold,
                        lineHeight = lineHeight,
                        fontSize = fontSize
                    )
                    Text(
                        LocalContext.current.getString(subtitle, name),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        lineHeight = lineHeight,
                        fontSize = fontSize
                    )
                }
            }
        }
    }

    @Composable
    fun QuickbootButton() {
        if (!uefilist.contains(99)) {
            val showQuickBootDialog = remember { mutableStateOf(false) }
            val showQuickBootSpinner = remember { mutableStateOf(false) }
            Card(
                onClick = { showQuickBootDialog.value = true },
                Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp)
                )
            ) {
                when {
                    showQuickBootSpinner.value -> {
                        PopupDialogs.SpinnerDialog(
                            icon = painterResource(id = R.drawable.ic_windows),
                            title = R.string.please_wait,
                            showDialog = showQuickBootSpinner.value,
                            lineHeight = lineHeight,
                            fontSize = fontSize
                        )
                    }
                }
                when {
                    showQuickBootDialog.value -> {
                        AlertDialog(
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_windows),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(40.dp)
                                )
                            },
                            title = {
                            },
                            text = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = LocalContext.current.getString(R.string.quickboot_question),
                                    textAlign = TextAlign.Center,
                                    fontSize = fontSize
                                )
                            },
                            onDismissRequest = ({ showQuickBootDialog.value = false; }),
                            dismissButton = {
                                Row(
                                    Modifier.align(Alignment.CenterHorizontally),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    if (uefilist.contains(120)) {
                                        AssistChip(
                                            modifier = Modifier.width(95.dp),
                                            onClick = {
                                                Thread {
                                                    showQuickBootDialog.value = false
                                                    showQuickBootSpinner.value = true
                                                    quickboot(0)
                                                    showQuickBootSpinner.value = false
                                                }.start()
                                            },
                                            label = {
                                                Text(
                                                    modifier = Modifier.padding(
                                                        top = 2.dp,
                                                        bottom = 2.dp
                                                    ),
                                                    text = LocalContext.current.getString(R.string.quickboot_question2),
                                                    color = MaterialTheme.colorScheme.inverseSurface,
                                                    fontSize = fontSize
                                                )
                                            }
                                        )
                                    }
                                    if (uefilist.contains(60)) {
                                        AssistChip(
                                            modifier = Modifier.width(95.dp),
                                            onClick = {
                                                Thread {
                                                    showQuickBootDialog.value = false
                                                    showQuickBootSpinner.value = true
                                                    quickboot(1)
                                                    showQuickBootSpinner.value = false
                                                }.start()
                                            },
                                            label = {
                                                Text(
                                                    modifier = Modifier.padding(
                                                        top = 2.dp,
                                                        bottom = 2.dp
                                                    ),
                                                    text = LocalContext.current.getString(R.string.quickboot_question1),
                                                    color = MaterialTheme.colorScheme.inverseSurface,
                                                    fontSize = fontSize
                                                )
                                            }
                                        )
                                    }
                                    if (uefilist
                                            .contains(1) and (!uefilist.contains(60) or !uefilist.contains(
                                            120
                                        ))
                                    ) {
                                        AssistChip(
                                            onClick = {
                                                Thread {
                                                    showQuickBootDialog.value = false
                                                    showQuickBootSpinner.value = true
                                                    quickboot(2)
                                                    showQuickBootSpinner.value = false
                                                }.start()
                                            },
                                            label = {
                                                Text(
                                                    text = LocalContext.current.getString(R.string.yes),
                                                    color = MaterialTheme.colorScheme.inverseSurface,
                                                    fontSize = fontSize
                                                )
                                            }
                                        )
                                    }
                                    AssistChip(
                                        onClick = ({ showQuickBootDialog.value = false; }),
                                        label = {
                                            Text(
                                                modifier = Modifier.padding(
                                                    top = 2.dp,
                                                    bottom = 2.dp
                                                ),
                                                text = LocalContext.current.getString(R.string.no),
                                                color = MaterialTheme.colorScheme.inverseSurface,
                                                fontSize = fontSize
                                            )
                                        }
                                    )
                                }
                            },
                            confirmButton = {
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(paddingValue),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        painter = painterResource(id = R.drawable.ic_windows),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Column {
                        Text(
                            LocalContext.current.getString(R.string.quickboot_title),
                            color = MaterialTheme.colorScheme.inverseSurface,
                            fontWeight = FontWeight.Bold,
                            lineHeight = lineHeight,
                            fontSize = fontSize
                        )
                        val modem = when (codename) {
                            "nabu" -> R.string.quickboot_subtitle_nomodem
                            else -> R.string.quickboot_subtitle
                        }
                        Text(
                            LocalContext.current.getString(modem),
                            color = MaterialTheme.colorScheme.inverseSurface,
                            lineHeight = lineHeight,
                            fontSize = fontSize
                        )
                    }
                }
            }
        }
    }
}