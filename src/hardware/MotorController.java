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
public class MotorController extends HardwareDevice {

    public MotorController (String name, int port){
        super(name, port);
        image = XMLParser.readImage("/hardwareImages/motorController.png");
        loc = Main.PointConstants.MODULE_LOC[port];
    }

    public void drawModule(Graphics2D g) {
        g.setColor(Color.BLUE);
        super.drawModule(g);

    }

    public String getName(){
        return name + " \n" + getSerialNumber();
    }


}
