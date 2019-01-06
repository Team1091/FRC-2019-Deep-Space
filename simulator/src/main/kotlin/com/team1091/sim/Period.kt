package com.team1091.sim

enum class Period(val seconds: Int) {
    PREMATCH(0), // 10 seconds
    AUTONOMOUS(15), // 15 seconds
    TELEOP(135), //
    DISABLED(0)
}