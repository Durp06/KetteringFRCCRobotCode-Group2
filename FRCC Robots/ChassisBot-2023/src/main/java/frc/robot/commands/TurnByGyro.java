package frc.robot.commands;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class TurnByGyro extends CommandBase {
    private DriveSubsystem global_ds;
    private ADXRS450_Gyro global_gyro;
    private double global_speed, global_turnDegrees;
  
  
    public TurnByGyro(DriveSubsystem driveSubsystem, ADXRS450_Gyro gyro, double turnDegrees, double speed) {
      addRequirements(driveSubsystem);
      global_ds = driveSubsystem;
      global_gyro = gyro;
      global_turnDegrees = turnDegrees;
      global_speed = speed;
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      // Calculate direction
      double speed = global_speed;
     
      // If we want to turn "backwards" but were given "forwards" speed, invert speed
            if ((global_turnDegrees < 0 && speed > 0) || (global_turnDegrees > 0 && speed < 0)) {
        speed = -speed;
      }
  
      // Reset Gyro
      global_gyro.reset();
     
      // Start Driving
      global_ds.arcadeDrive(0, global_speed);
    }
   // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    global_ds.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Read Gyro
    double degrees = global_gyro.getRotation2d().getDegrees();

    // If we're turning forwards
    boolean turningForward = global_turnDegrees > 0;

    // If we're turning forwards, we have reached destination if we're above or equal the reading degrees
    if(turningForward) {
      return degrees >= global_turnDegrees;
    } else { 
    // If we're turning backwards, we have reached our destination if we're below or equal to the reading degrees
      return degrees <= global_turnDegrees;
    }
  }

}
