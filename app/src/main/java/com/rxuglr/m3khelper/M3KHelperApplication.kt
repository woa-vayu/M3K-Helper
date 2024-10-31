package com.rxuglr.m3khelper

import android.app.Application
import com.topjohnwu.superuser.Shell
import kotlin.properties.Delegates

lateinit var M3KApp: M3KHelperApplication

class M3KHelperApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        M3KApp = this
        Shell.setDefaultBuilder(
            Shell.Builder.create().setFlags(Shell.FLAG_REDIRECT_STDERR).setTimeout(10)
        )
    }
}