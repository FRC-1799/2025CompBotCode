package frc.robot.subsystems.wrist;


import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class wristIO extends SubsystemBase{

    protected double setpoint=0;
   
    /**
     * sets the setpoint the wrist should try and acheave 
     * @param setpoint the setpoint the wrist will try and acheave
    */
    public void setSetpoint(Rotation2d setpoint){
        this.setpoint=setpoint.getDegrees();
    }
    
    /**@return wether or not the wrist is within tolerence from its current setpoint */
    public boolean isAtSetpoint(){
        return Math.abs(setpoint-getCurrentLocation())<Constants.wristConstants.tolerence;
    }
    
    /**@return the current lcoation in degrees*/
    public double getCurrentLocation(){
        throw new Error("The method get current location was called on the wrist IO class, however this method must be overrided to be used");
    }

    /**@return the current location as a roation2d */
    public Rotation2d getCurrentLocationR2D(){
        return Rotation2d.fromDegrees(getCurrentLocation());
    }

    /**@return the current setpoint as a rotation2d */
    public Rotation2d getSetpoint(){
        return Rotation2d.fromDegrees(setpoint);
    }

    /**resets the wrist to starting configs */
    public void reset(){
        setpoint=0;
    }
    

    /**@return wether or not the wrist is at a position at which the elevator can safely move */
    public boolean atLegalNonControlState(){
        return Math.abs(getCurrentLocationR2D().getDegrees())<Constants.wristConstants.tolerence;
    }
}
