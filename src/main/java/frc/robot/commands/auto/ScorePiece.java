package frc.robot.commands.auto;



import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Utils.scoringPosit;
import frc.robot.Utils.utillFunctions;
import frc.robot.subsystems.autoManager;
import frc.robot.subsystems.generalManager;


public class ScorePiece extends Command{
    scoringPosit posit;
    boolean mechIsFinished=false;
    Command driveCommand;
    Command mechCommand;
    boolean hasSpit;
    boolean driveIsFinished;


    /**
     * scores a peice at the defined scoring posit
     * @param posit the posit to score
     */
    public ScorePiece(scoringPosit posit){
        this.posit=posit;
        
    }


    /**initalizes the command */
    @Override
    public void initialize(){
        //sets the mechs to the proper score state
        generalManager.scoreAt(posit.level.getasInt());

        //starts auto drive
        driveCommand=SystemManager.swerve.driveToPose(posit.getScorePose());
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
            if (utillFunctions.pythagorean(SystemManager.getSwervePose().getX(), posit.getScorePose().getX(), SystemManager.getSwervePose().getY(), posit.getScorePose().getY())
                >=Constants.AutonConstants.autoDriveScoreTolerence){
                if (
                    utillFunctions.pythagorean(SystemManager.getSwervePose().getX(), posit.getScorePose().getX(),
                    SystemManager.getSwervePose().getY(), posit.getScorePose().getY())
                    <=Constants.AutonConstants.distanceWithinPathplannerDontWork){

                    driveCommand= new smallAutoDrive(posit.getScorePose());
                }
                driveCommand.schedule();
            }
            else{
                driveIsFinished=true;
            }
        }


        //starts outtake if relevent
        if (mechIsFinished&&driveIsFinished){
            generalManager.outtake();
            generalManager.setExternalEndCallback(this::intakeIsFinishedCall);
        }

        //debug info
        SmartDashboard.putNumber("reef pole", posit.pole.getRowAsIndex());
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
            autoManager.cycleCount++;
            autoManager.score+=posit.getPointValForItem();
        }
        
    }   
}
