package frc.robot.commands;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class GyroTurn extends CommandBase {
    private Timer timer;
    private double leftSpeed, rightSpeed, turnDegrees;
    private DriveSubsystem ss;
    private ADXRS450_Gyro gyro = new ADXRS450_Gyro();


    public GyroTurn(DriveSubsystem ss, double turnDegrees, double speed, ADXRS450_Gyro gyro){
        addRequirements(ss);
        this.ss = ss;
        this.turnDegrees = turnDegrees;
        this.leftSpeed = speed;
        this.rightSpeed = speed;
        this.gyro = gyro;
        timer = new Timer();
    }

    @Override
    public void initialize(){
        double lspeed = leftSpeed;
        double rspeed = rightSpeed;
        boolean direction = false; //false = negative/left and true = positive/right

        if (turnDegrees > 0 )
            rightSpeed = -rightSpeed;
        if (turnDegrees < 0)
            leftSpeed = -leftSpeed;
        
        gyro.reset();

        timer.reset();
        timer.start();

        ss.tankDrive(-rightSpeed, leftSpeed);
    }

    @Override
    public void execute(){}

    @Override
    public void end(boolean interrupted){
        timer.stop();
        ss.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished(){
        double degrees = gyro.getRotation2d().getDegrees();

        if (degrees >= turnDegrees){
            return true;
        }
        return false;
    }
}

