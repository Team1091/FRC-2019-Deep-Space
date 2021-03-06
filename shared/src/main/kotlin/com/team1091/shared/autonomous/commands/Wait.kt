package com.team1091.shared.autonomous.commands

import com.team1091.shared.control.RobotComponents
import com.team1091.shared.math.Time

class Wait(private val components: RobotComponents, private val timeToWait: Time = Time(0)) : Command {

    private lateinit var start: Time

    override fun firstRun() {
        start = Time.now()

       // println("Waiting")
    }

    override fun execute(dt: Double): Command? {

        return if (System.currentTimeMillis() - start.ms > timeToWait.ms) {
            null
        } else {
            this
        }


    }

    override fun cleanUp(dt: Double) {
    }


}