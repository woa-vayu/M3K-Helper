package com.remtrik.m3khelper.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.remtrik.m3khelper.M3KApp
import com.remtrik.m3khelper.R
import com.topjohnwu.superuser.ShellUtils
import kotlin.properties.Delegates

data class UEFICard(
    var uefiPath: String,
    val uefiType: Int,
)

object Variables {

    // static vars
    private val deviceCardsArray =
        arrayOf<DeviceCard>(
            vayuCard,
            nabuCard,
            raphaelCard, raphaelinCard, raphaelsCard,
            cepheusCard,
            berylliumCard,
            miatolCard, curtana_indiaCard, durandalCard, curtanaCard, excaliburCard, gramCard, joyeuseCard,
            alphaCard,
            mh2lm5gCard,
            mh2Card,
            betaCard,
            flashCard,
            guacamoleCard,
            hotdogCard,
            suryaCard, karnaCard,
            a52sxqCard,
            beyond1Card,
            emu64xaCard
        )

    val specialDeviceCardsArray =
        arrayOf<DeviceCard>(
            nabuCard
        )

    var UEFICardsArray =
        arrayOf<UEFICard>(
            UEFICard("", 1),
            UEFICard("", 60),
            UEFICard("", 90),
            UEFICard("", 120)
        )

    // device info
    val Ram: String = getMemory(M3KApp)
    val Slot: String =
        String.format("%S", ShellUtils.fastCmd("getprop ro.boot.slot_suffix")).drop(1)
    private var Codename1: String = Build.DEVICE
    private var Codename2: String = ShellUtils.fastCmd("getprop ro.lineage.device")
    lateinit var PanelType: String

    var CurrentDeviceCard: DeviceCard = unknownCard

    // dynamic vars
    var BootIsPresent: Int by Delegates.notNull()
    var WindowsIsPresent: Int by Delegates.notNull()
    val Warning: MutableState<Boolean> = mutableStateOf(false)
    var showAboutCard: MutableState<Boolean> = mutableStateOf(false)
    var UEFIList = arrayOf<Int>()

    var FontSize: TextUnit = 0.sp
    var PaddingValue: Dp = 0.dp
    var LineHeight: TextUnit = 0.sp

    @SuppressLint("RestrictedApi")
    fun vars() {
        for (card: DeviceCard in deviceCardsArray) {
            for (num: String in card.deviceCodename)
                if (Codename2.isNotEmpty() && Codename2.contains(num)) CurrentDeviceCard = card
                else if (Codename1.contains(num)) CurrentDeviceCard = card
        }
        CurrentDeviceCard.deviceCodename[0] = if (Codename2.isNotEmpty()) Codename2
        else Codename1

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
                    || panel.contains("ea8076_global")
                    || panel.contains("S6E3FC3")
                    || panel.contains("AMS646YD01") -> "Samsung"

            else -> M3KApp.getString(R.string.unknown_panel)
        }

        if (mountStatus()) {
            mountWindows()
            dynamicVars()
            umountWindows()
        } else dynamicVars()


    }

    fun dynamicVars() {
        WindowsIsPresent = when {
            CurrentDeviceCard.noMount -> R.string.no
            ShellUtils.fastCmd("find /sdcard/Windows/Windows/explorer.exe")
                .isNotEmpty() -> R.string.yes

            else -> R.string.no
        }
        UEFIList = arrayOf<Int>()
        val find = ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f | grep .img")
        if (find.isNotEmpty()) {
            var index = 1
            for (uefi: String in arrayOf("60", "90", "120")) {
                val path = ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep $uefi")
                if (path.isNotEmpty()
                ) {
                    UEFIList += uefi.toInt()
                    UEFICardsArray[index].uefiPath = path
                }
                index += 1
            }
            if (UEFIList.isEmpty()) {
                UEFIList += 1
                UEFICardsArray[1].uefiPath = find
            }
        }
        val win = if (!CurrentDeviceCard.noMount) ShellUtils.fastCmd("find /sdcard/Windows/boot.img") else null
        val android = ShellUtils.fastCmd("find /sdcard/m3khelper/boot.img")
        BootIsPresent = when {
            !win.isNullOrEmpty() && android.isNotEmpty() -> R.string.backup_both
            !win.isNullOrEmpty() -> R.string.backup_windows
            android.isNotEmpty() -> R.string.backup_android

            else -> R.string.no
        }
    }
}
