package com.rxuglr.m3kwoahelper

import android.app.Application
import android.os.Build
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rxuglr.m3kwoahelper.util.Commands
import com.rxuglr.m3kwoahelper.util.RAM
import com.topjohnwu.superuser.Shell
import com.topjohnwu.superuser.ShellUtils

lateinit var woahApp: WOAHelperApplication

// main variables
lateinit var ram : String
lateinit var name : String
val codename: String = Build.DEVICE
lateinit var slot : String

var fontSize = 15.sp
var paddingValue = 10.dp
var lineHeight = 15.sp

class WOAHelperApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        woahApp = this
        Shell.setDefaultBuilder(Shell.Builder.create().setFlags(Shell.FLAG_REDIRECT_STDERR).setTimeout(10))
        ram = RAM().getMemory(this)
        name = Commands.devicename()
        slot = String.format("%S", ShellUtils.fastCmd("getprop ro.boot.slot_suffix")).drop(1)
        if (codename == "nabu") {
            fontSize = 20.sp
            paddingValue = 20.dp
            lineHeight = 20.sp
        }
    }
}