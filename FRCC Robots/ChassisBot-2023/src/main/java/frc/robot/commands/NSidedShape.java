package frc.robot.commands;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;

public class NSidedShape extends SequentialCommandGroup {
    private Timer timer;
    private double sides, durrationTime;
    private DriveSubsystem ss;

    private ADXRS450_Gyro gyro;
    public NSidedShape(DriveSubsystem ss, double sides, ADXRS450_Gyro gyro){
        addRequirements(ss);
        // this.ss = ss;
        // this.sides = sides;
<<<<<<< HEAD
        this. gyro = gyro;
        //timer = new Timer();
        degreesToTurn = 2.5 / sides;
        durrationTime = 20;
        //timer.reset();
        //timer.start();
=======
        timer = new Timer();
        durrationTime = 10;
        timer.reset();
        timer.start();
>>>>>>> d7dc1f942315c656bcd8dbed57126b1cb87e239a

        for (int i=0; i<sides; i++){
            addCommands(
                new TimeDriveCommand(ss, sides/4, 0.3, 0.3),
                new GyroTurn(ss, 360/sides, .3, gyro)
            );
            //if (timer.get()>=durrationTime){
            //    break;
            //}
        }
    }
    
    // @Override
    // public void initialize(){
    // degreesToTurn = 2.5 / sides;
    // durrationTime = 10;
    // for(double i=sides ; i==0 ; --i) {
    // new TimeDriveCommand(ss, 2, 0.5, 0.5);
    // new TimeDriveCommand(ss, degreesToTurn, 0.5, -0.5);
    // }
    // timer.reset();
    // timer.start();
    // }

    // @Override
    // public void execute(){}

    // @Override
    // public void end(boolean interrupted){
    // timer.stop();
    // ss.tankDrive(0, 0);
    // }

    // @Override
    // public boolean isFinished(){
    // return timer.get()>=durrationTime;
    // }
}
