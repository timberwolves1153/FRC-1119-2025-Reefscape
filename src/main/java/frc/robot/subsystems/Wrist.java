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
import frc.robot.subsystems.Elevator.Setpoint;

public class Wrist extends SubsystemBase {
    // private double wristCurrentTarget = ElevatorSetpoints.FeederStation;
    
    // private SparkFlex wristMotor;

    // private SparkClosedLoopController wristController = wristMotor.getClosedLoopController();
    // private RelativeEncoder wristEncoder = wristMotor.getEncoder();

    // public Wrist() {
    //     wristMotor = new SparkFlex(54, MotorType.kBrushless);

    //     wristMotor.configure(
    //         Configs.Wrist.wristConfig,
    //         ResetMode.kResetSafeParameters,
    //         PersistMode.kPersistParameters
    //     );

    //     wristEncoder.setPosition(0);
    // }

    // private void wristToSetpoint() {
    //     wristController.setReference(wristCurrentTarget, ControlType.kMAXMotionPositionControl);
    // }

    // public Command setWristSetpoint(Setpoint setpoint) {
    //     return this.runOnce(
    //         () -> {
    //             switch (setpoint) {
    //                 case FeederStation:
    //                     wristCurrentTarget = WristSetpoints.FeederStation;
    //                     break;
    //                 case L1:
    //                     wristCurrentTarget = WristSetpoints.L1;
    //                     break;
    //                 case L2:
    //                     wristCurrentTarget = WristSetpoints.L2;
    //                     break;
    //                 case L3:
    //                     wristCurrentTarget = WristSetpoints.L3;
    //                     break;
    //             }
    //         }
    //     );
    // }

    // @Override
    // public void periodic() {
    //     wristToSetpoint();

    //     SmartDashboard.putNumber("Wrist Target Position", wristCurrentTarget);
    //     SmartDashboard.putNumber("Wrist True Position", wristEncoder.getPosition());
    // } 
}