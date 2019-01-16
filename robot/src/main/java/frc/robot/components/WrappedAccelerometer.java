package frc.robot.components;

import com.kauailabs.navx.frc.AHRS;
import com.team1091.shared.components.IAccelerometer;

public class WrappedAccelerometer implements IAccelerometer {

    private final AHRS accelerometer;

    public WrappedAccelerometer(AHRS accelerometer) {
        this.accelerometer = accelerometer;
    }

    @Override
    public double getX() {
        return accelerometer.getRawAccelX();
    }

    @Override
    public double getY() {
        return accelerometer.getRawAccelY();
    }

    @Override
    public double getZ() {
        return accelerometer.getRawAccelZ();
    }
}
