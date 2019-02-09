package com.team1091.shared.autonomous.commands

import com.team1091.shared.components.RobotSettings
import com.team1091.shared.control.RobotComponents

class TurnToTarget(val components: RobotComponents) : Command {

    override fun firstRun() {

    }

    override fun execute(dt: Double): Command? {

        // This will be used for an initial turn in towards the center


        // find target - we can get if its seen
        val turn = components.targetingSystem.getCenter()

        // if we dont see the target, then keep waiting until its seen
        if (!turn.seen) {
            return this
        }

        if (RobotSettings.centerZeroMargin > Math.abs(turn.center)) {
            return null
        }


        // if we get to this point, we see the target
        println("Turn $turn")

        components.driveSystem.arcadeDrive(
                forwardAmnt = 0.0,
                turnAmnt = if (turn.center < 0) -0.65 else 0.65,
                dt = dt
        )

        return this
    }

    override fun cleanUp(dt:Double) {
        components.driveSystem.arcadeDrive(0.0, 0.0, dt)

    }

}
