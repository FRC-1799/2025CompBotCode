package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class FieldPosits {
   

    public static class StaringGamePeices{
        public static final Pose2d leftStack= new Pose2d(1.2, 5.8, new Rotation2d());
        public static final Pose2d midStack = new Pose2d(1.2, 4, new Rotation2d());
        public static final Pose2d rightStack = new Pose2d(1.2, 2.2, new Rotation2d());
    }

    /**The positions a robot can use to intake coral */
    public static class IntakePoints{
        public static final Pose2d leftLeft= new Pose2d(1.547, 7.339, Rotation2d.fromDegrees(-55));
        public static final Pose2d leftMid= new Pose2d(1.164, 7.053, Rotation2d.fromDegrees(-55));
        public static final Pose2d leftRight= new Pose2d(0.639, 6.69, Rotation2d.fromDegrees(-55));
        public static final Pose2d rightLeft= new Pose2d(0.697, 1.325, Rotation2d.fromDegrees(55));
        public static final Pose2d rightMid= new Pose2d(1.154, 0.987, Rotation2d.fromDegrees(55));
        public static final Pose2d rightRight= new Pose2d(1.587, 0.667, Rotation2d.fromDegrees(55));
        public static final Pose2d[] coralSpawnPoints = {leftLeft, leftMid, leftRight, rightLeft, rightMid, rightRight};

    }

    /** the possible scoring positions for a robot */
    public static class scoringPosits{
        public static final Pose2d A = new Pose2d(3.197, 4.189, Rotation2d.fromDegrees(0));
        public static final Pose2d B = new Pose2d(3.197, 3.861, Rotation2d.fromDegrees(0));
        public static final Pose2d C = new Pose2d(3.7, 2.992, Rotation2d.fromDegrees(60));
        public static final Pose2d D = new Pose2d(3.985, 2.825, Rotation2d.fromDegrees(60));
        public static final Pose2d E = new Pose2d(4.989, 2.825, Rotation2d.fromDegrees(120));
        public static final Pose2d F = new Pose2d(5.285, 2.992, Rotation2d.fromDegrees(120));
        public static final Pose2d G = new Pose2d(5.774, 3.861, Rotation2d.fromDegrees(180));
        public static final Pose2d H = new Pose2d(5.774, 4.189, Rotation2d.fromDegrees(180));
        public static final Pose2d I = new Pose2d(5.278, 5.059, Rotation2d.fromDegrees(-120));
        public static final Pose2d J = new Pose2d(4.989, 5.225, Rotation2d.fromDegrees(-120));
        public static final Pose2d K = new Pose2d(3.985, 5.225, Rotation2d.fromDegrees(-60));
        public static final Pose2d L = new Pose2d(3.7, 5.060, Rotation2d.fromDegrees(-60));

        public static final Pose2d resetBecausePathplannerIsDumb = new Pose2d(4.733, 1.133, new Rotation2d());
        
        public static final Translation2d L1 = new Translation2d();
        public static final Translation2d L2 = new Translation2d();
        public static final Translation2d L3 = new Translation2d(-0.0533654, 0);
        public static final Translation2d L4 = new Translation2d(-0.2857754, 0);
        public static final Pose2d[] scoringPosits = {A, B, C, D, E, F, G, H, I, J, K, L};
        public static final reefPole[] scoringPoles = {
            reefPole.A,
            reefPole.B,
            reefPole.C,
            reefPole.D,
            reefPole.E,
            reefPole.F,
            reefPole.G,
            reefPole.H,
            reefPole.I,
            reefPole.J,
            reefPole.K,
            reefPole.L

        };
    }

    /**enum to encapsulate and provide basic information about scoring on a reef pole */
    public static enum reefPole{
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I,
        J,
        K,
        L;

        /**
         * @return the scoring posit needed to score on this level. 
         * \This value will be the position to score l1 or 2. to get the l3 and l4 scoring posits simply apply the l4 or l4 translation 
         */
        public Pose2d getScorePosit() {
            switch (this) {
                case A:
                    return scoringPosits.A;
                case B:
                    return scoringPosits.B;
                case C:
                    return scoringPosits.C;
                case D:
                    return scoringPosits.D;
                case E:
                    return scoringPosits.E;
                case F:
                    return scoringPosits.F;
                case G:
                    return scoringPosits.G;
                case H:
                    return scoringPosits.H;
                case I:
                    return scoringPosits.I;
                case J:
                    return scoringPosits.J;
                case K:
                    return scoringPosits.K;
                case L:
                    return scoringPosits.L;
                default:
                    throw new Error("This case is imposible to reach because all enum options are handled but needs to exist so java can be sure the function will always return a value.If you are seeing this as a user somthing has gone DEEPLY DEEPLY WRONG, maybe burn your code in mount doom");
            }
        }

        /**returns this row as an index starting with 0 for A and ending with 11 for L */
        public int getRowAsIndex() {
            switch (this) {
                case A:
                    return 0;
                case B:
                    return 1;
                case C:
                    return 2;
                case D:
                    return 3;
                case E:
                    return 4;
                case F:
                    return 5;
                case G:
                    return 6;
                case H:
                    return 7;
                case I:
                    return 8;
                case J:
                    return 9;
                case K:
                    return 10;
                case L:
                    return 11;
                default:
                    throw new Error("This case is imposible to reach because all enum options are handled but needs to exist so java can be sure the function will always return a value.If you are seeing this as a user somthing has gone DEEPLY DEEPLY WRONG, maybe burn your code in mount doom");
            }
        }
      }
      
    /**enum to encapsulate and provide information about scoring on a level of the reef */
    public static enum reefLevel{
        L1,
        L2,
        L3,
        L4;
        
        /**
         * creates a reefLevel from a number
         * @param level the level to make a pole on. THIS DOES NOT USE THE INDEX VALUE, IT USES THE NAME VALUE. aka if level is set to 1 a l1 reef level will be returned
         * @return a reef level of the specified level
         */
        public static reefLevel CreateFromLevel(int level){
            switch (level){
                case 1: return reefLevel.L1;
                case 2: return reefLevel.L2;
                case 3: return reefLevel.L3;
                case 4: return reefLevel.L4;
                default:
                    throw new Error("you tried to make a pole of an invalid level DUMBASS");
            }
        }

        /**
         * get the level number of a reef level.  THIS DOES NOT USE THE INDEX VALUE, IT USES THE NAME VALUE. aka a L1 reef level will return 1 not 0.
         * @return the level number of the reef
         */
        public int getasInt(){
            switch (this){
                case L1:
                    return 1;
                case L2:
                    return 2;
                case L3:
                    return 3;
                case L4:
                    return 4;
                default:
                    throw new Error("This case is imposible to reach because all enum options are handled but needs to exist so java can be sure the function will always return a value.If you are seeing this as a user somthing has gone DEEPLY DEEPLY WRONG, maybe burn your code in mount doom");
            }
        }

        /**@return the scoring translation for scoring at a particular level. this can be added to the scoring pose provided by reefPole to get a fully acurate scoring position*/
        public Translation2d getTranslation(){
            switch (this){
                case L1:
                    return FieldPosits.scoringPosits.L1;
                case L2:
                    return FieldPosits.scoringPosits.L2;
                case L3:
                    return FieldPosits.scoringPosits.L3;
                case L4:
                    return FieldPosits.scoringPosits.L4;
                default:
                    throw new Error("This case is imposible to reach because all enum options are handled but needs to exist so java can be sure the function will always return a value.If you are seeing this as a user somthing has gone DEEPLY DEEPLY WRONG, maybe burn your code in mount doom");
            }
        }

        /**returns the wrist value needed for this level in the form of a rotation 2d */
        public Rotation2d getWristVal(){
            switch (this){
                case L1:
                    return Constants.wristConstants.l1EncoderVal;
                case L2:
                    return Constants.wristConstants.l2EncoderVal;
                case L3:
                    return Constants.wristConstants.l3EncoderVal;
                case L4:
                    return Constants.wristConstants.l4EncoderVal;
                default:
                    throw new Error("This case is imposible to reach because all enum options are handled but needs to exist so java can be sure the function will always return a value.If you are seeing this as a user somthing has gone DEEPLY DEEPLY WRONG, maybe burn your code in mount doom");
            }
        }

        /**returns the elevator value needed for this level */
        public double getElevatorValue(){
            switch (this){
                case L1:
                    return Constants.elevatorConstants.l1EncoderVal;
                case L2:
                    return Constants.elevatorConstants.l2EncoderVal;
                case L3:
                    return Constants.elevatorConstants.l3EncoderVal;
                case L4:
                    return Constants.elevatorConstants.l4EncoderVal;
                default:
                    throw new Error("This case is imposible to reach because all enum options are handled but needs to exist so java can be sure the function will always return a value.If you are seeing this as a user somthing has gone DEEPLY DEEPLY WRONG, maybe burn your code in mount doom");
            }
        }
    }


    
}
