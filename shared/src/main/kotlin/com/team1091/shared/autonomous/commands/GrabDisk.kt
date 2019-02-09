package com.team1091.shared.autonomous.commands

import com.team1091.shared.system.GrabberSystem

class GrabDisk(val grabberSystem: GrabberSystem) : Command {

    override fun firstRun() {
    }

    override fun execute(dt: Double): Command? {
        grabberSystem.open()
        return null
    }

    override fun cleanUp(dt:Double) {
    }


}
