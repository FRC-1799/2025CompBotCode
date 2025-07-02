package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.subsystems.generalManager;

public class removeAlgaeAct extends Command{
    public removeAlgaeAct(){
        addRequirements(generalManager.subsystems);
    }

    @Override
    public void initialize(){
        SystemManager.algaeRemover.startRunning();
    }

    @Override
    public boolean isFinished(){
        return !SystemManager.algaeRemover.isRunning();
    }

    @Override
    public void end(boolean wasInterrupted){
        generalManager.endCallback(wasInterrupted);
    }
}
