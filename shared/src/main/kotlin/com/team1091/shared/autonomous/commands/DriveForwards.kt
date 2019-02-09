package com.team1091.shared.autonomous.commands

import com.team1091.shared.control.RobotComponents
import com.team1091.shared.math.Length

open class DriveForwards(
        private val components: RobotComponents,
        private val distance: Length
) : Command {

    private val forwards: Boolean = distance.distance >= 0

    override fun firstRun() {
        println("Drive Starting")
        components.leftEncoder.reset()
    }

    override fun execute(dt: Double): Command? {

        if (forwards) {
            if (components.leftEncoder.get() < distance.toInches()) {
                components.driveSystem.arcadeDrive(1.0, 0.0, dt)
                return this
            }
            return null

        } else { // backwards
            if (components.leftEncoder.get() > distance.toInches()) {
                components.driveSystem.arcadeDrive(-1.0, 0.0, dt)
                return this
            }
            return null

        }


    }

    override fun cleanUp(dt: Double) {
        println("Drive Cleaning")
        components.driveSystem.arcadeDrive(0.0, 0.0, dt)
    }

//    override fun getMessage(): String =
//            "Driving Forwards"


}