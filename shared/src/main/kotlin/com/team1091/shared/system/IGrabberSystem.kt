package com.team1091.shared.system

import com.team1091.shared.components.MouthState

interface IGrabberSystem {

    fun open()
    fun closed()
    fun extend()
    fun withdraw()
    fun isExtended(): Boolean
    fun isOpen(): Boolean
    fun getState(): MouthState
    fun readFromController()
    fun doWork(dt: Double)
    fun isPerformingGrab() : Boolean
}