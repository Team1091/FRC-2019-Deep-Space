package com.team1091.sim.phys

import org.jbox2d.dynamics.Body

class Obstacle(
        val x: Float,
        val y: Float,

        val xSize: Float,
        val ySize: Float
) {
    lateinit var body: Body
}