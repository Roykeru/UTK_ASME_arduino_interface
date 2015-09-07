import javax.swing.*;

/**
 * Created by USER on 8/9/2015.
 */
public class SerialWorker extends SwingWorker<Integer, Integer> {
    inputControl xboxInterface;

    protected Integer doInBackground() throws Exception {
        // Do a time-consuming task.
        xboxInterface = new inputControl();
        xboxInterface.controllerControl();
        return null;
    }

    public void kill(){
        xboxInterface.setIsRunning(false);
    }

    protected void done(){

    }
}