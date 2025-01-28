package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.TeleopSwerve;
import frc.robot.lib.util.AxisButton;
import frc.robot.subsystems.*;

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

  private final JoystickButton OpA = new JoystickButton(driver, XboxController.Button.kA.value);
  private final JoystickButton OpB = new JoystickButton(driver, XboxController.Button.kB.value);
  private final JoystickButton OpX = new JoystickButton(driver, XboxController.Button.kX.value);
  private final JoystickButton OpY = new JoystickButton(driver, XboxController.Button.kY.value);

  private final JoystickButton OpLeftBumper = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
  private final JoystickButton OpRightBumper = new JoystickButton(driver, XboxController.Button.kRightBumper.value);

  private final AxisButton OpLeftTrigger = new AxisButton(driver, XboxController.Axis.kLeftTrigger.value, 0.5);
  private final AxisButton OpRightTrigger = new AxisButton(driver, XboxController.Axis.kRightTrigger.value ,0.5);

  private final JoystickButton OpLeftStick = new JoystickButton(driver, XboxController.Button.kRightStick.value);
  private final JoystickButton OpRightStick = new JoystickButton(driver, XboxController.Button.kRightStick.value);

  private final POVButton OpPOVUp = new POVButton(driver, 180);
  private final POVButton OpPOVDown = new POVButton(driver, 0);
  private final POVButton OpPOVLeft = new POVButton(driver, 270);
  private final POVButton OpPOVRight = new POVButton(driver, 90);

  /* Subsystems */

  private final Swerve s_Swerve = new Swerve();

  public RobotContainer() {
    s_Swerve.setDefaultCommand(
      new TeleopSwerve(
        s_Swerve,
        () -> -driver.getRawAxis(translationAxis),
        () -> -driver.getRawAxis(strafeAxis),
        () -> -driver.getRawAxis(rotationAxis),
        () -> true
    ));

    /* Named Commands */

    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("AutoChooser", autoChooser);

    configureButtonBindings();
  }

  public void configureButtonBindings() {

  /* Driver Buttons */

  driveRightStick.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
  driveA.onTrue(new PathPlannerAuto("straightAuto2"));
  //driveA.onFalse(new InstantCommand(() -> s_Swerve.xPosition(false)));

  /* Operator Buttons */
  driveB.onTrue(new InstantCommand(() -> s_Swerve.resetEncoders()));

  }

  public Joystick getDriveController() {
    return driver;
  }

  public Joystick getOperatorController() {
    return operator;
  }

  public Command getAutonomousCommand() {
    // return autoChooser.getSelected();
    return new PathPlannerAuto("straightAuto2");
  }

  public void resetEncoders() {
    s_Swerve.resetEncoders();
  }
}