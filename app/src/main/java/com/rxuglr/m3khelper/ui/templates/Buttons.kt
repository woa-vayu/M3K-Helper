package com.rxuglr.m3khelper.ui.templates

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.rxuglr.m3khelper.util.Commands.backup
import com.rxuglr.m3khelper.util.Commands.flashuefi
import com.rxuglr.m3khelper.util.Commands.mountstatus
import com.rxuglr.m3khelper.util.Commands.mountwin
import com.rxuglr.m3khelper.util.Commands.quickboot
import com.rxuglr.m3khelper.util.Commands.umountwin
import com.rxuglr.m3khelper.util.Variables.FontSize
import com.rxuglr.m3khelper.util.Variables.LineHeight
import com.rxuglr.m3khelper.util.Variables.Name
import com.rxuglr.m3khelper.util.Variables.NoModem
import com.rxuglr.m3khelper.util.Variables.PaddingValue
import com.rxuglr.m3khelper.util.Variables.UEFIList
import com.rxuglr.m3khelper.util.sdp
import com.rxuglr.m3khelper.M3KApp
import com.rxuglr.m3khelper.R

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
        ElevatedCard(
            onClick = { showDialog.value = true },
            Modifier
                .height(105.sdp())
                .fillMaxWidth(),
        ) {
            when {
                showSpinner.value -> {
                    PopupDialogs.SpinnerDialog(
                        icon = painterResource(id = icon),
                        title = R.string.please_wait,
                        showDialog = showSpinner.value,
                    )
                }
            }
            when {
                showDialog.value -> {
                    PopupDialogs.Dialog(
                        icon = painterResource(id = icon),
                        title = null,
                        description = M3KApp.getString(question),
                        showDialog = showDialog.value,
                        onDismiss = { showDialog.value = false },
                        onConfirm = ({
                            Thread {
                                showDialog.value = false
                                showSpinner.value = true
                                command()
                                showSpinner.value = false
                            }.start()
                        })
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(PaddingValue),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.sdp())
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.sdp()),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column {
                    Text(
                        M3KApp.getString(title),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = FontSize,
                        lineHeight = LineHeight,
                    )
                    Text(
                        M3KApp.getString(subtitle),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        lineHeight = LineHeight,
                        fontSize = FontSize
                    )
                }
            }
        }
    }

    @Composable
    fun BackupButton() {
        val showBackupDialog = remember { mutableStateOf(false) }
        val showBackupSpinner = remember { mutableStateOf(false) }
        ElevatedCard(
            onClick = { showBackupDialog.value = true },
            Modifier
                .height(105.sdp())
                .fillMaxWidth(),
        ) {
            when {
                showBackupSpinner.value -> {
                    PopupDialogs.SpinnerDialog(
                        icon = painterResource(id = R.drawable.ic_backup),
                        title = R.string.please_wait,
                        showDialog = showBackupSpinner.value,
                    )
                }
            }
            when {
                showBackupDialog.value -> {
                    AlertDialog(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_backup),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(40.sdp())
                            )
                        },
                        title = {
                        },
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = M3KApp.getString(R.string.backup_boot_question),
                                textAlign = TextAlign.Center,
                                fontSize = FontSize,
                                lineHeight = LineHeight
                            )
                        },
                        onDismissRequest = ({ showBackupDialog.value = false; }),
                        dismissButton = {
                            Row(
                                Modifier.align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.spacedBy(15.sdp())
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
                                                top = 2.sdp(),
                                                bottom = 2.sdp()
                                            ),
                                            text = M3KApp.getString(R.string.android),
                                            color = MaterialTheme.colorScheme.inverseSurface,
                                            fontSize = FontSize
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
                                                top = 2.sdp(),
                                                bottom = 2.sdp()
                                            ),
                                            text = M3KApp.getString(R.string.windows),
                                            color = MaterialTheme.colorScheme.inverseSurface,
                                            fontSize = FontSize
                                        )
                                    }
                                )
                                AssistChip(
                                    onClick = ({ showBackupDialog.value = false; }),
                                    label = {
                                        Text(
                                            modifier = Modifier.padding(
                                                top = 2.sdp(),
                                                bottom = 2.sdp()
                                            ),
                                            text = M3KApp.getString(R.string.no),
                                            color = MaterialTheme.colorScheme.inverseSurface,
                                            fontSize = FontSize
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
                    .fillMaxHeight()
                    .padding(PaddingValue),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.sdp())
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.sdp()),
                    painter = painterResource(id = R.drawable.ic_backup),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column {
                    Text(
                        M3KApp.getString(R.string.backup_boot_title),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = FontSize,
                        lineHeight = LineHeight,
                    )
                    Text(
                        M3KApp.getString(R.string.backup_boot_subtitle),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        lineHeight = LineHeight,
                        fontSize = FontSize
                    )
                }
            }
        }
    }

    @Composable
    fun MountButton() {
        val showMountDialog = remember { mutableStateOf(false) }
        ElevatedCard(
            onClick = { showMountDialog.value = true },
            Modifier
                .height(105.sdp())
                .fillMaxWidth(),
        ) {
            when {
                showMountDialog.value -> {
                    if (mountstatus()) {
                        PopupDialogs.Dialog(
                            icon = painterResource(id = R.drawable.ic_folder_open),
                            title = null,
                            description = M3KApp.getString(R.string.mnt_question),
                            showDialog = showMountDialog.value,
                            onDismiss = { showMountDialog.value = false },
                            onConfirm = ({
                                mountwin()
                                showMountDialog.value = false
                            })
                        )
                    } else {
                        PopupDialogs.Dialog(
                            painterResource(id = R.drawable.ic_folder),
                            null,
                            M3KApp.getString(R.string.umnt_question),
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
                    .fillMaxHeight()
                    .padding(PaddingValue),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.sdp())
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.sdp()),
                    painter = painterResource(
                        id = if (mountstatus()) {
                            R.drawable.ic_folder_open
                        } else {
                            R.drawable.ic_folder
                        }
                    ),
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
                        M3KApp.getString(mounted),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.Bold,
                        lineHeight = LineHeight,
                        fontSize = FontSize
                    )
                    Text(
                        M3KApp.getString(R.string.mnt_subtitle),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        lineHeight = LineHeight,
                        fontSize = FontSize
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
        ElevatedCard(
            onClick = {
                showUEFIDialog.value = true
            },
            Modifier
                .height(105.sdp())
                .fillMaxWidth(),
            enabled = !UEFIList.contains(99)
        ) {
            when {
                showUEFISpinner.value -> {
                    PopupDialogs.SpinnerDialog(
                        icon = painterResource(id = R.drawable.ic_uefi),
                        title = R.string.please_wait,
                        showDialog = showUEFISpinner.value,
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
                                modifier = Modifier.size(40.sdp())
                            )
                        },
                        title = {
                        },
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = M3KApp.getString(R.string.flash_uefi_question),
                                textAlign = TextAlign.Center,
                                fontSize = FontSize
                            )
                        },
                        onDismissRequest = ({ showUEFIDialog.value = false; }),
                        dismissButton = {
                            Row(
                                Modifier.align(Alignment.CenterHorizontally),
                                horizontalArrangement = Arrangement.spacedBy(10.sdp())
                            ) {
                                if (UEFIList.contains(120)) {
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
                                                    top = 2.sdp(),
                                                    bottom = 2.sdp()
                                                ),
                                                text = M3KApp.getString(R.string.quickboot_question3),
                                                color = MaterialTheme.colorScheme.inverseSurface,
                                                fontSize = FontSize
                                            )
                                        }
                                    )
                                }
                                if (UEFIList.contains(90)) {
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
                                                    top = 2.sdp(),
                                                    bottom = 2.sdp()
                                                ),
                                                text = M3KApp.getString(R.string.quickboot_question2),
                                                color = MaterialTheme.colorScheme.inverseSurface,
                                                fontSize = FontSize
                                            )
                                        }
                                    )
                                }
                                if (UEFIList.contains(60)) {
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
                                                    top = 2.sdp(),
                                                    bottom = 2.sdp()
                                                ),
                                                text = M3KApp.getString(R.string.quickboot_question1),
                                                color = MaterialTheme.colorScheme.inverseSurface,
                                                fontSize = FontSize
                                            )
                                        }
                                    )
                                }
                                if (UEFIList.contains(1)
                                    and
                                    (!UEFIList.contains(60)
                                            && !UEFIList.contains(90)
                                            && !UEFIList.contains(120)
                                            )
                                ) {
                                    AssistChip(
                                        onClick = {
                                            Thread {
                                                showUEFIDialog.value = false
                                                showUEFISpinner.value = true
                                                flashuefi(3)
                                                showUEFISpinner.value = false
                                            }.start()
                                        },
                                        label = {
                                            Text(
                                                modifier = Modifier.padding(
                                                    top = 2.sdp(),
                                                    bottom = 2.sdp()
                                                ),
                                                text = M3KApp.getString(R.string.yes),
                                                color = MaterialTheme.colorScheme.inverseSurface,
                                                fontSize = FontSize
                                            )
                                        }
                                    )
                                }
                                AssistChip(
                                    onClick = ({ showUEFIDialog.value = false; }),
                                    label = {
                                        Text(
                                            modifier = Modifier.padding(
                                                top = 2.sdp(),
                                                bottom = 2.sdp()
                                            ),
                                            text = M3KApp.getString(R.string.no),
                                            color = MaterialTheme.colorScheme.inverseSurface,
                                            fontSize = FontSize
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
                    .fillMaxHeight()
                    .padding(PaddingValue),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.sdp())
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.sdp()),
                    painter = painterResource(id = R.drawable.ic_uefi),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                val title: Int
                val subtitle: Int
                if (!UEFIList.contains(99)) {
                    title = R.string.flash_uefi_title
                    subtitle = R.string.flash_uefi_subtitle
                } else {
                    title = R.string.uefi_not_found_title
                    subtitle = R.string.uefi_not_found_subtitle
                }
                Column {
                    Text(
                        M3KApp.getString(title),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        fontWeight = FontWeight.Bold,
                        lineHeight = LineHeight,
                        fontSize = FontSize
                    )
                    Text(
                        M3KApp.getString(subtitle, Name),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        lineHeight = LineHeight,
                        fontSize = FontSize
                    )
                }
            }
        }
    }

    @Composable
    fun QuickbootButton() {
        if (!UEFIList.contains(99)) {
            val showQuickBootDialog = remember { mutableStateOf(false) }
            val showQuickBootSpinner = remember { mutableStateOf(false) }
            ElevatedCard(
                onClick = { showQuickBootDialog.value = true },
                Modifier
                    .height(105.sdp())
                    .fillMaxWidth(),
            ) {
                when {
                    showQuickBootSpinner.value -> {
                        PopupDialogs.SpinnerDialog(
                            icon = painterResource(id = R.drawable.ic_windows),
                            title = R.string.please_wait,
                            showDialog = showQuickBootSpinner.value,
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
                                    modifier = Modifier.size(40.sdp())
                                )
                            },
                            title = {
                            },
                            text = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = M3KApp.getString(R.string.quickboot_question),
                                    textAlign = TextAlign.Center,
                                    fontSize = FontSize
                                )
                            },
                            onDismissRequest = ({ showQuickBootDialog.value = false; }),
                            dismissButton = {
                                Row(
                                    Modifier.align(Alignment.CenterHorizontally),
                                    horizontalArrangement = Arrangement.spacedBy(10.sdp())
                                ) {
                                    if (UEFIList.contains(120)) {
                                        AssistChip(
                                            modifier = Modifier.width(105.sdp()),
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
                                                        top = 2.sdp(),
                                                        bottom = 2.sdp()
                                                    ),
                                                    text = M3KApp.getString(R.string.quickboot_question3),
                                                    color = MaterialTheme.colorScheme.inverseSurface,
                                                    fontSize = FontSize
                                                )
                                            }
                                        )
                                    }
                                    if (UEFIList.contains(90)) {
                                        AssistChip(
                                            modifier = Modifier.width(105.sdp()),
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
                                                        top = 2.sdp(),
                                                        bottom = 2.sdp()
                                                    ),
                                                    text = M3KApp.getString(R.string.quickboot_question2),
                                                    color = MaterialTheme.colorScheme.inverseSurface,
                                                    fontSize = FontSize
                                                )
                                            }
                                        )
                                    }
                                    if (UEFIList.contains(60)) {
                                        AssistChip(
                                            modifier = Modifier.width(105.sdp()),
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
                                                    modifier = Modifier.padding(
                                                        top = 2.sdp(),
                                                        bottom = 2.sdp()
                                                    ),
                                                    text = M3KApp.getString(R.string.quickboot_question1),
                                                    color = MaterialTheme.colorScheme.inverseSurface,
                                                    fontSize = FontSize
                                                )
                                            }
                                        )
                                    }
                                    if (UEFIList.contains(1)
                                        and
                                        (!UEFIList.contains(60)
                                                && !UEFIList.contains(90)
                                                && !UEFIList.contains(120)
                                                )
                                    ) {
                                        AssistChip(
                                            onClick = {
                                                Thread {
                                                    showQuickBootDialog.value = false
                                                    showQuickBootSpinner.value = true
                                                    quickboot(3)
                                                    showQuickBootSpinner.value = false
                                                }.start()
                                            },
                                            label = {
                                                Text(
                                                    text = M3KApp.getString(R.string.yes),
                                                    color = MaterialTheme.colorScheme.inverseSurface,
                                                    fontSize = FontSize
                                                )
                                            }
                                        )
                                    }
                                    AssistChip(
                                        onClick = ({ showQuickBootDialog.value = false; }),
                                        label = {
                                            Text(
                                                modifier = Modifier.padding(
                                                    top = 2.sdp(),
                                                    bottom = 2.sdp()
                                                ),
                                                text = M3KApp.getString(R.string.no),
                                                color = MaterialTheme.colorScheme.inverseSurface,
                                                fontSize = FontSize
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
                        .fillMaxHeight()
                        .padding(PaddingValue),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.sdp())
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.sdp()),
                        painter = painterResource(id = R.drawable.ic_windows),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Column {
                        Text(
                            M3KApp.getString(R.string.quickboot_title),
                            color = MaterialTheme.colorScheme.inverseSurface,
                            fontWeight = FontWeight.Bold,
                            lineHeight = LineHeight,
                            fontSize = FontSize
                        )
                        val modem = when (NoModem.value) {
                            true -> R.string.quickboot_subtitle_nomodem
                            else -> R.string.quickboot_subtitle
                        }
                        Text(
                            M3KApp.getString(modem),
                            color = MaterialTheme.colorScheme.inverseSurface,
                            lineHeight = LineHeight,
                            fontSize = FontSize
                        )
                    }
                }
            }
        }
    }
}