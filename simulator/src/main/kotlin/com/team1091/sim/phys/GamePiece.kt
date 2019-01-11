package com.team1091.sim.phys

import org.jbox2d.dynamics.Body


class GamePiece(
        // Starting pos
        var x: Float,
        var y: Float, // position
        var r: Float,  // rotation, angular displacement

        val xSize: Float,
        val ySize: Float
) {
    lateinit var body: Body
}