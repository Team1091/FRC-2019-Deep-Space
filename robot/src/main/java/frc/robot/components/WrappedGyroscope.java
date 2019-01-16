package frc.robot.components;

import com.kauailabs.navx.frc.AHRS;
import com.team1091.shared.components.IGyroscope;
import com.team1091.shared.math.Rotation;
import org.jetbrains.annotations.NotNull;

public class WrappedGyroscope implements IGyroscope {

    private final AHRS accelerometer;

    public WrappedGyroscope(AHRS accelerometer) {
        this.accelerometer = accelerometer;
    }

    @Override
    @NotNull
    public Rotation get() {
        return new Rotation(Math.toRadians(accelerometer.getAngle()));
    }
}
