package com.team1091.sim.phys

import com.team1091.shared.control.RobotComponents
import com.team1091.shared.control.TeamRobot
import com.team1091.shared.game.StartingPos
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body

class SimRobot(
        val startingPos: StartingPos,

        val xSize: Float,
        val ySize: Float,

        val teamRobot: TeamRobot,
        // we need to read the drive instructions out to simulate them.
        val rc: RobotComponents
) {
    lateinit var body: Body

    // Used to calculate acceleration
    var lastVelocity = Vec2()
}


