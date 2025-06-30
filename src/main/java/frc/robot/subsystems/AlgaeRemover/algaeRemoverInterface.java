package frc.robot.subsystems.AlgaeRemover;

import edu.wpi.first.wpilibj2.command.Subsystem;

public interface algaeRemoverInterface extends Subsystem{
    public boolean isRunning();
    public void startRunning();
    public void stopRunning();
    
}
