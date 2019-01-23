package com.team1091.shared.autonomous.commands

import com.team1091.shared.control.RobotComponents

class ReleaseDisk(val components: RobotComponents) : Command {

    override fun firstRun() {
    }

    override fun execute(dt: Double): Command? {
        components.grabberSystem.release()
        return null
    }

    override fun cleanUp() {
    }

}
