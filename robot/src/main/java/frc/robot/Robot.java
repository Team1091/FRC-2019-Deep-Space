package frc.robot;

//import com.kauailabs.navx.frc.AHRS;

import com.team1091.shared.control.RobotComponents;
import com.team1091.shared.control.TeamRobot;
import com.team1091.shared.control.TeamRobotImpl;
import com.team1091.shared.game.StartingPos;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.components.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private final TeamRobot teamRobot;

    private UsbCamera foreCamera;
    private UsbCamera backCamera;
    private VideoSink sink;
    private WrappedXBox xbox;

    Robot() {

        xbox = new WrappedXBox(0);

//        AHRS accel = new AHRS(SerialPort.Port.kUSB);
        // create real components wrapped and send them to the other project
        // then delegate to our shared code
        teamRobot = new TeamRobotImpl(
                new RobotComponents(
                        xbox,
                        new WrappedDrive(
                                new Victor(0), // scLeft
                                new Victor(1)  // scRight
                        ),
                        new WrappedEncoder(3, 4),
                        new WrappedEncoder(5, 6),
//                        new WrappedAccelerometer(new AHRS(SPI.Port.kMXP)),
//                        new WrappedGyroscope(),
                        new WrappedMotor(
                                new Victor(2) // Lift Motor
                        ),
                        new WrappedSolenoid(
                                new DoubleSolenoid(0, 1) // grabber
                        ),
                        new WrappedSolenoid(
                                new DoubleSolenoid(2, 3) // grabber
                        ),
                        new TargetingSystem(),
                        new WrappedCompressor()
                )
        );
    }

    @Override
    public void robotInit() {

        foreCamera = CameraServer.getInstance().startAutomaticCapture(0);
        backCamera = CameraServer.getInstance().startAutomaticCapture(1);
        sink = CameraServer.getInstance().getServer();

        foreCamera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        backCamera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        //Robot setup so driver doesn't blind himself
        foreCamera.setBrightness(20);
        foreCamera.setExposureManual(20);
        foreCamera.setWhiteBalanceManual(50);

        backCamera.setBrightness(20);
        backCamera.setExposureManual(20);
        backCamera.setWhiteBalanceManual(50);

        StartingPos pos = StartingPos.BLUE_1;
        teamRobot.robotInit(pos);
    }

    boolean prevTrigger = false;

    private void cameraSelect() {

        if (rightTrigger() && !prevTrigger) {
            sink.setSource(backCamera);
        } else if (!rightTrigger() && prevTrigger) {
            sink.setSource(foreCamera);
        }
        prevTrigger = rightTrigger();
    }

    private boolean rightTrigger() {
        return xbox.getRightTrigger() > 0.75;
    }

    @Override
    public void disabledInit() {
        teamRobot.disabledInit();
    }

    @Override
    public void autonomousInit() {
        teamRobot.autonomousInit();
    }

    @Override
    public void teleopInit() {
        teamRobot.teleopInit();
    }

    @Override
    public void testInit() {
        teamRobot.testInit();
    }

    @Override
    public void disabledPeriodic() {
        cameraSelect();
        teamRobot.disabledPeriodic();
    }

    @Override
    public void autonomousPeriodic() {
        cameraSelect();
        teamRobot.autonomousPeriodic();
    }

    @Override
    public void teleopPeriodic() {
        cameraSelect();
        teamRobot.teleopPeriodic();
    }

    @Override
    public void testPeriodic() {
        cameraSelect();
        teamRobot.testPeriodic();
    }
}
