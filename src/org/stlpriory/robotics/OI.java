package org.stlpriory.robotics;

import org.stlpriory.robotics.commands.ExtendBallHolder;
import org.stlpriory.robotics.commands.PickUpBall;
import org.stlpriory.robotics.commands.PrepareToPickUpBall;
import org.stlpriory.robotics.commands.RetractBallHolder;
import org.stlpriory.robotics.commands.Shoot;
import org.stlpriory.robotics.commands.StopBallHolder;
import org.stlpriory.robotics.commands.StowBallHolder;
import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.ui.Gamepad;
import org.strongback.hardware.Hardware;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    private static final int CONTROLLER_PORT  = 1; // in driver station
    private final Gamepad gamepad;

    public OI() {
        SwitchReactor reactor = Strongback.switchReactor();
        this.gamepad = Hardware.HumanInterfaceDevices.logitechF310(CONTROLLER_PORT);
        
        reactor.whileTriggered(this.gamepad.getRightBumper(), ()->Strongback.submit(new RetractBallHolder(Robot.ballHolder)));
        reactor.whileUntriggered(this.gamepad.getRightBumper(), ()->Strongback.submit(new StopBallHolder(Robot.ballHolder)));

        reactor.whileTriggered(this.gamepad.getLeftBumper(), ()->Strongback.submit(new ExtendBallHolder(Robot.ballHolder)));
        reactor.whileUntriggered(this.gamepad.getLeftBumper(), ()->Strongback.submit(new StopBallHolder(Robot.ballHolder)));

        reactor.onTriggered(this.gamepad.getA(), ()->Strongback.submit(new StowBallHolder(Robot.ballHolder)));
        reactor.onTriggered(this.gamepad.getB(), ()->Strongback.submit(new PrepareToPickUpBall(Robot.ballHolder)));
        
        reactor.onTriggered(this.gamepad.getX(), ()->Strongback.submit(new PickUpBall(Robot.shooter)));
        reactor.onTriggered(this.gamepad.getY(), ()->Strongback.submit(new Shoot(Robot.shooter)));

    }
    
    public Gamepad getController() {
        return this.gamepad;
    }

}

