package org.stlpriory.robotics;

import org.stlpriory.robotics.commands.TimedDriveCommand;
import org.strongback.Strongback;
import org.strongback.components.Motor;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.Gamepad;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * A tank-drive robot controlled with a Logitech F310 XBox controller plugged into port 1 
 * on the Driver Station. The robot has two motors on each side, and each motor is controlled 
 * by one Talon motor controller.

 */
public class Robot extends IterativeRobot {

    private static final int GAMEPAD_PORT  = 1; // in driver station
    private static final int LF_MOTOR_PORT = 1;
    private static final int LR_MOTOR_PORT = 2;
    private static final int RF_MOTOR_PORT = 3;
    private static final int RR_MOTOR_PORT = 4;

    private static final double P = 0;
    private static final double I = 0;
    private static final double D = 0;
    private static final double F = 0;

    private TankDrive drive;
    private ContinuousRange leftSpeed;
    private ContinuousRange rightSpeed;
    
    // ==================================================================================
    //                            ROBOT INITIALIZATION
    // ==================================================================================

    @Override
    public void robotInit() {
        // Set up Strongback using its configurator. This is entirely optional, but we won't use 
        // events so it's slightly better if we turn them off. All other defaults are fine.
        Strongback.configure().recordNoEvents().initialize();

        // Set up the robot hardware ...
        initTankDrive();

        // Set up the human input controls for teleoperated mode. 
        // Left/right stick Y-values will return within the range of [-1,1]
        Gamepad gamepad = Hardware.HumanInterfaceDevices.logitechF310(GAMEPAD_PORT);
        this.leftSpeed  = gamepad.getLeftY();
        this.rightSpeed = gamepad.getRightY();
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }

    private void initTankDrive() {
        Motor left = Motor.compose(Hardware.Motors.talon(LF_MOTOR_PORT),
                                   Hardware.Motors.talon(LR_MOTOR_PORT));
        Motor right = Motor.compose(Hardware.Motors.talon(RF_MOTOR_PORT),
                                    Hardware.Motors.talon(RR_MOTOR_PORT))
                           .invert();
        this.drive = new TankDrive(left, right);

        // Set up the data recorder to capture the left & right motor speeds 
        // (since both motors on the same side should be at the same speed, we 
        // can just use the composed motors for each). We have to do this
        // before we start Strongback...
        Strongback.dataRecorder()
                  .register("Left motors", left)
                  .register("Right motors", right);
    }
    
    private void initCANTankDrive() {
        CANTalon leftFront  = createCANTalon(LF_MOTOR_PORT);
        CANTalon leftRear   = createCANTalon(LR_MOTOR_PORT);
        CANTalon rightFront = createCANTalon(RF_MOTOR_PORT);
        CANTalon rightRear  = createCANTalon(RR_MOTOR_PORT);

        // the number of encoder pulses per degree of revolution of the final shaft
        double pulsesPerDegree = 0.3333;
        // the number of turns of an analog pot or analog encoder over the 0-3.3V range; may be 0 if unused
        double analogTurnsOverVoltageRange = 0.0;
        
        Motor left = Motor.compose(Hardware.Motors.talonSRX(leftFront, pulsesPerDegree, analogTurnsOverVoltageRange),
                                   Hardware.Motors.talonSRX(leftRear, pulsesPerDegree, analogTurnsOverVoltageRange));
        Motor right = Motor.compose(Hardware.Motors.talonSRX(rightFront, pulsesPerDegree, analogTurnsOverVoltageRange),
                                    Hardware.Motors.talonSRX(rightRear, pulsesPerDegree, analogTurnsOverVoltageRange))
                           .invert();
        this.drive = new TankDrive(left, right);

        // Set up the data recorder to capture the left & right motor speeds 
        // (since both motors on the same side should be at the same speed, we 
        // can just use the composed motors for each). We have to do this
        // before we start Strongback...
        Strongback.dataRecorder()
                  .register("Left motors", left)
                  .register("Right motors", right);
    }
    
    private CANTalon createCANTalon(int deviceNumber) {
        CANTalon talon = new CANTalon(RR_MOTOR_PORT);
        talon.setPID(P, I, D, F, 0, 0, 0);
        talon.reverseSensor(true);
        return talon;
    }
    
    // ==================================================================================
    //                            AUTONOMOUS MODE
    // ==================================================================================

    @Override
    public void autonomousInit() {
        // Start Strongback functions ...
        Strongback.start();
        Strongback.submit(new TimedDriveCommand(drive, 0.5, 0.5, false, 5.0));
    }
    
    @Override
    public void autonomousPeriodic() {
    }
    
    // ==================================================================================
    //                            TELEOP MODE
    // ==================================================================================

    @Override
    public void teleopInit() {
        // Kill anything running if it is ...
        Strongback.disable();
        // Start Strongback functions if not already running...
        Strongback.start();
        // Record initial status values
        updateStatus();
    }

    @Override
    public void teleopPeriodic() {
        drive.tank(leftSpeed.read(), rightSpeed.read(), true);
        // Record updated status values
        updateStatus();
    }
    
    @Override
    public void disabledPeriodic() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
        // Record final status values
        updateStatus();
    }
    
    /**
     * Call the updateStatus methods on all subsystems
     */
    public void updateStatus() {
//        drivetrain.updateStatus();
//        ballHolder.updateStatus();
//        shooter.updateStatus();
    }

    @Override
    public void testInit() {
        LiveWindow.run();
    }
}
