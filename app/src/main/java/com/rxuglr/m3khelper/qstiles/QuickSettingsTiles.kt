package com.rxuglr.m3khelper.qstiles

import android.service.quicksettings.Tile.STATE_ACTIVE
import android.service.quicksettings.Tile.STATE_UNAVAILABLE
import android.service.quicksettings.TileService
import com.rxuglr.m3khelper.M3KApp
import com.rxuglr.m3khelper.R
import com.rxuglr.m3khelper.util.mountStatus
import com.rxuglr.m3khelper.util.mountWindows
import com.rxuglr.m3khelper.util.quickboot
import com.rxuglr.m3khelper.util.umountWindows
import com.rxuglr.m3khelper.util.Variables.CurrentDeviceCard
import com.rxuglr.m3khelper.util.Variables.UEFICardsArray
import com.rxuglr.m3khelper.util.Variables.UEFIList

class MountTile : TileService() { // PoC

    override fun onStartListening() {
        super.onStartListening()
        if (mountStatus()) {
            qsTile.state = STATE_ACTIVE
            qsTile.label = M3KApp.getString(
                R.string.mnt_question
            )
        } else {
            qsTile.state = STATE_ACTIVE
            qsTile.label = M3KApp.getString(
                R.string.umnt_question
            )
        }
    }

    override fun onClick() {
        super.onClick()
        if (mountStatus()) {
            mountWindows()
        } else {
            umountWindows()
        }
    }

}

class QuickBootTile : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        if (CurrentDeviceCard.noFlash == true) {
            qsTile.state = STATE_UNAVAILABLE
            qsTile.subtitle = M3KApp.getString(
                R.string.qs_unsupported
            )
            qsTile.updateTile()
        } else {
            if (UEFIList.isEmpty()) {
                qsTile.state = STATE_UNAVAILABLE
                qsTile.subtitle = M3KApp.getString(
                    R.string.uefi_not_found_title
                )
                qsTile.updateTile()
            } else {
                qsTile.state = STATE_ACTIVE; qsTile.subtitle = ""
            }
        }
    }

    override fun onClick() {
        super.onClick()
        if (UEFIList.isNotEmpty()) {
            if (UEFIList.contains(120)) {
                quickboot(UEFICardsArray[3].uefiPath)
            } else if (UEFIList.contains(90)) {
                quickboot(UEFICardsArray[2].uefiPath)
            } else if (UEFIList.contains(60)) {
                quickboot(UEFICardsArray[1].uefiPath)
            } else if (UEFIList.contains(1)) {
                quickboot(UEFICardsArray[0].uefiPath)
            }
        } else {
            qsTile.state = STATE_UNAVAILABLE
            qsTile.subtitle = M3KApp.getString(
                R.string.uefi_not_found_title
            )
            qsTile.updateTile()
        }
    }

}
