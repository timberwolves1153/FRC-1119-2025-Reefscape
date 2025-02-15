package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorSetpoints;

public class Elevator extends SubsystemBase {
    public enum Setpoint {
        FeederStation,
        L1,
        L2,
        L3
    }

    private boolean resetByButton = false;
    private boolean resetByLimit = false;

    private double elevatorCurrentTarget = ElevatorSetpoints.FeederStation;
    
    private SparkFlex elevatorMotorL;
    private SparkFlex elevatorMotorR;

    private SparkClosedLoopController elevatorController;
    private RelativeEncoder elevatorEncoder;

    DigitalInput elevatorLimit = new DigitalInput(0); //TODO: set DIO

    public Elevator() {
        elevatorMotorL = new SparkFlex(51, MotorType.kBrushless);
        elevatorMotorR = new SparkFlex(52, MotorType.kBrushless);

        elevatorController = elevatorMotorL.getClosedLoopController();
        elevatorEncoder = elevatorMotorL.getEncoder();

        elevatorMotorL.configure(
            Configs.Elevator.elevatorConfigL,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );

        elevatorMotorR.configure(
            Configs.Elevator.elevatorConfigR,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );

        elevatorEncoder.setPosition(0);
    }

    private void elevatorResetByButton() {
        if (!resetByButton && RobotController.getUserButton()) {
            resetByButton = true;
            elevatorEncoder.setPosition(0);
        } else if (!RobotController.getUserButton()) {
            resetByButton = false;
        }
    }

    private void elevatorResetByLimit() {
        if (!resetByLimit && elevatorLimit.get()) {
            resetByLimit = true;
            elevatorEncoder.setPosition(0);
        } else if (!elevatorLimit.get()) {
            resetByLimit = false;
        }
    }

    private void elevatorToSetpoint() {
            elevatorController.setReference(elevatorCurrentTarget,
            ControlType.kMAXMotionPositionControl
        );
    }

    public void elevatorUp() {
        elevatorMotorL.setVoltage(-2);
        elevatorMotorR.setVoltage(-2);
    }

    public void elevatorDown() {
        elevatorMotorL.setVoltage(2);
        elevatorMotorR.setVoltage(2);
    }

    public void elevatorStop() {
        elevatorMotorL.setVoltage(0);
        elevatorMotorR.setVoltage(0);
    }

    public void elevatorIdle() {
        elevatorMotorL.setVoltage(-0.2);
        elevatorMotorR.setVoltage(-0.2);
    }

    public Command setElevatorSetpoint(Setpoint setpoint) {
        return this.runOnce(
            () -> {
                switch (setpoint) {
                    case FeederStation:
                        elevatorCurrentTarget = ElevatorSetpoints.FeederStation;
                        break;
                    case L1:
                        elevatorCurrentTarget = ElevatorSetpoints.L1;
                        break;
                    case L2:
                        elevatorCurrentTarget = ElevatorSetpoints.L2;
                        break;
                    case L3:
                        elevatorCurrentTarget = ElevatorSetpoints.L3;
                        break;
                }
            }
        );
    }

    @Override
    public void periodic() {
        // elevatorToSetpoint();
        // elevatorResetByButton();
        // elevatorResetByLimit();

        SmartDashboard.putNumber("Elevator Target Position", elevatorCurrentTarget);
        SmartDashboard.putNumber("Elevator True Position", elevatorEncoder.getPosition());
    }
}