package com.team1091.shared.math

import kotlin.math.cos
import kotlin.math.sin

val Number.radians: Rotation
    get() = Rotation(this.toDouble())

val Number.degrees: Rotation
    get() = Rotation(Math.toRadians(this.toDouble()))

data class Rotation(private val radians: Double) {
    fun cos() = cos(radians)
    fun sin() = sin(radians)

    fun toDegrees() = radians * (180 / Math.PI)
    fun toRadians() = radians

    operator fun plus(other: Rotation) = Rotation(this.radians + other.radians)
    operator fun minus(other: Rotation) = Rotation(this.radians - other.radians)
}