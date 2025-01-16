package frc.robot.subsystems;


import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SystemManager;


public class CoralSim extends SubsystemBase {

private Pose3d pose = new Pose3d(-1000, -1000, -1000, new Rotation3d());
    

    private Set<CoralSimScoreLocation> scoreLocations = new HashSet<>();

    
    private Supplier<Pose3d> relativeClawPoseSupplier;

    public enum CoralSimLocation {
        INTAKE,
        CLAW,
        HIDDEN;
    }

    public enum CoralSimScoreLocation {
        A1(0, 0),
        A2(0, 1),
        A3(0,2),
        A4(0,3),
        B1(1, 0),
        B2(1, 1),
        B3(1,2),
        B4(1,3),
        C1(2, 0),
        C2(2, 1),
        C3(2,2),
        C4(2,3),
        D1(3, 0),
        D2(3, 1),
        D3(3,2),
        D4(3,3),
        E1(4, 0),
        E2(4, 1),
        E3(4,2),
        E4(4,3),
        F1(5, 0),
        F2(5, 1),
        F3(5,2),
        F4(5,3),
        G1(6, 0),
        G2(6, 1),
        G3(6,2),
        G4(6,3),
        H1(7, 0),
        H2(7, 1),
        H3(7,2),
        H4(7,3),
        I1(8, 0),
        I2(8, 1),
        I3(8,2),
        I4(8,3),
        J1(9, 0),
        J2(9, 1),
        J3(9,2),
        J4(9,3),
        K1(10, 0),
        K2(10, 1),
        K3(10,2),
        K4(10,3),
        L1(11, 0),
        L2(11, 1),
        L3(11,2),
        L4(11,3);
        Pose3d[][] targets = {
            {new Pose3d(4.75, 3.25, 0.45, new Rotation3d()), new Pose3d(4.75, 3.25, 0.8, new Rotation3d()), new Pose3d(4.75, 3.25, 1.2, new Rotation3d()), new Pose3d(4.75, 3.25, 1.825498, new Rotation3d())},
            {new Pose3d(5, 3.4, 0.45, new Rotation3d()), new Pose3d(5, 3.4, 0.8, new Rotation3d()), new Pose3d(5, 3.4, 1.2, new Rotation3d()), new Pose3d(4, 3.4, 1.825498, new Rotation3d())},
            {new Pose3d(5.3, 3.8, 0.45, new Rotation3d()), new Pose3d(5.3, 3.8, 0.8, new Rotation3d()), new Pose3d(5.3, 3.8, 1.2, new Rotation3d()), new Pose3d(5.3, 3.8, 1.825498, new Rotation3d())},
            {new Pose3d(5.3, 4.1, 0.45, new Rotation3d()), new Pose3d(5.3, 4.1, 0.8, new Rotation3d()), new Pose3d(5.3, 4.1, 1.2, new Rotation3d()), new Pose3d(5.3, 4.1, 1.825498, new Rotation3d())},
            {new Pose3d(5, 4.6, 0.45, new Rotation3d()), new Pose3d(5, 4.6, 0.8, new Rotation3d()), new Pose3d(5, 4.6, 1.2, new Rotation3d()), new Pose3d(4, 4.6, 1.825498, new Rotation3d())},
            {new Pose3d(4.75, 4.8, 0.45, new Rotation3d()), new Pose3d(4.75, 4.8, 0.8, new Rotation3d()), new Pose3d(4.75, 4.8, 1.2, new Rotation3d()), new Pose3d(4.75, 4.8, 1.825498, new Rotation3d())},
            {new Pose3d(4.2, 4.8, 0.45, new Rotation3d()), new Pose3d(4.2, 4.8, 0.8, new Rotation3d()), new Pose3d(4.2, 4.8, 1.2, new Rotation3d()), new Pose3d(4.2, 4.8, 1.825498, new Rotation3d())},
            {new Pose3d(4, 4.6, 0.45, new Rotation3d()), new Pose3d(4, 4.6, 0.8, new Rotation3d()), new Pose3d(4, 4.6, 1.2, new Rotation3d()), new Pose3d(4, 4.6, 1.825498, new Rotation3d())},
            {new Pose3d(3.6, 4.1, 0.45, new Rotation3d()), new Pose3d(3.6, 4.1, 0.8, new Rotation3d()), new Pose3d(3.6, 4.1, 1.2, new Rotation3d()), new Pose3d(3.6, 4.1, 1.825498, new Rotation3d())},
            {new Pose3d(3.6, 3.8, 0.45, new Rotation3d()), new Pose3d(3.6, 3.8, 0.8, new Rotation3d()), new Pose3d(3.6, 3.8, 1.2, new Rotation3d()), new Pose3d(3.6, 3.8, 1.825498, new Rotation3d())},
            {new Pose3d(4, 3.4, 0.45, new Rotation3d()), new Pose3d(4, 3.4, 0.8, new Rotation3d()), new Pose3d(4, 3.4, 1.2, new Rotation3d()), new Pose3d(4, 3.4, 1.825498, new Rotation3d())},
            {new Pose3d(4.2, 3.25, 0.45, new Rotation3d()), new Pose3d(4.2, 3.25, 0.8, new Rotation3d()), new Pose3d(4.2, 3.25, 1.2, new Rotation3d()), new Pose3d(4.2, 3.25, 1.825498, new Rotation3d())}
        };
        public final Pose3d pose;

