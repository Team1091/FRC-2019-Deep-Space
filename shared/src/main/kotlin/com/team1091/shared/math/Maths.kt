package com.team1091.shared.math

fun moveToward(cur: Double, goal: Double, speed: Double): Double {

    if (cur < goal) {
        return Math.min(cur + speed, goal)
    }
    if (cur > goal) {
        return Math.max(cur - speed, goal)
    }
    return cur
}

fun clamp(value: Double): Double {
    return Math.max(-1.0, Math.min(1.0, value))
}

fun clamp(value: Double, min: Double, max: Double): Double {
    return Math.max(min, Math.min(max, value))
}