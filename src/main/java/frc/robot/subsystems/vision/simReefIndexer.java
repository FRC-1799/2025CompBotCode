package frc.robot.subsystems.vision;

import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeReefSimulation;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.networktables.BooleanArrayPublisher;
import edu.wpi.first.networktables.IntegerArrayPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class simReefIndexer implements reefIndexerInterface{

    IntegerArrayPublisher reefBranch = NetworkTableInstance.getDefault().getIntegerArrayTopic("branch 10 ").publish();
    int heartBeat=0;

    @Override
    public boolean[][] getFullReefState() {
        int[][]base = ReefscapeReefSimulation.getInstance().get().getBranches(DriverStation.Alliance.Blue);
        boolean[][]returnable = new boolean[12][4];
        int i = 0;
        
        for(int[] branch : base){
            int j = 0;
            for(int item: branch){
                returnable[i][j] = item!=0;
                j++;
            }
            i++;
        }
        i=0;
        for (int[] pole: base){
            SmartDashboard.putNumberArray(String.valueOf(i), new double[]{pole[0],pole[1],pole[2],pole[3]});
            i++;
        }
        heartBeat++;
        SmartDashboard.putNumber("reef heartbeat", heartBeat);
        
        //reefBranch.set(publishArr);
        return returnable;
    }

    public void periodic(){
        getFullReefState();
    }

    @Override
    public boolean getIsClosed(int row, int level) {
        SmartDashboard.putNumber("reef pole requested", row);
    
        return getFullReefState()[row][level];
    }

    @Override
    public int getHighestLevelForRow(int row) {
        if (!getIsClosed(row, 3)){
            return 4;
        }
        if (!getIsClosed(row, 2)){
            return 3;
        }
        if (!getIsClosed(row, 1)){
            return 2;
        }
        return 1;
    }

 


    @Override
    public boolean hasAlgea(int row, int level){
        return false;
    }

    @Override 
    public boolean[][] getAlgeaPosits(){
        return new boolean[2][6];
    }
    
}
