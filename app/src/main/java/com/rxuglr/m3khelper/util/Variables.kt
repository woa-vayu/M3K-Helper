package com.rxuglr.m3khelper.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rxuglr.m3khelper.M3KApp
import com.rxuglr.m3khelper.util.Commands.mountwin
import com.rxuglr.m3khelper.util.Commands.umountwin
import com.rxuglr.m3khelper.R
import com.topjohnwu.superuser.ShellUtils
import kotlin.properties.Delegates

object Variables {

    // static vars
    private val Codenames: Array<String> =
        arrayOf(
            "vayu",
            "bhima",
            "nabu",
            "raphael",
            "raphaelin",
            "raphaels",
            "cepheus",
            "beryllium",
            "miatoll",
            "alphaplus",
            "mh2lm",
            "flashlmmd",
            "mh2lm5g",
            "emu64xa"
        )
    private val NoModemA: Array<String> =
        arrayOf(
            "nabu",
            "beryllium",
            "miatoll",
            "alphaplus",
            "mh2lm",
            "flashlmmd",
            "mh2lm5g",
            "vayu",
            "bhima"
        )
    private val NoFlashA: Array<String> =
        arrayOf(
        )
    private val NoBootA: Array<String> =
        arrayOf(
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
        )
    private val NoGroupA: Array<String> =
        arrayOf(
        )

    // device info
    val Ram: String = RAM().getMemory(M3KApp)
    lateinit var Name: String
    var Img: Int = 0
    val Slot: String =
        String.format("%S", ShellUtils.fastCmd("getprop ro.boot.slot_suffix")).drop(1)
    var Codename: String = Build.DEVICE

    lateinit var GuideLink: String
    lateinit var GroupLink: String

    private val Unknown: MutableState<Boolean> = mutableStateOf(false)
    val Unsupported: MutableState<Boolean> = mutableStateOf(false)
    val Warning: MutableState<Boolean> = mutableStateOf(false)

    val NoModem: MutableState<Boolean> = mutableStateOf(false)
    val NoFlash: MutableState<Boolean> = mutableStateOf(false)
    val NoBoot: MutableState<Boolean> = mutableStateOf(false)
    val NoSensors: MutableState<Boolean> = mutableStateOf(true)
    val NoGuide: MutableState<Boolean> = mutableStateOf(false)
    val NoGroup: MutableState<Boolean> = mutableStateOf(false)

    // dynamic vars
    var BootIsPresent: Int by Delegates.notNull()
    var WindowsIsPresent: Int by Delegates.notNull()
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

            else -> Codename
        }
        Name = when (Codename) {
            Codenames[0], Codenames[1] -> "POCO X3 Pro"
            Codenames[2] -> "Xiaomi Pad 5"
            Codenames[3] -> "Xiaomi Mi 9T Pro"
            Codenames[4] -> "Redmi K20 Pro"
            Codenames[5] -> "Redmi K20 Pro Premium"
            Codenames[6] -> "Xiaomi Mi 9"
            Codenames[7] -> "POCO F1"
            Codenames[8] -> "Xiaomi Redmi Note 9 Pro"
            Codenames[9] -> "LG G8"
            Codenames[10] -> "LG G8X"
            Codenames[11] -> "LG V50"
            Codenames[12] -> "LG V50S"
            Codenames[13] -> "emu64xa"
            else -> M3KApp.getString(R.string.unknown_device)
        }.toString()
        Img = when (Codename) {
            Codenames[0], Codenames[1] -> R.drawable.vayu
            Codenames[2] -> R.drawable.nabu
            Codenames[3], Codenames[4], Codenames[6] -> R.drawable.raphael
            Codenames[5] -> R.drawable.cepheus
            Codenames[7] -> R.drawable.beryllium
            Codenames[8] -> R.drawable.miatoll
            Codenames[9] -> R.drawable.alphaplus
            Codenames[10], Codenames[11], Codenames[12] -> R.drawable.mh2lm
            else -> R.drawable.ic_device_unknown
        }

        if (Codename.contains("OnePlus7TPro") || Codename.contains("OnePlus7Pro")) {
            Unsupported.value = true
        }

        if ((Name == M3KApp.getString(R.string.unknown_device))) {
            Unknown.value = true
            Warning.value = true
        }
        if (NoModemA.contains(Codename) || Unknown.value) {
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
        if (NoGuideA.contains(Codename) || Unknown.value) {
            NoGuide.value = true
        } else {
            GuideLink = when (Codename) {
                Codenames[0], Codenames[1] -> "https://github.com/woa-vayu/POCOX3Pro-Guides"
                Codenames[2] -> "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5"
                Codenames[3], Codenames[4], Codenames[5] -> "https://github.com/graphiks/woa-raphael"
                Codenames[6] -> "https://github.com/woacepheus/Port-Windows-11-Xiaomi-Mi-9"
                Codenames[7] -> "https://github.com/n00b69/woa-beryllium"
                Codenames[8] -> "https://github.com/woa-miatoll/Port-Windows-11-Redmi-Note-9-Pro"
                Codenames[9] -> "https://github.com/n00b69/woa-betalm"
                Codenames[10] -> "https://github.com/n00b69/woa-mh2lm"
                Codenames[11] -> "https://github.com/n00b69/woa-flashlmdd"
                Codenames[12] -> "https://github.com/n00b69/woa-mh2lm5g"
                Codenames[13] -> "https://google.com"
                else -> ""
            }
        }
        if (NoGroupA.contains(Codename) || Unknown.value) {
            NoGroup.value = true
        } else {
            GroupLink = when (Codename) {
                Codenames[0], Codenames[1] -> "https://t.me/winonvayualt"
                Codenames[2] -> "https://t.me/nabuwoa"
                Codenames[3], Codenames[4], Codenames[5] -> "https://t.me/woaraphael"
                Codenames[6] -> "https://t.me/woacepheus"
                Codenames[7] -> "https://t.me/WinOnF1"
                Codenames[8] -> "http://t.me/woamiatoll"
                Codenames[9], Codenames[10], Codenames[11], Codenames[12] -> "https://t.me/winong8x"
                Codenames[13] -> "https://google.com"
                else -> ""
            }
        }

        mountwin()
        WindowsIsPresent = when {
            ShellUtils.fastCmd("find /sdcard/Windows/Windows/explorer.exe")
                .isNotEmpty() -> R.string.yes

            else -> R.string.no
        }
        umountwin()

        DynamicVars()
    }

    fun DynamicVars() {
        UEFIList = arrayOf(0)
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

        BootIsPresent = when {
            ShellUtils.fastCmd("find /sdcard/Windows/boot.img").isNotEmpty()
                    && ShellUtils.fastCmd("find /sdcard/m3khelper/boot.img").isNotEmpty() -> R.string.backup_both

            ShellUtils.fastCmd("find /sdcard/Windows/boot.img").isNotEmpty() -> R.string.backup_windows

            ShellUtils.fastCmd("find /sdcard/m3khelper/boot.img").isNotEmpty() -> R.string.backup_android

            else -> R.string.no
        }
    }
}