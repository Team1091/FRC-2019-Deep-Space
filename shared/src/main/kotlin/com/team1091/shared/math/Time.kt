package com.team1091.shared.math

val Number.seconds: Time
    get() = Time((this.toDouble() * 1000).toLong())

val Number.minutes: Time
    get() = Time((this.toDouble() * 60_000).toLong())

data class Time(val ms: Long) {

    companion object {
        fun now() = Time(System.currentTimeMillis())
    }

    fun toSeconds() = ms / 1000.0
    fun toMinutes() = ms / (60 * 1000.0)
    fun toHours() = ms / (60 * 60 * 1000.0)

    operator fun plus(other: Time) = Time(this.ms + other.ms)
    operator fun minus(other: Time) = Time(this.ms - other.ms)
}