package frc.robot.subsystems.vision;


public abstract class reefIndexerIO{


    /**
     * @param row the row or reef collom to check. is 0 indexed
     * @param level the level to be checked. starts at l1 and is 0 indexed meaning that the level inputed will be 1 smaller than the standard level name
     * @return wether or not the specified node is open 
     * */
    public boolean getIsClosed(int row, int level){
        return !getFullReefState()[row][level];
    }

    /**
     * @param row the row or reef collom to check. is 1 indexed
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
        return this.getAlgeaPosits()[row][level];
    }

    /**
     * returns a 2d list of booleans containing all possible algea positions and describing which ones actualy contain algea
     * Outer list: One of the 6 positions inbetween two reef colloms in which an algea can be placed,
     * Inner list: Wether the algea is on the lower level(id 0 in between l2 and l3) or on the higher level (level one in between l3 and l4)
     * 
     */
    public abstract boolean[][] getAlgeaPosits();


    /**
     * Resets the reef to its starting position. SHOULD ONLY BE USED IN SIM FOR DEBUGING PERPOUSES
     * @throws error if called on a real vision system.
     */
    public abstract void resetSIMONLY();
    
    public boolean blockedByAlgae(int row, int level){
        if (level==0||level==3){
            return false;
        }
        else if (level==1){
            return hasAlgea((int)Math.floor(row/2), 0);
        }
        else if (level==2){
            return hasAlgea((int)Math.floor(row/2), 1)||hasAlgea((int)Math.floor(row/2), 0);
        }
        else{
            throw new Error("Attempted to get algae information about a level that doesnt exist. level number: " + level);
        }
    }
    public boolean isOpenSmart(int row, int level){
        return !this.getIsClosed(row, level)&&!this.blockedByAlgae(row, level);
    }

    /**
     * Sets the algae's value at a certain row and level to False once it has been removed by us
     * @param row one of the 6 positions inbetween two reef colloms in which an algea can be placed
     * @param level wether the algea is on the lower level(id 0 in between l2 and l3) or on the higher level (id 1 in between l3 and l4)
     */
    public abstract void freeAlgea(int row, int level);

    public int getAlgaeLevel(int row){
        if(hasAlgea(row, 0)){
            
            return 1;
        }
        else if (hasAlgea(row, 1)){
            
            return 2;
        }
        
        return 0;
    }

    public void periodic(){}


    /**@return a 2d list of booleans where each outer list represents a pole of the reef and each inner loop represents the nodes on that pole starting in the tought with id 0 and ending at l4 with id 3*/
    public abstract boolean[][] getFullReefState();
}
