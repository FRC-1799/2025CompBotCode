package frc.robot.subsystems.CoralGUI;

import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class compassGUI {
    protected NetworkTableInstance inst = NetworkTableInstance.getDefault();
    protected NetworkTable table = inst.getTable("compass");
    protected IntegerSubscriber level = table.getIntegerTopic("level").subscribe(0);
    protected IntegerSubscriber pole = table.getIntegerTopic("Position").subscribe(0);
    protected IntegerSubscriber slot = table.getIntegerTopic("slot").subscribe(1);

    public int getLevel(){
        return (int)level.get()+1;
    }
    public int getPole(){
        // SmartDashboard.putNumber("Received auto algin reeding", (int)Math.ceil(pole.get()*2/3));
        // SmartDashboard.putNumber("ceil", Math.ceil((pole.get()*2/3.0)));
        return (int)Math.ceil(pole.get()*2/3.0);
    }

    public int getSlot(){
        return (int)slot.get();
    }
}
