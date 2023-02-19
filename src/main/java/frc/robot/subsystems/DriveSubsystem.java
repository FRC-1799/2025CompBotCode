
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveSubsystem extends SubsystemBase {
  // right motors
  final MotorControllerGroup leftMotors = new MotorControllerGroup(
    new CANSparkMax(Constants.drive.lt, MotorType.kBrushless),
    new CANSparkMax(Constants.drive.lr, MotorType.kBrushless),
    new CANSparkMax(Constants.drive.lf, MotorType.kBrushless)
  );

  // left motors
  final MotorControllerGroup rightMotors = new MotorControllerGroup(
    new CANSparkMax(Constants.drive.rt, MotorType.kBrushless),
    new CANSparkMax(Constants.drive.rr, MotorType.kBrushless),
    new CANSparkMax(Constants.drive.rf, MotorType.kBrushless)
  );

  private final DifferentialDrive m_RobotDrive;

  private final ADXRS450_Gyro m_gyro = new ADXRS450_Gyro();

  public DriveSubsystem() {
    leftMotors.setInverted(true);
    m_RobotDrive = new DifferentialDrive(leftMotors,rightMotors);

    addChild("Drive", m_RobotDrive);
    addChild("Gyro", m_gyro);

    m_gyro.calibrate();

    reset();
  }

  public void drive(final double ySpeed, final double rotateValue) {
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);
  }

  public void drive(final double ySpeed, final double rotateValue, final double hDriveMotorSpeed) {
    m_RobotDrive.arcadeDrive(ySpeed, rotateValue);
  }

  public void reset() {
    m_gyro.reset();
  }

  public void log() {
    SmartDashboard.putNumber("Gyro", m_gyro.getAngle());
  }

  public double getHeading() {
    return m_gyro.getAngle();
  }

}
