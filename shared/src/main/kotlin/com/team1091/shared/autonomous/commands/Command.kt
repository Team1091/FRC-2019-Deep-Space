package com.team1091.shared.autonomous.commands

interface Command {

    fun firstRun()
    fun execute(dt: Double): Command?
    fun cleanUp()

    fun getMessage(): String {
        return this.javaClass.toString()
    }

    // firstRun() //gets called before first run
    // cleanup() // gets called after last
    // getElapsedTime()
}