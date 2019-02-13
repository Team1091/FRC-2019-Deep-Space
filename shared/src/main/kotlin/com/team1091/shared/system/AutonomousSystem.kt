package com.team1091.shared.system

import com.team1091.shared.autonomous.commands.Command

class AutonomousSystem {
    private var command: Command? = null

    fun init(command: Command) {
        this.command = command
        this.command?.firstRun()
    }

    fun replace(command: Command, dt: Double) {
        this.command?.cleanUp(dt)
        command.firstRun()
        this.command = command
    }

    fun drive(dt: Double) {

        if (command == null) {
            return  // Done with autonomous
        }

        val next = command!!.execute(dt)

        if (next != command) {
            command!!.cleanUp(dt)
            next?.firstRun()
        }

        command = next
    }

}
