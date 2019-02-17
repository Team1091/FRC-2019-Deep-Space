package com.team1091.shared.system

import com.team1091.shared.components.IGameController
import com.team1091.shared.components.IMotorController


class KickstandSystem(val kickstandMotor: IMotorController, val controller: IGameController) {

    var kickstandPower = 0.0


    fun liftAndStand() {
        kickstandMotor.set(kickstandPower)
        kickstandPower = 0.0
    }

    fun readFromController() {
        val kickstandPower = when {
            controller.getStart() -> 0.5
            controller.getBack() -> -0.5
            else -> 0.0
        }
        this.kickstandPower = kickstandPower
    }
}