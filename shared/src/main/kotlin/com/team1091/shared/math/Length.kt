package com.team1091.shared.math

// Measurements
val Number.americanFootballFields: Length
    get() = Length(this.toDouble() * (120.0 * 12.0 * 3.0))

val Number.yards: Length
    get() = Length(this.toDouble() * (12.0 * 3))

val Number.feet: Length
    get() = Length(this.toDouble() * 12.0)

val Number.inches: Length
    get() = Length(this.toDouble())

//val Number.robots: Length
//    get() = Length(this.toDouble() * 38.0)

class Length(val distance: Double) {
    fun toAmericanFootballFields() = distance / (120.0 * 12.0 * 3.0)
    fun toRobots() = distance / 38.0
    fun toYards() = distance / (12.0 * 3.0)
    fun toFeet() = distance / (12.0)
    fun toInches() = distance

    operator fun plus(other: Length) = Length(this.distance + other.distance)
    operator fun minus(other: Length) = Length(this.distance - other.distance)
}

fun min(one: Length, two: Length): Length = if (one.distance <= two.distance) one else two

fun max(one: Length, two: Length): Length = if (one.distance >= two.distance) one else two
