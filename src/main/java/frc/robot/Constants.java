// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.pathplanner.lib.config.PIDConstants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.PubSubOption;
import edu.wpi.first.networktables.PubSubOptions;
import edu.wpi.first.wpilibj.RobotBase;
import swervelib.math.Matter;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean constants. This
 * class should not be used for any other purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{

    private static double inchesToMeters(double inches){
        return inches*0.0254;
    }


  public static final double LOOP_TIME  = 0.02; //s, 20ms + 110ms sprk max velocity lag


  public static final class driveConstants {
    public static final double driveFrictionVoltage = 0.25;
    public static final double steerFrictionVoltage = 0.25;
    public static final double totalWidth=inchesToMeters(27);
    public static final double totalHeight=inchesToMeters(27);
    public static final double chassisWidth=inchesToMeters(27);
    public static final double chassisHeight=inchesToMeters(27);
    public static final double steerInertia = 0.025;
    public static final double wheelRadusInMeters = inchesToMeters(2);
    public static final double robotMass = 50;//(33) * 0.453592; // 32lbs * kg per pound
    public static final double maxSpeed  = 4.6;
    public static final Matter chassis    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(3)), robotMass);
    public static final double MOI = 6.884;
    public static final Pose2d startingPosit =  new Pose2d(7.182, 3.822, Rotation2d.fromDegrees(180));
 

    public static enum driveType {
        GENERIC,
        CTRE_ON_RIO,
        CTRE_ON_CANIVORE
    }

  }
  
      // Maximum speed of the robot in meters per second, used to limit acceleration.

  public static final class AutonConstants
  {

    // public static final PIDConstants TRANSLATION_PID = new PIDConstants(10, 0, 0.5);
    public static final PIDConstants translationPID = new PIDConstants(10, 0, 0);
    public static final PIDConstants smallAutoPID = new PIDConstants(0.7, 0, 0);

    public static final PIDConstants anglePID       = new PIDConstants(5, 0, 0);
    public static final double colisionSpeed = 0;

    public static final double widthOfMapMeters=8;
    public static final double heightOfMapMeters=8.9;
    public static final double bonusScore=1;

    public static final double autoDriveScoreTolerence  = 0.01;
    public static final double autoDriveIntakeTolerence = 0.1;
    public static final double distanceWithinPathplannerDontWork = 0.5;

    public static final double humanPlayerBeingBad = 0.5;
    public static final Transform2d intakeCoralOffset = new Transform2d(-0.47, 0, new Rotation2d());
    public static final int autoDriveCorrectCount = 3;
  
  }

  public static final class DrivebaseConstants
  {

    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static class OperatorConstants
  {

    // Joystick Deadband
    public static final double LEFT_X_DEADBAND  = 0.1;
    public static final double LEFT_Y_DEADBAND  = 0.1;
    public static final double RIGHT_X_DEADBAND = 0.1;
    public static final double TURN_CONSTANT    = 6;
    public static final int[] supportedPOV={0,90,180,270};
  }
  public static class sim{
    public static double targetTolerence =0.2;
    public static double l4CoralDropCheatX=0.08;
    public static double l4CoralDropCheatY=0.1;
    //DriveTrainSimulationConfig simConfig= new DriveTrainSimulationConfig(55, 1, 0.05, 1, 0.05, null, null);
  }

  public static class simConfigs{
    public static final boolean driveShouldBeSim=false||!RobotBase.isReal();
    public static final boolean intakeShouldBeSim=true||!RobotBase.isReal();
    public static final boolean aprilTagShouldBeSim=false||!RobotBase.isReal();
    public static final boolean wristShouldBeSim=true||!RobotBase.isReal();
    public static final boolean elevatorShouldBeSim=true||!RobotBase.isReal();
    public static final boolean reefIndexerShouldBeSim=true||!RobotBase.isReal();
    public static final boolean lidarShouldBeSim=true||!RobotBase.isReal();
    public static final boolean robotCanBeSimOnReal=true;

  }
  public static class controllerIDs{
    public static final int commandXboxController1ID=0;
    public static final int commandXboxController2ID=1;
  }

  public static class intakeConstants{
    public static final double intakeSpeed=0.3;
    public static final double outtakeSpeed=-0.3;
    public static final double intakeLength=0.245745;
    public static final int RightIntake = 50;
    public static final int LeftIntake = 50;
    public static final double coralFromWristLen = 0.0715772;
    public static final double coralLenght = 0.3;
    public static final double coralWidth = 0.11;

    public static final int frontBeamBrakePort=50;
    public static final int backBeamBrakePort=50;
  }

  public static class elevatorConstants{
    public static final double l4EncoderVal = 1.42;
    public static final double l3EncoderVal = 0.702381;
    public static final double l2EncoderVal = 0.414528;
    public static final double l1EncoderVal = 0;
    public static final double encoderToMeters =1;
    public static final double maxHeight = l4EncoderVal;
    public static final Rotation2d angle = Rotation2d.fromDegrees(70);
    public static final Translation3d fromRobotCenter = new Translation3d(0.0584454, 0, 0.583565 );
    public static final double intakePosit = 0;
    public static final double tolerence = 0.05;
    public static final double speedForSim =0.03;
    public static final double compressedLen = 0.889;

    public static final int leftMotorID=50;
    public static final int rightMotorID=50;


    // in init function
    public static final TalonFXConfiguration talonFXConfigs = new TalonFXConfiguration();

    // set slot 0 gains
    public static final Slot0Configs slot0Configs = new Slot0Configs()
        .withKS( 0.25) // Add 0.25 V output to overcome static friction
        .withKV(0.12) // A velocity target of 1 rps results in 0.12 V output
        .withKA(0.01) // An acceleration of 1 rps/s requires 0.01 V output
        .withKP(4.8) // A position error of 2.5 rotations results in 12 V output
        .withKI(0) // no output for integrated error
        .withKD(0.1) // A velocity error of 1 rps results in 0.1 V output
    ;

    // set Motion Magic settings
    MotionMagicConfigs motionMagicConfigs = new MotionMagicConfigs()
        .withMotionMagicAcceleration(160)// Target acceleration of 160 rps/s (0.5 seconds)
        .withMotionMagicCruiseVelocity(80)// Target cruise velocity of 80 rps
        .withMotionMagicJerk(1600)// Target jerk of 1600 rps/s/s (0.1 seconds)
    ;

    
  }

  public static class wristConstants{
    public static final Rotation2d l4EncoderVal = Rotation2d.fromDegrees(-15);
    public static final Rotation2d l3EncoderVal = Rotation2d.fromDegrees(0);
    public static final Rotation2d l2EncoderVal = Rotation2d.fromDegrees(0);
    public static final Rotation2d l1EncoderVal = Rotation2d.fromDegrees(0);
    public static final Rotation2d restingPosit = Rotation2d.fromDegrees(0);
    public static final Rotation2d intakePosit = Rotation2d.fromDegrees(0);

    public static final Rotation2d minDegrees = new Rotation2d();
    public static final Rotation2d maxDegrees = new Rotation2d();
    
    public static final double tolerence = 1;
    public static final double speedForSim=5;
    public static final int CANCoderID=50;
    public static final int CANCoderOffset=0;
    public static final int motorID=50;
    public static final PIDConstants wristPID = new PIDConstants(0.2, 0, 0.1);
  
  }


  public static class cameraConstants{
    public static Transform3d frontAprilTagCameraTrans = new Transform3d();

  }

// Since AutoBuilder is configured, we can use it to build pathfinding commands

}
