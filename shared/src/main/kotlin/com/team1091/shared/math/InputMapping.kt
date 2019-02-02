package com.team1091.shared.math

import kotlin.math.abs

fun squareACircle(u: Double, v: Double, pressed: Boolean = true): Pair<Double, Double> {
    var x = 0.0
    var y = 0.0
    val u2v2 = Math.pow(u, 2.0) + Math.pow(v, 2.0)
    if (pressed) {
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
    } else {
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