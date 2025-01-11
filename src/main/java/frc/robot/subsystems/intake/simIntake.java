package frc.robot.subsystems.intake;

import static edu.wpi.first.units.Units.Meters;

import org.ironmaple.simulation.IntakeSimulation;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SystemManager;

public class simIntake extends SubsystemBase implements intakeInterface{
    IntakeSimulation intakeSim;
    

  
    boolean isIntaking;
    int clock=0;
    int checkClock=0;

    public simIntake(){
        intakeSim= IntakeSimulation.InTheFrameIntake("Coral", SystemManager.swerve.getMapleSimDrive().get(), Meters.of(0.7), IntakeSimulation.IntakeSide.BACK, 1);
    }

    @Override 
    public void periodic(){
        if (clock!=checkClock){
            isIntaking=false;
            intakeSim.stopIntake();
            
        }
        clock++;

        SmartDashboard.putBoolean("hasCoral", hasPeice());
    }

    public void intake(){
        intakeSim.startIntake();
        isIntaking=true;
        checkClock=clock;

    }

    public boolean hasPeice(){
        return intakeSim.getGamePiecesAmount()==1;
    }

    public void outtake(){
        if (hasPeice()){
            intakeSim.obtainGamePieceFromIntake();
        }
    }

    
}
