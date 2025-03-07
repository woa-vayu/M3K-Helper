package com.remtrik.m3khelper.util

import java.util.Locale

/*
*  * Copyright (C) 2020 Vern Kuato
*  *
*  * Licensed under the Apache License, Version 2.0 (the "License");
*  * you may not use this file except in compliance with the License.
*  * You may obtain a copy of the License at
*  *
*  *      http://www.apache.org/licenses/LICENSE-2.0
*  *
*  * Unless required by applicable law or agreed to in writing, software
*  * distributed under the License is distributed on an "AS IS" BASIS,
*  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  * See the License for the specific language governing permissions and
*  * limitations under the License.
*  */


private fun floatForm(d: Double): String {
    return String.format(Locale.US, "%.2f", d)
}

fun bytesToHuman(size: Long): String {
    val kb: Long = 1024
    val mb = kb * 1024
    val gb = mb * 1024
    val tb = gb * 1024
    val pb = tb * 1024
    val eb = pb * 1024
    return when {
        size < kb -> floatForm(size.toDouble())
        size in kb..<mb -> floatForm(size.toDouble() / kb)
        size in mb..<gb -> floatForm(size.toDouble() / mb)
        size in gb..<tb -> floatForm(size.toDouble() / gb)
        size in tb..<pb -> floatForm(size.toDouble() / tb)
        size in pb..<eb -> floatForm(size.toDouble() / pb)
        else -> floatForm(size.toDouble() / eb)
    }
}

fun extractNumberFromString(source: String): String {
    val result = StringBuilder(100)
    for (ch in source.toCharArray()) {
        if (ch in '0'..'9') {
            result.append(ch)
        }
    }
    return result.toString()
}

