package frc.robot.components;

import com.team1091.shared.components.IAccelerometer;

public class WrappedAccelerometer implements IAccelerometer {

    // private final AHRS accelerometer;

//    public WrappedAccelerometer(AHRS accelerometer) {
//        this.accelerometer = accelerometer;
//    }

    @Override
    public double getX() {
        return 0;// return accelerometer.getRawAccelX();
    }

    @Override
    public double getY() {
        return 0;// return accelerometer.getRawAccelY();
    }

    @Override
    public double getZ() {
        return 0;// return accelerometer.getRawAccelZ();
    }
}
