package frc.robot.subsystems;

import java.lang.ModuleLayer.Controller;

import com.ctre.phoenix6.controls.Follower;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorSetpoints;

public class Elevator extends SubsystemBase {
    // public enum Setpoint {
    //     kFeederStation,
    //     L1,
    //     L2,
    //     L3
    // }

    // private boolean resetByButton = false;

    // private double elevatorCurrentTarget = ElevatorSetpoints.kFeederStation;
    
    // private SparkFlex elevatorMotorL;
    // private SparkFlex elevatorMotorR;

    // public static final SparkFlexConfig elevatorConfigL = new SparkFlexConfig();
    // public static final SparkFlexConfig elevatorConfigR = new SparkFlexConfig();

    // private SparkClosedLoopController elevatorController = elevatorMotorL.getClosedLoopController();
    // private RelativeEncoder elevatorEncoder = elevatorMotorL.getEncoder();

    // public Elevator() {
    //     elevatorMotorL = new SparkFlex(0, MotorType.kBrushless); //TODO: set ID
    //     elevatorMotorR = new SparkFlex(0, MotorType.kBrushless);

    //     elevatorConfigL
    //         .smartCurrentLimit(40)
    //         .idleMode(IdleMode.kBrake);
    //     elevatorConfigL.closedLoop
    //         .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
    //         .p(0.1)
    //         .outputRange(-1, 1);
        
    //     elevatorConfigL.limitSwitch
    //         .reverseLimitSwitchEnabled(true);

    //     elevatorConfigR.follow(0, true); //TODO: set
        
    //     elevatorMotorL.configure(
    //         elevatorConfigL,
    //         ResetMode.kResetSafeParameters,
    //         PersistMode.kPersistParameters
    //     );

    //     elevatorMotorR.configure(
    //         elevatorConfigR,
    //         ResetMode.kResetSafeParameters,
    //         PersistMode.kPersistParameters
    //     );

    //     elevatorEncoder.setPosition(0);
    // }

    // private void elevatorResetByButton() {
    //     if(!resetByButton && RobotController.getUserButton()) {
    //         resetByButton = true;
    //         elevatorEncoder.setPosition(0);
    //     } else if (!RobotController.getUserButton()) {
    //         resetByButton = false;
    //     }
    // }

    // private void elevatorResetByLimit() {
    //     //TODO: do it when get limit
    // }

    // private void elevatorToSetpoint() {
    //     elevatorController.setReference(elevatorCurrentTarget,
    //     ControlType.kMAXMotionPositionControl
    //     );
    // }

    // public Command setElevatorSetpoint(Setpoint setpoint) {
    //     return this.runOnce(
    //         () -> {
    //             switch (setpoint) {
    //                 case kFeederStation:
    //                     elevatorCurrentTarget = ElevatorSetpoints.kFeederStation;
    //                     break;
    //                 case L1:
    //                     elevatorCurrentTarget = ElevatorSetpoints.kL1;
    //                     break;
    //                 case L2:
    //                     elevatorCurrentTarget = ElevatorSetpoints.kL2;
    //                     break;
    //                 case L3:
    //                     elevatorCurrentTarget = ElevatorSetpoints.kL3;
    //                     break;
    //             }
    //         }
    //     );
    // }

    // @Override
    // public void periodic() {
    //     elevatorToSetpoint();

    //     SmartDashboard.putNumber("Elevator Target Position", elevatorCurrentTarget);
    //     SmartDashboard.putNumber("Elevator True Position", elevatorEncoder.getPosition());
    // }
}