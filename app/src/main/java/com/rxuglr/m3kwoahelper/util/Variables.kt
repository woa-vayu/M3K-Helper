package com.rxuglr.m3kwoahelper.util

import android.os.Build
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rxuglr.m3kwoahelper.woahApp
import com.topjohnwu.superuser.ShellUtils

object Variables {

    // static vars
    val Codenames: Array<String> =
        arrayOf(
            "vayu",
            "bhima",
            "nabu",
            "raphael",
            "raphaelin",
            "cepheus",
            "raphaels",
            "beryllium",
            "miatoll"
        )
    private val NoModemS: Array<String> =
        arrayOf(
            "nabu",
            "beryllium",
            "miatoll"
        )
    val Sensors: Array<String> =
        arrayOf(
            "raphael",
            "raphaelin",
            "cepheus",
            "raphaels"
        )

    // device info
    val Ram: String = RAM().getMemory(woahApp)
    var Name: String = ""
    val Slot: String =
        String.format("%S", ShellUtils.fastCmd("getprop ro.boot.slot_suffix")).drop(1)
    var Codename: String = Build.DEVICE
    val Unsupported: MutableState<Boolean> = mutableStateOf(false)
    val NoModem: MutableState<Boolean> = mutableStateOf(false)

    // dynamic vars
    var BootIsPresent: Boolean = false
    var WindowsIsPresent: Boolean = false
    var UEFIList: Array<Int> = arrayOf(0)
    var FontSize: TextUnit = 0.sp
    var PaddingValue: Dp = 0.dp
    var LineHeight: TextUnit = 0.sp

    fun vars() {
        if (Codename.contains("curtana")
            || Codename.contains("durandal")
            || Codename.contains("excalibur")
            || Codename.contains("gram")
            || Codename.contains("joyeuse")
        ) {
            Codename = "miatoll"
        }
        Name = when (Codename) {
            Codenames[0], Codenames[1] -> "POCO X3 Pro"
            Codenames[2] -> "Xiaomi Pad 5"
            Codenames[3] -> "Xiaomi Mi 9T Pro"
            Codenames[4] -> "Redmi K20 Pro"
            Codenames[5] -> "Xiaomi Mi 9"
            Codenames[6] -> "Redmi K20 Pro Premium"
            Codenames[7] -> "POCO F1"
            Codenames[8] -> "Xiaomi Redmi Note 9 Pro"
            else -> "Unknown"
        }.toString()
        if (NoModemS.contains(Codename)) {
            NoModem.value = true
        }
        if ((Name == "Unknown")) {
            Unsupported.value = true
        }
        if ((ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img").isEmpty())) {
            UEFIList += 99
        } else {
            if (ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 120")
                    .isNotEmpty()
            ) {
                UEFIList += 120
            }
            if (ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 60")
                    .isNotEmpty()
            ) {
                UEFIList += 60
            }
            if ((ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img")
                    .isNotEmpty()) and (!UEFIList.contains(60)) and !UEFIList.contains(120)
            ) {
                UEFIList += 1
            }
        }
    }
}