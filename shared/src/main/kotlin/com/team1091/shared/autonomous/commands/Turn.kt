package com.team1091.shared.autonomous.commands

import com.team1091.shared.control.RobotComponents
import com.team1091.shared.math.Rotation

class Turn(val components: RobotComponents, turnDegrees: Rotation) : Command {

    private val requiredTurnDistance: Double = Math.abs(turnDegrees.toDegrees() / 360.0) * (25 * Math.PI)
    private val isTurnRight: Boolean = turnDegrees.toRadians() > 0

    override fun firstRun() {
        println("Turn Starting")
        components.leftEncoder.reset()
        components.rightEncoder.reset()
    }

    override fun execute(dt: Double): Command? {


        val ltix = components.leftEncoder.getDistance()
        val rtix = components.rightEncoder.getDistance()

        val difference = Math.abs(rtix - ltix) / 2.0 // ticks per degree

        if (difference > requiredTurnDistance) {
            // We have turned far enough, we are done
            components.drive.arcadeDrive(0.0, 0.0)
            return null

        } else {
            components.drive.arcadeDrive(0.0, if (isTurnRight) 1.0 else -1.0)
            return this
        }

    }

    override fun cleanUp() {
        println("Turn Done")
        components.drive.arcadeDrive(0.0, 0.0)
    }

//    override fun getMessage(): String =
//            "Driving Forwards"


}