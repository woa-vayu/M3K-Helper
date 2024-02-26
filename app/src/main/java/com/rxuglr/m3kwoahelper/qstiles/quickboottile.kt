package com.rxuglr.m3kwoahelper.qstiles

import android.service.quicksettings.Tile.STATE_UNAVAILABLE
import android.service.quicksettings.TileService
import com.rxuglr.m3kwoahelper.util.Commands.quickboot
import com.rxuglr.m3kwoahelper.util.Variables.uefi

class QuickBootTile : TileService() {

    override fun onTileAdded() {
        super.onTileAdded()
        if (!uefi.contains(120) and !uefi.contains(60) and !uefi.contains(1)) {
           qsTile.state = STATE_UNAVAILABLE
           qsTile.updateTile()
        }
    }
    override fun onStartListening() {
        super.onStartListening()
        if (!uefi.contains(120) and !uefi.contains(60) and !uefi.contains(1)) {
            qsTile.state = STATE_UNAVAILABLE
            qsTile.updateTile()
        }
    }

    override fun onStopListening() {
        super.onStopListening()
        if (!uefi.contains(120) and !uefi.contains(60) and !uefi.contains(1)) {
            qsTile.state = STATE_UNAVAILABLE
            qsTile.updateTile()
        }
    }

    override fun onClick() {
        super.onClick()
        if (uefi.contains(120)) {quickboot(0)
        } else if (uefi.contains(60)) {quickboot(1)
        } else  if (uefi.contains(1)) {quickboot(2)}
    }

}
