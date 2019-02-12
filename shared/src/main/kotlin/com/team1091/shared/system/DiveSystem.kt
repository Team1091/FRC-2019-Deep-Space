package com.team1091.shared.system

import com.team1091.shared.components.IDrive
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

val maxAccel = 6.0

class DriveSystem(val drive: IDrive) {
    var targForwardAmnt = 0.0
    var targTurnAmnt = 0.0
//    var lastIncrement: Long = 0

    private var currentPower = 0.0


    fun drive(dt: Double) {
        if (targForwardAmnt > currentPower) {
            currentPower = min((currentPower + maxAccel * dt), targForwardAmnt)
        } else if (targForwardAmnt < currentPower) {
            currentPower = max((currentPower - maxAccel * dt), targForwardAmnt)
        }


        val decreaseInTurn = 1 - abs(currentPower) / 2

        drive.arcadeDrive(currentPower, targTurnAmnt * decreaseInTurn)
        targForwardAmnt = 0.0
        targTurnAmnt = 0.0
    }

    fun arcadeDrive(forward: Double, turn: Double) {
        targForwardAmnt = forward
        targTurnAmnt = turn
    }

//    fun getSpeedToSet(desiredSpeed: Double, currentSpeed: Double, accel: Double, dt: Double): Double {
//
//        if (desiredSpeed == currentSpeed) {
//            return desiredSpeed
//        }
//        if (desiredSpeed > currentSpeed) { // we are accelerating towards our target
//            // if we are going towards 0, just get there
//            return if (currentSpeed < 0) desiredSpeed else Math.min(currentSpeed + Math.min(accel * dt, accel), desiredSpeed)
//
//        }
//        // we are going slower
//        // if we are going towards 0, just get to dest
//        return if (currentSpeed > 0) desiredSpeed else Math.max(currentSpeed - Math.min(accel * dt, accel), desiredSpeed)
//
//    }
//
//    fun arcadeDrive(targForwardAmnt: Double, targTurnAmnt: Double, dt: Double) {
//        var currentForward = drive.getCurrentLinear()
//        var currentTurn = drive.getCurrentRotation()
//        this.targForwardAmnt = getSpeedToSet(targForwardAmnt, currentForward, RobotSettings.driveAccelStep, dt)
//        this.targTurnAmnt = getSpeedToSet(targTurnAmnt, currentTurn, RobotSettings.driveAccelStep, dt)
//        if (this.targForwardAmnt != 0.0) {
//            if (this.targForwardAmnt < .3 && this.targForwardAmnt > 0) {
//                this.targForwardAmnt = .3;
//            }
//            if (this.targForwardAmnt > -.3 && this.targForwardAmnt < 0) {
//                this.targForwardAmnt = -.3;
//            }
//        }
//        if (this.targTurnAmnt != 0.0) {
//            if (this.targTurnAmnt < .3 && this.targTurnAmnt > 0) {
//                this.targTurnAmnt = .3;
//            }
//            if (this.targTurnAmnt > -.3 && this.targTurnAmnt < 0) {
//                this.targTurnAmnt = -.3;
//            }
//        }
//    }
}