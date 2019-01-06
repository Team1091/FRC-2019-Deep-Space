package com.team1091.shared.system

import com.team1091.shared.autonomous.commands.Command

class AutonomousSystem {
    private var command: Command? = null

    fun init(command: Command) {
        this.command = command
        this.command?.firstRun()
    }

    fun drive(dt: Double) {

        if (command == null) {
            return  // Done with autonomous
        }

        val next = command!!.execute(dt)

        if (next != command) {
            command!!.cleanUp()
            next?.firstRun()
        }

        command = next
    }

}
