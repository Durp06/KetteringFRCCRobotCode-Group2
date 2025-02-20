// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.AvalButtons;
import frc.robot.Constants.AvalDriveModes;
import frc.robot.commands.AuxCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.GyroStraight;
import frc.robot.commands.GyroTurn;
import frc.robot.commands.NSidedShape;
import frc.robot.commands.TimeDriveCommand;
import frc.robot.commands.TurnByGyro;
import frc.robot.subsystems.AuxSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final AuxSubsystem m_aux1 = new AuxSubsystem(4);
  private final AuxSubsystem m_aux2 = new AuxSubsystem(5);
  private final AuxSubsystem m_aux3 = new AuxSubsystem(6);
  private final AuxSubsystem m_aux4 = new AuxSubsystem(7);
  private final AuxSubsystem m_aux5 = new AuxSubsystem(8);
  private final AuxSubsystem m_aux6 = new AuxSubsystem(9);

  // Joystick
  private final Joystick m_stick0 = new Joystick(0);
  private final Joystick m_stick1 = new Joystick(1);

  // Default Commands
  private final DriveCommand m_driveCommand = new DriveCommand(m_driveSubsystem, m_stick0, m_stick1);
  private final AuxCommand m_aux1Command = new AuxCommand(m_aux1, m_stick0, m_stick1, "PWM4");
  private final AuxCommand m_aux2Command = new AuxCommand(m_aux2, m_stick0, m_stick1, "PWM5");
  private final AuxCommand m_aux3Command = new AuxCommand(m_aux3, m_stick0, m_stick1, "PWM6");
  private final AuxCommand m_aux4Command = new AuxCommand(m_aux4, m_stick0, m_stick1, "PWM7");
  private final AuxCommand m_aux5Command = new AuxCommand(m_aux5, m_stick0, m_stick1, "PWM8");
  private final AuxCommand m_aux6Command = new AuxCommand(m_aux6, m_stick0, m_stick1, "PWM9");

  private SendableChooser<Command> autonSelector = new SendableChooser<>();

  private ADXRS450_Gyro gyro = new ADXRS450_Gyro();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer(Random rand) {
    // Configure the button bindings
    configureButtonBindings();

    // Set Default Commands
    setDefaultCommands();

    // Setup shuffleboard "Main Dashboard"
    setupShuffleboardDashboard();

    autonSelector.addOption("Better Gyro Turn", new GyroTurn(m_driveSubsystem, 180, 0.5, gyro));


    autonSelector.addOption("GyroStraight", new GyroStraight(m_driveSubsystem, 0.3, 3, gyro));

    //autonSelector.addOption("Gyro Turn", new TurnByGyro(m_driveSubsystem, gyro, 90, 0.5));

    autonSelector.addOption("Straight", new TimeDriveCommand(m_driveSubsystem, 3, 0.3, 0.3));
    
    autonSelector.addOption("Straight n' turn", new SequentialCommandGroup(
      new TimeDriveCommand(m_driveSubsystem, 3, 0.3, 0.3),
      new TimeDriveCommand(m_driveSubsystem, 3, -0.3, 0.3),
      new TimeDriveCommand(m_driveSubsystem, 3, 0.3, 0.3)
      ));

      autonSelector.addOption("NSidedShape", new NSidedShape(m_driveSubsystem,  5, gyro));
      autonSelector.addOption("NSidedRand", new NSidedShape(m_driveSubsystem, (int) rand.nextDouble() * Integer.MAX_VALUE, gyro));
      //Use that for when you want to do horrible things to the robot
    SmartDashboard.putData("Auton Selector", autonSelector);

    gyro.calibrate();
  }

  // Set Subsystem Default Commands
  public void setDefaultCommands() {
    m_driveSubsystem.setDefaultCommand(m_driveCommand);

    m_aux1.setDefaultCommand(m_aux1Command);
    m_aux2.setDefaultCommand(m_aux2Command);
    m_aux3.setDefaultCommand(m_aux3Command);
    m_aux4.setDefaultCommand(m_aux4Command);
    m_aux5.setDefaultCommand(m_aux5Command);
    m_aux6.setDefaultCommand(m_aux6Command);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

  }

  private void setupShuffleboardDashboard() {
    ShuffleboardTab primaryTab = Shuffleboard.getTab("Main Dashboard");

    // Preconfigured bots
    ShuffleboardLayout preconfBots = primaryTab.getLayout("Preconfigured Bots", BuiltInLayouts.kList)
    .withSize(2, 4)
    .withPosition(0, 0);
    // .withProperties(Map.of("Label position", "HIDDEN")); // hide labels for commands

    preconfBots.add("Seperated Aux Motors |A-Y, Triggers|", new InstantCommand(() -> {
      m_aux1Command.setButtons(AvalButtons.A_Y);
      m_aux2Command.setButtons(AvalButtons.Triggers);
      m_aux1Command.setInverted(false);
      m_aux2Command.setInverted(false);

    }));

    preconfBots.add("Linked Inline Aux Motors |Triggers|", new InstantCommand(() -> {
      m_aux1Command.setButtons(AvalButtons.Triggers);
      m_aux2Command.setButtons(AvalButtons.Triggers);
      m_aux1Command.setInverted(false);
      m_aux2Command.setInverted(false);
    }));

    preconfBots.add("Linked Opposing Aux Motors |Triggers|", new InstantCommand(() -> {
      m_aux1Command.setButtons(AvalButtons.Triggers);
      m_aux2Command.setButtons(AvalButtons.Triggers);
      m_aux1Command.setInverted(false);
      m_aux2Command.setInverted(true);
    }));


    // BYOB (Build your own bot)
    ShuffleboardLayout preconfDriveModes = primaryTab.getLayout("Quick Drive Modes", BuiltInLayouts.kList)
    .withSize(2, 4)
    .withPosition(2, 0);
    // .withProperties(Map.of("Label position", "HIDDEN")); // hide labels for commands

    preconfDriveModes.add("Arcade", new InstantCommand(() -> {
      m_driveCommand.setDriveMode(AvalDriveModes.Arcade);
    }));

    preconfDriveModes.add("Tank", new InstantCommand(() -> {
      m_driveCommand.setDriveMode(AvalDriveModes.Tank);
    }));

    preconfDriveModes.add("Swap Front-Back", new InstantCommand(() -> {
      m_driveCommand.swapInverted();
    }));    

    ShuffleboardLayout preconfIntakeModes = primaryTab.getLayout("Preconfigured Intakes |A-Y|", BuiltInLayouts.kList)
    .withSize(2, 4)
    .withPosition(4, 0);
    // .withProperties(Map.of("Label position", "HIDDEN")); // hide labels for commands

    preconfIntakeModes.add("Single Motor |PWM 4|", new InstantCommand(() -> {
      m_aux1Command.setButtons(AvalButtons.A_Y);
      m_aux1Command.setInverted(false);
    }));

    preconfIntakeModes.add("Dual Opposing Motors |PWM 4-5|", new InstantCommand(() -> {
      m_aux1Command.setButtons(AvalButtons.A_Y);
      m_aux2Command.setButtons(AvalButtons.A_Y);
      m_aux1Command.setInverted(false);
      m_aux2Command.setInverted(true);
    }));

    preconfIntakeModes.add("Dual Inline Motors |PWM 4-5|", new InstantCommand(() -> {
      m_aux1Command.setButtons(AvalButtons.A_Y);
      m_aux2Command.setButtons(AvalButtons.A_Y);
      m_aux1Command.setInverted(false);
      m_aux2Command.setInverted(false);
    }));

    preconfIntakeModes.add("Reverse Direction", new InstantCommand(() -> {
      m_aux1Command.swapInverted();
      m_aux2Command.swapInverted();
    }));

    ShuffleboardLayout preconfShooterModes = primaryTab.getLayout("Preconfigured Shooters |Triggers|", BuiltInLayouts.kList)
    .withSize(2, 4)
    .withPosition(6, 0);
    // .withProperties(Map.of("Label position", "HIDDEN")); // hide labels for commands

    preconfShooterModes.add("Single Motor |PWM 6|", new InstantCommand(() -> {
      m_aux3Command.setButtons(AvalButtons.Triggers);
      m_aux3Command.setInverted(false);
    }));

    preconfShooterModes.add("Dual Opposing Motors |PWM 6-7|", new InstantCommand(() -> {
      m_aux3Command.setButtons(AvalButtons.Triggers);
      m_aux4Command.setButtons(AvalButtons.Triggers);
      m_aux3Command.setInverted(false);
      m_aux4Command.setInverted(true);
    }));

    preconfShooterModes.add("Dual Inline Motors |PWM 6-7|", new InstantCommand(() -> {
      m_aux3Command.setButtons(AvalButtons.Triggers);
      m_aux4Command.setButtons(AvalButtons.Triggers);
      m_aux3Command.setInverted(false);
      m_aux4Command.setInverted(false);
    }));

    preconfShooterModes.add("Reverse Direction", new InstantCommand(() -> {
      m_aux3Command.swapInverted();
      m_aux4Command.swapInverted();
    }));
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autonSelector.getSelected();
  }
}
