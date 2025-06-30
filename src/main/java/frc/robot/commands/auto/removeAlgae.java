package frc.robot.commands.auto;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.FieldPosits.reefLevel.algeaRemoval;
import frc.robot.Utils.utillFunctions;
import frc.robot.subsystems.generalManager;


public class removeAlgae extends Command{
    algeaRemoval posit;
    boolean mechIsFinished=false;
    Command driveCommand;
    Command mechCommand;
    boolean hasSpit;
    boolean driveIsFinished;


    /**
     * scores a peice at the defined scoring posit
     * @param posit the posit to score
     */
    public removeAlgae(algeaRemoval posit){
        this.posit=posit;
        
    }


    /**initalizes the command */
    @Override
    public void initialize(){
        //sets the mechs to the proper score state
        generalManager.algaeConfig(posit.isLow);

        //starts auto drive
        driveCommand=SystemManager.swerve.driveToPose(posit.getPose());
        driveCommand.schedule();

        //gets the mech command and sets the proper mech callback
        mechCommand=generalManager.getStateCommand();
        generalManager.setExternalEndCallback(this::mechIsFinishedCall);
        

        //reinitalizes state booleans used
        mechIsFinished=false;
        hasSpit=false;
        driveIsFinished=false;
    }

    
     /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute() {
        //restarts the drive command if it finished early
        if (!driveCommand.isScheduled()){
            if (utillFunctions.pythagorean(SystemManager.getSwervePose().getX(), posit.getPose().getX(), SystemManager.getSwervePose().getY(), posit.getPose().getY())
                >=Constants.AutonConstants.autoDriveScoreTolerence){
                if (
                    utillFunctions.pythagorean(SystemManager.getSwervePose().getX(), posit.getPose().getX(),
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


        //starts outtake if relevent
        if (mechIsFinished&&driveIsFinished){
            generalManager.algaeRemove();
            generalManager.setExternalEndCallback(this::intakeIsFinishedCall);
        }

        //debug info
    }
   
     /**
     * function to be called when the mech is in its proper state
     * @param wasInterupted wether or not the intake routine was cancled
     */
    public void mechIsFinishedCall(boolean wasInterupted){
        if (wasInterupted){
            cancel();
        }

        mechIsFinished=true;
    }

    /**
     * function to be called when the outtake has happened
     * @param wasInterupted wether or not the intake routine was cancled
     */
    public void intakeIsFinishedCall(boolean wasInterupted){
        hasSpit=true;
    }

    /**
     * @return true once the peice has been outtaked
     */
    @Override
    public boolean isFinished(){
        return hasSpit;
    }
    

    /**
     * command called when the command finishes
     * @param wasInterupted wether or not the command was cancled
    */
    @Override
    public void end(boolean wasInterupted){
        if (driveCommand.isScheduled()){
            driveCommand.cancel();
            
        }
        if (!wasInterupted){
            SystemManager.reefIndexer.freeAlgea(posit.getRow(), posit.getLevel());
        }

        
    }   
}
