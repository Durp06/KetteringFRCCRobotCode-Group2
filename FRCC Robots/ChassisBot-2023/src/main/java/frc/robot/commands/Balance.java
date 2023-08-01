package frc.robot.commands;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;

public class Balance extends SequentialCommandGroup {
    private Timer timer;
    private double sides, durrationTime;
    private DriveSubsystem ss;
    private ADXRS450_Gyro gyro;

    
    public Balance(DriveSubsystem ss, ADXRS450_Gyro gyro){
        addRequirements(ss);
        this.gyro = gyro;

            addCommands(
                new GyroStraight(ss, 0.2, 3, gyro),
                new GyroStraight(ss, 0.2, 0.2, gyro)
            );
    }
}
