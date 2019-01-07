package frc.robot;

import com.team1091.shared.control.RobotComponents;
import com.team1091.shared.control.TeamRobot;
import com.team1091.shared.control.TeamRobotImpl;
import com.team1091.shared.game.StartingPos;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.components.WrappedAccelerometer;
import frc.robot.components.WrappedDrive;
import frc.robot.components.WrappedEncoder;
import frc.robot.components.WrappedGyroscope;
import frc.robot.components.WrappedXBox;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private final TeamRobot teamRobot;

    Robot() {
        // create real components wrapped and send them to the other project
        // then delegate to our shared code
        teamRobot = new TeamRobotImpl(
                new RobotComponents(
                        new WrappedXBox(0),
                        new WrappedDrive(
                                new Victor(0), // scLeft
                                new Victor(1)  // scRight
                        ),
                        new WrappedEncoder(3, 4),
                        new WrappedEncoder(5, 6),
                        new WrappedAccelerometer(new BuiltInAccelerometer()),
                        new WrappedGyroscope(0)
                )
        );
    }

    @Override
    public void robotInit() {

        // TODO: get this from the field or dropdown
        StartingPos pos = StartingPos.BLUE_1;
        teamRobot.robotInit(pos);
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
        teamRobot.disabledPeriodic();
    }

    @Override
    public void autonomousPeriodic() {
        teamRobot.autonomousPeriodic();
    }

    @Override
    public void teleopPeriodic() {
        teamRobot.teleopPeriodic();
    }

    @Override
    public void testPeriodic() {
        teamRobot.testPeriodic();
    }
}