package com.team1091.shared.autonomous.commands

import com.team1091.shared.components.RobotSettings
import com.team1091.shared.control.RobotComponents
import com.team1091.shared.math.clamp

class TurnToTarget(val components: RobotComponents) : Command {

    override fun firstRun() {
        println("Turning to target")
    }

    override fun execute(dt: Double): Command? {
        //println("Spam")
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
        //println("Turn $turn")

        components.driveSystem.arcadeDrive(
                0.0,
                if (Math.abs(turn.center) < 0.01) {
                    println("Auto Drive Power: 0")
                    0.0
                } else if (turn.center < 0) {
                    var tt = clamp(turn.center * 3, -0.65, -0.55)
                    //println("Auto Drive Power: " + tt)
                    tt
                } else {
                    var ttt = clamp(turn.center * 3, 0.55, 0.65)
                    //println("Auto Drive Power: " + ttt)
                    ttt
                }
        )

        return this
    }

    override fun cleanUp(dt: Double) {
        //components.driveSystem.arcadeDrive(0.0, 0.0)

    }

}
