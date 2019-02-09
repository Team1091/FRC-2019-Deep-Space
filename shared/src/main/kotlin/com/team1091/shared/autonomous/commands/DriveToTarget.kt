package com.team1091.shared.autonomous.commands

import com.team1091.shared.components.RobotSettings
import com.team1091.shared.control.RobotComponents
import com.team1091.shared.math.clamp

class DriveToTarget(val components: RobotComponents) : Command {

    override fun firstRun() {

    }

    override fun execute(dt: Double): Command? {

        // This will be used for an initial turn in towards the center


        // find target - we can get if its seen
        val turn = components.targetingSystem.getCenter()
        val stoppingDistance = 1

        // if we don't see the target, then keep waiting until its seen
        if (!turn.seen) {
            return this
        }

        // TODO: if we are there (maybe for a while), stop
        if (turn.distance >= stoppingDistance) {
            return null
        }


        // if we get to this point, we see the target
        println("Turn $turn")
        val forwardComponent = clamp(turn.distance / RobotSettings.distanceCoeffecient,
                RobotSettings.driveToMinMotorPower,
                RobotSettings.driveToMotorPower
        )

        val turnComponent = if (turn.center < 0) -0.4 else 0.4

        components.driveSystem.arcadeDrive(
                forwardComponent,
                turnComponent
        )

        return this

    }

    override fun cleanUp() {
        components.driveSystem.arcadeDrive(0.0, 0.0)
    }

}
