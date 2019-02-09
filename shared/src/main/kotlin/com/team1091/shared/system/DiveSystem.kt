package com.team1091.shared.system

import com.team1091.shared.components.IDrive
import com.team1091.shared.components.RobotSettings


class DriveSystem(val drive: IDrive) {
    var forwardAmnt = 0.0
    var turnAmnt = 0.0
    var lastIncrement:Long = 0


    fun drive() {
        drive.arcadeDrive(forwardAmnt, turnAmnt)
        forwardAmnt = 0.0
        turnAmnt = 0.0
    }

    fun getSpeedToSet(desiredSpeed: Double, currentSpeed: Double, accel: Double, dt: Double): Double {

        if (desiredSpeed == currentSpeed) {
            return desiredSpeed
        }
        if (desiredSpeed > currentSpeed) { // we are accelerating towards our target
            // if we are going towards 0, just get there
            return if (currentSpeed < 0) desiredSpeed else Math.min(currentSpeed + Math.min(accel * dt, accel), desiredSpeed)

        }
        // we are going slower
        // if we are going towards 0, just get to dest
        return if (currentSpeed > 0) desiredSpeed else Math.max(currentSpeed - Math.min(accel * dt, accel), desiredSpeed)

    }

    fun arcadeDrive(forwardAmnt: Double, turnAmnt: Double, dt:Double) {
        var currentForward = drive.getCurrentLinear()
        var currentTurn = drive.getCurrentRotation()
        this.forwardAmnt = getSpeedToSet(forwardAmnt, currentForward, RobotSettings.driveAccelStep, dt)
        this.turnAmnt = getSpeedToSet(turnAmnt, currentTurn, RobotSettings.driveAccelStep, dt)
        if(this.forwardAmnt != 0.0) {
            if(this.forwardAmnt < .3 && this.forwardAmnt > 0)  {
                this.forwardAmnt = .3;
            }
            if(this.forwardAmnt > -.3 && this.forwardAmnt < 0){
                this.forwardAmnt = -.3;
            }
        }
        if(this.turnAmnt != 0.0) {
            if(this.turnAmnt < .3 && this.turnAmnt > 0)  {
                this.turnAmnt = .3;
            }
            if(this.turnAmnt > -.3 && this.turnAmnt < 0){
                this.turnAmnt = -.3;
            }
        }
        //var currentTime = System.nanoTime()
        //this.forwardAmnt = forwardAmnt
        //this.turnAmnt = turnAmnt
       /* System.out.println(currentForward)
        System.out.println(currentTurn)
        if(currentTime - lastIncrement < RobotSettings.driveDelay * 1000000000)
        {
            this.forwardAmnt = currentForward
            this.turnAmnt = currentTurn
            System.out.println("Forward: " + currentForward)
            System.out.println("Turn: " + currentTurn)
            return
        }
        if(currentForward < forwardAmnt)
        {
            this.forwardAmnt = currentForward + RobotSettings.driveAccelStep
        }
        if(currentForward > forwardAmnt)
        {
            this.forwardAmnt = currentForward - RobotSettings.driveAccelStep
        }
        if(currentTurn < turnAmnt)
        {
            this.turnAmnt = currentTurn + RobotSettings.driveAccelStep
        }
        if(currentTurn > turnAmnt)
        {
            this.turnAmnt = currentTurn - RobotSettings.driveAccelStep
        }
        if (forwardAmnt == 0.0)
        {
            this.forwardAmnt = 0.0
        }
        if (turnAmnt == 0.0)
        {
            this.turnAmnt = 0.0
        }
        lastIncrement = currentTime*/
    }
}