package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Limelight extends SubsystemBase {
    private Swerve swerve;

    private double limelightYAngle;
    private Translation2d driveTranslation2d;
    private Translation2d leftTowerOffset;
    private Translation2d rightTowerOffset;

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight"); //name is set in limelight local
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");

    public Limelight(Swerve swerve) {
        leftTowerOffset = new Translation2d(-0.124, 0);
        rightTowerOffset = new Translation2d(0.124, 0);

        this.swerve = swerve;
    }

    private double getLimelightDistance() {
        limelightYAngle = + ty.getDouble(0);

        return ((Constants.Limelight.aprilTagHeight - Constants.Limelight.limelightHeight) / Math.tan(limelightYAngle)) - Constants.Limelight.limelightDepth;
    }

    private double getLimelightAngle() {
        return Constants.Limelight.limelightYaw + tx.getDouble(0);
    }

    public void driveLimelightCenter() {
        driveTranslation2d = new Translation2d(getLimelightDistance(), getLimelightAngle());

        swerve.drive(driveTranslation2d, getLimelightAngle(), false, true);
    }

    public void driveLimelightLeft() {
        driveTranslation2d = new Translation2d(getLimelightDistance(), getLimelightAngle()).plus(leftTowerOffset);

        swerve.drive(driveTranslation2d, getLimelightAngle(), false, true);
    }

    public void driveLimelightRight() {
        driveTranslation2d = new Translation2d(getLimelightDistance(), getLimelightAngle()).plus(rightTowerOffset);

        swerve.drive(driveTranslation2d, getLimelightAngle(), false, true);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Limelight Angle", getLimelightAngle());
        SmartDashboard.putNumber("Limelight Distance", getLimelightDistance());
    }
}