package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.sysid.SysIdRoutineLog;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

import static edu.wpi.first.units.Units.Volts;


public class Swerve extends SubsystemBase {
    public SwerveDriveOdometry 
    swerveOdometry;
    public SwerveModule[] mSwerveModules;
    public SwerveDriveKinematics swerveKinematics;
    public Pigeon2 gyro;
    public Alliance alliance;
    public double voltage;
    RobotConfig config;
    private final Field2d m_poseEstimatorField = new Field2d();
    private final SysIdRoutine driveTrainRoutine = new SysIdRoutine(
        new SysIdRoutine.Config(),
        new SysIdRoutine.Mechanism(this :: voltageDrive, this :: logMotors, this));

    private void voltageDrive(Voltage voltage) {
        for (SwerveModule module : mSwerveModules) {
            module.openLoopDiffDrive(voltage.in(Volts));
        }
    }

    private void logMotors(SysIdRoutineLog log) {
        for (SwerveModule module : mSwerveModules) {
            module.logMotor(log);
        }
    }

    public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
        return driveTrainRoutine.quasistatic(direction);
    }

    public Command sysIdDynamic(SysIdRoutine.Direction direction) {
            return driveTrainRoutine.dynamic(direction);
    }

    public Swerve() {
        gyro = new Pigeon2(Constants.Swerve.PigeonID);
        
        gyro.clearStickyFaults();
        zeroGyro();

        try {
            config = RobotConfig.fromGUISettings();
          } catch (Exception e) {
            e.printStackTrace();
          }

        mSwerveModules = new SwerveModule[] {
            new SwerveModule(0, Constants.Swerve.Mod0.constants),
            new SwerveModule(1, Constants.Swerve.Mod1.constants),
            new SwerveModule(2, Constants.Swerve.Mod2.constants),
            new SwerveModule(3, Constants.Swerve.Mod3.constants),
        };

        Timer.delay(1.0);
        resetModulesToAbsolute();
        
        swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, getAngle(), getModulePositions());
        

        AutoBuilder.configure(
            this::getPose,
            this::resetOdometry,
            this::getRobotRelativeSpeeds,
            (speeds, feedforwards) -> driveRobotRelative(speeds),
            new PPHolonomicDriveController(
                    new PIDConstants(5.0, 0.0, 0.0),
                    new PIDConstants(5.0, 0.0, 0.0)
            ),
            config,
            () -> {
              var alliance = DriverStation.getAlliance();
              if (alliance.isPresent()) {
                return alliance.get() == DriverStation.Alliance.Red;
              }
              return false;
            },
            this
            );
             
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates = 
            Constants.Swerve.swerveKinematics.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                        translation.getX(), translation.getY(), rotation, getAngle())
                    : new ChassisSpeeds(translation.getX(), translation.getY(), rotation));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

        for (SwerveModule mod : mSwerveModules) {
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }

    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);

        for (SwerveModule mod : mSwerveModules) {
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }

    public ChassisSpeeds getRobotRelativeSpeeds() {
        return Constants.Swerve.swerveKinematics.toChassisSpeeds(getStates());
    }

    public void driveRobotRelative(ChassisSpeeds robotRelativeSpeeds) {
        ChassisSpeeds targetSpeeds = ChassisSpeeds.discretize(robotRelativeSpeeds, 0.02);

        SwerveModuleState[] targetStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(targetSpeeds);
        setModuleStates(targetStates);
    }

    public void driveFieldRelative(ChassisSpeeds fieldRelativeSpeeds) {
        driveRobotRelative(ChassisSpeeds.fromFieldRelativeSpeeds(fieldRelativeSpeeds, getPose().getRotation()));
    }
    
    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(getAngle(), getModulePositions(), pose);
    }

    public SwerveModuleState[] getStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : mSwerveModules){
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for(SwerveModule mod : mSwerveModules){
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    public void zeroGyro() {
        gyro.setYaw(0);
    }

    public Rotation2d getAngle() {
        return (Constants.Swerve.invertGyro) ?
        Rotation2d.fromDegrees(360 - gyro.getYaw().getValueAsDouble()) :
        Rotation2d.fromDegrees(gyro.getYaw().getValueAsDouble());
    }

    public void resetModulesToAbsolute() {
        for (SwerveModule mod : mSwerveModules) {
            mod.resetToAbsolute();
        }
    }

    public double getGyroYaw() {
        return gyro.getYaw().getValueAsDouble();
    }

    public void driveForVoltage(double volts) {
        for (SwerveModule mod : mSwerveModules) {
            mod.setDriveVoltage(volts);
        }
    }

    public void xPosition(Boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates =
            new SwerveModuleState[] {
                new SwerveModuleState(1, Rotation2d.fromDegrees(45)),
                new SwerveModuleState(1, Rotation2d.fromDegrees(-45)),
                new SwerveModuleState(1, Rotation2d.fromDegrees(-45)),
                new SwerveModuleState(1, Rotation2d.fromDegrees(45))
            };
        
            for (SwerveModule mod : mSwerveModules) {
                mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
            }

            System.out.println("set to x position");
    }

    public void resetEncoders() {
        for (SwerveModule mod : mSwerveModules) {
            mod.resetEncoder();
        } 
    }

    @Override
    public void periodic() {
        swerveOdometry.update(getAngle(), getModulePositions());
         m_poseEstimatorField.setRobotPose(swerveOdometry.getPoseMeters());
         SmartDashboard.putData("Pose Estimator Field", m_poseEstimatorField);

        

        for(SwerveModule mod : mSwerveModules){
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " absoluteEncoderPorts", mod.getAbsoluteEncoder().getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Integrated", mod.getState().angle.getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);
            SmartDashboard.putNumber("Gyro Angle", getAngle().getDegrees());
            SmartDashboard.putNumber("Mod " + mod.moduleNumber + " Position", mod.getPosition().distanceMeters);
        }

    }
}