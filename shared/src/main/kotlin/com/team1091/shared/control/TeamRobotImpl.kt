package com.team1091.shared.control

import com.team1091.shared.autonomous.commands.CommandList
import com.team1091.shared.autonomous.commands.TurnToAngle
import com.team1091.shared.game.StartingPos
import com.team1091.shared.math.degrees
import com.team1091.shared.math.squareACircle
import com.team1091.shared.system.AutonomousSystem
import com.team1091.shared.system.PositionSystem


// This controls our robot in both the sim and real life
class TeamRobotImpl(
        val components: RobotComponents
) : TeamRobot {

    var toggle = true

    private val autonomousSystem = AutonomousSystem()

    lateinit var positionSystem: PositionSystem

    override fun robotInit(startingPos: StartingPos) {
        positionSystem = PositionSystem(
                components.accelerometer,
                components.gyroscope,
                startingPos.pos.x,
                startingPos.pos.y,
                0.0,
                0.0,
                startingPos.rotation
        )
    }

    override fun autonomousInit() {
        autonomousSystem.init(
                CommandList(
                        TurnToAngle(components, positionSystem, 90.degrees)
//                        DriveForwards(components, 20.0.inches),
//                        Wait(components, 2.seconds),
//                        Turn(components, 90.0.degrees),
//                        Wait(components, 2.seconds),
//                        DriveForwards(components, 20.0.inches),
//                        Wait(components, 2.seconds),
//                        Turn(components, (-90.0).degrees)
                )
        )

    }

    override fun autonomousPeriodic() {
        val dt = getTime()
        autonomousSystem.drive(dt)
        positionSystem.integrate(dt)
    }

    override fun teleopInit() {

    }

    override fun teleopPeriodic() {

        val (x, y) = squareACircle(
                components.gameController.getLeftX(),
                components.gameController.getLeftY(),
                pressStartToggle()
        )

        components.drive.arcadeDrive(y, x)

        val kickstandPower = if (components.gameController.pressedY()) {
            1.0
        } else if (components.gameController.pressedB()) {
            -0.5
        } else {
            0.0
        }

        components.kickstandMotor.set(kickstandPower)

    }

    fun pressStartToggle(): Boolean {
        if (components.gameController.getStart()) {
            toggle = !toggle
        }
        return toggle
    }

    override fun disabledInit() {

    }

    override fun disabledPeriodic() {

    }

    override fun testInit() {

    }

    override fun testPeriodic() {

    }


    private var lastFrameTime = System.nanoTime()

    private fun getTime(): Double {
        val currentTime = System.nanoTime()
        val dt = (currentTime.toDouble() - lastFrameTime.toDouble()) / 1000000000.0
        lastFrameTime = currentTime
        return dt
    }


}