package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.elevator.elevatorIO;
import frc.robot.subsystems.wrist.wristIO;
/**class to manage the interactions between the elevator and the wrist */
public class wristElevatorControlManager{


    public enum wristElevatorControlState{
        wrist,
        elevator,
        fixWrist,
        fixElevator,
        resting
    }


    protected static wristElevatorControlState state = wristElevatorControlState.resting;
    protected static wristIO wrist;
    protected static elevatorIO elevator;

    


    /**@return the current state in terms of a wristElevatorControlState*/
    public static wristElevatorControlState getState(){
        return state;
    }

    /**adds the wrist and elevator to be used by the manager */
    public static void addSystems(wristIO newWrist, elevatorIO newElevator){
        elevator=newElevator;
        wrist=newWrist;
    }


    /**Updates the wrist elevator manager. should be called every rio cycle */
    public static void periodic(){
        if (wrist==null||elevator==null){
            return;
        }

        //elevator state
        if (state == wristElevatorControlState.elevator){
            if (!wrist.atLegalNonControlState()){
                state=wristElevatorControlState.fixWrist;
            }
            if (elevator.isAtSetpoint()){
                if (wrist.isAtSetpoint()){
                    state=wristElevatorControlState.resting;
                }
                else{
                    state=wristElevatorControlState.wrist;
                }
            }
        }

        //wrist state
        else if(state==wristElevatorControlState.wrist){
            if(!elevator.atLegalNonControlState()&&wrist.getSetpoint().getDegrees()!=0){
                state=wristElevatorControlState.fixElevator;
            }
            if(!elevator.isAtSetpoint()){
                if (wrist.atLegalNonControlState()){
                    state=wristElevatorControlState.fixWrist;
                }
                else{
                    state=wristElevatorControlState.elevator;
                }
            }

            if (wrist.isAtSetpoint()){
                state=wristElevatorControlState.resting;
            }
        }


        //fix elevator state
        else if(state==wristElevatorControlState.fixElevator){
            if (elevator.atLegalNonControlState()){
                state=wristElevatorControlState.resting;
            }
        }

        //fix wrist state
        else if(state==wristElevatorControlState.fixWrist){
            if(wrist.atLegalNonControlState()){
                state=wristElevatorControlState.resting;
            }
        }

        //resting state
        else if (state==wristElevatorControlState.resting){
            if (!elevator.isAtSetpoint()){
                if (!wrist.atLegalNonControlState()){
                    state=wristElevatorControlState.fixWrist;
                }
                else{
                    state=wristElevatorControlState.elevator;
                }
            }
            else if(!wrist.isAtSetpoint()){
                state=wristElevatorControlState.wrist;
            }
        }

        SmartDashboard.putString("wristElevator state", state.name());

    }


}



