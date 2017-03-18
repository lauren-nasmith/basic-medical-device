import javax.swing.*;

public class UI {
    private JButton buttonUp;
    private JButton buttonDown;
    private JButton buttonOk;
    private JButton buttonCancel;
    private JTextArea textAreaDisplay;
    private JPanel deviceView;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Medical Device");
        frame.setContentPane(new UI().deviceView);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
