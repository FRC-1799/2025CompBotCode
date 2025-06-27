package frc.robot.subsystems.elevator;


import frc.robot.Constants;
import frc.robot.subsystems.wristElevatorControlManager;

public class simElevator  extends elevatorIO{


    public double position=0;
    protected double goal=0;
    

    @Override
    public double getEncoderVal() {
        return position;
    }

    @Override
    public double getHeight(){
        return position;
    }

    @Override
    public void periodic(){


        if (wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControlState.elevator||
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControlState.resting){

            goal=setpoint;
        }
        else{
            goal=Constants.elevatorConstants.maxHeight;
        }


        if (Math.abs(goal-position)<Constants.elevatorConstants.speedForSim){
            position=goal;
        }
        else if (goal>position){
            position+=Constants.elevatorConstants.speedForSim;
        }
        else{
            position-=Constants.elevatorConstants.speedForSim;
        }

        updateRender();
        
    }





    
    
}
