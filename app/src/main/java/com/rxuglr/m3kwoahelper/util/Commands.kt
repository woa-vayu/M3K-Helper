package com.rxuglr.m3kwoahelper.util

import com.rxuglr.m3kwoahelper.codename
import com.topjohnwu.superuser.ShellUtils

object Commands {

    val codenames = arrayOf("vayu", "bhima", "nabu", "raphael", "raphaelin", "cepheus", "raphaels")
    val nomodem = arrayOf("nabu")
    val sensors = arrayOf(codenames[3], codenames[4], codenames[5], codenames[6])
    val uefi = getuefilist()

    fun devicename(): String {
        return when (codename) {
            codenames[0],codenames[1] -> "POCO X3 Pro"
            codenames[2] -> "Xiaomi Pad 5"
            codenames[3] -> "Xiaomi Mi 9T Pro"
            codenames[4] -> "Redmi K20 Pro"
            codenames[5] -> "Xiaomi Mi 9"
            codenames[6] -> "Redmi K20 Pro Premium"
            else -> "Unknown"
        }
    }

    fun backup(where: Int) {
        val slot = ShellUtils.fastCmd("getprop ro.boot.slot_suffix")
        if (where == 1) {
            mountwin()
            ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/boot$slot of=/sdcard/windows/boot.img bs=32MB")
            umountwin()
        } else if (where == 2) {
            ShellUtils.fastCmd("mkdir /sdcard/m3khelper || true ")
            ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/boot$slot of=/sdcard/m3khelper/boot.img")
        }
    }

    fun displaytype(): Any {
        val panel = ShellUtils.fastCmd("cat /proc/cmdline")
        return when (codename) {
            codenames[0],codenames[1] -> if (panel.contains("j20s_42")) {"Huaxing"} else if (panel.contains("j20s_36")) {"Tianma"} else {"Unknown"}
            codenames[2] -> if (panel.contains("k82_42")) {"Huaxing"} else if (panel.contains("k82_36")) {"Tianma"} else {"Unknown"}
            codenames[3], codenames[4], codenames[5], codenames[6] -> if (panel.contains("ea8076_f1mp") or panel.contains("ea8076_f1p2") or panel.contains("ea8076_global")) {"Samsung"} else {"Samsung (Unsupported)"}
            else -> "Unknown"
        }
    }

    fun mountstatus(): Boolean {
        return ShellUtils.fastCmd("mount | grep " + ShellUtils.fastCmd("readlink -fn /dev/block/bootdevice/by-name/win"))
            .isEmpty()
    }

    fun mountwin() {
        ShellUtils.fastCmd("mkdir /mnt/sdcard/windows || true")
        ShellUtils.fastCmd("su -mm -c mount.ntfs /dev/block/by-name/win /sdcard/windows")
    }

    fun umountwin() {
        ShellUtils.fastCmd("su -mm -c umount /mnt/sdcard/windows")
        ShellUtils.fastCmd("rmdir /mnt/sdcard/windows")
    }

    fun dumpmodem() {
        mountwin()
        ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/modemst1 of=/sdcard/windows/Windows/System32/DriverStore/FileRepository/qcremotefs8150.inf_arm64_04af705613ed2d36/bootmodem_fs1")
        ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/modemst2 of=/sdcard/windows/Windows/System32/DriverStore/FileRepository/qcremotefs8150.inf_arm64_04af705613ed2d36/bootmodem_fs2")
        umountwin()
    }

    fun getuefilist(): Array<Int> {
        var uefilist = arrayOf(0)
        if ((ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img").isEmpty())) {
            uefilist +=99
        } else {
            if (ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 120")
                    .isNotEmpty()
            ) {
                uefilist += 120
            }
            if (ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 60")
                    .isNotEmpty()
            ) {
                uefilist += 60
            }
            if ((ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img")
                    .isNotEmpty()) and (!uefilist.contains(60)) and !uefilist.contains(120)
            ) {
                uefilist += 1
            }
        }
        return uefilist
    }

    private fun getuefipath(type: Int): String {
        val uefilist = getuefilist()
        val uefipath = arrayOf("", "", "")
        if (uefilist.contains(120)) {
            uefipath[0] =
                ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 120")
        }
        if (uefilist.contains(60)) {
            uefipath[1] += ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 60")
        }
        if (uefilist.contains(1)) {
            uefipath[2] += ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img")
        }
        return uefipath[type]
    }

    fun flashuefi(type: Int) {
        val slot = ShellUtils.fastCmd("getprop ro.boot.slot_suffix")
        ShellUtils.fastCmd("dd if=" + getuefipath(type) + " of=/dev/block/bootdevice/by-name/boot$slot")
    }

    fun checksensors(): Boolean {
        return if (!sensors.contains(codename)) true
        else {
            mountwin()
            val check =
                ShellUtils.fastCmd("find /sdcard/windows/Windows/System32/Drivers/DriverData/QUALCOMM/fastRPC/persist/sensors/*")
                    .isNotEmpty()
            umountwin()
           check
        }
    }
    fun dumpsensors() {
        mountwin()
        ShellUtils.fastCmd("cp -r /vendor/persist/sensors/* /sdcard/windows/Windows/System32/Drivers/DriverData/QUALCOMM/fastRPC/persist/sensors")
        umountwin()
    }

    fun quickboot(type: Int) {
        if (!nomodem.contains(codename))  { dumpmodem() }
        flashuefi(type)
        ShellUtils.fastCmd("svc power reboot")
    }
}