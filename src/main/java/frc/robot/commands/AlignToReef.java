// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Swerve;

public class AlignToReef extends Command {
  private PIDController xController, yController, rotController;
  private boolean isRightScore;
  private Timer dontSeeTagTimer, stopTimer;
  private Swerve swerve;
  private double tagID = -1;

  public AlignToReef(boolean isRightScore, Swerve swerve) {
    xController = new PIDController(Constants.Limelight.limelightXP, 0.0, 0);
    yController = new PIDController(Constants.Limelight.limelightYP, 0.0, 0);
    rotController = new PIDController(Constants.Limelight.limelightRotP, 0, 0);
    this.isRightScore = isRightScore;
    this.swerve = swerve;
    
    //addRequirements(swerve);
  }

  @Override
  public void initialize() {
    this.stopTimer = new Timer();
    this.stopTimer.start();
    this.dontSeeTagTimer = new Timer();
    this.dontSeeTagTimer.start();

    rotController.setSetpoint(Constants.Limelight.limelightRotSetpoint);
    rotController.setTolerance(Constants.Limelight.limelightRotTolerance);

    xController.setSetpoint(Constants.Limelight.limelightXSetpoint);
    xController.setTolerance(Constants.Limelight.limelightXTolerance);

    yController.setSetpoint(isRightScore ? Constants.Limelight.limelightYSetpoint : -Constants.Limelight.limelightYSetpoint);
    yController.setTolerance(Constants.Limelight.limelightYTolerance);

    tagID = LimelightHelpers.getFiducialID("");
  }

  @Override
  public void execute() {
    if (LimelightHelpers.getTV("") && LimelightHelpers.getFiducialID("") == tagID) {
      this.dontSeeTagTimer.reset();

      double[] postions = LimelightHelpers.getBotPose_TargetSpace("");
      SmartDashboard.putNumber("x", postions[2]);

      double xSpeed = xController.calculate(postions[2]);
      SmartDashboard.putNumber("xspeed", xSpeed);
      double ySpeed = -yController.calculate(postions[0]);
      double rotValue = -rotController.calculate(postions[4]);

      swerve.drive(new Translation2d(xSpeed, ySpeed), rotValue, false, true);

      if (!rotController.atSetpoint() ||
          !yController.atSetpoint() ||
          !xController.atSetpoint()) {
        stopTimer.reset();
      }
    } else {
      swerve.drive(new Translation2d(), 0, false, true);
    }

    SmartDashboard.putNumber("poseValidTimer", stopTimer.get());
  }

  @Override
  public void end(boolean interrupted) {
    swerve.drive(new Translation2d(), 0, false, true);
  }

  @Override
  public boolean isFinished() {
    return this.dontSeeTagTimer.hasElapsed(Constants.Limelight.noTagTime) ||
        stopTimer.hasElapsed(Constants.Limelight.alignmentTime);
  }
}