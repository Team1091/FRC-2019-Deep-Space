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
open class RobotComponents(
        val gameController: IGameController,
        drive: IDrive,
        val leftEncoder: IEncoder,
        val rightEncoder: IEncoder,
        val accelerometer: IAccelerometer,
        val gyroscope: IGyroscope,
        kickstandMotor: IMotorController,
        grabberSolenoid: ISolenoid,
        extenderSolenoid: ISolenoid,
        val targetingSystem: ITargetingSystem
) {
    open val driveSystem = DriveSystem(drive)
    val kickstandsystem = KickstandSystem(kickstandMotor, gameController)
    val autonomousSystem = AutonomousSystem()
    val grabberSystem = GrabberSystem(grabberSolenoid, extenderSolenoid, gameController)
    lateinit var positionSystem: PositionSystem

}