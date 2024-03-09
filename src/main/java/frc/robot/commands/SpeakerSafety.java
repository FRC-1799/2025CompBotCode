package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.SpeakerShooter;

public class SpeakerSafety extends RunCommand {
  public SpeakerSafety(SpeakerShooter shooter, DoubleSupplier flySpeed, DoubleSupplier indexSpeed) {
    super(
        ()->{
            shooter.SafetyFunction(
                MathUtil.applyDeadband(indexSpeed.getAsDouble(),0.1),
                MathUtil.applyDeadband(flySpeed.getAsDouble(), 0.1));
        },
        shooter
    );
    
    addRequirements(shooter);
  }      
}
