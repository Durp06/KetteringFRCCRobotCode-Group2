package frc.robot.commands;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class GyroStraight extends CommandBase {
    private Timer timer;
    private double rightSpeed, leftSpeed, driveTime;
    private DriveSubsystem ss;
    private ADXRS450_Gyro gyro;

    private final double DEVIATION_DEGREES = 3;
    private final double CORRECTION_SPEED = 0.3;

    public GyroStraight(DriveSubsystem ss, double speed, double driveTime, ADXRS450_Gyro gyro){
        addRequirements(ss);
        this.ss = ss;
        this.rightSpeed = speed;
        this.leftSpeed = speed;
        this.gyro = gyro;
        this.driveTime = driveTime;
        timer = new Timer();
    }

    @Override
    public void initialize(){
        double rspeed = rightSpeed;
        double lspeed = leftSpeed;
        
        gyro.reset();

        timer.reset();
        timer.start();

        ss.tankDrive(leftSpeed, rightSpeed);
    }

    @Override
    public void execute(){
        double degrees = gyro.getRotation2d().getDegrees();

        if (degrees >= DEVIATION_DEGREES){
            ss.tankDrive(leftSpeed + CORRECTION_SPEED, rightSpeed);
        } else if(degrees <= -DEVIATION_DEGREES){
            ss.tankDrive(leftSpeed, rightSpeed + CORRECTION_SPEED);
        } else {
            ss.tankDrive(leftSpeed, rightSpeed);
        }
    
    }

    @Override
    public void end(boolean interrupted){
        timer.stop();
        ss.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished(){
        return timer.get()>=driveTime;

    }
}

