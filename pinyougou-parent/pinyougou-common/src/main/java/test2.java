import java.util.HashMap;
import java.util.Random;

public class test2 {

    public static void main(String[] args) {

        HashMap<Position2D, Double> crossMap = null;
        Position2D positionKey = new Position2D();

        HashMap<Position2D, Double> positionMap = new HashMap<Position2D, Double>();
        Position2D crossPosition1 = new Position2D();

        crossMap = pointToLine(0, 0, 0, 6000, 16.444519f, 1935.8809f, positionMap, crossPosition1);
        System.out.println(crossMap);
        crossMap = pointToLine(0, 6000, 6000, 6000, 5839.8364f, 614.762f, positionMap, crossPosition1);
        System.out.println(crossMap);

//        getRotationAngle(center_x, center_y, positionKey.getX(), positionKey.getY(), sourceAngle, false, changeProb)
        int rotationAngle = getRotationAngle(3318.715f, 5817.642f, 3318.715f, 6000f, 186, false, 0.6f, true, 6000, 6000, 150);
        System.out.println(rotationAngle);
    }


    public static int getRotationAngle(float xs, float ys, float xt, float yt, int sourceAngle, boolean isEnergy, float changeProb, boolean isBorder, int x_max, int y_max, float borderDistance) {
//        DecimalFormat df = new DecimalFormat("######0.00");
        if (Math.abs(xt) <= 0.000001 && Math.abs(yt) <= 0.000001) {
            return getRandomAngle(sourceAngle);
//            float prob = (float) Math.random();
//            if (prob < changeProb) {
//                return sourceAngle;
//            } else {
//                int angle = getRandomAngle(sourceAngle);
//                return angle;
//            }

        } else {
            float x1 = 1.0F;
            float y1 = 0.0F;
            float x2 = xt - xs;
            float y2 = yt - ys;

            double value = (x1 * x2 + y1 * y2) / (Math.sqrt(x1 * x1 + y1 * y1) * Math.sqrt(x2 * x2 + y2 * y2)); // 余弦值

            if(value > 1.0){
                value = 1.0;
            }
            else if(value < -1.0){
                value = -1.0;
            }

            int angle = (int) Math.toDegrees(Math.acos(value));   // 角度

            double dm = x1 * y2 - x2 * y1;

            if (dm > 0) {
                angle = 360 - angle;
            }

            if (isEnergy) {
                return angle;
            } else {

                  int angleDiff = getAngleDiff(angle, sourceAngle);
//                int angleDiff = Math.abs(AiUtils.getChangeAngleIndex(sourceAngle, angle));

                if (angleDiff > 90) {
                    //障碍物的点在后方 无需躲避
                    return sourceAngle;
                } else if (angleDiff == 90) {
                    // 180度掉头躲避
                    if (angle + 180 >= 360) {
                        return angle + 180 - 360;
                    }
                    return angle + 180;
                }

                if (isBorder) {
                    if( (xt<=borderDistance && yt<=borderDistance) || (xt<=borderDistance && yt>=y_max-borderDistance) || (xt>=x_max-borderDistance && yt<=borderDistance) || (xt>=x_max-borderDistance && yt>=y_max-borderDistance)){
                        // 180度掉头躲避
                        if (angle + 180 >= 360) {
                            return angle + 180 - 360;
                        }
                        return angle + 180;
                    }
                }


                //反射躲避
                int targetReverseAngle = angle + 180;
                if (targetReverseAngle >= 360) {
                    targetReverseAngle -= 360;
                }

                int sourceReverseAngle = sourceAngle + 180;
                if (sourceReverseAngle >= 360) {
                    sourceReverseAngle -= 360;
                }

                if (sourceReverseAngle >= targetReverseAngle) {
                    int refract = sourceReverseAngle - targetReverseAngle;
                    int finalAngle = targetReverseAngle - refract;
                    return Math.floorMod((finalAngle + 360), 360);
                } else {
                    int refract = targetReverseAngle - sourceReverseAngle;
                    int finalAngle = targetReverseAngle + refract;
                    return Math.floorMod((finalAngle + 360), 360);
                }


            }
        }
    }

    public static int getAngleDiff(int angle, int sourceAngle) {
        int angleDiff = 0;
        if (Math.max(angle, sourceAngle) > 270 && Math.min(angle, sourceAngle) < 90) {
            angleDiff = 360 - Math.abs(angle - sourceAngle);
        } else {
            angleDiff = Math.abs(angle - sourceAngle);
        }

        return angleDiff;
    }

    public static int getRandomAngle(int sourceAngle) {
        int angle = 0;
        Random random = new Random();
        int flag = random.nextInt(2);

        int sourceReverseAngle = sourceAngle + 180;
        if (sourceReverseAngle >= 360) {
            sourceReverseAngle -= 360;
        }
        int leftAngle = sourceReverseAngle - 30;
        if (leftAngle < 0) {
            leftAngle += 360;
        }
        int rightAngle = sourceReverseAngle + 30;
        if (rightAngle > 360) {
            rightAngle -= 360;
        }
        int max1 = 360;
        int min1 = Math.max(leftAngle, rightAngle);
        int max2 = Math.min(leftAngle, rightAngle);
        int min2 = 0;

        if (sourceReverseAngle >= 330 || sourceReverseAngle <= 30) {
            angle = (int) (Math.random() * (min1 - max2 + 1) + max2);
        } else {
            angle = (int) ((Math.random() * (max1 - min1 + 1) + min1) * flag + (Math.random() * (max2 - min2 + 1) + min2) * (1 - flag));
        }

        return angle;
    }

    public static HashMap<Position2D, Double> pointToLine(float x1, float y1, float x2, float y2, float x0, float y0, HashMap<Position2D, Double> crossMap,Position2D crossPoint) {
//        HashMap<Position2D, Double> crossMap = new HashMap<Position2D, Double>();
//        Position2D crossPoint = new Position2D();

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
                crossPoint.setX(x2);
                crossPoint.setY(y2);
            } else {
                crossPoint.setX(x1);
                crossPoint.setY(y1);
            }
        } else {
            crossPoint.setX(x);
            crossPoint.setY(y);
        }
        crossMap.put(crossPoint, lineSpace(crossPoint.getX(), crossPoint.getY(), x0, y0));
        return crossMap;
    }

    public static double lineSpace(float x1, float y1, float x2, float y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return lineLength;
    }

}

