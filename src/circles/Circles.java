package circles;

import java.io.File;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Circles {

    public static void main(String[] args) throws IOException {
        final JFileChooser fc = new JFileChooser();

        //From: http://stackoverflow.com/questions/12558413/how-to-filter-file-type-in-filedialog
        fc.addChoosableFileFilter(new OpenFileFilter("jpeg", "Photo in JPEG format"));
        fc.addChoosableFileFilter(new OpenFileFilter("jpg", "Photo in JPEG format"));
        fc.addChoosableFileFilter(new OpenFileFilter("png", "PNG image"));

        int returnVal = fc.showDialog(fc, "Open");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            JFrame parent = new JFrame();

            //From: http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJOptionPanewithaJSlider.htm
            JOptionPane optionPane = new JOptionPane();
            JSlider slider = getSlider(optionPane);

            optionPane.setMessage(new Object[]{"Select a value: ", slider});
            optionPane.setOptionType(JOptionPane.OK_OPTION);
            JDialog dialog = optionPane.createDialog(parent, "Resolution");
            dialog.setVisible(true);

            int res;
            if (optionPane.getInputValue() == optionPane.UNINITIALIZED_VALUE) {
                res = 500;
            } else {
                res = (int) optionPane.getInputValue();
            }
            System.out.println("resolution: " + res);

            Frame frame = new Frame("Circles", file, (res));
            frame.setVisible(true);
        }

    }

    //From: http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJOptionPanewithaJSlider.htm
    static JSlider getSlider(final JOptionPane optionPane) {
        JSlider slider = new JSlider();
        slider.setPaintLabels(false);
        slider.setMajorTickSpacing(100);
        slider.setMaximum(1001);
        slider.setValue(500);
        slider.setMinimum(1);
        slider.setPaintTicks(true);
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider theSlider = (JSlider) changeEvent.getSource();
                if (!theSlider.getValueIsAdjusting()) {
                    optionPane.setInputValue(new Integer(theSlider.getValue()));
                }
            }
        };
        slider.addChangeListener(changeListener);
        return slider;
    }
}
