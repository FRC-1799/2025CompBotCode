package frc.robot.subsystems.wrist;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.wristElevatorControlManager;

public class simWrist extends wristIO{

   
    private double position;
    private double goal;

    @Override
    public void periodic(){

        if (
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControlState.wrist||
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControlState.resting){
            goal=setpoint;
        }
        else{
            goal=Constants.wristConstants.restingPosit.getDegrees();
        }

        if (Math.abs(goal-position)<Constants.wristConstants.speedForSim){
            position=goal;
        }
        else if (goal>position){
            position+=Constants.wristConstants.speedForSim;
        }
        else{
            position-=Constants.wristConstants.speedForSim;
        }

        SmartDashboard.putNumber("wristEncoder", position);
        SmartDashboard.putNumber("WristencoderR2D", getCurrentLocationR2D().getDegrees());
        SmartDashboard.putNumber("wristGoal", goal);
        SmartDashboard.putBoolean("WristAtSetpoint", isAtSetpoint());
    }


    @Override
    public double getCurrentLocation() {
        return position;
    }
}
