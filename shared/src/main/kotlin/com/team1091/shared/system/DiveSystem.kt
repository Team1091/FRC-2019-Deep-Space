package com.team1091.shared.system

import com.team1091.shared.components.IDrive


class DriveSystem(val drive: IDrive) {
    var forwardAmnt = 0.0
    var turnAmnt = 0.0


    fun drive() {
        drive.arcadeDrive(forwardAmnt, turnAmnt)
        forwardAmnt = 0.0
        turnAmnt = 0.0
    }

    fun arcadeDrive(forwardAmnt: Double, turnAmnt: Double) {
        this.forwardAmnt = forwardAmnt
        this.turnAmnt = turnAmnt
    }
}