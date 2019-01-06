package com.team1091.shared.autonomous.commands

import com.team1091.shared.control.RobotComponents

class FullAutonomous(val components: RobotComponents) : Command {

    override fun firstRun() {

    }

    override fun execute(dt: Double): Command? {

        //TODO: this all
        // with what we know, set the field and then plan on it

        // convert it into a list of actions

        // execute those actions until we we fail, then replan
        return null
    }

    override fun cleanUp() {

    }

}