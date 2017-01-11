package circles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements ActionListener {

    BufferedImage image;
    ArrayList<Circle> circles;
    Timer timer;
    static int Width = 0, Height = 0;
    boolean empty_space = true;
    int resolution;

    public Panel(Dimension s, BufferedImage img, int resolution) {
        super();
        setPreferredSize(s);
        setMinimumSize(s);
        image = img;
        timer = new Timer(30, this);
        circles = new ArrayList<Circle>();
        timer.start();
        this.resolution = resolution;
        Width = img.getWidth();
        Height = img.getHeight();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (empty_space) {
            for (int i = 0; i < resolution; i++) {
                int x;
                int y;
                int tries = 0;
                do {
                    x = (int) (Math.random() * (Width - 1) + 1);
                    y = (int) (Math.random() * (Height - 1) + 1);
                    tries++;
                } while (!GoodPoint(x, y) && tries < 1000);
                if (tries >= 1000) {
                    empty_space = false;
                    System.out.println("Finished");
                    Finished();
                    break;
                }
                // Getting pixel color by position x and y 
                int clr = image.getRGB(x, y);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;
                circles.add(new Circle(x, y, new Color(red, green, blue), circles));
            }
        }
        for (Circle c : circles) {
            c.update(g);
        }

    }

    private void Finished() {
        final JFileChooser fc = new JFileChooser();

        //From: http://stackoverflow.com/questions/12558413/how-to-filter-file-type-in-filedialog
        fc.addChoosableFileFilter(new OpenFileFilter("jpeg", "Photo in JPEG format"));
        fc.addChoosableFileFilter(new OpenFileFilter("jpg", "Photo in JPEG format"));
        fc.addChoosableFileFilter(new OpenFileFilter("png", "PNG image"));

        int returnVal = fc.showDialog(fc, "Save");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            BufferedImage saveImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = saveImage.createGraphics();
            for (Circle c : circles) {
                c.update(g2);
            }

            try {
                ImageIO.write(saveImage, getFileType(file.getName()), file);
                System.out.println("File save: " + file.getName() + " " + getFileType(file.getName()));
            } catch (IOException e) {
            }
        }
    }

    private String getFileType(String path) {
        String fileType = null;
        fileType = path.substring(path.indexOf('.', path.lastIndexOf('/')) + 1).toUpperCase();
        return fileType;
    }

    private boolean GoodPoint(int x, int y) {
        for (Circle c : circles) {
            if (c.inside(x, y)) {
                return false;
            }
        }
        return true;
    }

    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == timer) {
            repaint();// this will call at every 1 second
        }
    }

}
