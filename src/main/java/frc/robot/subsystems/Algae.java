package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkFlex;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants.PivotSetpoints;

public class Algae extends SubsystemBase {
    public enum PivotSetpoint {
        Reset,
        Stow,
        Collect,
        Climb
    }

    private SparkFlex pivotMotor;
    private SparkMax intakeMotor;

    private SparkClosedLoopController pivotController;
    private RelativeEncoder pivotEncoder;

    private double pivotCurrentTarget;

    public Algae() {
        pivotMotor = new SparkFlex(41, MotorType.kBrushless);
        intakeMotor = new SparkMax(42, MotorType.kBrushless);

        pivotController = pivotMotor.getClosedLoopController();
        pivotEncoder = pivotMotor.getEncoder();

        pivotCurrentTarget = PivotSetpoints.Stow;

        pivotMotor.configure(
            Configs.Algae.pivotConfig,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );

        intakeMotor.configure(
            Configs.Algae.intakeConfig,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );

        pivotEncoder.setPosition(0);
    }

    public void pivotUp() {
        pivotMotor.setVoltage(3);
    }

    public void pivotDown() {
        pivotMotor.setVoltage(-3);
    }

    public void pivotStop() {
        pivotMotor.setVoltage(0);
    }

    public void pivotToSetpoint() {
            pivotController.setReference(pivotCurrentTarget,
            ControlType.kMAXMotionPositionControl
        );
    }

    public void algaeIntake() {
        intakeMotor.setVoltage(-10);
    }

    public void algaeOuttake() {
        intakeMotor.setVoltage(6);
    }

    public void algaeStop() {
        intakeMotor.setVoltage(0);
    }

    public void algaeIdle() {
        intakeMotor.setVoltage(-2);
    }

    public Command setPivotSetpoint(PivotSetpoint setpoint) {
        return this.runOnce(
            () -> {
                switch (setpoint) {
                    case Reset:
                        pivotCurrentTarget = PivotSetpoints.Reset;
                        break;
                    case Stow:
                        pivotCurrentTarget = PivotSetpoints.Stow;
                        break;
                    case Collect:
                        pivotCurrentTarget = PivotSetpoints.Collect;
                        break;
                    case Climb:
                        pivotCurrentTarget = PivotSetpoints.Climb;
                        break;
                    default:
                        pivotCurrentTarget = PivotSetpoints.Stow;
                        break;
                }
            }
        );
    }

    @Override
    public void periodic() {
        pivotToSetpoint();

        SmartDashboard.putNumber("Pivot Target Position", pivotCurrentTarget);
        SmartDashboard.putNumber("Pivot True Position", pivotEncoder.getPosition());
    }
}