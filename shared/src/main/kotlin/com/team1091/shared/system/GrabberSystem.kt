package com.team1091.shared.system

import com.team1091.shared.components.IGameController
import com.team1091.shared.components.ISolenoid
import com.team1091.shared.components.MouthState
import com.team1091.shared.components.RobotSettings

class GrabberSystem(
        private val grabberSolenoid: ISolenoid,
        private val extenderSolenoid: ISolenoid,
        val controller: IGameController
) : IGrabberSystem {

    var targetState = MouthState.S4WithdrawnOpen
    var lastRanWork = 0.000000000
    var manualDiscPlaceStarted = false
    var manualDiscPlaceComplete = false
    var manualDiscRetrieveStarted = false
    var manualDiscRetrieveComplete = false

    override fun isExtended(): Boolean {
        return extenderSolenoid.get() == true
    }

    override fun isOpen(): Boolean {
        return grabberSolenoid.get() == true
    }

    override fun open() {
        grabberSolenoid.set(true)
    }

    override fun closed() {
        grabberSolenoid.set(false)
    }

    override fun extend() {
        extenderSolenoid.set(true)
    }

    override fun withdraw() {
        extenderSolenoid.set(false)
    }

    override fun getState(): MouthState {
        if (isExtended() && !isOpen()) {
            return MouthState.S1ExtendedClose
        }
        if (isExtended() && isOpen()) {
            return MouthState.S2ExtendedOpen
        }
        if (!isExtended() && !isOpen()) {
            return MouthState.S3WithdrawnClose
        }
        return MouthState.S4WithdrawnOpen
    }

    override fun readFromController() {
        var currentState = getState()
        if (controller.pressedX()) {
            if (!manualDiscPlaceComplete) {
                return
            }
            //Place Disc
            if (!manualDiscPlaceStarted) {
                targetState = MouthState.S4WithdrawnOpen
                manualDiscPlaceStarted = true
            }
            if (currentState == MouthState.S4WithdrawnOpen) {
                targetState = MouthState.S1ExtendedClose
                return
            }
            if (currentState == MouthState.S1ExtendedClose) {
                targetState = MouthState.S3WithdrawnClose
                manualDiscPlaceComplete = true
                return
            }
            return
        }
        if (controller.pressedY()) {
            if (!manualDiscRetrieveComplete) {
                return
            }
            //Retrieve Disc
            if (!manualDiscRetrieveStarted) {
                currentState = MouthState.S3WithdrawnClose
                manualDiscRetrieveStarted = true
            }
            if (currentState == MouthState.S3WithdrawnClose) {
                targetState = MouthState.S2ExtendedOpen
                return
            }
            if (currentState == MouthState.S2ExtendedOpen) {
                targetState = MouthState.S4WithdrawnOpen
                manualDiscRetrieveComplete = true
                return
            }
            return
        }
        manualDiscPlaceStarted = false
        manualDiscPlaceComplete = false
        manualDiscRetrieveStarted = false
        manualDiscRetrieveComplete = false
        targetState = MouthState.S4WithdrawnOpen;
    };

    override fun doWork(dt: Double) {
        if (lastRanWork + RobotSettings.grabberDelay > dt) {
            return
        }
        var currentState = getState()
        when (targetState) {
            MouthState.S1ExtendedClose -> currentState.gotoExtendedClosed(this)
            MouthState.S2ExtendedOpen -> currentState.gotoExtendedOpen(this)
            MouthState.S3WithdrawnClose -> currentState.gotoWithdrawnClosed(this)
            MouthState.S4WithdrawnOpen -> currentState.gotoWithdrawnClosed(this)
        }
        lastRanWork = dt
    }
}
