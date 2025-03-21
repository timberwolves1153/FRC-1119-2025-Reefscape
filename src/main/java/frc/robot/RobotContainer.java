package frc.robot;

import java.util.Set;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.hal.SimDevice.Direction;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.AlignCoral;
import frc.robot.commands.AlignToReef;
import frc.robot.commands.RunLEDs;
import frc.robot.commands.TeleopSwerve;
import frc.robot.lib.util.AxisButton;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Algae.PivotSetpoint;
import frc.robot.subsystems.Climber.ClimbSetpoint;
import frc.robot.subsystems.Elevator.ElevatorSetpoint;
import frc.robot.subsystems.Wrist.WristSetpoint;

public class RobotContainer {
  private final Joystick driver = new Joystick(0);
  private final Joystick operator = new Joystick(1);

  private final SendableChooser<Command> autoChooser;

  /* Driver Controls */

  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;

  /* Driver Buttons */

  private final JoystickButton driveA = new JoystickButton(driver, XboxController.Button.kA.value);
  private final JoystickButton driveB = new JoystickButton(driver, XboxController.Button.kB.value);
  private final JoystickButton driveX = new JoystickButton(driver, XboxController.Button.kX.value);
  private final JoystickButton driveY = new JoystickButton(driver, XboxController.Button.kY.value);

  private final JoystickButton driveLeftBumper = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
  private final JoystickButton driveRightBumper = new JoystickButton(driver, XboxController.Button.kRightBumper.value);

  private final AxisButton driveLeftTrigger = new AxisButton(driver, XboxController.Axis.kLeftTrigger.value, 0.5);
  private final AxisButton driveRightTrigger = new AxisButton(driver, XboxController.Axis.kRightTrigger.value ,0.5);

  private final JoystickButton driveLeftStick = new JoystickButton(driver, XboxController.Button.kLeftStick.value);
  private final JoystickButton driveRightStick = new JoystickButton(driver, XboxController.Button.kRightStick.value);

  private final POVButton DrivePOVUp = new POVButton(driver, 180);
  private final POVButton DrivePOVDown = new POVButton(driver, 0);
  private final POVButton DrivePOVLeft = new POVButton(driver, 270);
  private final POVButton DrivePOVRight = new POVButton(driver, 90);

  /* Operator Buttons */

  private final JoystickButton opA = new JoystickButton(operator, XboxController.Button.kA.value);
  private final JoystickButton opB = new JoystickButton(operator, XboxController.Button.kB.value);
  private final JoystickButton opX = new JoystickButton(operator, XboxController.Button.kX.value);
  private final JoystickButton opY = new JoystickButton(operator, XboxController.Button.kY.value);

  private final JoystickButton opLeftBumper = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
  private final JoystickButton opRightBumper = new JoystickButton(operator, XboxController.Button.kRightBumper.value);

  private final AxisButton opLeftTrigger = new AxisButton(operator, XboxController.Axis.kLeftTrigger.value, 0.5);
  private final AxisButton opRightTrigger = new AxisButton(operator, XboxController.Axis.kRightTrigger.value ,0.5);

  private final JoystickButton opLeftStick = new JoystickButton(operator, XboxController.Button.kLeftStick.value);
  private final JoystickButton opRightStick = new JoystickButton(operator, XboxController.Button.kRightStick.value);

  public final POVButton opPOVUp = new POVButton(operator, 0);
  public final POVButton opPOVDown = new POVButton(operator, 180);
  private final POVButton opPOVLeft = new POVButton(operator, 270);
  private final POVButton opPOVRight = new POVButton(operator, 90);

  /* Subsystems */

  private final Swerve s_Swerve = new Swerve();
  private final Algae algae = new Algae();
  private final Intake intake = new Intake();
  private final Elevator elevator = new Elevator();
  private final Wrist wrist = new Wrist();
  private final Climber climber = new Climber();
  private final LED LED = new LED();

  public RobotContainer() {
    s_Swerve.setDefaultCommand(
      new TeleopSwerve(
        s_Swerve,
        () -> -driver.getRawAxis(translationAxis),
        () -> -driver.getRawAxis(strafeAxis),
        () -> -driver.getRawAxis(rotationAxis),
        () -> true

    ));

    LED.setDefaultCommand(
      new RunLEDs(
        LED,
        () -> opPOVUp.getAsBoolean(),
        () -> opPOVDown.getAsBoolean(),
        () -> SmartDashboard.getNumber("Climber True Position", 0.0),
        () -> SmartDashboard.getNumber("Elevator True Position", 0.0)

    ));

    /* Named Commands */

    NamedCommands.registerCommand("Elevator Feeder", elevator.setElevatorSetpoint(ElevatorSetpoint.FeederStation));
    NamedCommands.registerCommand("Elevator L1", elevator.setElevatorSetpoint(ElevatorSetpoint.L1));
    NamedCommands.registerCommand("Elevator L2", elevator.setElevatorSetpoint(ElevatorSetpoint.L2));
    NamedCommands.registerCommand("Elevator L3", elevator.setElevatorSetpoint(ElevatorSetpoint.L3));

    NamedCommands.registerCommand("Wrist Feeder", wrist.setWristSetpoint(WristSetpoint.FeederStation));
    NamedCommands.registerCommand("Wrist L1", wrist.setWristSetpoint(WristSetpoint.L1));
    NamedCommands.registerCommand("Wrist L2", wrist.setWristSetpoint(WristSetpoint.L2));
    NamedCommands.registerCommand("Wrist L3", wrist.setWristSetpoint(WristSetpoint.L3));

    NamedCommands.registerCommand("Intake In", new InstantCommand(() -> intake.intakeIn(), intake));
    NamedCommands.registerCommand("Intake Out", new InstantCommand(() -> intake.intakeOut(), intake));
    NamedCommands.registerCommand("Intake Stop", new InstantCommand(() -> intake.intakeIdle(), intake));
    NamedCommands.registerCommand("Align Coral", new AlignCoral(intake));

    NamedCommands.registerCommand("Line Up Left", new AlignToReef(false, s_Swerve).withTimeout(0.5));
    NamedCommands.registerCommand("Line Up Right", new AlignToReef(true, s_Swerve).withTimeout(0.5));

    NamedCommands.registerCommand("Reset Gyro 0", new InstantCommand(() -> s_Swerve.zeroGyro(0), s_Swerve));
    NamedCommands.registerCommand("Reset Gyro 54", new InstantCommand(() -> s_Swerve.zeroGyro(54), s_Swerve));
    NamedCommands.registerCommand("Reset Gyro 60", new InstantCommand(() -> s_Swerve.zeroGyro(60), s_Swerve));
    NamedCommands.registerCommand("Reset Gyro 120", new InstantCommand(() -> s_Swerve.zeroGyro(120), s_Swerve));
    NamedCommands.registerCommand("Reset Gyro 126", new InstantCommand(() -> s_Swerve.zeroGyro(126), s_Swerve));
    NamedCommands.registerCommand("Reset Gyro 180", new InstantCommand(() -> s_Swerve.zeroGyro(180), s_Swerve));

    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("AutoChooser", autoChooser);

    configureButtonBindings();
  }

