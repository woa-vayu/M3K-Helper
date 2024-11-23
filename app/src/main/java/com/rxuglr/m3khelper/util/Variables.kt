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
import com.rxuglr.m3khelper.R
import com.rxuglr.m3khelper.util.Commands.mountwin
import com.rxuglr.m3khelper.util.Commands.umountwin
import com.topjohnwu.superuser.ShellUtils
import kotlin.properties.Delegates

object Variables {

    private val UnsupportedA: Array<String> =
        arrayOf(
        )

    // static vars
    private val Codenames: Array<String> =
        arrayOf(
            "vayu",
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
            "emu64xa",
            "guacamole",
            "hotdog",
            "surya"
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
            "hotdog",
            "guacamole",
            "surya"
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
    private val NoMountA: Array<String> =
        arrayOf(
        )
    private val SensorsA: Array<String> =
        arrayOf(
            "raphael",
            "raphaelin",
            "raphaels"
        )
    private val NoGuideA: Array<String> =
        arrayOf(
            "hotdog",
            "guacamole",
            "surya"
        )
    private val NoGroupA: Array<String> =
        arrayOf(
            "hotdog",
            "guacamole",
            "surya"
        )

    // device info
    val Ram: String = RAM().getMemory(M3KApp)
    lateinit var Name: String
    var Img: Int = 0
    val Slot: String =
        String.format("%S", ShellUtils.fastCmd("getprop ro.boot.slot_suffix")).drop(1)
    var Codename: String = Build.DEVICE
    lateinit var PanelType: String

    lateinit var GuideLink: String
    lateinit var GroupLink: String

    private val Unknown: MutableState<Boolean> = mutableStateOf(false)
    val Unsupported: MutableState<Boolean> = mutableStateOf(false)
    val Warning: MutableState<Boolean> = mutableStateOf(false)

    val NoModem: MutableState<Boolean> = mutableStateOf(false)
    val NoFlash: MutableState<Boolean> = mutableStateOf(false)
    val NoBoot: MutableState<Boolean> = mutableStateOf(false)
    val NoMount: MutableState<Boolean> = mutableStateOf(false)
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

            Codename.contains("OnePlus7TPro") -> "hotdog"
            Codename.contains("OnePlus7Pro") -> "guacamole"

            Codename.contains("bhima") -> "vayu"
            Codename.contains("karna") -> "surya"

            // might change that in future
            Codename.contains("mh2lm5g") -> "mh2lm5g"
            Codename.contains("alpha") -> "alphaplus"
            Codename.contains("flash") -> "flashlmmd"
            Codename.contains("beta") -> "betalm"
            Codename.contains("mh2") -> "mh2lm"

            else -> Codename
        }
        Name = when (Codename) {
            Codenames[0] -> "POCO X3 Pro"
            Codenames[1] -> "Xiaomi Pad 5"
            Codenames[2] -> "Xiaomi Mi 9T Pro"
            Codenames[3] -> "Redmi K20 Pro"
            Codenames[4] -> "Redmi K20 Pro Premium"
            Codenames[5] -> "Xiaomi Mi 9"
            Codenames[6] -> "POCO F1"
            Codenames[7] -> "Xiaomi Redmi Note 9 Pro"
            Codenames[8] -> "LG G8"
            Codenames[9] -> "LG G8X"
            Codenames[10] -> "LG V50"
            Codenames[11] -> "LG V50S"
            Codenames[12] -> "emu64xa"
            Codenames[13] -> "OnePlus 7 Pro"
            Codenames[14] -> "OnePlus 7T Pro"
            Codenames[15] -> "POCO X3 NFC"
            else -> M3KApp.getString(R.string.unknown_device)
        }.toString()
        Img = when (Codename) {
            Codenames[0], Codenames[15] -> R.drawable.vayu
            Codenames[1], Codenames[12] -> R.drawable.nabu
            Codenames[2], Codenames[3], Codenames[4] -> R.drawable.raphael
            Codenames[5] -> R.drawable.cepheus
            Codenames[6] -> R.drawable.beryllium
            Codenames[7] -> R.drawable.miatoll
            Codenames[8] -> R.drawable.alphaplus
            Codenames[9], Codenames[10], Codenames[11] -> R.drawable.mh2lm
            Codenames[13] -> R.drawable.guacamole
            Codenames[14] -> R.drawable.hotdog
            else -> R.drawable.ic_device_unknown
        }

