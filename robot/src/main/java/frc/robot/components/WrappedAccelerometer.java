package frc.robot.components;

import com.team1091.shared.components.IAccelerometer;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class WrappedAccelerometer implements IAccelerometer {

    private final BuiltInAccelerometer builtInAccelerometer;

    public WrappedAccelerometer(BuiltInAccelerometer builtInAccelerometer) {
        this.builtInAccelerometer = builtInAccelerometer;
    }

    @Override
    public double getX() {
        return builtInAccelerometer.getX();
    }

    @Override
    public double getY() {
        return builtInAccelerometer.getY();
    }

    @Override
    public double getZ() {
        return builtInAccelerometer.getZ();
    }
}
