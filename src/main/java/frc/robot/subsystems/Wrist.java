package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants.ElevatorSetpoints;
import frc.robot.Constants.WristSetpoints;

public class Wrist extends SubsystemBase {
    public enum WristSetpoint {
        FeederStation,
        L1,
        L2,
        L3
    }
    private double wristCurrentTarget = ElevatorSetpoints.FeederStation;
    
    private SparkFlex wristMotor;

    private SparkClosedLoopController wristController;
    private RelativeEncoder wristEncoder;

    public Wrist() {
        wristMotor = new SparkFlex(54, MotorType.kBrushless);
        wristController = wristMotor.getClosedLoopController();
        wristEncoder = wristMotor.getEncoder();

        wristCurrentTarget = WristSetpoints.FeederStation;

        wristMotor.configure(
            Configs.Wrist.wristConfig,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );

        wristEncoder.setPosition(0);
    }

    private void wristToSetpoint() {
            wristController.setReference(wristCurrentTarget,
            ControlType.kMAXMotionPositionControl
        );
    }

    public void wristUp() {
        wristMotor.setVoltage(2);
    }

    public void wristDown() {
        wristMotor.setVoltage(-2);
    }

    public void wristStop() {
        wristMotor.setVoltage(0);
    }

    public void wristIdle() {
        if (wristEncoder.getPosition() < -47) {
            wristMotor.setVoltage(0.325);
        } else if (wristEncoder.getPosition() < -32) {
            wristMotor.setVoltage(0.275);
        } else if (wristEncoder.getPosition() < -27) {
            wristMotor.setVoltage(0.225);
        } else if (wristEncoder.getPosition() < -22) {
            wristMotor.setVoltage(0);
        } else if (wristEncoder.getPosition() < -17) {
            wristMotor.setVoltage(-0.225);
        } else if (wristEncoder.getPosition() < -12) {
            wristMotor.setVoltage(-0.275);
        } else if (wristEncoder.getPosition() < 3) {
            wristMotor.setVoltage(-0.325);
        } else {
            wristMotor.setVoltage(0);
        }
    }

    public Command setWristSetpoint(WristSetpoint setpoint) {
        return this.runOnce(
            () -> {
                switch (setpoint) {
                    case FeederStation:
                        wristCurrentTarget = WristSetpoints.FeederStation;
                        break;
                    case L1:
                        wristCurrentTarget = WristSetpoints.L1;
                        break;
                    case L2:
                        wristCurrentTarget = WristSetpoints.L2;
                        break;
                    case L3:
                        wristCurrentTarget = WristSetpoints.L3;
                        break;
                }
            }
        );
    }

    @Override
    public void periodic() {
        wristToSetpoint();
        
        
        SmartDashboard.putNumber("Wrist Target Position", wristCurrentTarget);
        SmartDashboard.putNumber("Wrist True Position", wristEncoder.getPosition());
    } 
}