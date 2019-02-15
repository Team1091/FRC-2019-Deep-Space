package com.team1091.shared.components

interface IAccelerometer {
    fun getX(): Double
    fun getY(): Double
    fun getZ(): Double
    fun getAngle(): Double
    fun writeOutData()
}