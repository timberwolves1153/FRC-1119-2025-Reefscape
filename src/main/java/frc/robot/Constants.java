package frc.robot;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.lib.util.NEOSwerveConstants;
import frc.robot.lib.util.SwerveModuleConstants;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

public final class Constants {
  public static final boolean tuneSwerve = true;
  public static final double stickDeadband = 0.2;
  // other subsystems here

  public static final class Swerve {
    public static final int PigeonID = 5;
    public static final boolean invertGyro = false;

    public static final NEOSwerveConstants chosenModule = 
      NEOSwerveConstants.SDSMK4i(NEOSwerveConstants.driveGearRatios.SDSMK4i_L2);  //change if we change modules

    /* Drivetrain Constants */

    public static final double trackWidth = Units.inchesToMeters(23);
        public static final double wheelBase = Units.inchesToMeters(23);
        public static final double wheelCircumference = chosenModule.wheelCircumference;
        public static final double wheelDiameter = chosenModule.wheelDiameter;

    /* Swerve Kinematics */

    public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
            new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));

    public static final double voltageComp = 12;

    /* Module Gear Ratios */

    public static final double driveGearRatio = chosenModule.driveGearRatio;
    public static final double angleGearRatio = chosenModule.angleGearRatio;

    /* Inverts */
    public static final boolean angleMotorInvert = chosenModule.angleMotorInvert;
    public static final boolean driveMotorInvert = chosenModule.driveMotorInvert;

    public static final boolean absoluteEncoderPortsInvert = chosenModule.absoluteEncoderPortsInvert;

    /* Swerve Current Limiting */
    public static final int angleContinuousCurrentLimit = 25;
    public static final int anglePeakCurrentLimit = 20;
    public static final double anglePeakCurrentDuration = 0.1;
    public static final boolean angleEnableCurrentLimit = true;

    public static final int driveContinuousCurrentLimit = 35;
    public static final int drivePeakCurrentLimit = 60;
    public static final double drivePeakCurrentDuration = 0.1;
    public static final boolean driveEnableCurrentLimit = true;

    public static final double openLoopRamp = 0.25;
    public static final double closedLoopRamp = 0.0;

    /* Angle Motor PID Values */
    public static final double angleKP = 0.05;
    public static final double angleKI = 0.0;
    public static final double angleKD = 0.01;
    public static final double angleKFF = 0.0;

    /* Drive Motor PID Values */
    public static final double driveKP = 0.25;
    public static final double driveKI = 0.0;
    public static final double driveKD = 0.3;
    public static final double driveKFF = 0.0;

    /* Drive Motor Characterization Values (SYSID / 12) */
    public static final double driveKS = (0.01409);
    public static final double driveKV = (2.58118);
    public static final double driveKA = (0.43697);

    public static final double driveConversionPositionFactor = 
            (wheelDiameter * Math.PI) / driveGearRatio;
        public static final double driveConversionVelocityFactor = driveConversionPositionFactor / 60.0;
        public static final double angleConversionFactor = 360.0 / angleGearRatio;

    /* Swerve Profiling Values */
    public static final double maxSpeed = 4.5;

    public static final double maxAngularVelocity = 11.5;

    public static final IdleMode angleNeutralMode = IdleMode.kBrake;
    public static final IdleMode driveNeutralMode = IdleMode.kBrake;

    /* Module Constants */

    /* Module 0 */
    public static final class Mod0 {
      public static final int driveMotorID = 1;
      public static final int angleMotorID = 2;
      public static final int absoluteEncoderPorts = 0;
      public static final Rotation2d angleOffset = Rotation2d.fromDegrees(121.4);
      public static final SwerveModuleConstants constants = 
          new SwerveModuleConstants(driveMotorID, angleMotorID, absoluteEncoderPorts, angleOffset);
    }

    /* Module 1 */
    public static final class Mod1 {
        public static final int driveMotorID = 11;
        public static final int angleMotorID = 12;
        public static final int absoluteEncoderPorts = 1;
        public static final Rotation2d angleOffset = Rotation2d.fromDegrees(51.1);  
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, absoluteEncoderPorts, angleOffset);
    }
        
    /* Module 2 */
    public static final class Mod2 {
        public static final int driveMotorID = 21;
        public static final int angleMotorID = 22;
        public static final int absoluteEncoderPorts = 2;
        public static final Rotation2d angleOffset = Rotation2d.fromDegrees(254.5);
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, absoluteEncoderPorts, angleOffset);
        }

    /* Module 3 */
    public static final class Mod3 {
        public static final int driveMotorID = 31;
        public static final int angleMotorID = 32;
        public static final int absoluteEncoderPorts = 3;
        public static final Rotation2d angleOffset = Rotation2d.fromDegrees(34.4); 
        public static final SwerveModuleConstants constants = 
            new SwerveModuleConstants(driveMotorID, angleMotorID, absoluteEncoderPorts, angleOffset);
        }
  }

  public static final class AutoConstants { 
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1;
    public static final double kPYController = 1;
    public static final double kPThetaController = 1;

    /* Constraint for the motion profilied robot angle controller */
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints =
        new TrapezoidProfile.Constraints(
            kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }  

  public static final class Limelight {
    public static final double limelightOffset = 0;
    public static final double limelightHeight = 0;
    public static final double limelightDepth = 0;
    public static final double limelightYaw = 0;
    public static final double limelightPitch = 0;

    public static final double aprilTagHeight = 0; //All offset from a point at the center of the front of robot facing forward
  }

  public static final class ElevatorSetpoints {
    public static final double FeederStation = 0.0;
    public static final double L1 = 0.0;
    public static final double L2 = -4;
    public static final double L3 = -26.0;
  }

  public static final class WristSetpoints {
    public static final double FeederStation = -10.1;
    public static final double L1 = -10.1;
    public static final double L2 = -41.6;
    public static final double L3 = -41.0;
  }
}