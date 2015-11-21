import messaging.MotorMessage;
import net.java.games.input.Controller;

import javax.swing.*;
import java.util.List;

/**
 * Created by USER on 8/9/2015.
 */
public class SerialWorker extends SwingWorker<Integer, Integer> {
    inputControl xboxInterface;
    Controller control;

    public SerialWorker(Controller control){
        this.control = control;
    }

    protected Integer doInBackground() throws Exception {
        // Do a time-consuming task.

        xboxInterface = new inputControl();
        xboxInterface.controllerControl(control);
        return null;
    }

    public void kill(){
        xboxInterface.writeMessage(new MotorMessage(MotorMessage.Motor.COMBINE_MOTOR, 0));
        xboxInterface.writeMessage(new MotorMessage(MotorMessage.Motor.HOPPER_MOTOR, 0));
        xboxInterface.writeMessage(new MotorMessage(MotorMessage.Motor.RIGHT_FRONT_DRIVE_MOTOR, 0));
        xboxInterface.writeMessage(new MotorMessage(MotorMessage.Motor.LEFT_FRONT_DRIVE_MOTOR, 0));
        xboxInterface.writeMessage(new MotorMessage(MotorMessage.Motor.LIFTER_MOTOR, 0));
        xboxInterface.writeMessage(new MotorMessage(MotorMessage.Motor.SERVO_MOTOR, 0));
        xboxInterface.setIsRunning(false);
    }

    public void enable(){
        xboxInterface.setIsRunning(true);
    }

    protected void done(){

    }
}