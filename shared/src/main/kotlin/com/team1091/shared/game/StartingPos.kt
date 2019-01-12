package com.team1091.shared.game

import com.team1091.shared.math.Rotation
import com.team1091.shared.math.Vec2d
import com.team1091.shared.math.radians

enum class StartingPos(
        val alliance: Alliance,
        val pos: Vec2d,
        val rotation: Rotation
) {

    RED_1(Alliance.RED, Vec2d(15.0, 75.0), 0.radians),
    RED_2(Alliance.RED, Vec2d(15.0, 175.0), 0.radians),
    RED_3(Alliance.RED, Vec2d(15.0, 275.0), 0.radians),

    BLUE_1(Alliance.BLUE, Vec2d(635.0, 75.0), Math.PI.radians),
    BLUE_2(Alliance.BLUE, Vec2d(635.0, 175.0), Math.PI.radians),
    BLUE_3(Alliance.BLUE, Vec2d(635.0, 275.0), Math.PI.radians)

}

