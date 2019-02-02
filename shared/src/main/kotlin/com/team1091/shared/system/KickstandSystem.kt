package com.team1091.shared.system

import com.team1091.shared.components.IGameController
import com.team1091.shared.components.IMotorController


class KickstandSystem(val drive: IMotorController, val controller: IGameController) {

    var kickstandPower = 0.0;


    fun liftAndStand() {
        drive.set(kickstandPower);
        kickstandPower = 0.0;
    }

    fun readFromController() {
        val kickstandPower = if (controller.pressedY()) {
            0.5
        } else if (controller.pressedB()) {
            -1.0
        } else {
            0.0
        }
        this.kickstandPower = kickstandPower;
    }
}