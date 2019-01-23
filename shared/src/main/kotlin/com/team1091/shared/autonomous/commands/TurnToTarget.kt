package com.team1091.shared.autonomous.commands

import com.team1091.shared.control.RobotComponents

class TurnToTarget(val components: RobotComponents) : Command {

    override fun firstRun() {

    }

    override fun execute(dt: Double): Command? {
        val turn = components.targetingSystem.getCenter()
        components.drive.arcadeDrive(0.0, turn)

        return this
        // TODO: we should check to see if we are centered and stop
    }

    override fun cleanUp() {
        components.drive.arcadeDrive(0.0, 0.0)

    }

}
