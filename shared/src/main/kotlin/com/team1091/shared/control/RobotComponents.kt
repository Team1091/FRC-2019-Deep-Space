package com.team1091.shared.control

import com.team1091.shared.components.IAccelerometer
import com.team1091.shared.components.IDrive
import com.team1091.shared.components.IEncoder
import com.team1091.shared.components.IGameController
import com.team1091.shared.components.IGyroscope
import com.team1091.shared.components.IMotorController
import com.team1091.shared.components.ISolenoid
import com.team1091.shared.system.AutonomousSystem
import com.team1091.shared.system.DriveSystem
import com.team1091.shared.system.GrabberSystem
import com.team1091.shared.system.ITargetingSystem
import com.team1091.shared.system.KickstandSystem
import com.team1091.shared.system.PositionSystem

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
    val grabberSystem = GrabberSystem(grabberSolenoid0, grabberSolenoid1, gameController)
    lateinit var positionSystem: PositionSystem

}