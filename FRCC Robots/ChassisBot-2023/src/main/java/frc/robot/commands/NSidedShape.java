package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class NSidedShape extends CommandBase {
    private Timer timer;
    private double sides, durrationTime, degreesToTurn;
    private DriveSubsystem ss;
    
    public NSidedShape(DriveSubsystem ss, double sides){
        addRequirements(ss);
        this.ss = ss;
        this.sides = sides;
        this.durrationTime = durrationTime;
        this.degreesToTurn = degreesToTurn;
        timer = new Timer();
    }

    @Override
    public void initialize(){
        degreesToTurn = 2.5 / sides;
        durrationTime = 4;
        for(double i=sides ; i<0 ; --i) {
        new TimeDriveCommand(ss, 2, 0.5, 0.5);
        new TimeDriveCommand(ss, degreesToTurn, 0.5, -0.5);
        }        
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

