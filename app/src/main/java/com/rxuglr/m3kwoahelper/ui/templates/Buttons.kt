package com.rxuglr.m3kwoahelper.ui.templates

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rxuglr.m3kwoahelper.R
import com.rxuglr.m3kwoahelper.util.Commands

object Buttons  {

   @Composable
   fun BackupButton(fontSize: TextUnit, paddingValue: Dp) {
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
           if (showBackupSpinner.value) {
               PopupDialogs().SpinnerDialog(
                   icon = painterResource(id = R.drawable.ic_disk),
                   title = "Please wait..",
                   showDialog = showBackupSpinner.value
               )
           }
           if (showBackupDialog.value) {
               AlertDialog(
                   icon = {
                       Icon(
                           painter = painterResource(id = R.drawable.ic_disk),
                           contentDescription = null,
                           tint = MaterialTheme.colorScheme.primary
                       )
                   },
                   title = {
                   },
                   text = {
                       Text(
                           modifier = Modifier.fillMaxWidth(),
                           text = LocalContext.current.getString(R.string.backup_boot_question),
                           textAlign = TextAlign.Center
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
                                       Commands.backup(2)
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
                                       color = MaterialTheme.colorScheme.inverseSurface
                                   )
                               }
                           )
                           AssistChip(
                               onClick = {
                                   Thread {
                                       showBackupDialog.value = false
                                       showBackupSpinner.value = true
                                       Commands.mountwin()
                                       Commands.backup(1)
                                       Commands.umountwin()
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
                                       color = MaterialTheme.colorScheme.inverseSurface
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
           Row(
               modifier = Modifier
                   .padding(paddingValue),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.spacedBy(5.dp)
           ) {
               Icon(
                   modifier = Modifier
                       .height(40.dp)
                       .width(40.dp),
                   painter = painterResource(id = R.drawable.ic_disk),
                   contentDescription = null,
                   tint = MaterialTheme.colorScheme.primary
               )
               Column {
                   Text(
                       LocalContext.current.getString(R.string.backup_boot_title),
                       color = MaterialTheme.colorScheme.inverseSurface,
                       fontWeight = FontWeight.Bold
                   )
                   Text(
                       LocalContext.current.getString(R.string.backup_boot_subtitle),
                       color = MaterialTheme.colorScheme.inverseSurface,
                       lineHeight = 15.sp,
                       fontSize = fontSize
                   )
               }
           }
       }
   }


   @Composable
   fun MountButton(fontSize: TextUnit, paddingValue: Dp) {
       val showMountDialog = remember { mutableStateOf(false) }
       Card(
           onClick = { showMountDialog.value = true },
           Modifier
               .fillMaxWidth(),
           colors = CardDefaults.cardColors(
               containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp)
           )
       ) {
           if (showMountDialog.value) {
               if (Commands.mountstatus()) {
                   PopupDialogs().Dialog(
                       icon = painterResource(id = R.drawable.ic_windows),
                       title = null,
                       description = LocalContext.current.getString(R.string.mnt_question),
                       showDialog = showMountDialog.value,
                       onDismiss = { showMountDialog.value = false },
                       onConfirm = ({
                           Commands.mountwin()
                           showMountDialog.value = false
                       })
                   )
               } else {
                   PopupDialogs().Dialog(
                       painterResource(id = R.drawable.ic_windows),
                       null,
                       LocalContext.current.getString(R.string.umnt_question),
                       showMountDialog.value,
                       onDismiss = ({ showMountDialog.value = false; }),
                       onConfirm = ({
                           Commands.umountwin()
                           showMountDialog.value = false
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
                   painter = painterResource(id = R.drawable.ic_windows),
                   contentDescription = null,
                   tint = MaterialTheme.colorScheme.primary
               )
               Column {
                   val mounted: Int =
                       if (Commands.mountstatus()) {
                           R.string.mnt_title
                       } else {
                           R.string.umnt_title
                       }
                   Text(
                       LocalContext.current.getString(mounted),
                       color = MaterialTheme.colorScheme.inverseSurface,
                       fontWeight = FontWeight.Bold
                   )
                   Text(
                       LocalContext.current.getString(R.string.mnt_subtitle),
                       color = MaterialTheme.colorScheme.inverseSurface,
                       lineHeight = 15.sp,
                       fontSize = fontSize
                   )
               }
           }
       }
   }

   @Composable
   fun ModemButton(fontSize: TextUnit, paddingValue: Dp) {
       val showModemDialog = remember { mutableStateOf(false) }
       val showModemSpinner = remember { mutableStateOf(false) }
       Card(
           onClick = { showModemDialog.value = true },
           Modifier
               .fillMaxWidth(),
           colors = CardDefaults.cardColors(
               containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp)
           )
       ) {
           if (showModemSpinner.value) {
               PopupDialogs().SpinnerDialog(
                   icon = painterResource(id = R.drawable.ic_modem),
                   title = "Please wait..",
                   showDialog = showModemSpinner.value
               )
           }
           if (showModemDialog.value) {
               PopupDialogs().Dialog(
                   painterResource(id = R.drawable.ic_modem),
                   null,
                   LocalContext.current.getString(R.string.dump_modem_question),
                   showModemDialog.value,
                   onDismiss = ({ showModemDialog.value = false; }),
                   onConfirm = ({
                       Thread {
                           showModemDialog.value = false
                           showModemSpinner.value = true
                           Commands.dumpmodem()
                           showModemSpinner.value = false
                       }.start()
                   })
               )
           }
           Row(
               modifier = Modifier
                   .padding(paddingValue),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.spacedBy(5.dp)
           ) {
               Icon(
                   modifier = Modifier
                       .height(40.dp)
                       .width(40.dp),
                   painter = painterResource(id = R.drawable.ic_modem),
                   contentDescription = null,
                   tint = MaterialTheme.colorScheme.primary
               )
               Column {
                   Text(
                       LocalContext.current.getString(R.string.dump_modem_title),
                       color = MaterialTheme.colorScheme.inverseSurface,
                       fontWeight = FontWeight.Bold
                   )
                   Text(
                       LocalContext.current.getString(R.string.dump_modem_subtitle),
                       color = MaterialTheme.colorScheme.inverseSurface,
                       lineHeight = 15.sp,
                       fontSize = fontSize
                   )
               }
           }
       }
   }

   @Composable
   fun UEFIButton(fontSize: TextUnit, paddingValue: Dp) {
       val showUEFIDialog = remember { mutableStateOf(false) }
       val showUEFISpinner = remember { mutableStateOf(false) }
       Card(
           onClick = { showUEFIDialog.value = true },
           Modifier
               .fillMaxWidth(),
           colors = CardDefaults.cardColors(
               containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp)
           )
       ) {
           if (showUEFISpinner.value) {
               PopupDialogs().SpinnerDialog(
                   icon = painterResource(id = R.drawable.ic_uefi),
                   title = "Please wait..",
                   showDialog = showUEFISpinner.value
               )
           }
           if (showUEFIDialog.value) {
               AlertDialog(
                   icon = {
                       Icon(
                           painter = painterResource(id = R.drawable.ic_uefi),
                           contentDescription = null,
                           tint = MaterialTheme.colorScheme.primary
                       )
                   },
                   title = {
                   },
                   text = {
                       Text(
                           modifier = Modifier.fillMaxWidth(),
                           text = LocalContext.current.getString(R.string.flash_uefi_question),
                           textAlign = TextAlign.Center
                       )
                   },
                   onDismissRequest = ({ showUEFIDialog.value = false; }),
                   dismissButton = {
                       Row(
                           Modifier.align(Alignment.CenterHorizontally),
                           horizontalArrangement = Arrangement.spacedBy(10.dp)
                       ) {
                           if (Commands.uefi.contains(120)) {
                               AssistChip(
                                   onClick = {
                                       Thread {
                                           showUEFIDialog.value = false
                                           showUEFISpinner.value = true
                                           Commands.flashuefi(0)
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
                                           color = MaterialTheme.colorScheme.inverseSurface
                                       )
                                   }
                               )
                           }
                           if (Commands.uefi.contains(60)) {
                               AssistChip(
                                   onClick = {
                                       Thread {
                                           showUEFIDialog.value = false
                                           showUEFISpinner.value = true
                                           Commands.flashuefi(1)
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
                                           color = MaterialTheme.colorScheme.inverseSurface
                                       )
                                   }
                               )
                           }
                           if (Commands.uefi
                                   .contains(1) and (!Commands.uefi.contains(60) or !Commands.uefi.contains(120))
                           ) {
                               AssistChip(
                                   onClick = {
                                       Thread {
                                           showUEFIDialog.value = false
                                           showUEFISpinner.value = true
                                           Commands.flashuefi(2)
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
                                           color = MaterialTheme.colorScheme.inverseSurface
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
                                       color = MaterialTheme.colorScheme.inverseSurface
                                   )
                               }
                           )
                       }
                   },
                   confirmButton = {
                   }
               )
           }
           Row(
               modifier = Modifier
                   .padding(paddingValue),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.spacedBy(5.dp)
           ) {
               Icon(
                   modifier = Modifier
                       .height(40.dp)
                       .width(40.dp),
                   painter = painterResource(id = R.drawable.ic_uefi),
                   contentDescription = null,
                   tint = MaterialTheme.colorScheme.primary
               )
               Column {
                   Text(
                       LocalContext.current.getString(R.string.flash_uefi_title),
                       color = MaterialTheme.colorScheme.inverseSurface,
                       fontWeight = FontWeight.Bold
                   )
                   Text(
                       LocalContext.current.getString(R.string.flash_uefi_subtitle, Commands.displaytype()),
                       color = MaterialTheme.colorScheme.inverseSurface,
                       lineHeight = 15.sp,
                       fontSize = fontSize
                   )
               }
           }
       }
   }

   @Composable
   fun QuickbootButton(fontSize: TextUnit, paddingValue: Dp) {
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
           if (showQuickBootSpinner.value) {
               PopupDialogs().SpinnerDialog(
                   icon = painterResource(id = R.drawable.ic_windows),
                   title = "Please wait..",
                   showDialog = showQuickBootSpinner.value
               )
           }
           if (showQuickBootDialog.value) {
               AlertDialog(
                   icon = {
                       Icon(
                           painter = painterResource(id = R.drawable.ic_windows),
                           contentDescription = null,
                           tint = MaterialTheme.colorScheme.primary
                       )
                   },
                   title = {
                   },
                   text = {
                       Text(
                           modifier = Modifier.fillMaxWidth(),
                           text = LocalContext.current.getString(R.string.quickboot_question),
                           textAlign = TextAlign.Center
                       )
                   },
                   onDismissRequest = ({ showQuickBootDialog.value = false; }),
                   dismissButton = {
                       Row(
                           Modifier.align(Alignment.CenterHorizontally),
                           horizontalArrangement = Arrangement.spacedBy(10.dp)
                       ) {
                           if (Commands.uefi.contains(120)) {
                               AssistChip(
                                   modifier = Modifier.width(95.dp),
                                   onClick = {
                                       Thread {
                                           showQuickBootDialog.value = false
                                           showQuickBootSpinner.value = true
                                           Commands.quickboot(0)
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
                                           color = MaterialTheme.colorScheme.inverseSurface
                                       )
                                   }
                               )
                           }
                           if (Commands.uefi.contains(60)) {
                               AssistChip(
                                   modifier = Modifier.width(95.dp),
                                   onClick = {
                                       Thread {
                                           showQuickBootDialog.value = false
                                           showQuickBootSpinner.value = true
                                           Commands.quickboot(1)
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
                                           color = MaterialTheme.colorScheme.inverseSurface
                                       )
                                   }
                               )
                           }
                           if (Commands.uefi
                                   .contains(1) and (!Commands.uefi.contains(60) or !Commands.uefi.contains(120))
                           ) {
                               AssistChip(
                                   onClick = {
                                       Thread {
                                           showQuickBootDialog.value = false
                                           showQuickBootSpinner.value = true
                                           Commands.quickboot(2)
                                           showQuickBootSpinner.value = false
                                       }.start()
                                   },
                                   label = {
                                       Text(
                                           text = LocalContext.current.getString(R.string.yes),
                                           color = MaterialTheme.colorScheme.inverseSurface
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
                                       color = MaterialTheme.colorScheme.inverseSurface
                                   )
                               }
                           )
                       }
                   },
                   confirmButton = {
                   }
               )
           }
           Row(
               modifier = Modifier
                   .padding(paddingValue),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.spacedBy(5.dp)
           ) {
               Icon(
                   modifier = Modifier
                       .height(40.dp)
                       .width(40.dp),
                   painter = painterResource(id = R.drawable.ic_windows),
                   contentDescription = null,
                   tint = MaterialTheme.colorScheme.primary
               )
               Column {
                   Text(
                       LocalContext.current.getString(R.string.quickboot_title),
                       color = MaterialTheme.colorScheme.inverseSurface,
                       fontWeight = FontWeight.Bold
                   )
                   val modem = when (Build.DEVICE) {
                       "nabu" -> R.string.quickboot_subtitle_nomodem
                       else -> R.string.quickboot_subtitle
                   }
                   Text(
                       LocalContext.current.getString(modem),
                       color = MaterialTheme.colorScheme.inverseSurface,
                       lineHeight = 15.sp,
                       fontSize = fontSize
                   )
               }
           }
       }
   }
}