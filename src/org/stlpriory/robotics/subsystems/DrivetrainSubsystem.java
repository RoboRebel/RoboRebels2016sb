package org.stlpriory.robotics.subsystems;

import org.strongback.Strongback;
import org.strongback.components.Motor;
import org.strongback.components.TalonSRX;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;

public class DrivetrainSubsystem {
    
    static final int LF_MOTOR_PORT = 1;
    static final int LR_MOTOR_PORT = 2;
    static final int RF_MOTOR_PORT = 3;
    static final int RR_MOTOR_PORT = 4;
    
    protected TankDrive drive;
    
    public DrivetrainSubsystem() {
        // Setup motors on each side in a master/slave arrangement
        TalonSRX leftMaster = Hardware.Motors.talonSRX(LF_MOTOR_PORT);
        Hardware.Motors.talonSRX(LR_MOTOR_PORT, leftMaster, false);
        
        TalonSRX rightMaster = Hardware.Motors.talonSRX(RF_MOTOR_PORT);
        Hardware.Motors.talonSRX(RR_MOTOR_PORT, rightMaster, false);
        
        Motor left  = (Motor)leftMaster;
        Motor right = (Motor)rightMaster.invert();

        this.drive = new TankDrive(left, right);

        // Set up the data recorder to capture the left & right motor speeds 
        // (since both motors on the same side should be at the same speed, we 
        // can just use the composed motors for each). We have to do this
        // before we start Strongback...
        Strongback.dataRecorder()
                  .register("Left motors", left)
                  .register("Right motors", right);
    }
    
    public TankDrive getDrive() {
        return this.drive;
    }
    
    protected void initSubsystem() {
        // Setup motors on each side in a master/slave arrangement
        TalonSRX leftMaster = Hardware.Motors.talonSRX(LF_MOTOR_PORT);
        Hardware.Motors.talonSRX(LR_MOTOR_PORT, leftMaster, false);
        
        TalonSRX rightMaster = Hardware.Motors.talonSRX(RF_MOTOR_PORT);
        Hardware.Motors.talonSRX(RR_MOTOR_PORT, rightMaster, false);
        
        Motor left  = (Motor)leftMaster;
        Motor right = (Motor)rightMaster.invert();

        this.drive = new TankDrive(left, right);

        // Set up the data recorder to capture the left & right motor speeds 
        // (since both motors on the same side should be at the same speed, we 
        // can just use the composed motors for each). We have to do this
        // before we start Strongback...
        Strongback.dataRecorder()
                  .register("Left motors", left)
                  .register("Right motors", right);
        
    }

}
