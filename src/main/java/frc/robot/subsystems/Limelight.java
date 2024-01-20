package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.PrintCommand;

public class Limelight extends SubsystemBase{

//post to smart dashboard periodically
    public Limelight(){

    }
   @Override
   public void periodic(){
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    
//read values periodically
    

    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);

    // SmartDashboard.putNumber("blueX", table.getEntry("botpose_wpilib."));
   }



}

class Cords {

    double X;
    double Y;
    double Z;

    public Cords(double X, double Y, double Z){
        this.X=X;
        this.Y=Y;
        this.Z=Z;
    }
}
