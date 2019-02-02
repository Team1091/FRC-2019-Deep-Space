package com.team1091.shared.control

import com.team1091.shared.autonomous.commands.CommandList
import com.team1091.shared.autonomous.commands.DriveBackwards
import com.team1091.shared.autonomous.commands.DriveForwards
import com.team1091.shared.autonomous.commands.DriveToTarget
import com.team1091.shared.autonomous.commands.GrabDisk
import com.team1091.shared.autonomous.commands.ReleaseDisk
import com.team1091.shared.autonomous.commands.TurnToTarget
import com.team1091.shared.game.StartingPos
import com.team1091.shared.math.feet
import com.team1091.shared.math.inches
import com.team1091.shared.math.squareACircle
import com.team1091.shared.system.PositionSystem


// This controls our robot in both the sim and real life
class TeamRobotImpl(
        val components: RobotComponents
) : TeamRobot {


    private var rightBumperJustPressed = false
    private var leftBumperJustPressed = false

    override fun robotInit(startingPos: StartingPos) {
        components.positionSystem = PositionSystem(
                components.accelerometer,
                components.gyroscope,
                startingPos.pos.x,
                startingPos.pos.y,
                0.0,
                0.0,
                startingPos.rotation
        )

        components.targetingSystem.start()
    }

    override fun autonomousInit() {
        components.autonomousSystem.init(
                CommandList(
                        DriveForwards(components, 20.0.inches)
                )
        )

    }

    // since we are driving in autonomous, we should just call teleopPeriodic here
    override fun autonomousPeriodic() {
        teleopPeriodic()
    }

    override fun teleopInit() {

    }

    private fun doTeleopPeriodicAutonomous(dt: Double) {
        if (components.gameController.pressedRightBumper() && components.gameController.pressedLeftBumper()) {
            println("You are pressing both buttons, clearing commands")
            components.autonomousSystem.replace(CommandList()) // stops current commands
            rightBumperJustPressed = true
            leftBumperJustPressed = true
        }

        if (!components.gameController.pressedRightBumper()) {
            if (rightBumperJustPressed) { // and now is not
                println("Autonomous  let go")
                components.autonomousSystem.replace(CommandList()) // stops current commands
                rightBumperJustPressed = false
            }

        } else {
            if (!rightBumperJustPressed) {
                println("Starting Autonomous Assistance")
                components.autonomousSystem.replace(CommandList(
                        TurnToTarget(components),
                        DriveToTarget(components),
                        ReleaseDisk(components.grabberSystem),
                        DriveBackwards(components, (3).feet)
                ))
            }
            rightBumperJustPressed = true
        }

        if (!components.gameController.pressedLeftBumper()) {
            if (leftBumperJustPressed) { // and now is not
                println("Autonomous  let go")
                components.autonomousSystem.replace(CommandList()) // stops current commands
                leftBumperJustPressed = false
            }

        } else {
            if (!leftBumperJustPressed) {
                println("Starting Autonomous Assistance")
                components.autonomousSystem.replace(CommandList(
                        TurnToTarget(components),
                        DriveToTarget(components),
                        GrabDisk(components.grabberSystem),
                        DriveBackwards(components, (3).feet)
                ))
            }
            leftBumperJustPressed = true
        }

        components.autonomousSystem.drive(dt)

    }

    private fun doTeleopPeriodicManual(dt: Double) {
        with(components) {
            if (gameController.pressedRightBumper() || gameController.pressedLeftBumper()) {
                return
            }
            // Driving
            val (x, y) = squareACircle(
                    gameController.getLeftX(),
                    gameController.getLeftY(),
                    false
            )

            if (gameController.pressedA()) {
                driveSystem.arcadeDrive(y, x)
            } else {
                driveSystem.arcadeDrive(0.7 * y, 0.7 * x)
            }
            // Kickstand
            kickstandsystem.readFromController()
        }
    }

    override fun teleopPeriodic() {
        val dt = getTime()
        components.positionSystem.integrate(dt)
        doTeleopPeriodicAutonomous(dt)
        doTeleopPeriodicManual(dt)
        components.kickstandsystem.liftAndStand()
        components.driveSystem.drive()
    }

    override fun disabledInit() {

    }

    override fun disabledPeriodic() {
        val dt = getTime()
        components.positionSystem.integrate(dt)

    }

    override fun testInit() {

    }

    override fun testPeriodic() {
        val dt = getTime()
        components.positionSystem.integrate(dt)

    }


    private var lastFrameTime = System.nanoTime()

    private fun getTime(): Double {
        val currentTime = System.nanoTime()
        val dt = (currentTime.toDouble() - lastFrameTime.toDouble()) / 1000000000.0
        lastFrameTime = currentTime
        return dt
    }

}