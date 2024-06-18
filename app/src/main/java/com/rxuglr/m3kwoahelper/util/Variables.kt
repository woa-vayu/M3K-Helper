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
            "miatoll",
            "guacamole",
            "hotdog",
            "mh2lm",
            "alphaplus",
            "mh2lm5g",
            "flashlmmd",
            "marble"
        )
    private val NoModemA: Array<String> =
        arrayOf(
            "nabu",
            "beryllium",
            "miatoll",
            "Unknown",
            "marble"
        )
    private val NoFlashA: Array<String> =
        arrayOf(
            "hotdog",
            "guacamole"
        )
    private val NoBootA: Array<String> =
        arrayOf(
            "hotdog",
            "guacamole"
        )
    private val SensorsA: Array<String> =
        arrayOf(
            "raphael",
            "raphaelin",
            "cepheus",
            "raphaels"
        )
    private val NoGuideA: Array<String> =
        arrayOf(
            "guacamole",
            "hotdog",
            "Unknown"
        )
    private val NoGroupA: Array<String> =
        arrayOf(
            "Unknown"
        )

    // device info
    val Ram: String = RAM().getMemory(woahApp)
    var Name: String = ""
    val Slot: String =
        String.format("%S", ShellUtils.fastCmd("getprop ro.boot.slot_suffix")).drop(1)
    var Codename: String = Build.DEVICE
    val Unsupported: MutableState<Boolean> = mutableStateOf(false)
    val NoModem: MutableState<Boolean> = mutableStateOf(false)
    val NoFlash: MutableState<Boolean> = mutableStateOf(false)
    val NoBoot: MutableState<Boolean> = mutableStateOf(false)
    val NoSensors: MutableState<Boolean> = mutableStateOf(true)
    val NoGuide: MutableState<Boolean> = mutableStateOf(false)
    val NoGroup: MutableState<Boolean> = mutableStateOf(false)

    // dynamic vars
    var BootIsPresent: Boolean = false
    var WindowsIsPresent: Boolean = false
    var UEFIList: Array<Int> = arrayOf(0)
    var FontSize: TextUnit = 0.sp
    var PaddingValue: Dp = 0.dp
    var LineHeight: TextUnit = 0.sp

    fun vars() {
        Codename = when {
            Codename.contains("curtana")
                    || Codename.contains("durandal")
                    || Codename.contains("excalibur")
                    || Codename.contains("gram")
                    || Codename.contains("joyeuse") -> "miatoll"

            Codename.contains("OnePlus7TPro") -> "hotdog"
            Codename.contains("OnePlus7Pro") -> "guacamole"
            else -> "Unknown"
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
            Codenames[9] -> "OnePlus 7 Pro"
            Codenames[10] -> "OnePlus 7T Pro"
            Codenames[11] -> "LG G8X"
            Codenames[12] -> "LG G8"
            Codenames[13] -> "LG V50S"
            Codenames[14] -> "LG V50"
            else -> "Unknown"
        }.toString()
        if (NoModemA.contains(Codename)) {
            NoModem.value = true
        }
        if (NoFlashA.contains(Codename)) {
            NoFlash.value = true
        }
        if (NoBootA.contains(Codename)) {
            NoBoot.value = true
        }
        if (SensorsA.contains(Codename)) {
            NoSensors.value = false
        }
        if (NoGuideA.contains(Codename)) {
            NoGuide.value = true
        }
        if (NoGroupA.contains(Codename)) {
            NoGroup.value = true
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
