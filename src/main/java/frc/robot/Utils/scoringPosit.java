package frc.robot.Utils;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.FieldPosits.reefLevel;
import frc.robot.FieldPosits.reefPole;

public class scoringPosit {

    public reefLevel level;
    public reefPole pole;

    /**
     * Creates a scoring posit object with the specified pole and level
     * @param level the level to be used in the scoring posit
     * @param pole the pole to be used in the scoring posit
     */
    public scoringPosit(reefLevel level, reefPole pole){
        this.pole=pole;
        this.level=level;

    }

    /**@return the pose that should be used to score this particular posit*/
    public Pose2d getScorePose(){
        return pole.getScorePosit().plus(new Transform2d(level.getTranslation(), new Rotation2d()));
    }

    /**
     * @return the current worth of this item. 
     *While this will adjust for auto it may be slightly incosistent for routes that start and the end of auto since those routes will likly score after auto ends and telliop starts 
    */
    public int getPointValForItem() {
       return getPointValForItem(level.getasInt());
    }

    /**
     * gets the current point value of the specified level
     * * While this will adjust for auto it may be slightly incosistent for routes that start and the end of auto since those routes will likly score after auto ends and telliop starts 
     * @param level the level to calculate the score for
     * @return the point value of scoring at the given position
    */
    public static int getPointValForItem(int level) {
        if (DriverStation.isAutonomous()){
            switch (level){
                case 4: return 7;
                case 3: return 6;
                case 2: return 4;
                case 1: return 3;
            }
        }
        else{
            switch(level){
                case 4: return 5;
                case 3: return 4;
                case 2: return 3;
                case 1: return 2;
            }
        }
        return 0;
    } 




}
