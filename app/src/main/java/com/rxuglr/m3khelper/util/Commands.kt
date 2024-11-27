package com.rxuglr.m3khelper.util

import com.rxuglr.m3khelper.util.Variables.CurrentDeviceCard
import com.rxuglr.m3khelper.util.Variables.UEFIList
import com.topjohnwu.superuser.ShellUtils

object Commands {
    fun dumpBoot(where: Int) {
        val slot = ShellUtils.fastCmd("getprop ro.boot.slot_suffix")
        when (where) {
            1 -> {
                mountWindows()
                ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/boot$slot of=/sdcard/Windows/boot.img bs=32MB")
                umountWindows()
            }

            2 -> {
                ShellUtils.fastCmd("mkdir /sdcard/m3khelper || true ")
                ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/boot$slot of=/sdcard/m3khelper/boot.img")
            }
        }
    }

    fun mountStatus(): Boolean {
        return ShellUtils.fastCmd("mount | grep " + ShellUtils.fastCmd("readlink -fn /dev/block/bootdevice/by-name/win"))
            .isEmpty()
    }

    fun mountWindows() {
        ShellUtils.fastCmd("mkdir /mnt/sdcard/Windows || true")
        ShellUtils.fastCmd("su -mm -c mount.ntfs /dev/block/by-name/win /sdcard/Windows")
    }

    fun umountWindows() {
        ShellUtils.fastCmd("su -mm -c umount /mnt/sdcard/Windows")
        ShellUtils.fastCmd("rmdir /mnt/sdcard/Windows")
    }

    fun dumpModem() {
        mountWindows()
        ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/modemst1 of=$(find /sdcard/Windows/Windows/System32/DriverStore/FileRepository -name qcremotefs8150.inf_arm64_*)/bootmodem_fs1 bs=8388608")
        ShellUtils.fastCmd("dd if=/dev/block/bootdevice/by-name/modemst2 of=$(find /sdcard/Windows/Windows/System32/DriverStore/FileRepository -name qcremotefs8150.inf_arm64_*)/bootmodem_fs2 bs=8388608")
        umountWindows()
    }

    fun flashUEFI(uefiPath: String) {
        val slot = ShellUtils.fastCmd("getprop ro.boot.slot_suffix")
        ShellUtils.fastCmd("dd if=" + uefiPath + " of=/dev/block/bootdevice/by-name/boot$slot")
    }

    fun checkSensors(): Boolean {
        return if (CurrentDeviceCard.sensors == false) true
        else {
            mountWindows()
            val check =
                ShellUtils.fastCmd("find /sdcard/Windows/Windows/System32/Drivers/DriverData/QUALCOMM/fastRPC/persist/sensors/*")
                    .isNotEmpty()
            umountWindows()
            check
        }
    }

    fun dumpSensors() {
        mountWindows()
        ShellUtils.fastCmd("cp -r /vendor/persist/sensors/* /sdcard/Windows/Windows/System32/Drivers/DriverData/QUALCOMM/fastRPC/persist/sensors")
        umountWindows()
    }

    fun quickboot(uefiPath: String) {
        if (ShellUtils.fastCmd("find /sdcard/Windows/boot.img")
                .isEmpty()
        ) {
            dumpBoot(1)
        }
        if (ShellUtils.fastCmd("find /sdcard/m3khelper/boot.img")
                .isEmpty()
        ) {
            dumpBoot(2)
        }
        if (CurrentDeviceCard.noModem == false) {
            dumpModem()
        }
        if (CurrentDeviceCard.sensors == true && checkSensors() == false) {
            dumpSensors()
        }
        flashUEFI(uefiPath)
        ShellUtils.fastCmd("svc power reboot")
    }
}