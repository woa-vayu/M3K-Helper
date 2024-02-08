package com.rxuglr.m3kwoahelper.qstiles

import android.service.quicksettings.Tile.STATE_UNAVAILABLE
import android.service.quicksettings.TileService
import com.rxuglr.m3kwoahelper.util.Commands

class QuickBootTile : TileService() {

    override fun onTileAdded() {
        super.onTileAdded()
        val uefi = Commands.getuefilist()
        if (!uefi.contains(120) and !uefi.contains(60) and !uefi.contains(1)) {
           qsTile.state = STATE_UNAVAILABLE
           qsTile.updateTile()
        }
    }
    override fun onStartListening() {
        super.onStartListening()
        val uefi = Commands.getuefilist()
        if (!uefi.contains(120) and !uefi.contains(60) and !uefi.contains(1)) {
            qsTile.state = STATE_UNAVAILABLE
            qsTile.updateTile()
        }
    }

    override fun onStopListening() {
        super.onStopListening()
        val uefi = Commands.getuefilist()
        if (!uefi.contains(120) and !uefi.contains(60) and !uefi.contains(1)) {
            qsTile.state = STATE_UNAVAILABLE
            qsTile.updateTile()
        }
    }

    override fun onClick() {
        super.onClick()
        val uefi = Commands.getuefilist()
        if (uefi.contains(120)) {Commands.quickboot(0)
        } else if (uefi.contains(60)) {Commands.quickboot(1)
        } else  if (uefi.contains(1)) {Commands.quickboot(2)}
    }

}
