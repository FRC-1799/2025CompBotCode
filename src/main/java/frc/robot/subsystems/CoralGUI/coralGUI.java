package frc.robot.subsystems.CoralGUI;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.BooleanArraySubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.PubSubOption;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class coralGUI extends SubsystemBase {
    
    //private String[][] coralArray = new String[4][12];  // Array to hold the Coral data for 4 floors and 12 columns

    //NetworkTableInstance inst;
    //NetworkTable coralF1;
    //NetworkTable coralF2; 
    //NetworkTable coralF3;
    //NetworkTable coralF4;
    NetworkTable table = NetworkTableInstance.getDefault().getTable("GridData");
    BooleanArraySubscriber l1 = table.getBooleanArrayTopic("CoralF1").subscribe(null,  PubSubOption.keepDuplicates(true));

    public coralGUI() {
        // Initialize the network table instance
        // inst = NetworkTableInstance.getDefault();
        
        // // Retrieve the network tables for each floor
        // coralF1 = inst.getTable("GridData").getSubTable("CoralF1");
        // coralF2 = inst.getTable("GridData").getSubTable("CoralF2");
        // coralF3 = inst.getTable("GridData").getSubTable("CoralF3");
        // coralF4 = inst.getTable("GridData").getSubTable("CoralF4");

        // Update the coralArray with data from the network tables
        
    }

    public boolean[][] getGUIArray(){
        boolean[][]coralArray = new boolean [4][12];
        // for (int i = 0; i < 12; i++) {
        //     coralArray[3][i] = l1.get(); // Floor 4 (top)
        //     coralArray[2][i] = coralF3.getEntry(Integer.toString(i)).getString("0"); // Floor 3
        //     coralArray[1][i] = coralF2.getEntry(Integer.toString(i)).getString("0"); // Floor 2
        //     coralArray[0][i] = coralF1.getEntry(Integer.toString(i)).getString("0"); // Floor 1 (bottom)
        // }
        coralArray[0]=l1.get();
        return coralArray;
    }

    // Function to check if a specific floor and column contains a 1
    public boolean checkCoralValue(int floor, int column) {
        if (floor < 1 || floor > 4 || column < 1 || column > 12) {
            throw new IllegalArgumentException("Floor must be between 1 and 4, Column must be between 1 and 12.");
        }
        // Convert floor to array index (floor 1 = index 0, floor 4 = index 3)
        int floorIndex = 4 - floor;
        // Check if the value is 1
        return getGUIArray()[floorIndex][column - 1];
    }
}
