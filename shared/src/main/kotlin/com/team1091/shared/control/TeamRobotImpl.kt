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
import com.team1091.shared.system.AutonomousSystem
import com.team1091.shared.system.GrabberSystem
import com.team1091.shared.system.ITargetingSystem
import com.team1091.shared.system.PositionSystem


// This controls our robot in both the sim and real life
class TeamRobotImpl(
        val components: RobotComponents,
        val targetingSystem: ITargetingSystem
) : TeamRobot {

    private val autonomousSystem = AutonomousSystem()
    private val grabberSystem = GrabberSystem(components.grabberSolenoid)

    lateinit var positionSystem: PositionSystem
    private var rightBumperJustPressed = false
    private var leftBumperJustPressed = false

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
            autonomousSystem.replace(CommandList()) // stops current commands
            rightBumperJustPressed = true
            leftBumperJustPressed = true
        }

        if (!components.gameController.pressedRightBumper()) {
            if (rightBumperJustPressed) { // and now is not
                println("Autonomous  let go")
                autonomousSystem.replace(CommandList()) // stops current commands
                rightBumperJustPressed = false
            }

        } else {
            if (!rightBumperJustPressed) {
                println("Starting Autonomous Assistance")
                autonomousSystem.replace(CommandList(
                        TurnToTarget(components, targetingSystem),
                        DriveToTarget(components, targetingSystem),
                        ReleaseDisk(grabberSystem),
                        DriveBackwards(components, (3).feet)
                ))
            }
            rightBumperJustPressed = true
        }

        if (!components.gameController.pressedLeftBumper()) {
            if (leftBumperJustPressed) { // and now is not
                println("Autonomous  let go")
                autonomousSystem.replace(CommandList()) // stops current commands
                leftBumperJustPressed = false
            }

        } else {
            if (!leftBumperJustPressed) {
                println("Starting Autonomous Assistance")
                autonomousSystem.replace(CommandList(
                        TurnToTarget(components, targetingSystem),
                        DriveToTarget(components, targetingSystem),
                        GrabDisk(grabberSystem),
                        DriveBackwards(components, (3).feet)
                ))
            }
            leftBumperJustPressed = true
        }

        autonomousSystem.drive(dt)

    }

    private fun doTeleopPeriodicManual(dt: Double) {
        if (components.gameController.pressedRightBumper() || components.gameController.pressedLeftBumper()) {
            return
        }
        // Driving
        val (x, y) = squareACircle(
                components.gameController.getLeftX(),
                components.gameController.getLeftY(),
                false
        )

        if (components.gameController.pressedA()) {
            components.driveSystem.arcadeDrive(y, x)
        } else {
            components.driveSystem.arcadeDrive(0.7 * y, 0.7 * x)
        }
        // Kickstand
        components.kickstandsystem.readFromController()
    }

    override fun teleopPeriodic() {
        val dt = getTime()
        positionSystem.integrate(dt)
        doTeleopPeriodicAutonomous(dt)
        doTeleopPeriodicManual(dt)
        components.kickstandsystem.liftAndStand()
        components.driveSystem.drive()
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