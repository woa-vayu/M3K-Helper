package com.rxuglr.m3kwoahelper.util

import com.rxuglr.m3kwoahelper.util.Variables.UEFIList
import com.rxuglr.m3kwoahelper.util.Variables.Codename
import com.rxuglr.m3kwoahelper.util.Variables.Codenames
import com.rxuglr.m3kwoahelper.util.Variables.NoModem
import com.rxuglr.m3kwoahelper.util.Variables.Sensors
import com.topjohnwu.superuser.ShellUtils

object Commands {
    fun backup(where: Int) {
        val slot = ShellUtils.fastCmd("getprop ro.boot.slot_suffix")
        if (where == 1) {
            mountwin()
            ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/boot$slot of=/sdcard/Windows/boot.img bs=32MB")
            umountwin()
        } else if (where == 2) {
            ShellUtils.fastCmd("mkdir /sdcard/m3khelper || true ")
            ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/boot$slot of=/sdcard/m3khelper/boot.img")
        }
    }

    fun displaytype(): Any {
        val panel = ShellUtils.fastCmd("cat /proc/cmdline")
        return when (Codename) {
            Codenames[0], Codenames[1] -> if (panel.contains("j20s_42")) {
                "Huaxing"
            } else if (panel.contains("j20s_36")) {
                "Tianma"
            } else {
                "Unknown"
            }

            Codenames[2] -> if (panel.contains("k82_42")) {
                "Huaxing"
            } else if (panel.contains("k82_36")) {
                "Tianma"
            } else {
                "Unknown"
            }

            Codenames[3], Codenames[4], Codenames[5], Codenames[6] -> if (panel.contains("ea8076_f1mp") or panel.contains(
                    "ea8076_f1p2"
                ) or panel.contains("ea8076_global")
            ) {
                "Samsung"
            } else {
                "Samsung (Unsupported)"
            }

            else -> "Unknown"
        }
    }

    fun mountstatus(): Boolean {
        return ShellUtils.fastCmd("mount | grep " + ShellUtils.fastCmd("readlink -fn /dev/block/bootdevice/by-name/win"))
            .isEmpty()
    }

    fun mountwin() {
        ShellUtils.fastCmd("mkdir /mnt/sdcard/Windows || true")
        ShellUtils.fastCmd("su -mm -c mount.ntfs /dev/block/by-name/win /sdcard/Windows")
    }

    fun umountwin() {
        ShellUtils.fastCmd("su -mm -c umount /mnt/sdcard/Windows")
        ShellUtils.fastCmd("rmdir /mnt/sdcard/Windows")
    }

    fun dumpmodem() {
        mountwin()
        ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/modemst1 of=$(find /sdcard/Windows/Windows/System32/DriverStore/FileRepository/qcremotefs8150.inf_arm64_* -type f | grep fs1)")
        ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/modemst2 of=$(find /sdcard/Windows/Windows/System32/DriverStore/FileRepository/qcremotefs8150.inf_arm64_* -type f | grep fs2)")
        umountwin()
    }

    private fun getuefipath(type: Int): String {
        val uefiPath = arrayOf("", "", "")
        if (UEFIList.contains(120)) {
            uefiPath[0] =
                ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 120")
        }
        if (UEFIList.contains(60)) {
            uefiPath[1] += ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img | grep 60")
        }
        if (UEFIList.contains(1)) {
            uefiPath[2] += ShellUtils.fastCmd("find /mnt/sdcard/UEFI/ -type f  | grep .img")
        }
        return uefiPath[type]
    }

    fun flashuefi(type: Int) {
        val slot = ShellUtils.fastCmd("getprop ro.boot.slot_suffix")
        ShellUtils.fastCmd("dd if=" + getuefipath(type) + " of=/dev/block/bootdevice/by-name/boot$slot")
    }

    fun checksensors(): Boolean {
        return if (!Sensors.contains(Codename)) true
        else {
            mountwin()
            val check =
                ShellUtils.fastCmd("find /sdcard/Windows/Windows/System32/Drivers/DriverData/QUALCOMM/fastRPC/persist/sensors/*")
                    .isNotEmpty()
            umountwin()
            check
        }
    }

    fun dumpsensors() {
        mountwin()
        ShellUtils.fastCmd("cp -r /vendor/persist/sensors/* /sdcard/Windows/Windows/System32/Drivers/DriverData/QUALCOMM/fastRPC/persist/sensors")
        umountwin()
    }

    fun quickboot(type: Int) {
        if (ShellUtils.fastCmd("find /sdcard/Windows/boot.img")
                .isEmpty()
        ) {
            backup(1);
        }
        if (ShellUtils.fastCmd("find /sdcard/m3khelper/boot.img")
                .isEmpty()
        ) {
            backup(2);
        }
        if (!NoModem.value) {
            dumpmodem()
        }
        flashuefi(type)
        ShellUtils.fastCmd("svc power reboot")
    }
}