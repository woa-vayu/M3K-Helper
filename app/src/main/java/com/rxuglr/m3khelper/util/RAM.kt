package com.rxuglr.m3khelper.util

import android.app.ActivityManager
import android.content.Context

object RAM {
    private fun getTotalMemory(context: Context): Long {
        val actManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        return memInfo.totalMem
    }

    fun getMemory(context: Context): String {
        val mem: String = MemoryUtils.bytesToHuman(getTotalMemory(context))
        val ram = MemoryUtils.extractNumberFromString(mem).toInt()
        return (if (ram > 800) 12 else if (ram > 600) 8 else 6).toString() + "GB"
    }
}
