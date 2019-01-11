package com.team1091.sim

import com.studiohartman.jamepad.ControllerManager
import com.team1091.shared.control.RobotComponents
import com.team1091.shared.control.TeamRobotImpl
import com.team1091.shared.game.StartingPos
import com.team1091.shared.system.PositionSystem
import com.team1091.sim.components.SimAccelerometer
import com.team1091.sim.components.SimController
import com.team1091.sim.components.SimDrive
import com.team1091.sim.components.SimEncoder
import com.team1091.sim.components.SimGyroscope
import com.team1091.sim.phys.GamePiece
import com.team1091.sim.phys.Obstacle
import com.team1091.sim.phys.SimRobot
import org.jbox2d.dynamics.Body
import processing.core.PApplet
import java.awt.Color


fun main(args: Array<String>) {
    PApplet.main("com.team1091.sim.Simulator")
}


class Simulator : PApplet() {
    private val simWorld: SimWorld
    private val controllers = ControllerManager()

    init {
        controllers.initSDLGamepad()

        val startingPos = StartingPos.values()

        val robots = Array(6) { id ->

            val start = startingPos[id]

            val rc = RobotComponents(
                    SimController(controllers, id),
                    SimDrive(100000.0, 1000000.0),
                    SimEncoder(20.0),
                    SimEncoder(-20.0),
                    SimAccelerometer(),
                    SimGyroscope()
            )

            SimRobot(start,
                    25f, 30f,
                    TeamRobotImpl(rc),
                    rc // These are needed to simulate its position.
            )
        }

        simWorld = SimWorld(
                robots = robots,
                gamePieces = arrayOf(
                        GamePiece(100f, 100f, 0f, 15f, 15f),
                        GamePiece(100f, 150f, 0f, 15f, 15f),
                        GamePiece(100f, 200f, 0f, 15f, 15f),

                        GamePiece(550f, 100f, 0f, 15f, 15f),
                        GamePiece(550f, 150f, 0f, 15f, 15f),
                        GamePiece(550f, 200f, 0f, 15f, 15f)
                ),
                obstacles = arrayOf(
                        Obstacle(-25f, 160f, 50f, 320f), // left
                        Obstacle(675f, 160f, 50f, 320f), // right
                        Obstacle(325f, -25f, 650f, 50f), // top
                        Obstacle(325f, 345f, 650f, 50f) // bottom
                )
        )
    }

    override fun settings() {
        size(1200, 600)
    }

    override fun setup() {
        fill(120f, 50f, 240f)
    }


    private var lastTime = 0
    override fun draw() {

        val now = millis()
        val delta = (now - lastTime) / 1000.0
        lastTime = now

        simWorld.stepSimulation(delta)
        render()
    }


    private fun render() {
        background(200f)
        pushMatrix()
        // shift from camera
        translate(20f, 20f)
        scale(1.75f)

        // draw everything
        fill(100f)
        rect(0f, 0f, simWorld.fieldXSize.toFloat(), simWorld.fieldYSize.toFloat())

        // Draw all the obstacles
        for (obstacles in simWorld.obstacles) {
            draw(obstacles.body, obstacles.xSize, obstacles.ySize, Color.DARK_GRAY.rgb)
        }

        for (gamePiece in simWorld.gamePieces) {
            draw(gamePiece.body, gamePiece.xSize, gamePiece.ySize, Color.YELLOW.rgb)
        }

        // Draw all the robots
        for (robot in simWorld.robots) {
            draw(robot.body, robot.xSize, robot.ySize, robot.startingPos.alliance.color, true)
            drawLine(((robot.teamRobot as TeamRobotImpl)).positionSystem)
        }

        popMatrix()

        fill(0f)
        text("${simWorld.currentGameState.name} - ${simWorld.elapsedSec.toLong()}", 10f, 10f)
    }

    private fun drawLine(positionSystem: PositionSystem) {
        pushMatrix()
        // TODO: this goes out of control
        val position = positionSystem.getPos()
        translate(position.x.toFloat(), position.y.toFloat())
        rotate(position.r.toRadians().toFloat())
        triangle(10f, 0f, 0f, -10f, 0f, 10f)
        popMatrix()
    }

    private fun draw(body: Body, xSize: Float, ySize: Float, color: Int, facing: Boolean = false) {
        // Unfortunately, no one can be told what the matrix is.  You have to see it for yourself.
        pushMatrix()

        translate(body.position.x, body.position.y)
        rotate(body.angle)

        pushMatrix()
        translate(-xSize / 2f, -ySize / 2f)
        fill(color)
        rect(0f, 0f, xSize, ySize)
        popMatrix()

        if (facing) {
            fill(Color.GREEN.rgb)
            line(0f, 0f, xSize / 2f, 0f)
            triangle(xSize / 2f, 0f, 0f, -ySize / 2f, 0f, ySize / 2f)
        }
        popMatrix()
    }

}




