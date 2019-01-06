package frc.robot.components;

import com.team1091.shared.components.IGyroscope;
import com.team1091.shared.math.Rotation;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class WrappedGyroscope implements IGyroscope {

    private final Gyro analogGyro;

    public WrappedGyroscope(int channel) {
        if (channel != 0 && channel != 1) {
            throw new RuntimeException("Gyro only works on channels 0 + 1");
        }

        this.analogGyro = new AnalogGyro(channel);
    }

    @Override
    public Rotation get() {
        return new Rotation(Math.toRadians(analogGyro.getAngle()));
    }
}
