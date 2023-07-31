package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class TurnLeftCommand extends CommandBase {
    private Timer timer;
    private double leftSpeed, rightSpeed, durrationTime;
    private DriveSubsystem ss;
    
    public TurnLeftCommand(DriveSubsystem ss, double durrationTime, double leftSpeed, double rightSpeed){
        addRequirements(ss);
        this.ss = ss;
        this.durrationTime = durrationTime;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        timer = new Timer();
    }

    @Override
    public void initialize(){
        ss.tankDrive(rightSpeed, leftSpeed);
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

