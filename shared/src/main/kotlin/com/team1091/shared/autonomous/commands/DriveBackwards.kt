package com.team1091.shared.autonomous.commands

import com.team1091.shared.components.RobotSettings
import com.team1091.shared.control.RobotComponents
import com.team1091.shared.math.Length
import com.team1091.shared.math.clamp

class DriveBackwards(
        val components: RobotComponents
) : Command {
    override fun firstRun() {

    }

    override fun cleanUp(dt: Double) {

    }

    override fun execute(dt: Double): Command? {
        val turn = components.targetingSystem.getCenter()
        val stoppingDistance = 75

        // if we don't see the target, then don't move
        if (!turn.seen) {
            return null
        }
        // if we are there (maybe for a while), stop
        if (turn.distance >= stoppingDistance) {
            return null
        }

        // if we get to this point, we see the target
        //println("Turn $turn")
        val forwardComponent = clamp(turn.distance / RobotSettings.distanceCoeffecient,
                RobotSettings.driveToMinMotorPower,
                RobotSettings.driveToMotorPower
        )

        components.driveSystem.arcadeDrive(forwardComponent, 0.0)
        return this
    }
}