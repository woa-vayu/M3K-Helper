package com.rxuglr.m3kwoahelper.qstiles

import android.service.quicksettings.Tile.STATE_ACTIVE
import android.service.quicksettings.Tile.STATE_UNAVAILABLE
import android.service.quicksettings.TileService
import com.rxuglr.m3kwoahelper.R
import com.rxuglr.m3kwoahelper.util.Commands.quickboot
import com.rxuglr.m3kwoahelper.util.Variables.UEFIList
import com.rxuglr.m3kwoahelper.woahApp

class QuickBootTile : TileService() {

    override fun onTileAdded() {
        super.onTileAdded()
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

    override fun onStopListening() {
        super.onStopListening()
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
