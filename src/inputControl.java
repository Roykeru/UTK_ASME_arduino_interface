import net.java.games.input.*;

/**
 * Created by USER on 7/12/2015.
 */

import messaging.IMessage;
import messaging.MotorMessage;

import java.io.IOException;

public class inputControl {

    private boolean isRunning = true;


    public void controllerControl(){

        double xaxis = 0;
        double yaxis = 0;
        double xrotation = 0;
        double yrotation = 0;
        double throttleLeft;
        double throttleRight;
        double leftDirection;
        double rightDirection;

        Controller[] ca = ControllerEnvironment.getDefaultEnvironment().getControllers();
        Controller xboxController = null;

        for (int i = 0; i < ca.length && xboxController == null; i++) {
            if (ca[i].getName().equals("XBOX 360 For Windows (Controller)")) {
                xboxController = ca[i];
                System.out.println(xboxController.getName());
            }
        }

        if (xboxController == null){
            System.out.print("No Xbox 360 controller found");
            return;
        }

        while(isRunning) {
            xboxController.poll();
            EventQueue queue = xboxController.getEventQueue();
            Event event = new Event();
            while(queue.getNextEvent(event)) {
                StringBuffer buffer = new StringBuffer(xboxController.getName());
                buffer.append(" at ");
                buffer.append(event.getNanos()).append(", ");
                Component comp = event.getComponent();
                buffer.append(comp.getName()).append(" changed to ");
                float value = event.getValue();
                switch (event.getComponent().toString()) {
                    case "Button 0":
                            if (value == 1.0f) {
                                writeMessage(new MotorMessage(MotorMessage.Motor.HOPPER_MOTOR, 1));
                                //System.out.println("A Button On");
                            } else {
                                writeMessage(new MotorMessage(MotorMessage.Motor.HOPPER_MOTOR, 0));
                                //System.out.println("A Button Off");
                            }
                        break;

                    case "Button 1":
                        if (value == 1.0f) {
                            //System.out.println("B Button On");
                        } else {
                            //System.out.println("B Button Off");
                        }
                        break;

                    case "Button 2":
                        if (value == 1.0f) {
                            //System.out.println("X Button On");
                        } else {
                            //System.out.println("X Button Off");
                        }
                        break;

                    case "Button 3":
                        if (value == 1.0f) {
                            //System.out.println("Y Button On");
                        } else {
                            //System.out.println("Y Button Off");
                        }
                        break;

                    case "X Axis":

                        xaxis = value;
                        throttleLeft = Math.sqrt(Math.pow(xaxis, 2) + Math.pow(yaxis, 2));
                        leftDirection = Math.tan(yaxis / xaxis);
                        if (throttleLeft > .2) {
                            writeMessage(new MotorMessage(MotorMessage.Motor.LEFT_BACK_DRIVE_MOTOR,
                                    Math.abs(throttleLeft * leftDirection)));
                            writeMessage(new MotorMessage(MotorMessage.Motor.LEFT_FRONT_DRIVE_MOTOR,
                                    Math.abs(throttleLeft * leftDirection)));
                            //System.out.println(String.format("left stick throttleLeft is %1$s and direction is %2$s", throttleLeft, leftDirection));
                        }
                        break;

                    case "Y Axis":
                        yaxis = value;
                        throttleLeft = Math.sqrt(Math.pow(xaxis, 2) + Math.pow(yaxis, 2));
                        leftDirection = Math.tan(yaxis / xaxis);
                        if (throttleLeft > .2) {
                            writeMessage(new MotorMessage(MotorMessage.Motor.LEFT_BACK_DRIVE_MOTOR,
                                    Math.abs(throttleLeft * leftDirection)));
                            writeMessage(new MotorMessage(MotorMessage.Motor.LEFT_FRONT_DRIVE_MOTOR,
                                    Math.abs(throttleLeft * leftDirection)));
                            //System.out.println(String.format("left stick throttleLeft is %1$s and direction is %2$s", throttleLeft, leftDirection));
                        }
                        break;

                    case "X Rotation":
                        xrotation = value;
                        throttleRight = Math.sqrt(Math.pow(xrotation, 2) + Math.pow(yrotation, 2));
                        rightDirection = Math.tan(yrotation / xrotation);
                        if (throttleRight > .2){
                            writeMessage(new MotorMessage(MotorMessage.Motor.RIGHT_BACK_DRIVE_MOTOR,
                                    Math.abs(throttleRight * rightDirection)));
                            writeMessage(new MotorMessage(MotorMessage.Motor.RIGHT_FRONT_DRIVE_MOTOR,
                                    Math.abs(throttleRight * rightDirection)));
                            //System.out.println(String.format("right stick throttleLeft is %1$s and direction is %2$s", throttleRight, rightDirection));

                        }
                        break;

                    case "Y Rotation":
                        yrotation = value;
                        throttleRight = Math.sqrt(Math.pow(xrotation, 2) + Math.pow(yrotation, 2));
                        rightDirection = Math.tan(yrotation / xrotation);
                        if (throttleRight > .2){
                            writeMessage(new MotorMessage(MotorMessage.Motor.RIGHT_BACK_DRIVE_MOTOR,
                                    Math.abs(throttleRight * rightDirection)));
                            writeMessage(new MotorMessage(MotorMessage.Motor.RIGHT_FRONT_DRIVE_MOTOR,
                                    Math.abs(throttleRight * rightDirection)));
                            //System.out.println(String.format("right stick throttleLeft is %1$s and direction is %2$s", throttleRight, rightDirection));

                        }
                        break;

                    case "Z Axis":
                        //System.out.println(String.format("Z Axis Magnitude is %s", value));
                        writeMessage(new MotorMessage(MotorMessage.Motor.LIFTER_MOTOR, value));
                        break;

                }
            }
        }
    }

    public void writeMessage(IMessage msg){
        try {
            byte[] test = msg.getBytes();
            for(int i = 0; i < msg.getBytes().length; i++){
                int positive = test[i] & 0xff;
                //System.out.print(positive);
                //System.out.print(' ');
            }
            //System.out.println(' ');
            serialTest.output.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIsRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

    public static void main(String[] args){
    }
}
