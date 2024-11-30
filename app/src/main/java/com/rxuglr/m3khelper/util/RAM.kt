package com.rxuglr.m3khelper.util

import android.app.ActivityManager
import android.content.Context


private fun getTotalMemory(context: Context): Long {
    val actManager =
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val memInfo = ActivityManager.MemoryInfo()
    actManager.getMemoryInfo(memInfo)
    return memInfo.totalMem
}

fun getMemory(context: Context): String {
    val mem: String = bytesToHuman(getTotalMemory(context))
    val ram = extractNumberFromString(mem).toInt()
    return when {
        ram > 800 -> 12
        ram > 600 -> 8
        else -> 6
    }.toString() + "GB"
}

