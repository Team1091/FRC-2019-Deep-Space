package com.team1091.shared.system

import com.team1091.shared.components.IDrive
import com.team1091.shared.components.IGameController
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

const val maxAccel = 8.0
const val doubleMaxAccel = 100.0
class DriveSystem(val drive: IDrive, val gameController: IGameController) {
    private var targForwardAmnt = 0.0
    private var targTurnAmnt = 0.0
    private var currentPower = 0.0


    fun drive(dt: Double) {
        val accel = if(gameController.pressedB()) doubleMaxAccel else maxAccel

        if (targForwardAmnt > currentPower) {
            currentPower = min((currentPower + accel * dt), targForwardAmnt)
        } else if (targForwardAmnt < currentPower) {
            currentPower = max((currentPower - accel * dt), targForwardAmnt)
        }


        val decreaseInTurn = 1 - (abs(currentPower) / 2)

        drive.arcadeDrive(currentPower, targTurnAmnt * decreaseInTurn)
        targForwardAmnt = 0.0
        targTurnAmnt = 0.0
    }

    fun arcadeDrive(forward: Double, turn: Double) {
        targForwardAmnt = forward
        targTurnAmnt = turn
    }

}