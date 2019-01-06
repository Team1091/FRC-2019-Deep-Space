package com.team1091.sim.components

import com.team1091.shared.components.IDrive
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import kotlin.math.cos
import kotlin.math.sin

class SimDrive(val linearForce: Double, val rotationForce: Double) : IDrive {

    var linearAccel: Double = 0.0
    var rotationalAccel: Double = 0.0

    override fun arcadeDrive(linear: Double, rotation: Double) {
        this.linearAccel = linearForce * linear
        this.rotationalAccel = rotationForce * rotation
    }

    fun applyForce(body: Body) {

        // TODO: limit velocity in any direction.  More friction in ways against the wheel
        // Todo: we need to have sliding friction.  Rolling forwards is easier than sliding

        //println("${linearAccel} ${rotationalAccel}")
        body.applyTorque(rotationalAccel.toFloat())

        body.applyForceToCenter(Vec2(
                cos(body.angle) * linearAccel.toFloat(),
                sin(body.angle) * linearAccel.toFloat()
        ))
    }

}