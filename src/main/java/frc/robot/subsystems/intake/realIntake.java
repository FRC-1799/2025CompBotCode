package frc.robot.subsystems.intake;
import java.util.function.BooleanSupplier;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SystemManager;

public class realIntake extends intakeIO{ 

	
	SparkMax intakeTop = new SparkMax(Constants.intakeConstants.LeftIntake, MotorType.kBrushless);
	SparkMax intakeBottom = new SparkMax(Constants.intakeConstants.RightIntake, MotorType.kBrushless);
	DigitalInput frontBeambrake = new DigitalInput(Constants.intakeConstants.frontBeamBrakePort);
	DigitalInput backBeambrake = new DigitalInput(Constants.intakeConstants.backBeamBrakePort);
	hasPeiceState peiceState=hasPeiceState.full;



	

	public realIntake() {
		intakeBottom.setInverted(true);
		intakeTop.setInverted(false);
	}

	

	@Override
	public boolean hasPeice() {
		return peiceState==hasPeiceState.full;
	}
	
	public void checkForPiece(){

		//starting
		if (peiceState==hasPeiceState.starting){
			if(frontBeambrake.get()){
				peiceState=hasPeiceState.full;
			}
			else{
				peiceState=hasPeiceState.empty;
			}
		}

		//empty
		else if (peiceState==hasPeiceState.empty){
			if (backBeambrake.get()){
				peiceState=hasPeiceState.intaking;
			}
		}

		//intakeing
		else if(peiceState==hasPeiceState.intaking){
			if (!backBeambrake.get()){
				if (frontBeambrake.get()){
					peiceState=hasPeiceState.full;
				}
				else{
					peiceState=hasPeiceState.empty;
				}
			}
		}

		//full
		else if(peiceState==hasPeiceState.full){
			if(!frontBeambrake.get()){
				peiceState=hasPeiceState.empty;
			}
		}
	}







	@Override
	public void periodic(){
		if (stopTrigger.getAsBoolean()){
		    stop();					   // Stop Intake Motor
		}
		if (state == intakeState.intaking){
		    intakeTop.set(Constants.intakeConstants.intakeSpeed); // Start Intake Motor
			intakeBottom.set(Constants.intakeConstants.intakeSpeed);
		}
		else if (state == intakeState.outtaking){
		    intakeTop.set(Constants.intakeConstants.outtakeSpeed); // Start OutTake Motor
			intakeBottom.set(Constants.intakeConstants.outtakeSpeed);
		}
		else{
			intakeTop.set(0);
			intakeBottom.set(0);
		}
	}
	
	@Override
	public void stop() {
		intakeTop.stopMotor();
	}



	@Override
	public intakeState getState() {
		return state;
	}


}