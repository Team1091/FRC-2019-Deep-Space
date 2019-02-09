package com.team1091.shared.system

import com.team1091.shared.components.ISolenoid

class GrabberSystem(
        private val grabberSolenoid0: ISolenoid,
        private val grabberSolenoid1: ISolenoid
) : IGrabberSystem {

    override fun grab() {
        grabberSolenoid0.set(true)

    }

    override fun release() {
        grabberSolenoid0.set(false)
    }

}