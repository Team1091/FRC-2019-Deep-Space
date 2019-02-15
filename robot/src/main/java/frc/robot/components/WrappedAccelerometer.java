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
        return accelerometer.getRawAccelX();
    }

    @Override
    public double getX() {
        return accelerometer.getRawAccelX();
    }

    @Override
    public double getY() {
        return accelerometer.getRawAccelY();
    }

    @Override
    public double getZ() {
        return accelerometer.getRawAccelZ();
    }

    @Override
    public void writeOutData(){
        /* Display 6-axis Processed Angle Data                                      */
        SmartDashboard.putBoolean(  "IMU_Connected",        accelerometer.isConnected());
        SmartDashboard.putBoolean(  "IMU_IsCalibrating",    accelerometer.isCalibrating());
        SmartDashboard.putNumber(   "IMU_Yaw",              accelerometer.getYaw());
        SmartDashboard.putNumber(   "IMU_Pitch",            accelerometer.getPitch());
        SmartDashboard.putNumber(   "IMU_Roll",             accelerometer.getRoll());

        /* Display tilt-corrected, Magnetometer-based heading (requires             */
        /* magnetometer calibration to be useful)                                   */

        SmartDashboard.putNumber(   "IMU_CompassHeading",   accelerometer.getCompassHeading());

        /* Display 9-axis Heading (requires magnetometer calibration to be useful)  */
        SmartDashboard.putNumber(   "IMU_FusedHeading",     accelerometer.getFusedHeading());

        /* These functions are compatible w/the WPI Gyro Class, providing a simple  */
        /* path for upgrading from the Kit-of-Parts gyro to the navx MXP            */

        SmartDashboard.putNumber(   "IMU_TotalYaw",         accelerometer.getAngle());
        SmartDashboard.putNumber(   "IMU_YawRateDPS",       accelerometer.getRate());

        /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */

        SmartDashboard.putNumber(   "IMU_Accel_X",          accelerometer.getWorldLinearAccelX());
        SmartDashboard.putNumber(   "IMU_Accel_Y",          accelerometer.getWorldLinearAccelY());
        SmartDashboard.putBoolean(  "IMU_IsMoving",         accelerometer.isMoving());
        SmartDashboard.putBoolean(  "IMU_IsRotating",       accelerometer.isRotating());

        /* Display estimates of velocity/displacement.  Note that these values are  */
        /* not expected to be accurate enough for estimating robot position on a    */
        /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
        /* of these errors due to single (velocity) integration and especially      */
        /* double (displacement) integration.                                       */

        SmartDashboard.putNumber(   "Velocity_X",           accelerometer.getVelocityX());
        SmartDashboard.putNumber(   "Velocity_Y",           accelerometer.getVelocityY());
        SmartDashboard.putNumber(   "Displacement_X",       accelerometer.getDisplacementX());
        SmartDashboard.putNumber(   "Displacement_Y",       accelerometer.getDisplacementY());

        /* Display Raw Gyro/Accelerometer/Magnetometer Values                       */
        /* NOTE:  These values are not normally necessary, but are made available   */
        /* for advanced users.  Before using this data, please consider whether     */
        /* the processed data (see above) will suit your needs.                     */

        SmartDashboard.putNumber(   "RawGyro_X",            accelerometer.getRawGyroX());
        SmartDashboard.putNumber(   "RawGyro_Y",            accelerometer.getRawGyroY());
        SmartDashboard.putNumber(   "RawGyro_Z",            accelerometer.getRawGyroZ());
        SmartDashboard.putNumber(   "RawAccel_X",           accelerometer.getRawAccelX());
        SmartDashboard.putNumber(   "RawAccel_Y",           accelerometer.getRawAccelY());
        SmartDashboard.putNumber(   "RawAccel_Z",           accelerometer.getRawAccelZ());
        SmartDashboard.putNumber(   "RawMag_X",             accelerometer.getRawMagX());
        SmartDashboard.putNumber(   "RawMag_Y",             accelerometer.getRawMagY());
        SmartDashboard.putNumber(   "RawMag_Z",             accelerometer.getRawMagZ());
        SmartDashboard.putNumber(   "IMU_Temp_C",           accelerometer.getTempC());
        SmartDashboard.putNumber(   "IMU_Timestamp",        accelerometer.getLastSensorTimestamp());

        /* Omnimount Yaw Axis Information                                           */
        /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount  */
        AHRS.BoardYawAxis yaw_axis = accelerometer.getBoardYawAxis();
        SmartDashboard.putString(   "YawAxisDirection",     yaw_axis.up ? "Up" : "Down" );
        SmartDashboard.putNumber(   "YawAxis",              yaw_axis.board_axis.getValue() );

        /* Sensor Board Information                                                 */
        SmartDashboard.putString(   "FirmwareVersion",      accelerometer.getFirmwareVersion());

        /* Quaternion Data                                                          */
        /* Quaternions are fascinating, and are the most compact representation of  */
        /* orientation data.  All of the Yaw, Pitch and Roll Values can be derived  */
        /* from the Quaternions.  If interested in motion processing, knowledge of  */
        /* Quaternions is highly recommended.                                       */
        SmartDashboard.putNumber(   "QuaternionW",          accelerometer.getQuaternionW());
        SmartDashboard.putNumber(   "QuaternionX",          accelerometer.getQuaternionX());
        SmartDashboard.putNumber(   "QuaternionY",          accelerometer.getQuaternionY());
        SmartDashboard.putNumber(   "QuaternionZ",          accelerometer.getQuaternionZ());

        /* Connectivity Debugging Support                                           */
        SmartDashboard.putNumber(   "IMU_Byte_Count",       accelerometer.getByteCount());
        SmartDashboard.putNumber(   "IMU_Update_Count",     accelerometer.getUpdateCount());
    }
}
