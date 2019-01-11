package com.team1091.shared.control

import com.team1091.shared.game.StartingPos

interface TeamRobot {

    // Called once when the robot is first started
    fun robotInit(startingPos: StartingPos)


    // Called once right before autonomous starts
    fun autonomousInit()

    // Continuously called during periodic
    fun autonomousPeriodic()


    // Called once when periodic starts
    fun teleopInit()

    // Continuously called during teleop
    fun teleopPeriodic()


    // Called once when disabled.
    fun disabledInit()

    // Continuously called when disabled
    fun disabledPeriodic()


    // Called once in test
    fun testInit()

    // Continuously called when in test
    fun testPeriodic()
}