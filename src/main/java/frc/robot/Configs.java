package frc.robot;

import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

public final class Configs {
    public final class SwerveModule {
        public static final SparkMaxConfig angleConfig = new SparkMaxConfig();
        public static final SparkMaxConfig driveConfig = new SparkMaxConfig();

        static {
            angleConfig
                .inverted(Constants.Swerve.angleMotorInvert)
                .idleMode(Constants.Swerve.angleNeutralMode)
                .smartCurrentLimit(Constants.Swerve.angleContinuousCurrentLimit);

            angleConfig.encoder
                .positionConversionFactor(Constants.Swerve.angleConversionFactor)
                .velocityConversionFactor(Constants.Swerve.driveConversionVelocityFactor);

            angleConfig.closedLoop
                .pidf(Constants.Swerve.angleKP, Constants.Swerve.angleKI, Constants.Swerve.angleKD, Constants.Swerve.angleKFF)
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .outputRange(-1, 1);
        
            angleConfig.signals
                .primaryEncoderPositionAlwaysOn(true)
                .primaryEncoderPositionPeriodMs(10)
                .primaryEncoderVelocityAlwaysOn(true)
                .primaryEncoderVelocityPeriodMs(20)
                .appliedOutputPeriodMs(20)
                .busVoltagePeriodMs(20)
                .outputCurrentPeriodMs(20);
        }

        static {
            driveConfig
                .inverted(Constants.Swerve.driveMotorInvert)
                .idleMode(Constants.Swerve.driveNeutralMode)
                .smartCurrentLimit(Constants.Swerve.driveContinuousCurrentLimit);

            driveConfig.encoder
                .positionConversionFactor(Constants.Swerve.driveConversionPositionFactor)
                .velocityConversionFactor(Constants.Swerve.driveConversionVelocityFactor);

            driveConfig.closedLoop
                .pidf(Constants.Swerve.driveKP, Constants.Swerve.driveKI, Constants.Swerve.driveKD, Constants.Swerve.driveKFF)
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .outputRange(-1, 1);

            driveConfig.signals
                .primaryEncoderPositionAlwaysOn(true)
                .primaryEncoderPositionPeriodMs(10)
                .primaryEncoderVelocityAlwaysOn(true)
                .primaryEncoderVelocityPeriodMs(20)
                .appliedOutputPeriodMs(20)
                .busVoltagePeriodMs(20)
                .outputCurrentPeriodMs(20);
        }
    }

    public final class Intake {
        public static final SparkMaxConfig intakeConfig = new SparkMaxConfig();

        static {
            intakeConfig
                .inverted(false)
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(40);
        }
    }

    public final class Algae {
        public static final SparkFlexConfig pivotConfig = new SparkFlexConfig();
        public static final SparkMaxConfig intakeConfig = new SparkMaxConfig();

        static {
            pivotConfig
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kBrake);

            pivotConfig.closedLoop
                .p(0.1);

            pivotConfig.closedLoop.maxMotion
                .maxVelocity(3500)
                .maxAcceleration(2500)
                .allowedClosedLoopError(0.25);

            intakeConfig
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kBrake);
        }
    }

    public final class Elevator {
        public static final SparkFlexConfig elevatorConfigL = new SparkFlexConfig();
        public static final SparkFlexConfig elevatorConfigR = new SparkFlexConfig();

        static {
            elevatorConfigL
                .idleMode(IdleMode.kBrake)
                .inverted(false)
                .smartCurrentLimit(40)
                .voltageCompensation(12);

            elevatorConfigL.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .p(0.1)
                .outputRange(-1, 1);

            elevatorConfigL.closedLoop.maxMotion
                .maxVelocity(4500)
                .maxAcceleration(4500)
                .allowedClosedLoopError(0.1);

            elevatorConfigR
                .idleMode(IdleMode.kBrake)
                .inverted(false)
                .follow(51, true)
                .smartCurrentLimit(40)
                .voltageCompensation(12);
        }
    }

    public final class Wrist {
        public static final SparkFlexConfig wristConfig = new SparkFlexConfig();

        static {
            wristConfig
                .inverted(false)
                .smartCurrentLimit(40);
            wristConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .pid(0.14, 0, 0.2)
                .outputRange(-1, 1);
            wristConfig.closedLoop.maxMotion
                .maxVelocity(1500)
                .maxAcceleration(4500)
                .allowedClosedLoopError(0.1);
        }
    }

    public final class Climber {
        public static final SparkFlexConfig climberConfig = new SparkFlexConfig();

        static {
            climberConfig
                .inverted(false)
                .smartCurrentLimit(40);
            climberConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .pid(0.14, 0, 0.2)
                .outputRange(-1, 1);
            climberConfig.closedLoop.maxMotion
                .maxVelocity(5000)
                .maxAcceleration(2500)
                .allowedClosedLoopError(0.1);
        }
    }
}
