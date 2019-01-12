package com.team1091.sim.components

import com.team1091.shared.components.IEncoder

class SimEncoder(val rotDist: Double) : IEncoder {

    var rotation: Double = 0.0

    override fun reset() {
        rotation = 0.0
    }

    override fun get(): Double {
        return rotation
    }

    override fun getDistance(): Double {
        return rotation // times how many clicks per unit of distance?
    }
}
