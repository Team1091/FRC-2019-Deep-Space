package com.team1091.shared.autonomous.commands

import com.team1091.shared.system.GrabberSystem

class GrabDisk(val grabberSystem: GrabberSystem) : Command {

    override fun firstRun() {
    }

    override fun execute(dt: Double): Command? {
        grabberSystem.grab()
        return null
    }

    override fun cleanUp() {
    }


}

enum class MouthState(
        gotoS1: (grabberSytstem: GrabberSystem) -> MouthState,
        gotoS2: (grabberSytstem: GrabberSystem) -> MouthState,
        gotoS3: (grabberSytstem: GrabberSystem) -> MouthState,
        gotoS4: (grabberSytstem: GrabberSystem) -> MouthState
) {
    S1ExtendedClose(
            gotoS1 = {  S1ExtendedClose },
            gotoS2 = { S1ExtendedClose },
            gotoS3 = { S1ExtendedClose },
            gotoS4 = { S1ExtendedClose }
    ),
    S2ExtendedOpen({ S1ExtendedClose }, { S1ExtendedClose }, { S1ExtendedClose }, { S1ExtendedClose }),
    S3WithdrawnClose({ S1ExtendedClose }, { S1ExtendedClose }, { S1ExtendedClose }, { S1ExtendedClose }),
    S4WithdrawnOpen({ S1ExtendedClose }, { S1ExtendedClose }, { S1ExtendedClose }, { S1ExtendedClose })
}
