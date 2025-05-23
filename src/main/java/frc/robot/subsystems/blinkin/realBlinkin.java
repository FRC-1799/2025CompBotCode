package frc.robot.subsystems.blinkin;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class realBlinkin  extends SubsystemBase implements blinkinInterface{
    protected Spark blinkin = new Spark(Constants.blinkinPort);  
    protected color ledColor = color.white;

    @Override
    public color getColor(){
        return ledColor;
    }

    @Override
    public void setColor(color color){
        this.ledColor=color;
    }

    @Override
    public void periodic(){
        SmartDashboard.putString("blinkinColor", ledColor.name);
        blinkin.set(ledColor.id);
    }
}
