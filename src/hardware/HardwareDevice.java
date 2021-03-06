package hardware;

import main.Main.PointConstants;
import main.XMLParser;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Windows on 2016-10-23.
 */
public abstract class HardwareDevice {
    BufferedImage image;
    int port = 0;
    protected int drawingX = 0;
    String name = "";
    Point loc;
    Point cablePoint;
    protected ArrayList<HardwareDevice> children;
    public String serialNumber = "";
    public static Color greyText = new Color(0x808080);
    static Color greyLine = new Color(0xADADAD);
    static Color greySerial = new Color(0x696969);

    public HardwareDevice(String name, int port){

        this.name = name;
        this.port = port;

        cablePoint = new Point(170, 0);
        children = new ArrayList<>();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void drawModule(Graphics2D g){
        Color c = g.getColor();

        Point pt = loc;

        AffineTransform at = new AffineTransform();

        at.translate(pt.x, pt.y);

        if (isOnLeft()) {
            at.rotate(Math.PI / 2, image.getWidth() / 2, image.getHeight() / 2);
            cablePoint = new Point(190, PointConstants.CABLE_LOC.x);
        } else {
            at.rotate(-Math.PI / 2, image.getWidth() / 2, image.getHeight() / 2);
            cablePoint = new Point(10, 200 - PointConstants.CABLE_LOC.x);
        }//else

        if (image != null) {
            g.drawImage(image, at, null);
        } else {
            g.drawRect(pt.x, pt.y, 200, 200);
        }//else

        drawWires(g);

        g.setStroke(new BasicStroke(2));
        g.setFont(g.getFont().deriveFont(Font.BOLD, 16));
        drawNames(g);
    }

    public void drawNames(Graphics g){
        int childrenY = loc.y + 30;
        if (isOnLeft()) {
            g.setColor(greySerial);
            g.drawString(serialNumber, loc.x - 130, loc.y + 30);
            g.drawString(name, loc.x - 130, loc.y + 10);

            g.setColor(greyText);
            for (HardwareDevice child : children) {
                g.drawString(child.port + "    " + child.getName(), loc.x - 150, childrenY += 20);
            }//for

            g.setColor(greyLine);
            g.drawLine(loc.x - 150, loc.y + 35, loc.x - 50, loc.y + 35);
            g.drawLine(loc.x - 135, loc.y, loc.x - 135, loc.y + 155);
        } else {
            g.setColor(greySerial);
            g.drawString(serialNumber, loc.x + 250, loc.y + 30);
            g.drawString(name, loc.x + 250, loc.y + 10);

            g.setColor(greyText);
            for (HardwareDevice child : children) {
                g.drawString(child.port + "    " + child.getName(), loc.x + 230, childrenY += 20);
            }//for

            g.setColor(greyLine);
            g.drawLine(loc.x + 230, loc.y + 35, loc.x + 330, loc.y + 35);
            g.drawLine(loc.x + 245, loc.y, loc.x + 245, loc.y + 155);
        }//else
    }

    public boolean isOnLeft() {
        return port > 2;
    }

    protected void drawWires(Graphics2D g){

        Point portLoc = PointConstants.PORTS[port];
        Point powerModule = PointConstants.powerModule;
        Point lWire = PointConstants.leftWireSplit;
        Point rWire = PointConstants.rightWireSplit;

        int PADDING = 20;

        g.setStroke(new BasicStroke(5));

        int level = (isOnLeft())? (5 - (port - 2)) : (port + 1);

        g.drawLine(portLoc.x + powerModule.x, portLoc.y + powerModule.y, portLoc.x + powerModule.x, powerModule.y + portLoc.y - 40 * level);

        if (isOnLeft()) {
            g.drawLine(portLoc.x + powerModule.x, powerModule.y + portLoc.y - 40 * level, lWire.x + (5 - level * PADDING), powerModule.y + portLoc.y - 40 * level);
            g.drawLine(lWire.x + (5 - level * PADDING), powerModule.y + portLoc.y - 40 * level, lWire.x + (5 - level * PADDING), loc.y + cablePoint.y);
            g.drawLine(lWire.x + (5 - level * PADDING), loc.y + cablePoint.y, loc.x + cablePoint.x, loc.y + cablePoint.y);
        } else {
            g.drawLine(portLoc.x + powerModule.x, powerModule.y + portLoc.y - 40 * level, rWire.x - (level * PADDING), powerModule.y + portLoc.y - 40 * level);
            g.drawLine(rWire.x - (level * PADDING), powerModule.y + portLoc.y - 40 * level, rWire.x - (level * PADDING), loc.y + cablePoint.y);
            g.drawLine(rWire.x - (level * PADDING), loc.y + cablePoint.y, loc.x + cablePoint.x, loc.y + cablePoint.y);
        }//else



    }

    public String getName(){
        return name;
    }


    protected void drawStringInBox(Graphics g, String s, int x, int y, int width, int height, int padding){
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        g.drawRect(x + padding, y + padding, width - 2 * padding, height - 2 * padding);
        int stringWidth = strSize(g, s);
        int fontSize = g.getFontMetrics().getHeight();

        if(stringWidth > width - 2 * padding){
            String [] words = s.split(" ");

            String line = "";
            int numLines = 1;
            for (int j = 0; j < words.length; j++) {
                System.out.println(words[j]);
                if (strSize(g, line + " " + words[j]) > width - 2 * padding) {
                    if((padding + fontSize) * numLines > height) break;
                    g.drawString(line, x + width / 2 - strSize(g, line) / 2, y + (padding + fontSize) * numLines);
                    numLines++;
                    line = words[j];
                } else {
                    line += " " + words[j];
                }

                if(j == words.length - 1){
                    g.drawString(line, x + width / 2 - strSize(g, line) / 2, y + (padding + fontSize) * numLines);
                }
            }

        } else {
            g.drawString(s, x + width / 2 - stringWidth / 2, y + padding + fontSize);
        }



    }

    protected int strSize(Graphics g, String str){
        return g.getFontMetrics().stringWidth(str);
    }

    public void setName(String name){
        this.name = name;
    }

    public void addChild(HardwareDevice h){
        children.add(h);
    }

    public String toString(){
        String childrenName = "";
        for(HardwareDevice h: children){
            childrenName += "\n    " + h;
        }
        return "Name: " + name + " Port: " + port + childrenName;
    }

    public int numChildren(){
        return children.size();
    }

    public int futureSize(){
        return (numChildren() > 1)?numChildren() * 110:220;
    }

    public String getPort(){
        return port + "";
    }



}
