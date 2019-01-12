package frc.robot.components;

import com.team1091.shared.components.IAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class WrappedAccelerometer implements IAccelerometer {

    private final Accelerometer accelerometer;

    public WrappedAccelerometer(Accelerometer accelerometer) {
        this.accelerometer = accelerometer;
    }

    @Override
    public double getX() {
        return accelerometer.getX();
    }

    @Override
    public double getY() {
        return accelerometer.getY();
    }

    @Override
    public double getZ() {
        return accelerometer.getZ();
    }
}
