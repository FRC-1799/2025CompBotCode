package frc.robot.subsystems.wrist;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.wristElevatorControlManager;

public class simWrist extends wristIO{

   
    private double position;
    private double goal;

    @Override
    public void periodic(){

        if (
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.wrist||
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.resting){
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
    }


    @Override
    public double getCurrentLocation() {
        return position;
    }
}
