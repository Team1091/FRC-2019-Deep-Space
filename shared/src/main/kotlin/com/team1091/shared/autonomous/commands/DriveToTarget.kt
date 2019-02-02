package com.team1091.shared.autonomous.commands

import com.team1091.shared.control.RobotComponents
import com.team1091.shared.system.ITargetingSystem
import java.lang.Double.min

class DriveToTarget(val components: RobotComponents, val targetingSystem: ITargetingSystem) : Command {

    override fun firstRun() {

    }

    override fun execute(dt: Double): Command? {

        // This will be used for an initial turn in towards the center


        // find target - we can get if its seen
        val turn = targetingSystem.getCenter()

        // if we dont see the target, then keep waiting until its seen
        if (!turn.seen) {
            return this
        }

        // TODO: if we are there (maybe for a while), stop
//        if () {
//            return null
//        }


        // if we get to this point, we see the target
        println("Turn ${turn}")

        val forwardComponent = min(turn.distance / 50.0, 1.0)
        val turnComponent = if (turn.center < 0) -0.4 else 0.4

        // TODO: we need to keep aligned center wise
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
