package circles;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Frame extends JFrame {

    Panel panel;
    File file;
    BufferedImage img;

    public Frame(String name, File file, int resolution) throws IOException {
        super(name);

        img = ImageIO.read(file);

        this.setSize(img.getWidth(), img.getHeight());
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        panel = new Panel(this.getSize(), img, resolution);
        this.add(panel, BorderLayout.CENTER);
    }

}
