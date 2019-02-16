package frc.robot.components;

import com.team1091.shared.components.ISolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class WrappedSolenoid implements ISolenoid {

    private final DoubleSolenoid solenoid;

    public WrappedSolenoid(DoubleSolenoid solenoid) {
        this.solenoid = solenoid;
    }

    public void set(Boolean value) {
        if (value == null) {
            solenoid.set(DoubleSolenoid.Value.kOff);
        } else if (value) {
            solenoid.set(DoubleSolenoid.Value.kForward);
        } else {
            solenoid.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public Boolean get() {
        var value = solenoid.get();
        if (value == DoubleSolenoid.Value.kOff) {
            return null;
        } else
            return value == DoubleSolenoid.Value.kForward;
    }
}