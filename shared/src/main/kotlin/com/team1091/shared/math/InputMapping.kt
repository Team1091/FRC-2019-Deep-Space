package com.team1091.shared.math

import kotlin.math.abs

fun squareACircle(u: Double, v: Double): Pair<Double, Double> {
    var x = 0.0
    var y = 0.0
    var u2v2 = Math.pow(u, 2.0) + Math.pow(v, 2.0)
    val stretchType = 1 //Stretch type 1 is currently functional, the others may not work.
    if (stretchType == 1) {
        var unchangedEquation = Math.sqrt(u2v2)
        if (Math.pow(u, 2.0) >= Math.pow(v, 2.0)) {
            x = Math.signum(u) * unchangedEquation
        } else {
            x = Math.signum(v) * (u / v) * unchangedEquation
        }
        if (Math.pow(u, 2.0) >= Math.pow(v, 2.0)) {
            y = Math.signum(u) * (v / u) * unchangedEquation
        } else {
            y = Math.signum(v) * unchangedEquation
        }
        /*
    } else if (stretchType == 2) {
        var unchangedEquation = Math.sqrt(
                u2v2 - Math.sqrt(
                        u2v2 * (u2v2 - (4 * Math.pow(u, 2.0) * Math.pow(v, 2.0)))
                )
        )
        x = Math.signum(u * v) / (v * Math.sqrt(2.0)) * unchangedEquation
        y = Math.signum(u * v) / (u * Math.sqrt(2.0)) * unchangedEquation
    } else if (stretchType == 3) {
        x = .5 * Math.sqrt(
                2 + Math.pow(u, 2.0) - Math.pow(v, 2.0) + 2 * Math.sqrt(2.0) * u
        )
        */
    } else if (stretchType == 4) {
        x = u
        y = v
    }
    if (abs(u) <= 0.0001) {
        x = 0.0
    }
    if (abs(v) <= 0.0001) {
        y = 0.0
    }
  //  println("$x, $y")
    // https://arxiv.org/pdf/1509.06344.pdf
    return Pair(x, y)
}