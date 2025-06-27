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
	hasPieceState pieceState=hasPieceState.full;
	

	

	public realIntake() {
	}


	@Override
	public boolean hasPiece() {
		SmartDashboard.putBoolean("HasPieceChecked", true);
		return pieceState==hasPieceState.full;
	}
	
	public void checkForPiece(){

		//starting
		if (pieceState==hasPieceState.starting){
			if(!frontBeambrake.get()){
				pieceState=hasPieceState.full;
			}
			else{
				pieceState=hasPieceState.empty;
			}
		}

		//empty
		else if (pieceState==hasPieceState.empty){
			if (!backBeambrake.get()){
				pieceState=hasPieceState.intaking;
			}
		}

		//intaking
		else if(pieceState==hasPieceState.intaking){


			if (backBeambrake.get()){
				if (!frontBeambrake.get()){
					pieceState=hasPieceState.full;
				}
				else{
					pieceState=hasPieceState.empty;
				}

			}
		}

		//full
		else if(pieceState==hasPieceState.full){
			if(frontBeambrake.get()){
				pieceState=hasPieceState.empty;
			}
		}
	}







	@Override
	public void periodic(){
		checkForPiece();
		if (stopTrigger.getAsBoolean()){

			SmartDashboard.putBoolean("stopSucceeded", true);
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
		SmartDashboard.putString("hasPieceState", pieceState.name());
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