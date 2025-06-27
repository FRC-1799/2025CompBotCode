package frc.robot.commands.auto;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.FieldPosits.reefLevel.algaeRemovalEnum;
import frc.robot.Utils.utilFunctions;
import frc.robot.subsystems.generalManager;


public class removeAlgae extends Command{
    algaeRemovalEnum posit;
    boolean mechIsFinished=false;
    Command driveCommand;
    Command mechCommand;
    boolean hasSpit;
    boolean driveIsFinished;


    /**
     * scores a piece at the defined scoring posit
     * @param posit the posit to score
     */
    public removeAlgae(algaeRemovalEnum posit){
        this.posit=posit;
        
    }


    /**initializes the command */
    @Override
    public void initialize(){
        //sets the mechanisms to the proper score state
        generalManager.algaeConfig(posit.isLow);

        //starts auto drive
        driveCommand=SystemManager.swerve.driveToPose(posit.getPose());
        driveCommand.schedule();

        //gets the mech command and sets the proper mech callback
        mechCommand=generalManager.getStateCommand();
        generalManager.setExternalEndCallback(this::mechIsFinishedCall);
        

        //reinitializes state booleans used
        mechIsFinished=false;
        hasSpit=false;
        driveIsFinished=false;
    }

    
     /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute() {
        //restarts the drive command if it finished early
        if (!driveCommand.isScheduled()){
            if (utilFunctions.pythagorean(SystemManager.getSwervePose().getX(), posit.getPose().getX(), SystemManager.getSwervePose().getY(), posit.getPose().getY())
                >=Constants.AutonConstants.autoDriveScoreTolerance){
                if (
                    utilFunctions.pythagorean(SystemManager.getSwervePose().getX(), posit.getPose().getX(),
                    SystemManager.getSwervePose().getY(), posit.getPose().getY())
                    <=Constants.AutonConstants.distanceWithinPathplannerDontWork){

                    driveCommand= new smallAutoDrive(posit.getPose());
                }
                driveCommand.schedule();
            }
            else{
                driveIsFinished=true;
            }
        }


        //starts outtake if relevant
        if (mechIsFinished&&driveIsFinished){
            generalManager.algaeRemove();
            generalManager.setExternalEndCallback(this::intakeIsFinishedCall);
        }

        //debug info
    }
   
     /**
     * function to be called when the mech is in its proper state
     * @param wasInterrupted wether or not the intake routine was canceled
     */
    public void mechIsFinishedCall(boolean wasInterrupted){
        if (wasInterrupted){
            cancel();
        }

        mechIsFinished=true;
    }

    /**
     * function to be called when the outtake has happened
     * @param wasInterrupted wether or not the intake routine was canceled
     */
    public void intakeIsFinishedCall(boolean wasInterrupted){
        hasSpit=true;
    }

    /**
     * @return true once the piece has been outtaked
     */
    @Override
    public boolean isFinished(){
        return hasSpit;
    }
    

    /**
     * command called when the command finishes
     * @param wasInterrupted wether or not the command was canceled
    */
    @Override
    public void end(boolean wasInterrupted){
        if (driveCommand.isScheduled()){
            driveCommand.cancel();
            
        }
        if (!wasInterrupted){
            SystemManager.reefIndexer.freeAlgae(posit.getRow(), posit.getLevel());
        }

        
    }   
}
