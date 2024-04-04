package com.rxuglr.m3kwoahelper.qstiles

import android.service.quicksettings.Tile.STATE_ACTIVE
import android.service.quicksettings.Tile.STATE_UNAVAILABLE
import android.service.quicksettings.TileService
import com.rxuglr.m3kwoahelper.R
import com.rxuglr.m3kwoahelper.util.Commands.mountstatus
import com.rxuglr.m3kwoahelper.util.Commands.mountwin
import com.rxuglr.m3kwoahelper.util.Commands.quickboot
import com.rxuglr.m3kwoahelper.util.Commands.umountwin
import com.rxuglr.m3kwoahelper.util.Variables.UEFIList
import com.rxuglr.m3kwoahelper.woahApp

class MountTile : TileService() { // PoC

    override fun onStartListening() {
        super.onStartListening()
        if (mountstatus()) {
            qsTile.state = STATE_ACTIVE
            qsTile.label = woahApp.getString(
                R.string.mnt_question
            )
        } else {
            qsTile.state = STATE_ACTIVE
            qsTile.label = woahApp.getString(
                R.string.umnt_question
            )
        }
    }

    override fun onClick() {
        super.onClick()
        if (mountstatus()) {
            mountwin()
        } else {
            umountwin()
        }
    }

}

class QuickBootTile : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        if (UEFIList.contains(99)) {
            qsTile.state = STATE_UNAVAILABLE
            qsTile.subtitle = woahApp.getString(
                R.string.uefi_not_found_title
            )
            qsTile.updateTile()
        } else {
            qsTile.state = STATE_ACTIVE; qsTile.subtitle = ""
        }
    }

    override fun onClick() {
        super.onClick()
        if (!UEFIList.contains(99)) {
            if (UEFIList.contains(120)) {
                quickboot(0)
            } else if (UEFIList.contains(60)) {
                quickboot(1)
            } else if (UEFIList.contains(1)) {
                quickboot(2)
            }
        } else {
            qsTile.state = STATE_UNAVAILABLE
            qsTile.subtitle = woahApp.getString(
                R.string.uefi_not_found_title
            )
            qsTile.updateTile()
        }
    }

}
