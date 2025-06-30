package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.Utils.warningManager;
import frc.robot.subsystems.generalManager;

public class resting extends Command{

    /**creates a command that does nothing but hold the space a state would so that other commands can not sneak into the mechs */
    public resting(){
        addRequirements(generalManager.subsystems);
    }

    @Override
    public void initialize(){
        SystemManager.intake.stop();
    }

    /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute(){
        if (generalManager.getStateCommand()!=this){
            warningManager.throwAlert(warningManager.generalRoutineCalledManualy);
            cancel();
        }
    }

    /**
     * command called when the command finishes
     * @param wasInterupted wether or not the command was cancled
    */
    @Override
    public void end(boolean wasInterupted){
        generalManager.endCallback(wasInterupted);
    }
}
