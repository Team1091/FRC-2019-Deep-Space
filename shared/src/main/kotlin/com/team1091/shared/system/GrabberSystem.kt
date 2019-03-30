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
    var lastRanWork = 0.0
    var manualDiscPlaceStarted = false
    var manualDiscPlaceComplete = false
    var manualDiscRetrieveStarted = false
    var manualDiscRetrieveComplete = false

    override fun isPerformingGrab(): Boolean {
        return manualDiscRetrieveStarted;
    }

    override fun isExtended(): Boolean {
        return extenderSolenoid.get() == true
    }

    override fun isOpen(): Boolean {
        return grabberSolenoid.get() == false
    }

    override fun open() {
        grabberSolenoid.set(false)
    }

    override fun closed() {
        grabberSolenoid.set(true)
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

    fun placeDisk() {
        val currentState = getState()
        if (manualDiscPlaceComplete) {
            return
        }
        //Place Disc
        if (!manualDiscPlaceStarted) {
            targetState = MouthState.S4WithdrawnOpen
            manualDiscPlaceStarted = true
            return
        }
        if (currentState == MouthState.S4WithdrawnOpen) {
            targetState = MouthState.S1ExtendedClose
            return
        }
        if (currentState == MouthState.S1ExtendedClose) {
            targetState = MouthState.S4WithdrawnOpen
            manualDiscPlaceComplete = true
            return
        }
    }

    fun manualPlaceDisk() {
        val currentState = getState()
        if (manualDiscPlaceComplete) {
            return
        }
        if (!manualDiscPlaceStarted) {
            targetState = MouthState.S4WithdrawnOpen
            manualDiscPlaceStarted = true
            return
        }
        if (currentState == MouthState.S4WithdrawnOpen) {
            targetState = MouthState.S1ExtendedClose
            return
        }
    }

    fun finishPlaceDisk() {
        val currentState = getState()
        if (manualDiscPlaceComplete) {
            return
        }
        if (currentState == MouthState.S1ExtendedClose) {
            targetState = MouthState.S4WithdrawnOpen
            manualDiscPlaceComplete = true
            return
        }
    }

    fun manualRetrieveDisk() {
        val currentState = getState()
        if (manualDiscRetrieveComplete) {
            return
        }
        if (!manualDiscRetrieveStarted) {
            targetState = MouthState.S3WithdrawnClose
            manualDiscRetrieveStarted = true
            return;
        }
        if (currentState == MouthState.S3WithdrawnClose) {
            targetState = MouthState.S1ExtendedClose
            return
        }
    }

    fun finishRetreiveDisk() {
        val currentState = getState()
        if (manualDiscRetrieveComplete) {
            return
        }

        if (currentState == MouthState.S1ExtendedClose) {
            targetState = MouthState.S2ExtendedOpen
            return
        }

        if (currentState == MouthState.S2ExtendedOpen) {
            targetState = MouthState.S4WithdrawnOpen
            manualDiscRetrieveComplete = true
            return
        }
    }

    fun retreiveDisk() {
        val currentState = getState()
        if (manualDiscRetrieveComplete) {
            return
        }
        //Retrieve Disc
        if (!manualDiscRetrieveStarted) {
            targetState = MouthState.S3WithdrawnClose
            manualDiscRetrieveStarted = true
            return;
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
    }

    var xWasPressed = false
    var yWasPressed = false

    override fun readFromController() {
        if (controller.pressedX()) {
            manualPlaceDisk()
            xWasPressed = true
            return
        } else if (xWasPressed) {
            xWasPressed = false
            finishPlaceDisk()
            return
        }
        if (controller.pressedY()) {
            manualRetrieveDisk()
            return
        } else if (yWasPressed) {
            yWasPressed = false
            finishRetreiveDisk()
            return
        }
        manualDiscPlaceStarted = false
        manualDiscPlaceComplete = false
        manualDiscRetrieveStarted = false
        manualDiscRetrieveComplete = false
        targetState = MouthState.S4WithdrawnOpen
    }

    override fun doWork(dt: Double) {
        if (lastRanWork < RobotSettings.grabberDelay) {
            lastRanWork += dt
            return
        }
        val currentState = getState()
        //println(currentState.toString())
        //println("Grabber is: " + isOpen())
        //println("Extendr is: " + isExtended())
        when (targetState) {
            MouthState.S1ExtendedClose -> currentState.gotoExtendedClosed(this)
            MouthState.S2ExtendedOpen -> currentState.gotoExtendedOpen(this)
            MouthState.S3WithdrawnClose -> currentState.gotoWithdrawnClosed(this)
            MouthState.S4WithdrawnOpen -> currentState.gotoWithdrawnOpen(this)
        }
        lastRanWork = 0.0
    }
}
