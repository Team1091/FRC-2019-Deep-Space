package com.team1091.shared.components

interface IGameController {

    fun getLeftX(): Double
    fun getLeftY(): Double

    fun getRightX(): Double
    fun getRightY(): Double

    fun pressedA(): Boolean
    fun pressedB(): Boolean
    fun pressedX(): Boolean
    fun pressedY(): Boolean

    fun getLeftTrigger(): Double
    fun getRightTrigger(): Double

    fun pressedLeftBumper(): Boolean
    fun pressedRightBumper(): Boolean

    fun getLeftStick(): Boolean
    fun getRightStick(): Boolean

    fun getStart(): Boolean
    fun getBack(): Boolean

    // Currently not implemented on the frc stuff, may be able to work around it
//    fun getDPadUp():Boolean
//    fun getDPadDown():Boolean
//    fun getDPadLeft():Boolean
//    fun getDPadRight():Boolean

}