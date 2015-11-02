
/**
 * Created by Michael Haines on 12/28/2014.
 */
//import ch.aplu.xboxcontroller.*;
import messaging.IMessage;
import messaging.MotorMessage;

import java.io.IOException;

/**
 *
 *
 * @author USER
 */
public class xboxControllerTest {

    /*XboxController xc;
    private double throttleLeft = 0;
    private double throttleRight = 0;
    private double throttleHopper = 0;
    //public static BufferedReader input;
    //public static OutputStream output;
    //final serialTest test = new serialTest();

    public xboxControllerTest() {
        this.xc = new XboxController();
        xc.setLeftThumbDeadZone(.1);
        //final serialTest test = new serialTest();
        if (!xc.isConnected()) {
            System.out.println("Xbox controller not connected");
            xc.release();
            return;
        }
        xboxController();
    }
    public void xboxController(){
        xc.addXboxControllerListener(new XboxControllerAdapter() {

            public void leftThumbMagnitude(double value) {
                    throttleLeft = value;
                    //writeMessage(new MotorMessage(MotorMessage.motors.LEFT_FRONT_DRIVE_MOTOR, value));
            }
            public void leftThumbDirection(double magnitude){
                    writeMessage(new MotorMessage(MotorMessage.Motor.LEFT_BACK_DRIVE_MOTOR,
                            Math.abs(throttleLeft * Math.sin(magnitude))));
                    writeMessage(new MotorMessage(MotorMessage.Motor.LEFT_FRONT_DRIVE_MOTOR,
                            Math.abs(throttleLeft * Math.sin(magnitude))));
            }

            public void rightThumbMagnitude(double value){
                throttleRight = value;
            }
            public void rightThumbDirection(double magnitude){
                writeMessage(new MotorMessage(MotorMessage.Motor.RIGHT_BACK_DRIVE_MOTOR,
                        Math.abs(throttleRight * Math.sin(magnitude))));
                writeMessage(new MotorMessage(MotorMessage.Motor.RIGHT_FRONT_DRIVE_MOTOR,
                        Math.abs(throttleRight * Math.sin(magnitude))));
            }

            public void buttonA(boolean pressed){
                if (pressed){
                    writeMessage(new MotorMessage(MotorMessage.Motor.HOPPER_MOTOR, 1));
                }
                else if (!pressed){
                    writeMessage(new MotorMessage(MotorMessage.Motor.HOPPER_MOTOR, 0));
                }
            }

            public void rightTrigger (double value) {
                writeMessage(new MotorMessage(MotorMessage.Motor.LIFTER_MOTOR, value));

            }
            public void leftTrigger (double value){
                writeMessage(new MotorMessage(MotorMessage.Motor.LIFTER_MOTOR, -value));
            }
        });

        /*JOptionPane.showMessageDialog(null,
                "Xbox controller connected.\n"
                + "Press B",
                "RumbleDemo V1.0 (www.aplu.ch)",
                JOptionPane.PLAIN_MESSAGE);*/

/*
    }

    public void writeMessage(IMessage msg){
        try {
            serialTest.output.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*try {
            int c = 0;
            //serialTest test = new serialTest();

            new xboxControllerTest();
            InputStreamReader Ir = new InputStreamReader(System.in);
            BufferedReader Br = new BufferedReader(Ir);
        } catch (Exception e) {
        }


    }*/
}