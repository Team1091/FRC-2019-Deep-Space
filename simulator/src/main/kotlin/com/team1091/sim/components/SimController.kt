package com.team1091.sim.components

import com.studiohartman.jamepad.ControllerManager
import com.team1091.shared.components.IGameController

class SimController(
        private val controllers: ControllerManager,
        private val id: Int
) : IGameController {

    private fun state() = controllers.getState(id)

    override fun getLeftX(): Double = state().leftStickX.toDouble()
    override fun getLeftY(): Double = state().leftStickY.toDouble()

    override fun getRightX(): Double = state().rightStickX.toDouble()
    override fun getRightY(): Double = state().rightStickY.toDouble()

    override fun pressedA(): Boolean = state().a
    override fun pressedB(): Boolean = state().b
    override fun pressedX(): Boolean = state().x
    override fun pressedY(): Boolean = state().y

    override fun getLeftTrigger(): Double = state().leftTrigger.toDouble()
    override fun getRightTrigger(): Double = state().rightTrigger.toDouble()

    override fun pressedLeftBumper(): Boolean = state().lb
    override fun pressedRightBumper(): Boolean = state().rb

    override fun getLeftStick(): Boolean = state().leftStickClick
    override fun getRightStick(): Boolean = state().rightStickClick

    override fun getStart(): Boolean = state().start
    override fun getBack(): Boolean = state().back

//  Works in sim, but not on robot right now
//    override fun getDPadUp(): Boolean = state().dpadUp
//    override fun getDPadDown(): Boolean = state().dpadDown
//    override fun getDPadLeft(): Boolean = state().dpadLeft
//    override fun getDPadRight(): Boolean = state().dpadRight
}