        if (UnsupportedA.contains(Codename)) {
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
        if (NoMountA.contains(Codename)) {
            NoMount.value = true
        }
        if (SensorsA.contains(Codename)) {
            NoSensors.value = false
        }
        if (NoGuideA.contains(Codename) || Unknown.value) {
            NoGuide.value = true
        } else {
            GuideLink = when (Codename) {
                Codenames[0] -> "https://github.com/woa-vayu/POCOX3Pro-Guides"
                Codenames[1] -> "https://github.com/erdilS/Port-Windows-11-Xiaomi-Pad-5"
                Codenames[2], Codenames[3], Codenames[4] -> "https://github.com/graphiks/woa-raphael"
                Codenames[5] -> "https://github.com/ivnvrvnn/Port-Windows-XiaoMI-9"
                Codenames[6] -> "https://github.com/n00b69/woa-beryllium"
                Codenames[7] -> "https://github.com/woa-miatoll/Port-Windows-11-Redmi-Note-9-Pro"
                Codenames[8] -> "https://github.com/n00b69/woa-betalm"
                Codenames[9] -> "https://github.com/n00b69/woa-mh2lm"
                Codenames[10] -> "https://github.com/n00b69/woa-flashlmdd"
                Codenames[11] -> "https://github.com/n00b69/woa-mh2lm5g"
                Codenames[12] -> "https://google.com"
                Codenames[15] -> "https://github.com/woa-surya/POCOX3NFC-Guides"
                else -> ""
            }
        }
        if (NoGroupA.contains(Codename) || Unknown.value) {
            NoGroup.value = true
        } else {
            GroupLink = when (Codename) {
                Codenames[0] -> "https://t.me/winonvayualt"
                Codenames[1] -> "https://t.me/nabuwoa"
                Codenames[2], Codenames[3], Codenames[4] -> "https://t.me/woaraphael"
                Codenames[5] -> "https://t.me/woacepheus"
                Codenames[6] -> "https://t.me/WinOnF1"
                Codenames[7] -> "http://t.me/woamiatoll"
                Codenames[8], Codenames[9], Codenames[10], Codenames[11] -> "https://t.me/winong8x"
                Codenames[12] -> "https://google.com"
                Codenames[13], Codenames[14] -> "https://t.me/onepluswoachat"
                Codenames[15] -> "https://t.me/windows_on_pocox3_nfc"
                else -> ""
            }
        }

        val panel = ShellUtils.fastCmd("cat /proc/cmdline")
        PanelType = when {
            panel.contains("j20s_42") 
                    || panel.contains("k82_42")
                    || panel.contains("huaxing") -> "Huaxing"
            
            panel.contains("j20s_36")
                    || panel.contains("tianma")
                    || panel.contains("k82_36") -> "Tianma"

            panel.contains("ebbg") -> "EBBG"
            
            panel.contains("samsung")
                    || panel.contains("ea8076_f1mp")
                    || panel.contains("ea8076_f1p2")
                    || panel.contains("ea8076_global") -> "Samsung"

            else -> M3KApp.getString(R.string.unknown_panel)
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
        if ((ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img")
                .isEmpty())) 
        {
            UEFIList += 99
        } else {
            if (ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 120")
                    .isNotEmpty())
            {
                UEFIList += 120
            }
            if (ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 90")
                    .isNotEmpty())
            {
                UEFIList += 90
            }
            if (ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 60")
                    .isNotEmpty())
            {
                UEFIList += 60
            }
            if ((ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img").isNotEmpty())
                and
                (!UEFIList.contains(60)
                        and !UEFIList.contains(90)
                        and !UEFIList.contains(120))) 
            {
                UEFIList += 1
            }
        }

        BootIsPresent = when {
            ShellUtils.fastCmd("find /sdcard/Windows/boot.img")
                .isNotEmpty()
                    && 
            ShellUtils.fastCmd("find /sdcard/m3khelper/boot.img")
                .isNotEmpty() -> R.string.backup_both

            ShellUtils.fastCmd("find /sdcard/Windows/boot.img")
                .isNotEmpty() -> R.string.backup_windows

            ShellUtils.fastCmd("find /sdcard/m3khelper/boot.img")
                .isNotEmpty() -> R.string.backup_android

            else -> R.string.no
        }
    }
}
