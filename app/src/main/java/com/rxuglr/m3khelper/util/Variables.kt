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
import com.rxuglr.m3khelper.util.Commands.mountWindows
import com.rxuglr.m3khelper.util.Commands.umountWindows
import com.topjohnwu.superuser.ShellUtils
import kotlin.properties.Delegates

object Variables {

    // static vars
    private val deviceCardsArray: Array<DeviceCard> =
        arrayOf(
            vayuCard, bhimaCard,
            nabuCard,
            raphaelCard, raphaelinCard, raphaelsCard,
            cepheusCard,
            berylliumCard,
            miatolCard, curtanaCard, durandalCard, excaliburCard, gramCard, joyeuseCard,
            alphaCard,
            mh2lm5gCard,
            mh2Card,
            betaCard,
            flashCard,
            guacamoleCard,
            hotdogCard,
            suryaCard, karnaCard,
            emu64xaCard
        )

    // device info
    val Ram: String = RAM().getMemory(M3KApp)
    val Slot: String =
        String.format("%S", ShellUtils.fastCmd("getprop ro.boot.slot_suffix")).drop(1)
    private var Codename: String = Build.DEVICE
    lateinit var PanelType: String

    var CurrentDeviceCard: DeviceCard = unknownCard
    val Warning: MutableState<Boolean> = mutableStateOf(false)

    // dynamic vars
    var BootIsPresent: Int by Delegates.notNull()
    var WindowsIsPresent: Int by Delegates.notNull()
    var UEFIList: Array<Int> = arrayOf(0)

    var FontSize: TextUnit = 0.sp
    var PaddingValue: Dp = 0.dp
    var LineHeight: TextUnit = 0.sp

    @SuppressLint("RestrictedApi")
    fun vars() {
        for (card: DeviceCard in deviceCardsArray) {
            if (Codename.contains(card.deviceCodename)) CurrentDeviceCard = card
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


        mountWindows()
        WindowsIsPresent = when {
            ShellUtils.fastCmd("find /sdcard/Windows/Windows/explorer.exe")
                .isNotEmpty() -> R.string.yes

            else -> R.string.no
        }
        umountWindows()

        dynamicVars()
    }

    fun dynamicVars() {
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
