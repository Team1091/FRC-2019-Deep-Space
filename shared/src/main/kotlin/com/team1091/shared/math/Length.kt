package com.team1091.shared.math

// Measurements
val Number.americanFootballFields: Length
    get() = Length(this.toDouble() * (109.1))

val Number.yards: Length
    get() = Length(this.toDouble() * (0.9144))

val Number.feet: Length
    get() = Length(this.toDouble() * 0.3048)

val Number.inches: Length
    get() = Length(this.toDouble() * 0.0254)

val Number.centimeters: Length
    get() = Length(this.toDouble() * 0.01)

val Number.meters: Length
    get() = Length(this.toDouble())

//val Number.robots: Length
//    get() = Length(this.toDouble() * 38.0)

class Length(val distance: Double) {
    fun toAmericanFootballFields() = distance / (109.1)
    fun toYards() = distance / (0.9144)
    fun toFeet() = distance / (0.3048)
    fun toInches() = distance / 0.0254
    fun toCentimeters() = distance / 0.01
    fun toMeters() = distance

    operator fun plus(other: Length) = Length(this.distance + other.distance)
    operator fun minus(other: Length) = Length(this.distance - other.distance)
}

fun min(one: Length, two: Length): Length = if (one.distance <= two.distance) one else two

fun max(one: Length, two: Length): Length = if (one.distance >= two.distance) one else two
