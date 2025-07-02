package frc.robot.subsystems.lidar;

import java.util.List;
import java.util.function.BooleanSupplier;

import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Translation2d;

public interface lidarInterface {

    /**@return a list of pairs of translations where the translations represent the corners of the the individual obstacles seen by the lidar */
    public List<Pair<Translation2d, Translation2d>> fetchObstacles();

    /**@return wether or not a valid connection to the lidar has been made */
    public boolean isConnected();

    /**
     * sets a killSwitch to be used by the lidar. when the kill switch is true the fetchObstacles command will return a blank list
     * @param killSwitchSupplier a boolean supplier that provides wether or not the lidar lib should provide data
     */
    public void setKillSwitchSupplier(BooleanSupplier killSwitchSupplier);

    /**@return true if the lidar module is alive and providing data*/
    public boolean isAlive();
}
