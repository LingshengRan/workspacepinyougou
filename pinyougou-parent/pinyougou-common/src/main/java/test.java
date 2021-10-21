import com.sun.jmx.snmp.Timestamp;

import javax.sound.midi.Soundbank;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class test {
    public static void main(String[] args) {
        HashMap<Position2D, Double> positionMap = new HashMap<Position2D, Double>();
        Position2D crossPosition = new Position2D();

        HashMap<Position2D, Double> position2DDoubleHashMap = null;

        long startTime = System.currentTimeMillis();
        for (int i=1; i<=1000000; i++){
            position2DDoubleHashMap = pointToLine(0, 0, 1, 1, 2, 2, positionMap, crossPosition);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);


    }

    public static HashMap<Position2D, Double> pointToLine(float x1, float y1, float x2, float y2, float x0, float y0, HashMap<Position2D, Double> crossMap1, Position2D crossPoint1) {
        //HashMap<Position2D, Double> crossMap1 = new HashMap<Position2D, Double>();
        //Position2D crossPoint1 = new Position2D();
        float dx = x1 - x2;
        float dy = y1 - y2;

        float u = (x0 - x1) * dx + (y0 - y1) * dy;
        u /= dx * dx + dy * dy;

        float x = (x1 + u * dx);
        float y = (y1 + u * dy);

        float d = Math.abs((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        float d1 = Math.abs((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
        float d2 = Math.abs((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));

        if (d1 > d || d2 > d) {
            if (d1 > d2) {
                crossPoint1.setX(x2);
                crossPoint1.setY(y2);
            } else {
                crossPoint1.setX(x1);
                crossPoint1.setY(y1);
            }
        } else {
            crossPoint1.setX(x);
            crossPoint1.setY(y);
        }
        crossMap1.put(crossPoint1, lineSpace(crossPoint1.getX(), crossPoint1.getY(), x0, y0));
        return crossMap1;
    }

    public static double lineSpace(float x1, float y1, float x2, float y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return lineLength;
    }

}
