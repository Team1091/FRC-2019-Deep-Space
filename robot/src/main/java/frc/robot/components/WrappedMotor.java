package frc.robot.components;

import com.team1091.shared.components.IMotorController;
import edu.wpi.first.wpilibj.Victor;

public class WrappedMotor implements IMotorController {

    private final Victor victor;

    public WrappedMotor(Victor victor) {
        this.victor = victor;
    }

    public void set(double speed) {
        victor.set(speed);
    }


}
