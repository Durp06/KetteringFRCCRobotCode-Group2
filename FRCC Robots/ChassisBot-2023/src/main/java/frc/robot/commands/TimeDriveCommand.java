package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class TimeDriveCommand extends CommandBase {
    private Timer timer;
    private double rightSpeed, leftSpeed, durrationTime;
    private DriveSubsystem ss;

    public TimeDriveCommand(DriveSubsystem ss, double durrationTime, double rightSpeed, double leftSpeed){
        addRequirements(ss);
        this.ss = ss;
        this.durrationTime = durrationTime;
        this.rightSpeed = rightSpeed;
        this.leftSpeed = leftSpeed;
        timer = new Timer();
    }

    @Override
    public void initialize(){
        ss.tankDrive(leftSpeed, rightSpeed);
        timer.reset();
        timer.start();
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
        return timer.get()>=durrationTime;
    }
}

