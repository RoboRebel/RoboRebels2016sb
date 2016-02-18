package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.hardware.AMOpticalEncoderSpecs;
import org.strongback.Strongback;
import org.strongback.components.Motor;
import org.strongback.components.TalonSRX;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

/**
 * Robot drive train subsystem consisting of 4 CIM motors configured in 2 master/slave arrangements.
 * The motors are controlled by Talon SRX speed controllers wired for PWM control.
 */
public class DrivetrainSubsystem {
    
    public static final int LF_MOTOR_ID = 3;
    public static final int LR_MOTOR_ID = 4;
    public static final int RF_MOTOR_ID = 2;
    public static final int RR_MOTOR_ID = 1;

    public static final double P_VALUE = 0.5;
    public static final double I_VALUE = 0.02;
    public static final double D_VALUE = 0;
    public static final double F_VALUE = 0.5;
    public static final int IZONE_VALUE = (int) (0.2 * AMOpticalEncoderSpecs.PULSES_PER_REV);
    
    public static final boolean MASTER_SLAVE_MODE = true;
    
    protected TankDrive drive;
    
    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================
    
    public DrivetrainSubsystem() {
        
        if (MASTER_SLAVE_MODE) {
            initFollowerTankDrive();
        } else {
            initTankDrive();
        }
    }
    
    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================
    
    public TankDrive getDrive() {
        return this.drive;
    }

    // ==================================================================================
    //                    P R O T E C T E D   M E T H O D S
    // ==================================================================================

    protected void initTankDrive() {
        TalonSRX leftFront  = Hardware.Motors.talonSRX( createTalon(LF_MOTOR_ID) );
        TalonSRX leftRear   = Hardware.Motors.talonSRX( createTalon(LR_MOTOR_ID) );  
        
        TalonSRX rightFront = Hardware.Motors.talonSRX(createTalon(RF_MOTOR_ID) );
        TalonSRX rightRear  = Hardware.Motors.talonSRX( createTalon(RR_MOTOR_ID) );
        
        Motor left  = Motor.compose(leftFront, leftRear);
        Motor right = Motor.compose(rightFront, rightRear).invert();

        this.drive = new TankDrive(left, right);

        // Set up the data recorder to capture the left & right motor speeds 
        // (since both motors on the same side should be at the same speed, we 
        // can just use the composed motors for each). We have to do this
        // before we start Strongback...
        Strongback.dataRecorder()
                  .register("Left motors", left)
                  .register("Right motors", right);
        
    }
    
    protected void initFollowerTankDrive() {
        TalonSRX leftFront  = Hardware.Motors.talonSRX( createTalon(LF_MOTOR_ID) );
        Hardware.Motors.talonSRX(LR_MOTOR_ID, leftFront, false);
        
        TalonSRX rightFront = Hardware.Motors.talonSRX(createTalon(RF_MOTOR_ID) );
        Hardware.Motors.talonSRX(RR_MOTOR_ID, rightFront, false);
        
        Motor left  = (Motor)leftFront;
        Motor right = (Motor)rightFront.invert();

        this.drive = new TankDrive(left, right);

        // Set up the data recorder to capture the left & right motor speeds 
        // (since both motors on the same side should be at the same speed, we 
        // can just use the composed motors for each). We have to do this
        // before we start Strongback...
        Strongback.dataRecorder()
                  .register("Left motors", left)
                  .register("Right motors", right);
    }
    
    protected CANTalon createTalon(final int deviceNumber) {
        try {
            CANTalon talon = new CANTalon(deviceNumber);
            talon.changeControlMode(TalonControlMode.PercentVbus);

            talon.configNominalOutputVoltage(+0.0f, -0.0f);
            talon.configPeakOutputVoltage(+12.0f, -12.0f);

            // brake mode: true for brake; false for coast
            talon.enableBrakeMode(true);

            // Voltage ramp rate in volts/sec (works regardless of mode)
            // (e.g. setVoltageRampRate(6.0) results in 0V to 6V in one sec)
            talon.setVoltageRampRate(6.0);

            return talon;

        } catch (Exception e) {
            throw e;
        }
    }

}
