package com.team1091.shared.control

import com.team1091.shared.components.*

// Put all the robot's components in here, and we can pass it around.  May just want to pass around the TeamRobotImpl
class RobotComponents(
        val gameController: IGameController,
        val drive: IDrive,
        val leftEncoder: IEncoder,
        val rightEncoder: IEncoder,
        val accelerometer: IAccelerometer,
        val gyroscope: IGyroscope,
        val kickstandMotor: IMotorController
)