package frc.robot.components;

import com.team1091.shared.components.ISolenoid;
import edu.wpi.first.wpilibj.Solenoid;

public class WrappedSolenoid implements ISolenoid {

    private Solenoid solenoid;

    public WrappedSolenoid(Solenoid solenoid) {
        this.solenoid = solenoid;
    }

    @Override
    public void set(boolean value) {
        solenoid.set(value);
    }
}