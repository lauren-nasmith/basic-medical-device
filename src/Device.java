import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Device {

    // UI Components
    private JButton buttonUp;
    private JButton buttonDown;
    private JButton buttonOk;
    private JButton buttonCancel;
    private JTextArea textAreaDisplay;
    private JPanel deviceView;

    private double infusionRate;
    private double volume;
    private double time;
    private State currentState;

    // State keeps track of what stage of input the device is in
    private enum State{
        INPUT_VOLUME,
        INPUT_TIME,
        OUTPUT_RATE
    }

    public Device() {
        // Have the current state as input volume, and the display asks for this
        currentState = State.INPUT_VOLUME;
        textAreaDisplay.setText("Volume in mg: ");
        infusionRate = 0.0;
        volume = 0.0;
        time = 0.0;
        // Set up listeners for click events for the device
        buttonUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onUpClicked();
            }
        });
        buttonDown.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onDownClicked();
            }
        });
        buttonOk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onOkClicked();
            }
        });
        buttonCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onCancelClicked();
            }
        });
    }

    /**
     * INPUT_VOLUME: sets volume to 0
     * INPUT_TIME: sets time to 0 first time, then returns to INPUT_VOLUME when time == 0
     * OUTPUT_RATE: returns to INPUT_TIME
     */
    private void onCancelClicked(){
        switch(currentState){
            case INPUT_VOLUME:
                volume = 0.0;
                textAreaDisplay.setText("Volume: " + this.volume + "mg");
                break;
            case INPUT_TIME:
                if(time == 0.0){
                    currentState = State.INPUT_VOLUME;
                    textAreaDisplay.setText("Volume: " + this.volume + "mg");
                }
                else {
                    time = 0.0;
                    textAreaDisplay.setText("Time: " + this.time + "mins");
                }
                break;
            case OUTPUT_RATE:
                currentState = State.INPUT_TIME;
                textAreaDisplay.setText("Time: " + this.time + "mins");
                break;
            default:
                break;
        }
    }

    /**
     * Submits the time/volume and calculates the rate of infusion if volume and time are valid
     */
    private void onOkClicked(){
        switch(currentState){
            case INPUT_VOLUME:
                // This is submitting the volume
                currentState = State.INPUT_TIME;
                textAreaDisplay.setText("Time in minutes: ");
                break;
            case INPUT_TIME:
                //This is submitting the time, so we calculate the rate
                currentState = State.OUTPUT_RATE;
                infusionRate = volume / time;
                textAreaDisplay.setText("Infusion Rate: " + String.format("%.2f", infusionRate) + "mg/min");
                break;
            default:
                break;
        }
    }

    /**
     * Decreases the volume by 1mg if the input stage is at INPUT_VOLUME
     * Decreases the time by 1min if the input stage is at INPUT_TIME
     */
    private void onDownClicked(){
        switch(currentState){
            case INPUT_VOLUME:
                if(volume > 0){
                    this.volume -= 1;
                    textAreaDisplay.setText("Volume: " + this.volume + "mg");
                }
                break;
            case INPUT_TIME:
                if(time > 0){
                    this.time -= 1;
                    textAreaDisplay.setText("Time: " + this.time + "mins");
                }
                break;
            default:
                break;
        }
    }

    /**
     * Increases the volume by 1mg if the input stage is at INPUT_VOLUME
     * Increases the time by 1min if the input stage is at INPUT_TIME
     */
    private void onUpClicked(){
        switch(currentState){
            case INPUT_VOLUME:
                this.volume += 1;
                // Update the display
                textAreaDisplay.setText("Volume: " + this.volume + "mg");
                break;
            case INPUT_TIME:
                this.time += 1;
                //Update the display
                textAreaDisplay.setText("Time: " + this.time + "mins");
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Medical Device");
        frame.setContentPane(new Device().deviceView);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
