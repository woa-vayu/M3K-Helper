package com.rxuglr.m3kwoahelper

import android.app.Application
import android.os.Build
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rxuglr.m3kwoahelper.util.Commands
import com.rxuglr.m3kwoahelper.util.RAM
import com.rxuglr.m3kwoahelper.util.Variables.vars
import com.topjohnwu.superuser.Shell
import com.topjohnwu.superuser.ShellUtils

lateinit var woahApp: WOAHelperApplication


class WOAHelperApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        woahApp = this
        Shell.setDefaultBuilder(Shell.Builder.create().setFlags(Shell.FLAG_REDIRECT_STDERR).setTimeout(10))
        vars()
    }
}