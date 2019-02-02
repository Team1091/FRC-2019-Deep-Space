package com.team1091.shared.system

import com.team1091.shared.control.ImageInfo

interface ITargetingSystem {

    fun getCenter(): ImageInfo

    fun start()

}