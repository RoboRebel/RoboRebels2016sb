package org.stlpriory.robotics.subsystems;

import org.strongback.Strongback;
import org.strongback.components.Motor;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.CANTalon;

public class CANDrivetrainSubsystem extends DrivetrainSubsystem {

    static final double P = 0;
    static final double I = 0;
    static final double D = 0;
    static final double F = 0;
    
    public CANDrivetrainSubsystem() {
        initSubsystem();
    }
    
    protected void initSubsystem() {
        CANTalon leftFront  = createCANTalon(LF_MOTOR_PORT);
        CANTalon leftRear   = createCANTalon(LR_MOTOR_PORT);
        CANTalon rightFront = createCANTalon(RF_MOTOR_PORT);
        CANTalon rightRear  = createCANTalon(RR_MOTOR_PORT);

        // the number of encoder pulses per degree of revolution of the final shaft
        double pulsesPerDegree = 0.3333;
        // the number of turns of an analog pot or analog encoder over the 0-3.3V range; may be 0 if unused
        double analogTurnsOverVoltageRange = 0;
        
        Motor left = Motor.compose(Hardware.Motors.talonSRX(leftFront,   pulsesPerDegree, analogTurnsOverVoltageRange),
                                   Hardware.Motors.talonSRX(leftRear,    pulsesPerDegree, analogTurnsOverVoltageRange));
        Motor right = Motor.compose(Hardware.Motors.talonSRX(rightFront, pulsesPerDegree, analogTurnsOverVoltageRange),
                                    Hardware.Motors.talonSRX(rightRear,  pulsesPerDegree, analogTurnsOverVoltageRange))
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
    
    private CANTalon createCANTalon( int deviceNumber ) {
        CANTalon talon = new CANTalon(deviceNumber);
        talon.setPID(P, I, D, F, 0, 0, 0);
        talon.reverseSensor(true);
        return talon;
    }

}
