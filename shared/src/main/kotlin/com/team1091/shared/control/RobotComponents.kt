package com.team1091.shared.control

import com.team1091.shared.components.IAccelerometer
import com.team1091.shared.components.IDrive
import com.team1091.shared.components.IEncoder
import com.team1091.shared.components.IGameController
import com.team1091.shared.components.IGyroscope

// Put all the robot's components in here, and we can pass it around.  May just want to pass around the TeamRobotImpl
class RobotComponents(
        val gameController: IGameController,
        val drive: IDrive,
        val leftEncoder: IEncoder,
        val rightEncoder: IEncoder,
        val accelerometer: IAccelerometer,
        val gyroscope: IGyroscope
)