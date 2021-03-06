package hardware;


import main.Main;
import main.XMLParser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static main.XMLParser.topY;

/**
 * Created by Windows on 2016-10-23.
 */
public class ServoController extends HardwareDevice {

    public ServoController (String name, int port){
        super(name, port);
        image = XMLParser.readImage("/hardwareImages/servoController.png");
        loc = Main.PointConstants.MODULE_LOC[port];
    }

    public void drawModule(Graphics2D g) {
        g.setColor(Color.RED);
        super.drawModule(g);
    }

    @Override
    public void drawNames(Graphics g){
        int childrenY = loc.y + 30;
        if (isOnLeft()) {
            g.setColor(greySerial);
            g.drawString(serialNumber, loc.x - 130, loc.y + 30);
            g.drawString(name, loc.x - 130, loc.y + 10);

            g.setColor(greyText);
            for (int i = children.size() - 1; i >= 0; i--) {
                HardwareDevice child = children.get(i);
                g.drawString(child.port + "    " + child.getName(), loc.x - 150, childrenY += 20);
            }//for

            g.setColor(greyLine);
            g.drawLine(loc.x - 150, loc.y + 35, loc.x - 50, loc.y + 35);
            g.drawLine(loc.x - 135, loc.y + 10, loc.x - 135, loc.y + 155);
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

    @Override
    public String getName() {
        return super.getName() + " \n" + getSerialNumber();
    }
}
