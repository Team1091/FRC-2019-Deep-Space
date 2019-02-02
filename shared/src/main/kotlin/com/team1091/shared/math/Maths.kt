package com.team1091.shared.math

import kotlin.math.max
import kotlin.math.min

fun moveToward(cur: Double, goal: Double, speed: Double): Double {

    if (cur < goal) {
        return min(cur + speed, goal)
    }
    if (cur > goal) {
        return max(cur - speed, goal)
    }
    return cur
}

fun clamp(value: Double): Double {
    return max(-1.0, min(1.0, value))
}

fun clamp(value: Double, min: Double, max: Double): Double {
    return max(min, min(max, value))
}