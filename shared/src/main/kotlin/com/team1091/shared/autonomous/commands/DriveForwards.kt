package com.team1091.shared.autonomous.commands

import com.team1091.shared.components.RobotSettings
import com.team1091.shared.control.RobotComponents
import com.team1091.shared.math.Length
import com.team1091.shared.math.clamp

open class DriveForwards(
        private val components: RobotComponents,
        private val distance: Length
) : Command {

    private val forwards: Boolean = distance.distance >= 0

    override fun firstRun() {
    }

    override fun execute(dt: Double): Command? {
        return null
    }

    override fun cleanUp(dt: Double) {
        //println("Drive Cleaning")
      //  components.driveSystem.arcadeDrive(0.0, 0.0)
    }

//    override fun getMessage(): String =
//            "Driving Forwards"


}