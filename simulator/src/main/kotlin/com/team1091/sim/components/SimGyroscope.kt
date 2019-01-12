package com.team1091.sim.components

import com.team1091.shared.components.IGyroscope
import com.team1091.shared.math.Rotation
import com.team1091.shared.math.radians

class SimGyroscope : IGyroscope {

    private var angle = 0.radians

    override fun get(): Rotation {
        return angle // TODO: this needs to actually rotate
    }

    fun set(r: Rotation) {
        angle = r
    }

    fun add(radians: Rotation) {
        angle += radians
    }

}