  public void configureButtonBindings() {

  /* Testing */

  // driveY.whileTrue(s_Swerve.sysIdQuasistatic(Direction.kForward));
  // driveA.whileTrue(s_Swerve.sysIdQuasistatic(Direction.kReverse));

  // driveX.whileTrue(s_Swerve.sysIdDynamic(Direction.kForward));
  // driveB.whileTrue(s_Swerve.sysIdDynamic(Direction.kReverse));

  driveLeftStick.onTrue(new InstantCommand(() -> s_Swerve.resetEncoders(), s_Swerve));

  /* Driver Buttons */

  driveRightStick.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro(0)));

  driveY.onTrue(new InstantCommand(() -> climber.climberUp(), climber));
  driveY.onFalse(new InstantCommand(() -> climber.climberStop(), climber));
  driveY.onTrue(algae.setPivotSetpoint(PivotSetpoint.Climb));
  driveA.onTrue(new InstantCommand(() -> climber.climberDown(), climber));
  driveA.onFalse(new InstantCommand(() -> climber.climberStop(), climber));

  // driveLeftTrigger.onTrue(new DeferredCommand(() -> new AlignToReef(false, s_Swerve), Set.of(s_Swerve)));
  // driveRightTrigger.onTrue(new DeferredCommand(() -> new AlignToReef(true, s_Swerve), Set.of(s_Swerve)));

	driveLeftTrigger.onTrue(new AlignToReef(false, s_Swerve).withTimeout(1));
  driveRightTrigger.onTrue(new AlignToReef(true, s_Swerve).withTimeout(1));

  /* Operator Buttons */
  
  opX.onTrue(elevator.setElevatorSetpoint(ElevatorSetpoint.FeederStation));
  opA.onTrue(elevator.setElevatorSetpoint(ElevatorSetpoint.L1));
  opB.onTrue(elevator.setElevatorSetpoint(ElevatorSetpoint.L2));
  opY.onTrue(elevator.setElevatorSetpoint(ElevatorSetpoint.L3));

  opX.onTrue(wrist.setWristSetpoint(WristSetpoint.FeederStation));
  opA.onTrue(wrist.setWristSetpoint(WristSetpoint.L1));
  opB.onTrue(wrist.setWristSetpoint(WristSetpoint.L2));
  opY.onTrue(wrist.setWristSetpoint(WristSetpoint.L3));

  opPOVUp.onTrue(new InstantCommand(() -> intake.intakeOut(), intake));
  opPOVUp.onFalse(new InstantCommand(() -> intake.intakeStop(), intake));
  opPOVDown.onTrue(new InstantCommand(() -> intake.intakeIn(), intake));
  opPOVDown.onFalse(new AlignCoral(intake));

  opRightBumper.onTrue(algae.setPivotSetpoint(PivotSetpoint.Collect));
  opRightTrigger.onTrue(algae.setPivotSetpoint(PivotSetpoint.Stow));
  
  opLeftBumper.onTrue(new InstantCommand(() -> algae.algaeIntake(), algae));
  opLeftBumper.onFalse(new InstantCommand(() -> algae.algaeIdle(), algae));
  opLeftTrigger.onTrue(new InstantCommand(() -> algae.algaeOuttake(), algae));
  opLeftTrigger.onFalse(new InstantCommand(() -> algae.algaeIdle(), algae));

  opLeftStick.onTrue(elevator.setElevatorSetpoint(ElevatorSetpoint.FeederStation));
  opLeftStick.onTrue(wrist.setWristSetpoint(WristSetpoint.FeederStation));
  opLeftStick.onTrue(algae.setPivotSetpoint(PivotSetpoint.Reset));
  opLeftStick.onTrue(climber.setClimberSetpoint(ClimbSetpoint.Reset));

  // opRightStick.onTrue(new InstantCommand(() -> elevator.elevatorResetByButton(), elevator));

  }

  public Joystick getDriveController() {
    return driver;
  }

  public Joystick getOperatorController() {
    return operator;
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
    // return new PathPlannerAuto("leaveAuto");
  }

  public void resetEncoders() {
    s_Swerve.resetEncoders();
  }
}