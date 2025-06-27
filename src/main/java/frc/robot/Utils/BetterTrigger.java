package frc.robot.Utils;

import static edu.wpi.first.util.ErrorMessages.requireNonNullParam;


import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class BetterTrigger extends Trigger{

    private final BooleanSupplier m_condition;
    private final EventLoop m_loop;

   
    /**
     * creates a trigger object that fixes some of the internal jank in the original WPIlib trigger
     * @param loop the loop that this trigger should attached too
     * @param condition the condition this trigger will use internally
     */
    public BetterTrigger(EventLoop loop, BooleanSupplier condition) {
        super(loop, condition);
        m_loop = requireNonNullParam(loop, "loop", "Trigger");
        m_condition = requireNonNullParam(condition, "condition", "Trigger");
    }

    /**
     * creates a trigger object that fixes some of the internal jank in the original WPIlib trigger
     * @param condition the condition represented by this trigger
     */
    public BetterTrigger(BooleanSupplier condition) {
        this(CommandScheduler.getInstance().getDefaultButtonLoop(), condition);
    }

    


  /**
   * Starts the command whenever the condition is true and the command isn't running. The command will be canceled whenever the trigger changes to false.
   * Will restart the command if it ends and the condition is still true
   * @param command the command to start
   * @return this trigger, so calls can be chained
   */
  public Trigger whileTrue(Command command) {
    requireNonNullParam(command, "command", "whileTrue");
    m_loop.bind(
        new Runnable() {
          

          @Override
          public void run() {
            boolean pressed = m_condition.getAsBoolean();

            if (!CommandScheduler.getInstance().isScheduled(command)&& pressed) {
              command.schedule();
            } else if (!pressed) {
              command.cancel();
            }

            
          }
        });
    return this;
  }

 
  /**
   * Starts the command whenever the condition is false and the command isn't running. The command will be canceled whenever the trigger changes to true.
   * Will restart the command if it ends and the condition is still false.
   * @param command the command to start.
   * @return this trigger, so calls can be chained.
   */
  public Trigger whileFalse(Command command) {
    requireNonNullParam(command, "command", "whileFalse");
    m_loop.bind(
        new Runnable() {
          

          @Override
          public void run() {
            boolean pressed = m_condition.getAsBoolean();

            if (!CommandScheduler.getInstance().isScheduled(command)&&!pressed) {
              command.schedule();
            } else if (pressed) {
              command.cancel();
            }

            
          }
        });
    return this;
  }



}
