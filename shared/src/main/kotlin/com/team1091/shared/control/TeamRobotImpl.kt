package com.team1091.shared.control

import com.team1091.shared.autonomous.commands.CommandList
import com.team1091.shared.autonomous.commands.DriveForwards
import com.team1091.shared.autonomous.commands.DriveToTarget
import com.team1091.shared.autonomous.commands.ReleaseDisk
import com.team1091.shared.autonomous.commands.TurnToAngle
import com.team1091.shared.autonomous.commands.TurnToTarget
import com.team1091.shared.game.StartingPos
import com.team1091.shared.math.degrees
import com.team1091.shared.math.feet
import com.team1091.shared.math.squareACircle
import com.team1091.shared.system.AutonomousSystem
import com.team1091.shared.system.GrabberSystem
import com.team1091.shared.system.ITargetingSystem
import com.team1091.shared.system.PositionSystem


// This controls our robot in both the sim and real life
class TeamRobotImpl(
        val components: RobotComponents,
        val targetingSystem: ITargetingSystem
) : TeamRobot {

    var toggle = true

    private val autonomousSystem = AutonomousSystem()
    private val grabberSystem = GrabberSystem(components.grabberSolenoid)
    //    private val targetingSystem:ITargetingSystem
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

        targetingSystem.start()
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

    private var justPressed = false
    override fun teleopPeriodic() {
        val dt = getTime()
        positionSystem.integrate(dt)

        if (components.gameController.pressedX()) {
            if (!justPressed) {
                autonomousSystem.replace(CommandList(
                        TurnToTarget(components, targetingSystem),
                        DriveToTarget(components),
                        ReleaseDisk(grabberSystem),
                        DriveForwards(components, (-3).feet)
                ))
            }

            val dt = getTime()
            autonomousSystem.drive(dt)
            justPressed = true

            return
        } else if (justPressed) { // and now is not
            autonomousSystem.replace(CommandList()) // stops current commands
            justPressed = false
        }

        // Driving
        val (x, y) = squareACircle(
                components.gameController.getLeftX(),
                components.gameController.getLeftY(),
                pressStartToggle()
        )

        components.drive.arcadeDrive(y, x)

        // Kickstand
        val kickstandPower = if (components.gameController.pressedY()) {
            0.5
        } else if (components.gameController.pressedB()) {
            -1.0
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
        val dt = getTime()
        positionSystem.integrate(dt)

    }

    override fun testInit() {

    }

    override fun testPeriodic() {
        val dt = getTime()
        positionSystem.integrate(dt)

    }


    private var lastFrameTime = System.nanoTime()

    private fun getTime(): Double {
        val currentTime = System.nanoTime()
        val dt = (currentTime.toDouble() - lastFrameTime.toDouble()) / 1000000000.0
        lastFrameTime = currentTime
        return dt
    }


}