package frc.robot.components;

import com.team1091.shared.components.IGameController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

public class WrappedXBox implements IGameController {

    // wrap the existing xbox controller
    private final XboxController xbox;

    public WrappedXBox(int port) {
        xbox = new XboxController(port);
    }

    @Override
    public double getLeftX() {
        return xbox.getX(GenericHID.Hand.kLeft);
    }

    @Override
    public double getLeftY() {
        return xbox.getY(GenericHID.Hand.kLeft);
    }


    @Override
    public double getRightX() {
        return xbox.getX(GenericHID.Hand.kRight);
    }

    @Override
    public double getRightY() {
        return xbox.getY(GenericHID.Hand.kRight);
    }


    @Override
    public boolean pressedY() {
        return xbox.getYButton();
    }

    @Override
    public boolean pressedX() {
        return xbox.getXButton();
    }

    @Override
    public boolean pressedB() {
        return xbox.getBButton();
    }

    @Override
    public boolean pressedA() {
        return xbox.getAButton();
    }


    @Override
    public double getLeftTrigger() {
        return xbox.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    @Override
    public double getRightTrigger() {
        return xbox.getTriggerAxis(GenericHID.Hand.kRight);
    }

    @Override
    public boolean pressedLeftBumper() {
        return xbox.getBumper(GenericHID.Hand.kLeft);
    }

    @Override
    public boolean pressedRightBumper() {
        return xbox.getBumper(GenericHID.Hand.kRight);
    }

    @Override
    public boolean getLeftStick() {
        return xbox.getStickButton(GenericHID.Hand.kLeft);
    }

    @Override
    public boolean getRightStick() {
        return xbox.getStickButton(GenericHID.Hand.kRight);
    }

    @Override
    public boolean getStart() {
        return xbox.getStartButton();
    }

    @Override
    public boolean getBack() {
        return xbox.getBackButton();
    }

    // Does the dpad exist?  Might need to listen to certain buttons
//    @Override
//    public boolean getDPadUp() {
//        return false;
//    }
//
//    @Override
//    public boolean getDPadDown() {
//        return false;
//    }
//
//    @Override
//    public boolean getDPadLeft() {
//        return false;
//    }
//
//    @Override
//    public boolean getDPadRight() {
//        return false;
//    }
}
