package com.rxuglr.m3kwoahelper

import android.app.Application
import com.rxuglr.m3kwoahelper.util.Variables.vars
import com.topjohnwu.superuser.Shell

lateinit var woahApp: WOAHelperApplication


class WOAHelperApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        woahApp = this
        Shell.setDefaultBuilder(
            Shell.Builder.create().setFlags(Shell.FLAG_REDIRECT_STDERR).setTimeout(10)
        )
        vars()
    }
}