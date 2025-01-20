package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SystemManager;
import frc.robot.Utils.utillFunctions;
import frc.robot.commands.auto.IntakePeiceCommand;
import frc.robot.commands.auto.ScorePiece;

public class autoManager{

    public static boolean hasControl=false;
    public static Command currentRoutine=null;
    public static void autoManagerInit(){

    }

    
 
    public static void periodic(){

    }

    public static Command getAutoAction(){
        if (SystemManager.intake.hasPeice()){
            return new ScorePiece(utillFunctions.getBestScorePosit());
        }
        else{
            return new IntakePeiceCommand(utillFunctions.getBestIntakePosit());
        }

    }

    public static void resetAutoAction(){
        getAutoAction().schedule();
    }
}
