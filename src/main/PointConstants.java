package main;


import com.sun.glass.ui.Size;

import java.awt.*;

/**
 * Created by Windows on 2016-10-27.
 */
public class PointConstants {

    public final static Point powerModule = new Point(600, 500);
    public final static Point phone = new Point(725, 750);
    public final static Point battery = new Point(600, 750);


    public final static Size MODULE_SIZE = new Size(200, 200);

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
