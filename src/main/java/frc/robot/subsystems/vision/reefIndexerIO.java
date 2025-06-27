package frc.robot.subsystems.vision;


public class reefIndexerIO{

    /**@return a 2d list of booleans where each outer list represents a pole of the reef and each inner loop represents the nodes on that pole starting in the trough with id 0 and ending at l4 with id 3*/
    public boolean[][] getFullReefState(){
        throw new Error("getFullReefState was called on the reefIndexerIO class. however this function must be overridden to function");
    }

    /**
     * @param row the row or reef Colum to check. is 0 indexed
     * @param level the level to be checked. starts at l1 and is 0 indexed meaning that the level inputted will be 1 smaller than the standard level name
     * @return wether or not the specified node is open 
     * */
    public boolean getIsClosed(int row, int level){
        return !getFullReefState()[row][level];
    }

    /**
     * @param row the row or reef column to check. is 1 indexed
     * @returns the highest available level for the row given */
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
     * @param row one of the 6 positions in between two reef columns in which an algae can be placed,
     * @param level wether the algae is on the lower level(id 0 in between l2 and l3) or on the higher level (level one in between l3 and l4)
     * @return wether or not the specified posit has algae
     */
    public boolean hasAlgae(int row, int level){
        return this.getAlgaePosits()[row][level];
    }

    /**
     * returns a 2d list of booleans containing all possible algae positions and describing which ones actually contain algae
     * Outer list: One of the 6 positions in between two reef columns in which an algae can be placed,
     * Inner list: Wether the algae is on the lower level(id 0 in between l2 and l3) or on the higher level (level one in between l3 and l4)
     * 
     */
    public boolean[][] getAlgaePosits(){
        throw new Error("getAlgaePosits was called on the reefIndexer class. however this function must be overrided to function");
    }


    /**
     * Resets the reef to its starting position. SHOULD ONLY BE USED IN SIM FOR DEBUGGING PURPOSES
     * @throws error if called on a real vision system.
     */
    public void resetSIMONLY(){
        throw new Error("This function is only allowed on simulated robots and should only be used for debugging reasons");
    }
    
    public boolean blockedByAlgae(int row, int level){
        if (level==0||level==3){
            return false;
        }
        else if (level==1){
            return hasAlgae((int)Math.floor(row/2), 0);
        }
        else if (level==2){
            return hasAlgae((int)Math.floor(row/2), 1)||hasAlgae((int)Math.floor(row/2), 0);
        }
        else{
            throw new Error("Attempted to get algae information about a level that doesn't exist. level number: " + level);
        }
    }
    public boolean isOpenSmart(int row, int level){
        return !this.getIsClosed(row, level)&&!this.blockedByAlgae(row, level);
    }

    /**
     * Sets the algae's value at a certain row and level to False once it has been removed by us
     * @param row one of the 6 positions in between two reef columns in which an algae can be placed
     * @param level wether the algae is on the lower level(id 0 in between l2 and l3) or on the higher level (id 1 in between l3 and l4)
     */
    public void freeAlgae(int row, int level){
        throw new Error("This function is being used on the reef indexer interface but should instead be called on an implementation");
    }

    public int getAlgaeLevel(int row){
        if(hasAlgae(row, 0)){
            
            return 1;
        }
        else if (hasAlgae(row, 1)){
            
            return 2;
        }
        
        return 0;
    }

    public void periodic(){}
}
