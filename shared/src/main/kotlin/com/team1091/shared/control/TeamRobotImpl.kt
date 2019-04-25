package com.team1091.shared.control

import com.team1091.shared.autonomous.commands.*
import com.team1091.shared.game.StartingPos

// This controls our robot in both the sim and real life
class TeamRobotImpl(
        val components: RobotComponents
) : TeamRobot {

    private var yJustPressed = false
    private var xJustPressed = false

    override fun robotInit(startingPos: StartingPos) {
        components.targetingSystem.start()
        components.grabberSystem.withdraw();
        components.grabberSystem.open();
    }

    override fun autonomousInit() {
        components.autonomousSystem.init(CommandList())
    }

    // since we are driving in autonomous, we should just call teleopPeriodic here
    override fun autonomousPeriodic() {
        teleopPeriodic()
    }

    override fun teleopInit() {

    }

    private fun doAutonomousScore(dt: Double) {
        if (!components.gameController.pressedX()) {
            if (yJustPressed) { // and now is not
                //println("Let Go Right Bumper")
                components.autonomousSystem.replace(CommandList(), dt) // stops current commands
                yJustPressed = false
            }
            return
        }
        if (!yJustPressed) {
            //println("Pressed Right Bumper")
            components.autonomousSystem.replace(CommandList(
                    TurnToTarget(components),
                    DriveToTarget(components),
                    ReleaseDisk(components.grabberSystem),
                    DriveBackwards(components),
                    Stop(components)
            ), dt)
        }
//        println("Started Score")
        yJustPressed = true
    }

    private fun doAutonomousDiskPickup(dt: Double) {
        if (!components.gameController.pressedY()) {
            if (xJustPressed) { // and now is not
                //println("Left Bumper Let go")
                components.autonomousSystem.replace(CommandList(), dt) // stops current commands
                xJustPressed = false
            }
            return
        }
        if (!xJustPressed) {
            //println("Left bumper pressed")
            components.autonomousSystem.replace(CommandList(
                    TurnToTarget(components),
                    DriveToTarget(components),
                    GrabDisk(components.grabberSystem),
                    DriveBackwards(components),
                    Stop(components)
            ), dt)
        }
//        println("Started Pickup")
        xJustPressed = true
    }

    private fun doTeleopPeriodicAutonomous(dt: Double) {
        if (components.gameController.pressedY() && components.gameController.pressedX()) {
            components.autonomousSystem.replace(CommandList(), dt) // stops current commands
            yJustPressed = true
            xJustPressed = true
            return
        }
        doAutonomousDiskPickup(dt)
        doAutonomousScore(dt)
        components.autonomousSystem.drive(dt)
    }

    private fun doTeleopPeriodicManual(dt: Double) {
        with(components) {
            if (gameController.pressedX() || gameController.pressedY()) {
                return
            }

            // this.grabberSystem.

            // Driving
            val x = gameController.getLeftX()
            val y = gameController.getLeftY()

            if (gameController.pressedA() || gameController.pressedB()) {
                driveSystem.arcadeDrive(y, x)
            } else {
                driveSystem.arcadeDrive(0.7 * y, 0.7 * x)
            }
            kickstandsystem.readFromController()

            // Grabber
            grabberSystem.readFromController()

        }
    }

    var lastRunWork = 0.0;
    override fun teleopPeriodic() {
        val dt = getTime()
        doTeleopPeriodicAutonomous(dt)
        doTeleopPeriodicManual(dt)
        components.kickstandsystem.liftAndStand()
        components.grabberSystem.doWork(dt)
        components.driveSystem.drive(dt)
    }

    override fun disabledInit() {

    }

    override fun disabledPeriodic() {
//        val dt = getTime()
//        components.positionSystem.integrate(dt)

    }

    override fun testInit() {

    }

    override fun testPeriodic() {
//        val dt = getTime()
//        components.positionSystem.integrate(dt)
    }


    private var lastFrameTime = System.nanoTime()

    private fun getTime(): Double {
        val currentTime = System.nanoTime()
        val dt = (currentTime.toDouble() - lastFrameTime.toDouble()) / 1000000000.0
        lastFrameTime = currentTime
        return dt
    }

}