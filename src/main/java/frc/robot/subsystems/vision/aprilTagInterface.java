package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface aprilTagInterface extends Subsystem{
    /**@return the pose estimated by the vision system */
    Pose3d getFrontPose();
    Pose3d getBackPose();
    Double getFrontTimestamp();
    Double getBackTimestamp();

}
