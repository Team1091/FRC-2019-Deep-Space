package frc.robot.components;

import com.team1091.shared.components.IDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class WrappedDrive implements IDrive {
    private final DifferentialDrive differentialDrive;
    private double speed = 0;
    private double turn = 0;

    public WrappedDrive(SpeedController scLeft, SpeedController scRight) {
        differentialDrive = new DifferentialDrive(scLeft, scRight);
    }

    @Override
    public void arcadeDrive(double speed, double turn) {
        differentialDrive.arcadeDrive(speed, turn);
        this.speed = speed;
        this.turn = turn;
    }

    @Override
    public double getCurrentLinear() {
        return speed;
    }

    @Override
    public double getCurrentRotation() {
        return turn;
    }
}
