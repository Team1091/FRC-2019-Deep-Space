package com.team1091.shared.system

import com.team1091.shared.components.ISolenoid

class GrabberSystem(val pnumatics: ISolenoid) : IGrabberSystem {

    override fun grab() {
        pnumatics.set(true)
    }

    override fun release() {
        pnumatics.set(false)
    }

}