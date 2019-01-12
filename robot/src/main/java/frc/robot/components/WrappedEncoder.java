package frc.robot.components;

import com.team1091.shared.components.IEncoder;
import edu.wpi.first.wpilibj.Encoder;

public class WrappedEncoder implements IEncoder {

    private final Encoder encoder;
    private final double multiplier;

    public WrappedEncoder(int channelA, int channelB) {
        encoder = new Encoder(channelA, channelB);
        multiplier = 1.0;
    }

    public WrappedEncoder(int channelA, int channelB, boolean reverse, double multiplier) {
        encoder = new Encoder(channelA, channelB, reverse);
        this.multiplier = multiplier;
    }

    @Override
    public void reset() {
        encoder.reset();
    }

    @Override
    public double get() {
        return encoder.get();
    }

    @Override
    public double getDistance() {
        return encoder.get() * multiplier;
    }
}
