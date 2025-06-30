package frc.robot.subsystems.AlgaeRemover;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class realAlgaeRemover extends SubsystemBase implements algaeRemoverInterface {
    boolean isRunning=false;
    Timer runningTimer = new Timer();
   
    protected SparkFlex motor = new SparkFlex(Constants.algaeRemoverConstants.motorID, MotorType.kBrushless);
    
    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void startRunning() {
        runningTimer.reset();
        runningTimer.start();
        isRunning=true;
        
    }

    @Override
    public void stopRunning() {
        isRunning=false;
    }

    @Override
    public void periodic(){
        if (isRunning&&runningTimer.get()>Constants.algaeRemoverConstants.algeaTimerVal){
            runningTimer.stop();
            isRunning=false;
        }

        if (isRunning){
            motor.set(Constants.algaeRemoverConstants.motorSpeed);
        }
        else{
            motor.set(0);
        }
        SmartDashboard.putBoolean("algaeRemoverIsRunning", isRunning);
    }
    
}
