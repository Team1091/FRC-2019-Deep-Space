package com.team1091.shared.autonomous.commands

import com.team1091.shared.control.RobotComponents

class GrabDisk(val components: RobotComponents) : Command {

    override fun firstRun() {
    }

    override fun execute(dt: Double): Command? {
        components.grabberSystem.grab()
        return null
    }

    override fun cleanUp() {
    }

}
