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
        xboxInterface.setIsRunning(false);
    }

    public void enable(){
        xboxInterface.setIsRunning(true);
    }

    protected void done(){

    }
}