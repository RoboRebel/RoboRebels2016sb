package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.hardware.AMOpticalEncoderSpecs;
import org.stlpriory.robotics.hardware.CIMMotorSpecs;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;

/**
 * Robot drive train subsystem consisting of 4 CIM motors configured in 2 master/slave arrangements. 
 * The motors are controlled by Talon SRX speed controllers through a CAN bus and encoder feedback.
 */
public class CANDrivetrainSubsystem extends DrivetrainSubsystem {

    public static final double P_VALUE = 0.5;
    public static final double I_VALUE = 0.02;
    public static final double D_VALUE = 0;
    public static final double F_VALUE = 0.5;
    public static final int IZONE_VALUE = (int) (0.2 * AMOpticalEncoderSpecs.PULSES_PER_REV);
    
    public static double RAMP_RATE = 2;
    
    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public CANDrivetrainSubsystem() {
        
        if (MASTER_SLAVE_MODE) {
            initFollowerTankDrive();
        } else {
            initTankDrive();
        }
    }
    
    // ==================================================================================
    //                    P R O T E C T E D   M E T H O D S
    // ==================================================================================

    @Override
    protected CANTalon createTalon(final int deviceNumber) {
        try {
            CANTalon talon = new CANTalon(deviceNumber);
            talon.changeControlMode(TalonControlMode.Speed);
            talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);

            talon.configNominalOutputVoltage(+0.0f, -0.0f);
            talon.configPeakOutputVoltage(+12.0f, -12.0f);

            talon.configEncoderCodesPerRev(AMOpticalEncoderSpecs.CYCLES_PER_REV);

            // keep the motor and sensor in phase
            talon.reverseSensor(false);

            // Soft limits can be used to disable motor drive when the sensor position
            // is outside of the limits
            talon.setForwardSoftLimit(CIMMotorSpecs.MAX_SPEED_RPM);
            talon.enableForwardSoftLimit(false);
            talon.setReverseSoftLimit(-CIMMotorSpecs.MAX_SPEED_RPM);
            talon.enableReverseSoftLimit(false);

            // brake mode: true for brake; false for coast
            talon.enableBrakeMode(true);

            // Voltage ramp rate, in volts/sec, which limits the rate at which the 
            // throttle will change. Affects all  modes.
            // For example, 0V to 6V in one sec would be a value of 6.0
//            talon.setVoltageRampRate(RAMP_RATE);

            // The allowable close-loop error whereby the motor output is neutral regardless
            // of the calculated result. When the closed-loop error is within the allowable
            // error the PID terms are zeroed (F term remains in effect) and the integral
            // accumulator is cleared. Value is in the same units as the closed loop error.
            // Initially make the allowable error 10% of a revolution
            int allowableClosedLoopErr = (int) (0.1 * AMOpticalEncoderSpecs.PULSES_PER_REV);
            talon.setAllowableClosedLoopErr(allowableClosedLoopErr);

            talon.setProfile(0);
            talon.setP(P_VALUE);
            talon.setI(I_VALUE);
            talon.setD(D_VALUE);
            talon.setF(F_VALUE);
            talon.setIZone(IZONE_VALUE);

            // Closed loop ramp rate, in volts/sec, which limits the rate at which the 
            // throttle will change. Only affects position and speed closed loop modes.
            // For example, 0V to 6V in one sec would be a value of 6.0
            talon.setCloseLoopRampRate(RAMP_RATE);
            return talon;

        } catch (Exception e) {
            throw e;
        }
    }

}
