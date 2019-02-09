package com.team1091.shared.components

interface ISolenoid {
    fun set(value: Boolean?)
    fun get(): Boolean?
}