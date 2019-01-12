package frc.robot.components;

import com.team1091.shared.components.IDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class WrappedDrive implements IDrive {
    private final DifferentialDrive differentialDrive;

    public WrappedDrive(SpeedController scLeft, SpeedController scRight) {
        differentialDrive = new DifferentialDrive(scLeft, scRight);
    }

    @Override
    public void arcadeDrive(double speed, double turn) {
        differentialDrive.arcadeDrive(speed, turn);
    }

}
