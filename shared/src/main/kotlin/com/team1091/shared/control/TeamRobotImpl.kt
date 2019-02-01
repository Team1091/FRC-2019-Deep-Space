package com.team1091.shared.control

import com.team1091.shared.autonomous.commands.*
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

//    var toggle = true

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
//                        TurnToAngle(components, positionSystem, 90.degrees)
                        DriveForwards(components, 20.0.inches)
//                        Wait(components, 2.seconds),
//                        Turn(components, 90.0.degrees),
//                        Wait(components, 2.seconds),
//                        DriveForwards(components, 20.0.inches),
//                        Wait(components, 2.seconds),
//                        Turn(components, (-90.0).degrees)
                )
        )

    }

    // since we are driving in autonomous, we should just call teleopPeriodic here
    override fun autonomousPeriodic() {
        teleopPeriodic()
//        val dt = getTime()
//        autonomousSystem.drive(dt)
//        positionSystem.integrate(dt)
//
//        components.kickstandMotor.set(0.0)
//        components.driveSystem.drive();

    }

    override fun teleopInit() {

    }

    fun doTeleopPeriodicAutonomous(dt:Double){
     if (justPressed) { // and now is not
               autonomousSystem.replace(CommandList()) // stops current commands
               justPressed = false
           }
        if(!components.gameController.pressedX()){
            return
        }
        if (!justPressed) {
            println("Goodbye world")
            autonomousSystem.replace(CommandList(
                    TurnToTarget(components, targetingSystem),
                    DriveToTarget(components),
                    ReleaseDisk(grabberSystem),
                    DriveForwards(components, (-3).feet)
            ))
        }

        autonomousSystem.drive(dt)
        justPressed = true

        components.kickstandMotor.set(0.0)
        components.driveSystem.drive();
    }

    fun doTeleopPeriodicManual(dt:Double){
        if(components.gameController.pressedX()) {
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

    private var justPressed = false
    override fun teleopPeriodic() {
        val dt = getTime()
        positionSystem.integrate(dt)
        doTeleopPeriodicAutonomous(dt)
        doTeleopPeriodicManual(dt)
        components.kickstandsystem.liftAndStand();
        components.driveSystem.drive();
    }


//    fun pressStartToggle(): Boolean {
//        if (components.gameController.getStart()) {
//            toggle = !toggle
//        }
//        return toggle
//    }

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