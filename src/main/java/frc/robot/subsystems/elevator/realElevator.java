package frc.robot.subsystems.elevator;
 
 import com.ctre.phoenix6.controls.Follower;
 import com.ctre.phoenix6.controls.MotionMagicVoltage;
 import com.ctre.phoenix6.hardware.TalonFX;
 
 import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

import frc.robot.subsystems.wristElevatorControlManager;
 
 
 public class realElevator  extends elevatorIO{
     
     protected double goal=0;
     protected double oldGoal=0;
     
 
     protected TalonFX mainMotor = new TalonFX(Constants.elevatorConstants.mainMotorID);
     protected TalonFX offMotor = new TalonFX(Constants.elevatorConstants.altMotorID);
    protected DigitalInput resetSwitch = new DigitalInput(Constants.elevatorConstants.resetSwitchID);
      // create a Motion Magic request, voltage output
      final MotionMagicVoltage motionVoltage = new MotionMagicVoltage(0);
 
 
 
     public realElevator(){
        mainMotor.getConfigurator().apply(Constants.elevatorConstants.slot0Configs);
        
        mainMotor.getConfigurator().apply(Constants.elevatorConstants.motionMagicConfigs);
        offMotor.setControl(new Follower(Constants.elevatorConstants.mainMotorID, true));

     }
 

     @Override
     public double getEncoderVal() {
         return mainMotor.getPosition().getValueAsDouble();
         
     }
     
     @Override
     public void periodic(){
        if (wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.elevator||wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.resting){
            goal=setpoint;
        }

        else{
            goal=Constants.elevatorConstants.maxHeight;
        } 
        SmartDashboard.putBoolean("reset switch", !resetSwitch.get());
        if (!resetSwitch.get()){
            mainMotor.setPosition(0);
        }
        SmartDashboard.putNumber("elevatorGoal", goal);
        SmartDashboard.putNumber("Elevator setpoint", setpoint);
        SmartDashboard.putNumber("elevatorPose", getEncoderVal());
        if (goal!=oldGoal){ 
            mainMotor.setControl(motionVoltage.withPosition(goal*Constants.elevatorConstants.encoderToMeters).withSlot(0));
        }
        oldGoal=goal;
         // set target position to 100 rotations
         //mainMotor.setControl(motionVoltage.withPosition(goal*Constants.elevatorConstants.encoderToMeters));
     }

 
 }
 