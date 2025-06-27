package frc.robot.commands;

import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class QuickSwapCommand extends Command{
    boolean current=false;
    BooleanSupplier supplier;
    Command trueCommand;
    Command falseCommand;
    Command selected;
    /**
     * Creates a Command that will dynamically swap between two other commands. 
     * Nether of the child commands will actually be scheduled but instead they will be managed and updated by the quick swap command. 
     * However both child commands requirements will still be required and handled by the quick swap command
     * @param trueCommand Command to be active when the quick swap command is in its true state
     * @param falseCommand Command to be active when the quick swap command is in its false state
     * @param supplier supplies wether or not the quick swap command should be in its true or false state.
     */
    public QuickSwapCommand(Command trueCommand, Command falseCommand, BooleanSupplier supplier){
        this.supplier=supplier;
        this.trueCommand=trueCommand;
        this.falseCommand=falseCommand;

        for (Subsystem subsystem: trueCommand.getRequirements()){
            addRequirements(subsystem);
        }
        for (Subsystem subsystem: falseCommand.getRequirements()){
            if (!this.getRequirements().contains(subsystem)){
                addRequirements(subsystem);
            }
        }
    }

    /**
     * Creates a Command that will dynamically swap between two other commands. 
     * Nether of the child commands will actually be scheduled but instead they will be managed and updated by the quick swap command. 
     * This constructor will not require the requirements of ether sub command
     * @param trueCommand Command to be active when the quick swap command is in its true state
     * @param falseCommand Command to be active when the quick swap command is in its false state
     * @param supplier supplies wether or not the quick swap command should be in its true or false state.
     * @param requirements The subsystems this command should require
     */
    public QuickSwapCommand(Command trueCommand, Command falseCommand, BooleanSupplier supplier, Subsystem[] requirements){
        this.supplier=supplier;
        this.trueCommand=trueCommand;
        this.falseCommand=falseCommand;
        for (Subsystem subsystem: requirements){
            addRequirements(subsystem);
        }
    }

    /**initializes the command */
    @Override
    public void initialize(){
        this.current=supplier.getAsBoolean();
        if (current){
            this.selected=trueCommand;
        }
        else{
            this.selected=falseCommand;
        }
        this.selected.initialize();
    }

    /**called ever rio cycle while the command is scheduled*/
    @Override
    public void execute(){
        if (supplier.getAsBoolean()!=current){
            if (supplier.getAsBoolean()){
                selected=trueCommand;
            }
            else{
                selected=falseCommand;
            }
            this.current=supplier.getAsBoolean();
            selected.initialize();
        }
        selected.execute();
    }

    /**
     * @return true once the currently selected command is finished
     */
    @Override
    public boolean isFinished(){
        return selected.isFinished();
    }

    /**Called when the command is finished. will call the end function of both child commands */
    @Override
    public void end(boolean WasInterrupted){
        trueCommand.end(WasInterrupted);
        falseCommand.end(WasInterrupted);
    }
}
