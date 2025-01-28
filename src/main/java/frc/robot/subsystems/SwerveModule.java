package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.measure.MutDistance;
import edu.wpi.first.units.measure.MutLinearVelocity;
import edu.wpi.first.units.measure.MutVoltage;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.sysid.SysIdRoutineLog;
import frc.robot.lib.math.OnBoardModuleState;
import frc.robot.lib.util.SwerveModuleConstants;
import frc.robot.Constants;

public class SwerveModule {

    
    public int moduleNumber;
    private Rotation2d angleOffset;
    private Rotation2d lastAngle;

    private SparkMax mAngleMotor;
    private SparkMax mDriveMotor;
    private AnalogInput angleEncoder;
    private RelativeEncoder driveEncoder;

    private RelativeEncoder integratedAngleEncoder;
    private final SparkClosedLoopController angleController;
    private final SparkClosedLoopController driveController;

    private final MutVoltage mutableAppliedVoltage = Volts.mutable(0);
    private final MutDistance mutableDistance = Meters.mutable(0);
    private final MutLinearVelocity mutableVelocity = MetersPerSecond.mutable(0);

    SparkMaxConfig angleConfig = new SparkMaxConfig();
    SparkMaxConfig driveConfig = new SparkMaxConfig();

    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(
        Constants.Swerve.driveKS,
        Constants.Swerve.driveKV,
        Constants.Swerve.driveKA
    );

    public SwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants) {
        this.moduleNumber = moduleNumber;
        this.angleOffset = moduleConstants.angleOffset;
        this.angleEncoder = new AnalogInput(moduleConstants.absoluteEncoderPorts);

        /* Angle Configs */

        mAngleMotor = new SparkMax(moduleConstants.angleMotorID, MotorType.kBrushless);

        integratedAngleEncoder = mAngleMotor.getEncoder();
        angleController = mAngleMotor.getClosedLoopController();

        configAngleMotor();

        /* Drive Configs */

        mDriveMotor = new SparkMax(moduleConstants.driveMotorID, MotorType.kBrushless);

        driveEncoder = mDriveMotor.getEncoder();
        driveEncoder.setPosition(0);
        driveController = mDriveMotor.getClosedLoopController();

        configDriveMotor();

        lastAngle = getState().angle;
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
        desiredState = OnBoardModuleState.optimize(desiredState, getState().angle);
        setAngle(desiredState);
        setSpeed(desiredState, isOpenLoop);
    }

    public void resetToAbsolute() {
        double absolutePosition = getAbsoluteEncoder().getDegrees() - angleOffset.getDegrees();
        integratedAngleEncoder.setPosition(absolutePosition);
    }

    public void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if (isOpenLoop) {
            double percentOutput = desiredState.speedMetersPerSecond / Constants.Swerve.maxSpeed;
            mDriveMotor.set(percentOutput);
        } else {
            driveController.setReference(
                desiredState.speedMetersPerSecond,
                ControlType.kVelocity,
                ClosedLoopSlot.kSlot0,
                feedforward.calculate(desiredState.speedMetersPerSecond));
        }
    }

    private void setAngle(SwerveModuleState desiredState) {
        Rotation2d angle = (Math.abs(desiredState.speedMetersPerSecond) <= (Constants.Swerve.maxSpeed * 0.01)) ? lastAngle : desiredState.angle;
        angleController.setReference(angle.getDegrees(), ControlType.kPosition);
        lastAngle = angle;
    }

    private Rotation2d getAngle() {
        return Rotation2d.fromDegrees(integratedAngleEncoder.getPosition());
    }

    public Rotation2d getAbsoluteEncoder() {
        double absolutePositionDegrees = angleEncoder.getVoltage() / RobotController.getVoltage5V() * 360;
        return Rotation2d.fromDegrees(absolutePositionDegrees);
    }

    public void configAngleMotor() {
        angleConfig
        .inverted(Constants.Swerve.angleMotorInvert)
        .idleMode(Constants.Swerve.angleNeutralMode)
        .smartCurrentLimit(Constants.Swerve.angleContinuousCurrentLimit);

        angleConfig.encoder
        .positionConversionFactor(Constants.Swerve.angleConversionFactor);

        angleConfig.closedLoop
        .pidf(Constants.Swerve.angleKP, Constants.Swerve.angleKI, Constants.Swerve.angleKD, Constants.Swerve.angleKFF);
        
        angleConfig.signals
        .primaryEncoderPositionAlwaysOn(true)
        .primaryEncoderPositionPeriodMs(10)
        .primaryEncoderVelocityAlwaysOn(true)
        .primaryEncoderVelocityPeriodMs(20)
        .appliedOutputPeriodMs(20)
        .busVoltagePeriodMs(20)
        .outputCurrentPeriodMs(20);
         
        mAngleMotor.configure(angleConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        resetToAbsolute();
    }

    public void configDriveMotor() {
        driveConfig
        .inverted(Constants.Swerve.driveMotorInvert)
        .idleMode(Constants.Swerve.driveNeutralMode)
        .smartCurrentLimit(Constants.Swerve.driveContinuousCurrentLimit);

        driveConfig.encoder
        .positionConversionFactor(Constants.Swerve.driveConversionPositionFactor);

        driveConfig.closedLoop
        .pidf(Constants.Swerve.driveKP, Constants.Swerve.driveKI, Constants.Swerve.driveKD, Constants.Swerve.driveKFF);

        driveConfig.signals
        .primaryEncoderPositionAlwaysOn(true)
        .primaryEncoderPositionPeriodMs(10)
        .primaryEncoderVelocityAlwaysOn(true)
        .primaryEncoderVelocityPeriodMs(20)
        .appliedOutputPeriodMs(20)
        .busVoltagePeriodMs(20)
        .outputCurrentPeriodMs(20);
         
        mDriveMotor.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        resetToAbsolute();
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
            driveEncoder.getVelocity(),
            getAngle()
        );
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            driveEncoder.getPosition(),
            getAngle()
        );
    }

    public double getDriveDistance() {
        return driveEncoder.getPosition();
    }

    public double getDriveVelocity() {
        return driveEncoder.getVelocity();
    }

    public double getDriveAppliedOutput() {
        return mDriveMotor.getAppliedOutput();
    }

    public void setDriveVoltage(double volts) {
        mDriveMotor.setVoltage(volts);
    }

    public void openLoopDiffDrive(double voltage) {
        angleController.setReference(0, ControlType.kPosition);
        mDriveMotor.setVoltage(voltage);
    }

    public void logMotor(SysIdRoutineLog log) {
        log.motor("module# " + moduleNumber)
        .voltage(mutableAppliedVoltage.mut_replace(
            mDriveMotor.getAppliedOutput() * mDriveMotor.getBusVoltage(), Volts))
            .linearVelocity(mutableVelocity.mut_replace(driveEncoder.getVelocity(), MetersPerSecond))
            .linearPosition(mutableDistance.mut_replace(driveEncoder.getPosition(), Meters));
    }

    public void resetEncoder() {
        driveEncoder.setPosition(0);
    }
        
}