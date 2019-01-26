package com.team1091.shared.autonomous.commands

import com.team1091.shared.control.RobotComponents
import com.team1091.shared.system.ITargetingSystem

class TurnToTarget(val components: RobotComponents, val targetingSystem: ITargetingSystem) : Command {

    override fun firstRun() {

    }

    override fun execute(dt: Double): Command? {
        val turn = targetingSystem.getCenter()
        if(turn == null){
            return this
        }
        components.driveSystem.arcadeDrive(0.0, turn)
        return this
        // TODO: we should check to see if we are centered and stop
    }

    override fun cleanUp() {
        components.driveSystem.arcadeDrive(0.0, 0.0)

    }

}
