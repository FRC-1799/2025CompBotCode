package frc.robot.subsystems.blinkin;

public interface blinkinInterface {
    /**enum to contain all the possible colors of a blinkin */
    enum color{
        white(0.93, "white"),
        blue(0.85,"blue"),
        orange(0.65, "orange"),
        green(0.77, "green");

        String name;
        double id;
        /**
         * creates a color
         * @param id the id of the blinkin color to use
         * @param name the name of the color
         */
        color(double id, String name){
            this.id=id;
            this.name=name;
        }

        /**Dev function so colors can start to be implemented before they have been fully fleshed out internally */
        color(){
            throw new Error("You attempted to use a color that does not exist yet");
        }
    }

    /**
     * sets the color of the blinkin
     * @param color the color object to set
     */
    public void setColor(color color);

    /**@return returns the current color in the form of a color enum*/
    public color getColor();
}
