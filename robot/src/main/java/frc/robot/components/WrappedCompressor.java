package frc.robot.components;

import com.team1091.shared.control.ICompressor;
import edu.wpi.first.wpilibj.Compressor;

public class WrappedCompressor implements ICompressor {
    private Compressor compressor;
    private boolean isOn;

    public WrappedCompressor() {
        compressor = new Compressor(0);
    }

    @Override
    public void on() {

        if (!isOn) {
            compressor.setClosedLoopControl(true);
            isOn = true;
        }
    }

    @Override
    public void off() {
        if (isOn) {
            compressor.setClosedLoopControl(false);
            isOn = false;
        }


    }
}
