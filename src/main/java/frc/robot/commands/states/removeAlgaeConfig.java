

package frc.robot.commands.states;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.FieldPosits.reefLevel.algeaRemoval;
import frc.robot.SystemManager;
import frc.robot.subsystems.generalManager;

public class removeAlgaeConfig extends Command {
    algeaRemoval level;

    public removeAlgaeConfig(algeaRemoval level){
        this.level=level;
        addRequirements(generalManager.subsystems);
    }

    /**initalizes the command */
    @Override
    public void initialize(){
        SystemManager.elevator.setSetpoint(level.getElevatorValue());
        SystemManager.wrist.setSetpoint(level.getWristValue());
        
    }


    /**@return true once the robot has entered the correct config */
    @Override
    public boolean isFinished(){
        return SystemManager.elevator.isAtSetpoint()&&SystemManager.wrist.isAtSetpoint();
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
