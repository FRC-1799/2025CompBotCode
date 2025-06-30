package frc.robot.subsystems.AlgaeRemover;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class simAlgaeRemover extends SubsystemBase implements algaeRemoverInterface {
    boolean isRunning=false;
    Timer runningTimer = new Timer();

    
    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void startRunning() {
        runningTimer.reset();
        runningTimer.start();
        
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
        SmartDashboard.putBoolean("algaeRemoverIsSim", true);
    }
    
}
