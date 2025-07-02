package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.units.LinearVelocityUnit;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.SystemManager;
import frc.robot.Utils.utilFunctions;
import frc.robot.Utils.warningManager;
import frc.robot.commands.sim.CreateCoral;
import frc.robot.subsystems.generalManager;

public class IntakePieceCommand extends Command{
    Pose2d intakePose;
    boolean mechIsFinished=false;
    boolean driveIsFinished=false;
    boolean coralHasBeenSpawned=false;
    Command driveCommand;
    Command mechCommand;


    /**
     * initialize a command to intake a piece
     * @param intakePose the pose for the robot to intake from
     */
    public IntakePieceCommand(Pose2d intakePose){
        this.intakePose=intakePose;
        //addRequirements(SystemManager.swerve);
    }

    /**initializes the command */
    @Override
    public void initialize(){
        generalManager.intake();
        driveCommand=SystemManager.swerve.driveToPose(intakePose, LinearVelocity.ofBaseUnits(Constants.AutonConstants.collisionSpeed, LinearVelocityUnit.combine(Units.Meters, Units.Second)));
        driveCommand.schedule();
        mechCommand=generalManager.getStateCommand();
        generalManager.setExternalEndCallback(this::mechIsFinishedCall);
        mechIsFinished=false;
        driveIsFinished=false;
        coralHasBeenSpawned=false;
        
    }

    /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute() {
        //restarts the drive command if it finished early
        SmartDashboard.putBoolean("Drive is finished", driveIsFinished);
        SmartDashboard.putBoolean("coralHasBeenSpawned", coralHasBeenSpawned);
        if (!driveCommand.isScheduled()){
            if (utilFunctions.pythagorean(SystemManager.getSwervePose().getX(), intakePose.getX(), SystemManager.getSwervePose().getY(), intakePose.getY())
                >=Constants.AutonConstants.autoDriveIntakeTolerance){
                if (
                    utilFunctions.pythagorean(SystemManager.getSwervePose().getX(), intakePose.getX(),
                    SystemManager.getSwervePose().getY(), intakePose.getY())
                    <=Constants.AutonConstants.distanceWithinPathplannerDontWork){

                    driveCommand= new smallAutoDrive(intakePose);
                }
                driveCommand.schedule();
            }
            else{
                driveIsFinished=true;
            }
        }


        //spawns a coral if the robot is simulated and the time is appropriate
        if (driveIsFinished&&Constants.simConfigs.intakeShouldBeSim&&!coralHasBeenSpawned){
            coralHasBeenSpawned=true;
            new WaitCommand(Constants.AutonConstants.humanPlayerBeingBad).andThen(new CreateCoral(intakePose.plus(Constants.AutonConstants.intakeCoralOffset))).schedule();
    }

    }


    /**
     * function to be called when the mech is in its proper state
     * @param wasInterrupted wether or not the intake routine was canceled
     */
    public void mechIsFinishedCall(boolean wasInterrupted){
        if (wasInterrupted){
            cancel();
            warningManager.throwAlert(warningManager.autoInternalCanceled);
        }
        mechIsFinished=true;

    }

    /**
     * @return true once a piece has been acquired
     */
    @Override
    public boolean isFinished(){
        return SystemManager.intake.hasPiece();
    }


    /**
     * command called when the command finishes
     * @param wasInterrupted wether or not the command was canceled
    */
    @Override
    public void end(boolean wasInterrupted){
        SmartDashboard.putBoolean("auto intake is running", false);
        if (driveCommand.isScheduled()){
            driveCommand.cancel();
        }
    }
}   
