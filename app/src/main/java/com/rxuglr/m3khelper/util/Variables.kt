package com.rxuglr.m3khelper.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rxuglr.m3khelper.R
import com.rxuglr.m3khelper.M3KApp
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
            "flashlmmd"
        )
    private val NoModemA: Array<String> =
        arrayOf(
            "nabu",
            "beryllium",
            "miatoll",
            "mh2lm",
            "alphaplus",
            "mh2lm5g",
            "flashlmmd"
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
        )
    private val NoGroupA: Array<String> =
        arrayOf(
        )

    // device info
    val Ram: String = RAM().getMemory(M3KApp)
    var Name: String = ""
    val Slot: String =
        String.format("%S", ShellUtils.fastCmd("getprop ro.boot.slot_suffix")).drop(1)
    var Codename: String = Build.DEVICE
    var GuideLink: String = ""
    var GroupLink: String = ""
    val Unsupported: MutableState<Boolean> = mutableStateOf(false)
    val Warning: MutableState<Boolean> = mutableStateOf(false)
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

    @SuppressLint("RestrictedApi")
    fun vars() {
        Codename = when {
            Codename.contains("curtana")
                    || Codename.contains("durandal")
                    || Codename.contains("excalibur")
                    || Codename.contains("gram")
                    || Codename.contains("joyeuse") -> "miatoll"

            Codename.contains("OnePlus7TPro") -> "hotdog"
            Codename.contains("OnePlus7Pro") -> "guacamole"
            else -> Codename
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
            else -> M3KApp.getString(R.string.unknown_device)
        }.toString()

        if ((Name == M3KApp.getString(R.string.unknown_device))) {
            Unsupported.value = true
            Warning.value = true
        }
        if (NoModemA.contains(Codename) || Unsupported.value) {
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
        if (NoGuideA.contains(Codename) || Unsupported.value) {
            NoGuide.value = true
        } else {
            GuideLink = when (Codename) {
                Codenames[0], Codenames[1] -> "https://github.com/woa-vayu/Port-Windows-11-Poco-X3-pro"
                Codenames[2] -> "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5"
                Codenames[3], Codenames[4], Codenames[6] -> "https://github.com/graphiks/woa-raphael"
                Codenames[5] -> "https://github.com/woacepheus/Port-Windows-11-Xiaomi-Mi-9"
                Codenames[7] -> "https://github.com/n00b69/woa-beryllium"
                Codenames[8] -> "https://github.com/Rubanoxd/Port-Windows-11-redmi-note-9_pro"
                Codenames[11], Codenames[13], Codenames[14] -> "https://github.com/Icesito68/Port-Windows-11-Lge-devices"
                else -> ""
            }
        }
        if (NoGroupA.contains(Codename) || Unsupported.value) {
            NoGroup.value = true
        } else {
            GroupLink = when (Codename) {
                Codenames[0], Codenames[1] -> "https://t.me/winonvayualt"
                Codenames[2] -> "https://t.me/nabuwoa"
                Codenames[3], Codenames[4], Codenames[6] -> "https://t.me/woaraphael"
                Codenames[5] -> "https://t.me/woacepheus"
                Codenames[7] -> "https://t.me/WinOnF1"
                Codenames[8] -> "http://t.me/woamiatoll"
                Codenames[9], Codenames[10] -> "https://t.me/onepluswoachat"
                Codenames[11], Codenames[12], Codenames[13], Codenames[14] -> "https://t.me/winong8x"
                else -> ""
            }
        }

        if ((ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img").isEmpty())) {
            UEFIList += 99
        } else {
            if (ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 120")
                    .isNotEmpty()
            ) {
                UEFIList += 120
            }
            if (ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 90")
                    .isNotEmpty()
            ) {
                UEFIList += 90
            }
            if (ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 60")
                    .isNotEmpty()
            ) {
                UEFIList += 60
            }
            if ((ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img").isNotEmpty())
                and
                (!UEFIList.contains(60)
                        and !UEFIList.contains(90)
                        and !UEFIList.contains(120)
                        )
            ) {
                UEFIList += 1
            }
        }
    }
}