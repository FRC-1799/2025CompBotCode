package frc.robot.subsystems.intake;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SystemManager;

public class intakeIO extends SubsystemBase{

    public enum hasPeiceState{
		intaking,
		full,
		empty,
		starting
	}

    public static enum intakeState{
        intaking,
        outtaking,
        resting;
    }

    protected intakeState state;
    BooleanSupplier stopTrigger=()->{return false;};

    /**@return wether or not the intake currently contains a peice.*/
    public boolean hasPeice(){throw new Error("The method hasPeice should have been implemented in a subclass but was not");}

    /**sets the intake state to intaking untill a peice is intaked */
    public void intake(){
        intakeUntil(()->this.hasPeice());
    }

    /**
     * sets the intake state to intake untill trigger returns true 
     * @param trigger the supplier that will stop the intake when it returns true
    */
    public void intakeUntil(BooleanSupplier trigger){
        state=intakeState.intaking;
		stopTrigger=trigger;
    }

    /**sets the intake state to outtake until the peice is outtaked*/
    public void outtake(){
        outtakeUntil(()->!this.hasPeice());
    };

    /**
     * outtakes untill the trigger returns true
     * @param trigger the supplier that will stop the intake when it returns true
     */
    public void outtakeUntil(BooleanSupplier trigger){
        state=intakeState.outtaking;
        stopTrigger=trigger;
    }

    /**stops the intake */
    public void stop(){throw new Error("The method stop should have been implemented in a subclass but was not");}

    /**resets the intake */
    public void reset(){
        stop();
		state = intakeState.resting;
    }

    /**@return the state of the intake in the form of a intakeState */
    public intakeState getState(){
        return state;
    }

    /**gets a translation that represents the change from the 0,0 of the intake the point a coral would be */
    public Translation3d getTranslation(){
        
        Rotation2d rotation = SystemManager.wrist.getCurrentLocationR2D();
        return new Translation3d(
            Math.sin(-rotation.getRadians()+Math.toRadians(20))*Constants.intakeConstants.coralFromWristLen+Constants.intakeConstants.coralLenght/2,
            0,
            Math.cos(-rotation.getRadians()+Math.toRadians(20))*Constants.intakeConstants.coralFromWristLen)
            
            .plus(SystemManager.elevator.getTranslation());    
    }
   
} 
