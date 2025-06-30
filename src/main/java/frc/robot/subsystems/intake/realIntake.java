package frc.robot.subsystems.intake;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.SystemManager;

public class realIntake extends intakeIO{ 

	
	//SparkMax intakeTop = new SparkMax(Constants.intakeConstants.topIntake, MotorType.kBrushless);
	SparkMax intakeBottom = new SparkMax(Constants.intakeConstants.bottomIntake, MotorType.kBrushless);
	DigitalInput frontBeambrake = new DigitalInput(Constants.intakeConstants.frontBeamBrakePort);
	DigitalInput backBeambrake = new DigitalInput(Constants.intakeConstants.backBeamBrakePort);
	hasPeiceState peiceState=hasPeiceState.full;
	

	

	public realIntake() {
	}

	// @Override
	// public void backRun(){
	// 	this.state=intakeState.backRun;
	// 	//this.stopTrigger=()->fron
	// }

	@Override
	public boolean hasPeice() {
		SmartDashboard.putBoolean("HasPeiceChecked", true);
		return peiceState==hasPeiceState.full;
	}
	
	public void checkForPiece(){

		//starting
		if (peiceState==hasPeiceState.starting){
			if(!frontBeambrake.get()){
				peiceState=hasPeiceState.full;
			}
			else{
				peiceState=hasPeiceState.empty;
			}
		}

		//empty
		else if (peiceState==hasPeiceState.empty){
			if (!backBeambrake.get()){
				peiceState=hasPeiceState.intaking;
			}
		}

		//intakeing
		else if(peiceState==hasPeiceState.intaking){


			if (backBeambrake.get()){
				if (!frontBeambrake.get()){
					peiceState=hasPeiceState.full;
				}
				else{
					peiceState=hasPeiceState.empty;
				}

			}
		}

		//full
		else if(peiceState==hasPeiceState.full){
			if(frontBeambrake.get()){
				peiceState=hasPeiceState.empty;
			}
		}
	}







	@Override
	public void periodic(){
		checkForPiece();
		if (stopTrigger.getAsBoolean()){

			SmartDashboard.putBoolean("stopSucceded", true);
			state=intakeState.resting;
		    stop();					   // Stop Intake Motor
		}


		if (state == intakeState.intaking){
		    //intakeTop.set(Constants.intakeConstants.intakeSpeed); // Start Intake Motor
			intakeBottom.set(Constants.intakeConstants.intakeSpeed);
		}
		else if (state == intakeState.outtaking){
		    //intakeTop.set(Constants.intakeConstants.outtakeSpeed); // Start OutTake Motor
			if (SystemManager.elevator.isAtTop()){
				intakeBottom.set(-Constants.intakeConstants.outtakeSpeed);
			}
			
			else{
				intakeBottom.set(Constants.intakeConstants.outtakeSpeed);
			}
		}
		else{
			//intakeTop.set(0);
			intakeBottom.set(0);
		}
		SmartDashboard.putString("intakeState", state.name());
		SmartDashboard.putString("hasPeiceState", peiceState.name());
		SmartDashboard.putBoolean("frontBeamBreak", frontBeambrake.get());
		SmartDashboard.putBoolean("backBeamBreak", backBeambrake.get());
	}
	
	@Override
	public void stop() {
		intakeBottom.stopMotor();
		//intakeTop.stopMotor();
	}



	@Override
	public intakeState getState() {
		return state;
	}


}