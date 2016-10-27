package hardware;

import main.XMLParser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Windows on 2016-10-23.
 */
public class Sensor extends HardwareDevice {
    String type = "";

    public Sensor (String name, int port, String type){
        super(name, port);
        this.type = type;
        image = XMLParser.readImage("/hardwareImages/sensor.png");

    }


    @Override
    public int drawModule(Graphics g, int startX) {
        return 0;
    }
}