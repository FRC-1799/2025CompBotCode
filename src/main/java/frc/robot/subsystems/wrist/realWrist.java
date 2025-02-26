package frc.robot.subsystems.wrist;

import com.ctre.phoenix6.hardware.core.CoreCANcoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants;
import frc.robot.subsystems.wristElevatorControlManager;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;


public class realWrist extends wristIO{


    
    private double goal;

    protected CoreCANcoder wristEncoder = new CoreCANcoder(Constants.wristConstants.CANCoderID);
    protected SparkFlex wristMotor = new SparkFlex(Constants.wristConstants.motorID, MotorType.kBrushless);
    protected PIDController wristPID = new PIDController(Constants.wristConstants.wristPID.kP, Constants.wristConstants.wristPID.kI, Constants.wristConstants.wristPID.kD);


    public realWrist(){
        wristPID.setTolerance(Constants.wristConstants.tolerence);
    }

    @Override
    public void periodic(){

        if (wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.wrist||
            wristElevatorControlManager.getState()==wristElevatorControlManager.wristElevatorControllState.resting){     
            goal=setpoint;
        }

        else{
            goal=Constants.wristConstants.restingPosit.getDegrees();
        }

        wristPID.setSetpoint(goal);
        wristMotor.set(wristPID.calculate(getCurrentLocation()));
    }

    @Override
    public double getCurrentLocation() {
        return (wristEncoder.getAbsolutePosition().getValue().in(edu.wpi.first.units.Units.Degrees)+Constants.wristConstants.CANCoderOffset)%360;
    }

}