        CoralSimScoreLocation(int row, int col) {
            pose=targets[row][col];
        }
    }

    private CoralSimLocation location = CoralSimLocation.CLAW;
    private Timer timer = new Timer();

    public CoralSim(Supplier<Pose3d> relativeClawPoseSupplier) {

        
        this.relativeClawPoseSupplier = relativeClawPoseSupplier;

    }

    @Override
    public void periodic() {
        switch (location) {
            case CLAW -> {
                Pose3d relativeCoralPose = relativeClawPoseSupplier.get()
                        .plus(new Transform3d(0.143, 0, 0, new Rotation3d()));
                pose = new Pose3d(SystemManager.getSwervePose())
                        .plus(new Transform3d(relativeCoralPose.getTranslation(), relativeCoralPose.getRotation()))
                        .plus(new Transform3d(0, 0, 0, new Rotation3d(0, Math.PI / 2.0, 0)));
            }
            case INTAKE -> {
                Pose2d drivetrainPose = SystemManager.getSwervePose();
                Pose3d topPose = new Pose3d(drivetrainPose)
                        .plus(new Transform3d(-0.26, 0, 0.69, new Rotation3d(0, Math.PI / 4.0, 0)));
                Pose3d bottomPose = new Pose3d(SystemManager.getSwervePose())
                        .plus(new Transform3d(-0.04, 0, 0.45, new Rotation3d(0, Math.PI / 4.0, 0)));
                Pose3d inRobotPose = new Pose3d(SystemManager.getSwervePose())
                        .plus(new Transform3d(0.232, 0, 0.23, new Rotation3d()));
                double time = timer.get();
                if (time < 0.25) {
                    pose = topPose.interpolate(bottomPose, time * 4);
                } else if (time < 0.5) {
                    pose = bottomPose.interpolate(inRobotPose, (time - 0.25) * 4);
                } else {
                    pose = inRobotPose;
                }
            }
            default -> {
                pose = new Pose3d(-1000, -1000, -1000, new Rotation3d());
            }
        }

        
    }

    public Command setLocationCommand(CoralSimLocation location) {
        return Commands.runOnce(() -> {
            setLocation(location);
        });
    }
    public void setLocation(CoralSimLocation location){
        this.location = location;
        timer.reset();
        timer.start();
    }
    public Command addScoringLocationCommand(CoralSimScoreLocation location) {
        return Commands.runOnce(() -> {
            this.scoreLocations.add(location);
        });
    }

    public Command removeScoringLocationCommand(CoralSimScoreLocation location) {
        return Commands.runOnce(() -> {
            this.scoreLocations.remove(location);
        });
    }

}
