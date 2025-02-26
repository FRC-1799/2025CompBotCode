package frc.robot.subsystems.elevator;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.subsystems.wristElevatorControlManager;

public class simElevator  extends elevatorIO{


    public double position=0;
    protected double goal=0;
    

    @Override
    public double getEncoderVal() {
        return position;
    }

    @Override
    public void periodic(){


        if (wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.elevator||
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.resting){

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
