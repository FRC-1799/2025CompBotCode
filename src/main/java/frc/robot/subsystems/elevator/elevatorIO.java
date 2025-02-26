package frc.robot.subsystems.elevator;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.SystemManager;

public class elevatorIO extends SubsystemBase {

    protected double setpoint=Constants.elevatorConstants.startingPosit;

    protected Mechanism2d mech = new Mechanism2d(0,0);//Constants.elevatorConstants.fromRobotCenter.getX(), Constants.elevatorConstants.fromRobotCenter.getZ());//0.86, 1.75);
    protected MechanismRoot2d root = mech.getRoot("elevatorRoot", Constants.elevatorConstants.fromRobotCenter.getX(), Constants.elevatorConstants.fromRobotCenter.getY());
    protected MechanismLigament2d elevator = new MechanismLigament2d("elevator main", Constants.elevatorConstants.compressedLen, Constants.elevatorConstants.angle.getDegrees());
    protected MechanismLigament2d wrist;

    public elevatorIO(){
        root.append(elevator);
        wrist = elevator.append(new MechanismLigament2d("wrist ligament", Constants.intakeConstants.intakeLength, 0, 10, new Color8Bit(0,0,365)));
        SmartDashboard.putData("elevator", mech);
    }

    /**
     * sets the setpoint of the elevator in raw encoder units. Use setSetpoint instead
     * @param setpoint the setpoint to set in raw encoder units.
     */
    public void setSetpointRaw(double setpoint){
        this.setpoint=setpoint;
    };

    /**
     * sets the setpoint of the elevator in meters
     * @param setpoint the height of the elevator in meters
     */
    public void setSetpoint(double setpoint){
        setSetpointRaw(setpoint*Constants.elevatorConstants.encoderToMeters);
    };

    /**@return wether or not the elevator is within tolerence of its setpoint*/
    public boolean isAtSetpoint(){
        return Math.abs(setpoint-getEncoderVal())<Constants.elevatorConstants.tolerence;
    };

    /**@return the 3d translation from the botom of the elevator to the current point. all mesurments use the rotation point of the wrist for consistency*/
    public Translation3d getTranslation(){
        return new Translation3d(getHeight()*Math.cos(Constants.elevatorConstants.angle.getRadians()), 0, getHeight()*Math.sin(Constants.elevatorConstants.angle.getRadians())).plus(Constants.elevatorConstants.fromRobotCenter);
    };

    /**@return returns the internal encoder value of the elevator encoder. use getHeight instead*/
    public double getEncoderVal(){throw new Error("This method must be overriden in a child method");}

    /**@return the height of the elevator in meters. all measurments use the rotation point of the wrist for consistency*/
    public double getHeight(){
        return getEncoderVal()/Constants.elevatorConstants.encoderToMeters;
    };

    /**resets the elevator to its starting config */
    public void reset(){
        setpoint=0;
    };


    /**@return true if the elevator is at a point in which the wrist can move without breaking anything */
    public boolean atLegalNonControlState(){
        return Math.abs(getHeight()-Constants.elevatorConstants.maxHeight)<Constants.elevatorConstants.tolerence;
    }

    /**updates the internal mechanism  */
    public void updateRender(){
        elevator.setLength(getHeight()+Constants.elevatorConstants.fromRobotCenter.getZ());
        wrist.setAngle(SystemManager.wrist.getCurrentLocationR2D().getDegrees());
        SmartDashboard.putNumber("wristVal", SystemManager.wrist.getCurrentLocationR2D().getDegrees());
    }
}
