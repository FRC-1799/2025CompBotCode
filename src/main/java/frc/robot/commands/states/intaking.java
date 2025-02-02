package frc.robot.commands.states;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.subsystems.generalManager;


public class intaking extends Command{
    public boolean isInIntakingFase;

    public intaking(){
        addRequirements(generalManager.subsystems);
    }

    @Override
    public void initialize(){
        if (SystemManager.intake.hasPeice()){
            cancel();
        }
        isInIntakingFase=false;

        SystemManager.elevator.setSetpoint(Constants.elevatorConstants.intakePosit);
        SystemManager.wrist.setSetpoint(Constants.wristConstants.intakePosit);
    }

    @Override
    public void execute(){
        if (!isInIntakingFase && SystemManager.elevator.isAtSetpoint() && SystemManager.wrist.isAtSetpoint()){
            SystemManager.intake.intake();
            isInIntakingFase=true;
        }

        
        
    }

    @Override
    public boolean isFinished(){
        return SystemManager.intake.hasPeice();
    }

    @Override
    public void end(boolean wasInterupted){
        generalManager.endCallback(wasInterupted);
        SystemManager.intake.stop();
    }
}
