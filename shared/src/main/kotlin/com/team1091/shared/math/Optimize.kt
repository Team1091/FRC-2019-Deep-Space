package com.team1091.shared.math

// Experiment getting PID weights using twiddle
// https://martin-thoma.com/twiddle/
fun optimize(error: (Array<Double>) -> Double): Array<Double> {

    // Choose an initialization parameter vector
    val p = arrayOf(0.0, 0.0, 0.0)

    // Define potential changes
    val dp = arrayOf(1.0, 1.0, 1.0)

    // Calculate the error
    var bestErr = error(p)

    val threshold = 0.001

    while (dp.sum() > threshold) {
        for (i in 0 until p.size) {
            p[i] += dp[i]
            var err = error(p)

            if (err < bestErr) {  // There was some improvement
                bestErr = err
                dp[i] *= 1.1
            } else {  // There was no improvement
                p[i] -= 2 * dp[i]  // Go into the other direction
                err = error(p)

                if (err < bestErr) {  // There was an improvement
                    bestErr = err
                    dp[i] *= 1.05
                } else {  // There was no improvement
                    p[i] += dp[i]
                    // As there was no improvement, the step size in either
                    // direction, the step size might simply be too big.
                    dp[i] *= 0.95
                }
            }
        }
    }

    return p

}

