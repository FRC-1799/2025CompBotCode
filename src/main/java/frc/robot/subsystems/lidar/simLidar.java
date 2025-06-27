package frc.robot.subsystems.lidar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.SystemManager;

public class simLidar implements lidarInterface{

    BooleanSupplier killSwitchSupplier = ()->false;
    

    @Override
    public List<Pair<Translation2d, Translation2d>> fetchObstacles() {
        List<Pair<Translation2d, Translation2d>> obstacles = new ArrayList<>();

        if(!(isAlive()&&isConnected())){
            return obstacles;
        }

        obstacles.add(SystemManager.fakeBot.getHitbox());

        // for (Pair<Translation2d, Translation2d> pair:fakeBotHitBoxes){
        //     obstacles.add(pair);
        // }
        


        return obstacles;
    }

    @Override
    public boolean isConnected() {
        return SystemManager.fakeBot!=null;
    }

    @Override
    public void setKillSwitchSupplier(BooleanSupplier killSwitchSupplier) {
        this.killSwitchSupplier=killSwitchSupplier;
    }

    @Override
    public boolean isAlive() {
        return !killSwitchSupplier.getAsBoolean();
    }
    
}
