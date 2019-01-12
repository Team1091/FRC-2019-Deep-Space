package com.team1091.sim.components

import com.team1091.shared.components.IAccelerometer


class SimAccelerometer(
        private var x: Double = 0.0,
        private var y: Double = 0.0,
        private var z: Double = -1.0
) : IAccelerometer {

    override fun getX(): Double = x
    override fun getY(): Double = y
    override fun getZ(): Double = z

    fun set(x: Float, y: Float, z: Float) {
        this.x = x.toDouble()
        this.y = y.toDouble()
        this.z = z.toDouble()
    }

}