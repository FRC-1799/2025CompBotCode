package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.elevator.elevatorIO;
import frc.robot.subsystems.wrist.wristIO;
/**class to manage the interactions between the elevator and the wrist */
public class wristElevatorControlManager{


    public enum wristElevatorControllState{
        wrist,
        elevator,
        fixWrist,
        fixElevator,
        resting
    }


    protected static wristElevatorControllState state = wristElevatorControllState.resting;
    protected static wristIO wrist;
    protected static elevatorIO elevator;

    


    /**@return the current state in terms of a wristElevatorControllState*/
    public static wristElevatorControllState getState(){
        return state;
    }

    /**adds the wrist and elevator to be used by the manager */
    public static void addSystems(wristIO newWrist, elevatorIO newElevator){
        elevator=newElevator;
        wrist=newWrist;
    }


    /**Updates the wrist elevevator manager. should be called every rio cycle */
    public static void periodic(){
        if (wrist==null||elevator==null){
            return;
        }

        //elevator state
        if (state == wristElevatorControllState.elevator){
            if (!wrist.atLegalNonControlState()){
                state=wristElevatorControllState.fixWrist;
            }
            if (elevator.isAtSetpoint()){
                if (wrist.isAtSetpoint()){
                    state=wristElevatorControllState.resting;
                }
                else{
                    state=wristElevatorControllState.wrist;
                }
            }
        }

        //wrist state
        else if(state==wristElevatorControllState.wrist){
            if(!elevator.atLegalNonControlState()){
                state=wristElevatorControllState.fixElevator;
            }
            if(!elevator.isAtSetpoint()){
                if (wrist.atLegalNonControlState()){
                    state=wristElevatorControllState.fixWrist;
                }
                else{
                    state=wristElevatorControllState.elevator;
                }
            }

            if (wrist.isAtSetpoint()){
                state=wristElevatorControllState.resting;
            }
        }


        //fix elevator state
        else if(state==wristElevatorControllState.fixElevator){
            if (elevator.atLegalNonControlState()){
                state=wristElevatorControllState.resting;
            }
        }

        //fix wrist state
        else if(state==wristElevatorControllState.fixWrist){
            if(wrist.atLegalNonControlState()){
                state=wristElevatorControllState.resting;
            }
        }

        //resting state
        else if (state==wristElevatorControllState.resting){
            if (!elevator.isAtSetpoint()){
                if (!wrist.atLegalNonControlState()){
                    state=wristElevatorControllState.fixWrist;
                }
                else{
                    state=wristElevatorControllState.elevator;
                }
            }
            else if(!wrist.isAtSetpoint()){
                state=wristElevatorControllState.wrist;
            }
        }

        SmartDashboard.putString("wristElevator state", state.name());

    }


}



