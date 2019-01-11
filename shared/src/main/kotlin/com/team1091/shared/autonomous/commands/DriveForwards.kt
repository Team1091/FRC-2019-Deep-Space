package com.team1091.shared.autonomous.commands

import com.team1091.shared.control.RobotComponents
import com.team1091.shared.math.Length

class DriveForwards(private val components: RobotComponents, private val distance: Length) : Command {

    override fun firstRun() {
        println("Drive Starting")
        components.leftEncoder.reset()
    }

    override fun execute(dt: Double): Command? {

        if (components.leftEncoder.get() < distance.toInches()) {
            components.drive.arcadeDrive(1.0, 0.0)
            return this
        }
        return null

    }

    override fun cleanUp() {
        println("Drive Cleaning")
        components.drive.arcadeDrive(0.0, 0.0)
    }

//    override fun getMessage(): String =
//            "Driving Forwards"


}