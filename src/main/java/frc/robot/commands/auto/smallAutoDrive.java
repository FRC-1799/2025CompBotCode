package frc.robot.commands.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Utils.utillFunctions;


public class smallAutoDrive extends Command{
    protected Pose2d pose;
    protected int correctCount=0;
    protected double runtime=0;
    protected static StructPublisher<Pose2d> goalPosePublisher = NetworkTableInstance.getDefault().getStructTopic("goalPose", Pose2d.struct).publish();
    protected PIDController pid = new PIDController(Constants.AutonConstants.smallAutoPID.kP, Constants.AutonConstants.smallAutoPID.kI, Constants.AutonConstants.smallAutoPID.kD);

    /**
     * Creates a custom single PID auto drive. intended for driving short distances to incredible accuracy. does not contain any form of obstical avoidence.
     * @param pose the pose to drive too.
     */
    public smallAutoDrive(Pose2d pose){
        this.pose=pose;
        pid.setSetpoint(0);
        pid.setTolerance(Constants.AutonConstants.autoDriveScoreTolerence);
        
        addRequirements(SystemManager.swerve);
    }

    @Override
    public void initialize(){
        runtime=0;
        pid.calculate(utillFunctions.pythagorean(pose.getX(), SystemManager.getSwervePose().getX(), pose.getY(), SystemManager.getSwervePose().getY()));
        if (isFinished()){
            cancel();
        }
    }

     /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute(){
        
        Rotation2d angleRad = new Rotation2d(-(pose.getX()-SystemManager.getSwervePose().getX()), pose.getY()-SystemManager.getSwervePose().getY());
        
        double speed = -pid.calculate(utillFunctions.pythagorean(pose.getX(), SystemManager.getSwervePose().getX(), pose.getY(), SystemManager.getSwervePose().getY()));
        SystemManager.swerve.drive(speed, angleRad, pose.getRotation());

        SmartDashboard.putNumber("smallDriveSpeed", speed);
        SmartDashboard.putNumber("smallDriveError", utillFunctions.pythagorean(pose.getX(), SystemManager.getSwervePose().getX(), pose.getY(), SystemManager.getSwervePose().getY()));
        SmartDashboard.putBoolean("smallDriveRunning", true);
        runtime+=0.02;
        SmartDashboard.putNumber("runtime", runtime);

        
        goalPosePublisher.set(pose); 
    }


    /**
     * @return true once the robot has been within tolerence for three frames straight
     */
    @Override
    public boolean isFinished(){
        return pid.atSetpoint()&& SystemManager.swerve.getRobotVelocity().vxMetersPerSecond<0.01&& SystemManager.swerve.getRobotVelocity().vyMetersPerSecond<0.01;
        // if (pid.atSetpoint()){
        //     correctCount++;
            
        // }
        // else{
        //     correctCount=0;
        // }
        // return correctCount>Constants.AutonConstants.autoDriveCorrectCount;

    }


    /**
     * command called when the command finishes
     * @param wasInterupted wether or not the command was cancled
    */
    @Override
    public void end(boolean wasCancled){
        SmartDashboard.putBoolean("smallDriveRunning", false);
    }
}
