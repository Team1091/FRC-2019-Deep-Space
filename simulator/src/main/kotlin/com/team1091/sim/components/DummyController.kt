package com.team1091.sim.components

import com.team1091.shared.components.IGameController

/**
 * A controller that does nothing
 */
class DummyController : IGameController {


    override fun getLeftX(): Double = 0.0
    override fun getLeftY(): Double = 0.0

    override fun getRightX(): Double = 0.0
    override fun getRightY(): Double = 0.0

    override fun pressedA(): Boolean = false
    override fun pressedB(): Boolean = false
    override fun pressedX(): Boolean = false
    override fun pressedY(): Boolean = false

    override fun getLeftTrigger(): Double = 0.0
    override fun getRightTrigger(): Double = 0.0

    override fun pressedLeftBumper(): Boolean = false
    override fun pressedRightBumper(): Boolean = false

    override fun getLeftStick(): Boolean = false
    override fun getRightStick(): Boolean = false

    override fun getStart(): Boolean = false
    override fun getBack(): Boolean = false

//  Works in sim, but not on robot right now, so until we figure that out we can just comment it out
//    override fun getDPadUp(): Boolean = state().dpadUp
//    override fun getDPadDown(): Boolean = state().dpadDown
//    override fun getDPadLeft(): Boolean = state().dpadLeft
//    override fun getDPadRight(): Boolean = state().dpadRight
}