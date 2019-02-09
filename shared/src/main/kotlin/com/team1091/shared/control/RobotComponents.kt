package com.team1091.shared.control

import com.team1091.shared.components.*
import com.team1091.shared.system.*

// Put all the robot's components in here, and we can pass it around.
class RobotComponents(
        val gameController: IGameController,
        drive: IDrive,
        val leftEncoder: IEncoder,
        val rightEncoder: IEncoder,
        val accelerometer: IAccelerometer,
        val gyroscope: IGyroscope,
        kickstandMotor: IMotorController,
        grabberSolenoid0: ISolenoid,
        grabberSolenoid1: ISolenoid,
        val targetingSystem: ITargetingSystem
) {
    val driveSystem = DriveSystem(drive)
    val kickstandsystem = KickstandSystem(kickstandMotor, gameController)
    val autonomousSystem = AutonomousSystem()
    val grabberSystem = GrabberSystem(grabberSolenoid0, grabberSolenoid1)
    lateinit var positionSystem: PositionSystem

}