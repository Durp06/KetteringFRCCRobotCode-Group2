package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class GyroTurn extends CommandBase {
    private Timer timer;
    private double rightSpeed, leftSpeed, turnDegrees;
    private DriveSubsystem ss;
    private ADXRS450_Gyro gyro = new ADXRS450_Gyro();


    public GyroTurn(DriveSubsystem ss, double turnDegrees, double speed, ADXRS450_Gyro gyro){
        addRequirements(ss);
        this.ss = ss;
        this.turnDegrees = turnDegrees;
        this.rightSpeed = speed;
        this.leftSpeed = speed;
        this.gyro = gyro;
        timer = new Timer();
    }

    @Override
    public void initialize(){
        double rspeed = rightSpeed;
        double lspeed = leftSpeed;
        boolean direction = false; //false = negative/left and true =

        if (turnDegrees > 0 )
            lspeed = -leftSpeed;
        else
            lspeed = leftSpeed;
        if (turnDegrees < 0)
            rspeed = -rightSpeed;
        else
            rspeed = rightSpeed;
        
        gyro.reset();

        timer.reset();
        timer.start();

        ss.tankDrive(lspeed, rspeed);
    }

    @Override
    public void execute(){
        double error = turnDegrees - gyro.getAngle();
        double output = error * 0.15;
        output = MathUtil.clamp(output, -rightSpeed, rightSpeed);
        
        ss.arcadeDrive(0, output);
    }

    @Override
    public void end(boolean interrupted){
        timer.stop();
        ss.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished(){
        double degrees = gyro.getRotation2d().getDegrees();

        return turnDegrees - Math.abs(degrees) < 3;
        }
}

