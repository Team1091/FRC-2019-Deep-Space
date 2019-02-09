package com.team1091.shared.components

interface IDrive {
    fun arcadeDrive(linear: Double, rotation: Double)
    fun getCurrentLinear():Double
    fun getCurrentRotation():Double
}