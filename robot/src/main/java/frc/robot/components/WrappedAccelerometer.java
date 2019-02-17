package frc.robot.components;

import com.kauailabs.navx.frc.AHRS;
import com.team1091.shared.components.IAccelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WrappedAccelerometer implements IAccelerometer {

    private final AHRS accelerometer;

    public WrappedAccelerometer(AHRS accelerometer) {
        this.accelerometer = accelerometer;
    }

    @Override
    public double getAngle(){
        return 0;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getZ() {
        return 0;
    }

    @Override
    public void writeOutData(){

    }
}
