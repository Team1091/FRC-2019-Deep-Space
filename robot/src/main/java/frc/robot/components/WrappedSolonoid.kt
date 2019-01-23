package frc.robot.components

import com.team1091.shared.components.ISolenoid
import edu.wpi.first.wpilibj.Solenoid

class WrappedSolenoid(val solenoid: Solenoid) : ISolenoid {

    override fun set(value: Boolean) {
        solenoid.set(value)
    }

}