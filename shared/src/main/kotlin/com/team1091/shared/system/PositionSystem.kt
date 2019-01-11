package com.team1091.shared.system

import com.team1091.shared.components.IAccelerometer
import com.team1091.shared.components.IGyroscope
import com.team1091.shared.math.Rotation

class PositionSystem(
        val accelerometer: IAccelerometer,
        val gyroscope: IGyroscope,

        private var xP: Double,
        private var yP: Double,

        private var xV: Double,
        private var yV: Double,

        private val initialAngle: Rotation,
        private var angle: Rotation = initialAngle.copy()
) {

    fun integrate(dt: Double) {

        // gyroscope already handles integration
        angle = initialAngle + gyroscope.get()

        val aX = accelerometer.getX()
        val aY = accelerometer.getY()

        // rotate instantaneous accelerations
        val aXr = (angle.cos() * aX) - (angle.sin() * aY)
        val aYr = (angle.sin() * aX) + (angle.cos() * aY)

        // integrate velocity
        xV += (aXr * dt)
        yV += (aYr * dt)

        // integrate position
        xP += (xV * dt)
        yP += (yV * dt)

    }

    fun getPos(): Position = Position(xP, yP, angle)

}

class Position(val x: Double, val y: Double, val r: Rotation)