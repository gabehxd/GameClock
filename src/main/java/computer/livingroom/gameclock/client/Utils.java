package computer.livingroom.gameclock.client;

import java.awt.*;

public class Utils {
    public static int getHexColor(Color color) {
        //What the fuck java
        return (color.getAlpha() << 24) | (color.getRed() << 16) | (color.getGreen() << 8) | color.getBlue();
    }
}
