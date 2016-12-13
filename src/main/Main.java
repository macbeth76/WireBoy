package main;

import FXTSwing.CreditsPage;
import FXTSwing.FXTButton;
import FXTSwing.InstructionsDialog;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.PI;
import static javax.swing.JFileChooser.APPROVE_OPTION;

/**
 * Created by Windows on 2016-11-13.
 */
public class Main {
    
    public static JFrame frame;
    static JButton chooseFile;
    static JButton save;
    static JButton instructions;
    static JButton credits;
    static JFileChooser files;
    static JPanel picture;
    static FileFilter xmlFilter;

    public static Color backgroundColor = Color.WHITE;
    public static Color foregroundColor = Color.BLUE;
    public static Color fixitGreen = new Color(0x79FF15);

    public static void main(String [] args){
        frame = new JFrame("WireBoy");
        frame.setLayout(new MigLayout("pack, flowy, center x"));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);

        frame.setIconImage(XMLParser.readImage("/otherImages/ftclogo.jpg"));

        picture = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                if(XMLParser.image != null){
                    g.drawImage(XMLParser.image, 0, 0, 760, 590, null);
                } else {
                    g2.setColor(backgroundColor);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(10));
                    g2.drawRect(0, 0, getWidth(), getHeight());
                }
            }
        };


        frame.add(picture, "w 760, h 590, wrap");

        instructions = new FXTButton("Instructions");
        instructions.addActionListener(new ButtonHandler());
        frame.add(instructions, "w 160, h 50, split 4");

        chooseFile = new FXTButton("Choose File");
        chooseFile.addActionListener(new ButtonHandler());
        frame.add(chooseFile, "w 160, h 50");

        save = new FXTButton("Save");
        save.addActionListener(new ButtonHandler());
        frame.add(save, "w 160, h 50");

        credits = new FXTButton("Credits");
        credits.addActionListener(new ButtonHandler());
        frame.add(credits, "w 160, h 50");

        files = new JFileChooser();
        files.setForeground(foregroundColor);
        files.setBackground(backgroundColor);

        xmlFilter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory()|| f.getName().endsWith(".xml");
            }

            @Override
            public String getDescription() {
                return ".XML";
            }
        };



        frame.pack();
        frame.setVisible(true);
    }

    private static class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource().equals(save) && XMLParser.image != null){
                files.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if(f.isDirectory()) return true;
                        return (f.getName().endsWith(".jpg") || f.getName().endsWith(".png"));
                    }

                    @Override
                    public String getDescription() {
                        return "Images";
                    }
                });
                files.setSelectedFile(new File(""));
                int result = files.showSaveDialog(frame);

                if(result == APPROVE_OPTION){
                    try {
                        File toWrite = files.getSelectedFile();
                        ImageIO.write(XMLParser.image, "png", new File(removeFileExtensions(toWrite.getAbsolutePath()) + ".png"));
                    } catch (IOException f) {
                        XMLParser.show("HELLO MY DARLING" + f.getMessage());
                    }
                }

            } else if(e.getSource().equals(chooseFile)){
                files.setFileFilter(xmlFilter);
                int result = files.showOpenDialog(frame);
                if(result == APPROVE_OPTION){
                    XMLParser.reset();
                    XMLParser.parseXML(files.getSelectedFile().getAbsolutePath());
                    XMLParser.generateImage(removeFileExtensions(files.getSelectedFile().getName()));
                    frame.repaint();
                }
            } else if(e.getSource().equals(instructions)){
                new InstructionsDialog();
            } else if(e.getSource().equals(credits)){
                new CreditsPage();
            }
        }
    }

    private static String removeFileExtensions(String s){
        System.out.println(s);
        String [] bits = s.split("\\.");
        System.out.println(bits[0]);
        return  bits[0];
    }

    public static class PointConstants {

        public final static Point powerModule = new Point(600, 500);
        public final static Point phone = new Point(725, 750);
        public final static Point battery = new Point(600, 750);


        public final static Point leftWireSplit = new Point(500, 380);
        public final static Point rightWireSplit = new Point(900, 380);

        public final static Point CABLE_LOC = new Point(150, 10);
        public final static Point USB_POWER_MODULE = new Point(powerModule.x + 240, powerModule.y + 100);
        public final static Point POWER_POWER_MODULE = new Point(powerModule.x + 250, powerModule.y + 147);
        public final static Point USB_PHONE = new Point(phone.x + 5, phone.y + 200);
        public final static Point POWER_BATTERY = new Point(battery.x + 50, battery.y);

        public final static Point CDIM_CABLE_LOC = new Point(50, 10);

        public final static Point CHILDREN_LIST_LOC = new Point(0, 50);

        public final static Point[] MODULE_LOC = {new Point(1000, 600),
                new Point(1000, 300),
                new Point(1000, 0),
                new Point(200, 0),
                new Point(200, 300),
                new Point(200, 600),
                new Point(200, 900)};

        public final static Point[] PORTS = {
                new Point(211, 30),
                new Point(183, 30),
                new Point(155, 30),
                new Point(127, 30),
                new Point(99, 30),
                new Point(71, 30),
                new Point(43, 30)};




    }
}
