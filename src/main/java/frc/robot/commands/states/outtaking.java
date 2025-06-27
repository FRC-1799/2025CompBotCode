package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.generalManager;

public class outtaking extends Command{

    /**creates a command to manage the outtaking state*/
    public outtaking(){
        addRequirements(generalManager.subsystems);
    }

    /**initializes the command */
    @Override
    public void initialize(){
        if (!SystemManager.intake.hasPiece()){
            cancel();
        }
        
        SystemManager.intake.outtake();
    }


    /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute(){
        if (generalManager.getStateCommand()!=this){
            warningManager.throwAlert(warningManager.generalRoutineCalledManually);
            cancel();
        }
    }

    /**
     * returns true once the intake is empty
     */
    @Override 
    public boolean isFinished(){
        return !SystemManager.intake.hasPiece();
    }

    /**
     * command called when the command finishes
     * @param wasInterrupted wether or not the command was canceled
    */
    @Override 
    public void end(boolean wasInterrupted){
        SystemManager.intake.stop();
        generalManager.endCallback(wasInterrupted);      
    }
}
