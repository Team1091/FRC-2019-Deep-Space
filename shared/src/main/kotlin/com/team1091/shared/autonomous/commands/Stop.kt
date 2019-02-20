package com.team1091.shared.autonomous.commands

import com.team1091.shared.control.RobotComponents
import com.team1091.shared.math.Time

class Stop(private val components: RobotComponents, private val timeToWait: Time = Time(0)) : Command {

    override fun firstRun() {
    }

    override fun execute(dt: Double): Command? {

        return this


    }

    override fun cleanUp(dt: Double) {
    }


}