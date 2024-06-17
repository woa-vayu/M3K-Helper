package com.rxuglr.m3kwoahelper.util

import com.rxuglr.m3kwoahelper.util.Variables.NoModem
import com.rxuglr.m3kwoahelper.util.Variables.NoSensors
import com.rxuglr.m3kwoahelper.util.Variables.UEFIList
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
        return when {
            panel.contains("j20s_42") || panel.contains("k82_42") -> "Huaxing"
            panel.contains("j20s_36")
                    || panel.contains("tianma")
                    || panel.contains("k82_36") -> "Tianma"

            panel.contains("ebbg") -> "EBBG"
            panel.contains("samsung")
                    || panel.contains("ea8076_f1mp")
                    || panel.contains("ea8076_f1p2")
                    || panel.contains("ea8076_global") -> "Samsung"

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
        ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/modemst1 of=$(find /sdcard/Windows/Windows/System32/DriverStore/FileRepository -name qcremotefs8150.inf_arm64_*)/bootmodem_fs1 bs=8388608")
        ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/modemst2 of=$(find /sdcard/Windows/Windows/System32/DriverStore/FileRepository -name qcremotefs8150.inf_arm64_*)/bootmodem_fs2 bs=8388608")
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
        return if (NoSensors.value) true
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
            backup(1)
        }
        if (ShellUtils.fastCmd("find /sdcard/m3khelper/boot.img")
                .isEmpty()
        ) {
            backup(2)
        }
        if (!NoModem.value) {
            dumpmodem()
        }
        flashuefi(type)
        ShellUtils.fastCmd("svc power reboot")
    }
}