package com.team1091.shared.system

import com.team1091.shared.components.IGameController
import com.team1091.shared.components.IMotorController
import com.team1091.shared.control.ICompressor


class KickstandSystem(val kickstandMotor: IMotorController, val controller: IGameController, val compressor: ICompressor) {

    var kickstandPower = 0.0


    fun liftAndStand() {
        kickstandMotor.set(kickstandPower)
        kickstandPower = 0.0
    }

    fun readFromController() {

        val kickstandPower = when {
            controller.getStart() -> {
                compressor.off()
                1.0
            }
            controller.getBack() -> {
                compressor.off()
                -0.8
            }
            else -> {
                compressor.on()
                0.0
            }
        }
        this.kickstandPower = kickstandPower
    }
}