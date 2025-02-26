package frc.robot.subsystems.vision;

import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeReefSimulation;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class simReefIndexer extends reefIndexerIO{
     

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

        
        //reefBranch.set(publishArr);
        return returnable;
    }



    @Override
    public void resetSIMONLY(){
        ReefscapeReefSimulation.getInstance().get().clearReef();
    }


    @Override 
    public boolean[][] getAlgeaPosits(){
        return new boolean[2][6];
    }
    
}
