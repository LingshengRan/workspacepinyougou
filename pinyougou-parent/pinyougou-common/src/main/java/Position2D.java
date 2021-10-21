/**
 * @program: Copyright (C), 2019-2021, 上海序言泽网络科技有限公司
 * @author: Xiu.Liu
 * @description:
 * @create: 2021-03-31 21:04
 **/
public class Position2D {
    private float x;
    private float y;
    private int angleIndex; //方向角度索引
    private boolean isValid; //是否有效

    public Position2D(float x, float y, int angleIndex, boolean isValid) {
        this.x = x;
        this.y = y;
        this.angleIndex = angleIndex;
        this.isValid = isValid;
    }

    public Position2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Position2D() {

    }

    @Override
    public String toString() {
        return "Pos{" +
                "x=" + x +
                ", y=" + y +
                ", isValid=" + isValid +
                ", angle=" + angleIndex +
                '}';
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getAngleIndex() {
//        if (angleIndex > 23) {
//            angleIndex = angleIndex - 24;
//        }
//        if (angleIndex < 0) {
//            angleIndex = angleIndex + 24;
//        }
        return angleIndex;
    }

    public void setAngleIndex(int angleIndex) {
//        if (angleIndex > 23) {
//            angleIndex = angleIndex - 24;
//        }
//        if (angleIndex < 0) {
//            angleIndex = angleIndex + 24;
//        }
        this.angleIndex = angleIndex;
    }
}
