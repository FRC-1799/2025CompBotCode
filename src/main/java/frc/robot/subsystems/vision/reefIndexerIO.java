package frc.robot.subsystems.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class reefIndexerIO extends SubsystemBase{

    /**@return a 2d list of booleans where each outer list represents a pole of the reef and each inner loop represents the nodes on that pole starting in the tought with id 0 and ending at l4 with id 3*/
    public boolean[][] getFullReefState(){
        throw new Error("getFullReefState was called on the reefIndexerIO class. however this function must be overrided to function");
    }

    /**
     * @param row the row or reef collom to check. is 0 indexed
     * @param level the level to be checked. starts at l1 and is 0 indexed meaning that the level inputed will be 1 smaller than the standard level name
     * @return wether or not the specified node is open 
     * */
    public boolean getIsClosed(int row, int level){
        return !getFullReefState()[row][level];
    }

    /**
     * @param row the row or reef collom to check. is 0 indexed
     * @returns the heighest available level for the row given */
    public int getHighestLevelForRow(int row){
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
    
    /**
     * @param row one of the 6 positions inbetween two reef colloms in which an algea can be placed,
     * @param level wether the algea is on the lower level(id 0 in between l2 and l3) or on the higher level (level one in between l3 and l4)
     * @return wether or not the specified posit has algea
     */
    public boolean hasAlgea(int row, int level){
        return !getAlgeaPosits()[level][row];
    }

    /**
     * returns a 2d list of booleans containing all possible algea positions and describing which ones actualy contain algea
     * Outer list: One of the 6 positions inbetween two reef colloms in which an algea can be placed,
     * Inner list: Wether the algea is on the lower level(id 0 in between l2 and l3) or on the higher level (level one in between l3 and l4)
     * 
     */
    public boolean[][] getAlgeaPosits(){
        throw new Error("getAlgaePosits was called on the reefIndexer class. however this function must be overrided to function");
    }


    /**
     * Resets the reef to its starting position. SHOULD ONLY BE USED IN SIM FOR DEBUGING PERPOUSES
     * @throws error if called on a real vision system.
     */
    public void resetSIMONLY(){
        throw new Error("This function is only allowed on simulated robots and should only be used for debugging reasons");
    }
    
}
