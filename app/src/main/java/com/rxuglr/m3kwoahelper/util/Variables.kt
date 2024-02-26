package com.rxuglr.m3kwoahelper.util

import android.os.Build
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rxuglr.m3kwoahelper.woahApp
import com.topjohnwu.superuser.ShellUtils

object Variables {

    val codenames =
        arrayOf("vayu", "bhima", "nabu", "raphael", "raphaelin", "cepheus", "raphaels")
    val nomodem = arrayOf("nabu")
    val sensors = arrayOf(codenames[3], codenames[4], codenames[5], codenames[6])
    val uefi = Commands.getuefilist()

    val ram = RAM().getMemory(woahApp)
    val name = Commands.devicename()
    val slot = String.format("%S", ShellUtils.fastCmd("getprop ro.boot.slot_suffix")).drop(1)
    val codename: String = Build.DEVICE
    var fontSize = 15.sp
    var paddingValue = 10.dp
    var lineHeight = 15.sp
    fun vars() {
        if (codename == "nabu") {
            fontSize = 20.sp
            paddingValue = 20.dp
            lineHeight = 20.sp
        }
    }
}