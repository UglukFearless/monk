package net.uglukfearless.monk.utils.gameplay.dangers;

import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.constants.Constants;

/**
 * Created by Ugluk on 16.06.2016.
 */
public class ObstaclesMap {

    public static boolean pit1 = false;
    public static boolean pit2 = false;

    public static boolean first = true;
    public static boolean one = true;
    public static boolean cycle = false;

    public static Vector2 velocity = Constants.WORLD_STATIC_VELOCITY;

    public static float sectionLength = Constants.GROUND_WIDTH/3f;

    public static float x = 0;

    public static short[][] map = new short[8][2];

    public static void setObstacle(int i, int j, short type, boolean pit) {

        if (!pit&&!one) {
            map [i+5][j] = type;
        } else if (pit&&!one) {
            map[i+4][j] = type;
        } else if (!pit&&one) {
            map [i+1][j] = type;
        } else if (pit&&one) {
            map [i][j] = type;
        }
    }

    public static void pit() {
        if (!one) {
            pit2 = true;
        } else {
            pit1 = true;
        }
    }

    public static void leaf() {
        if (!first) {
            one = false;

            if (cycle) {
                for (int i=0;i<4;i++) {
                    for (int j=0;j<2;j++) {
                        map[i][j] = map[i+4][j];
                        map[i+4][j] = 0;
                    }
                }
                if (pit1) {
                    x +=Constants.GROUND_WIDTH + Constants.GROUND_PIT;
                } else {
                    x +=Constants.GROUND_WIDTH;
                }
                pit1=pit2;
                pit2=false;
            } else {
                cycle = true;
            }

        } else {
            first = false;
        }
    }

    public static short getObstacleCode(int i, int j) {
        return map[i][j];
    }

    public static int [] checkPlacing(float inX, float inY) {
        int[] point = new int[2];

        if (inX < x || inX > x + Constants.GROUND_WIDTH*2 + Constants.GROUND_PIT*2) {
            point[0] = -1;
            point[1] = -1;
            return point;
        }
        if (pit1&&(inX-x)<Constants.GROUND_PIT) {
            point[0] = 0;
        } else if (pit1&&(inX-x)<Constants.GROUND_WIDTH + Constants.GROUND_PIT) {
            point[0] = ((int)((inX - x - Constants.GROUND_PIT)/sectionLength) + 1);
        } else if (pit1&&pit2&&(inX-x)<Constants.GROUND_WIDTH + Constants.GROUND_PIT*2) {
            point[0] = 4;
        } else if (pit1&&pit2&&(inX-x)<Constants.GROUND_WIDTH*2 + Constants.GROUND_PIT*2) {
            point[0] = ((int)((inX - x - 2*Constants.GROUND_PIT)/sectionLength) + 2);
        } else if (pit1&&!pit2&&(inX-x)<Constants.GROUND_WIDTH*2 + Constants.GROUND_PIT) {
            point[0] = ((int)((inX - x - Constants.GROUND_PIT)/sectionLength) + 2);
        }

        else if (!pit1&&(inX-x)<Constants.GROUND_WIDTH) {
            point[0] = ((int)((inX - x)/sectionLength) + 1);
        } else if (!pit1&&pit2&&(inX-x)<Constants.GROUND_WIDTH + Constants.GROUND_PIT) {
            point[0] = 4;
        } else if (!pit1&&pit2&&(inX-x)<Constants.GROUND_WIDTH*2 + Constants.GROUND_PIT) {
            point[0] = ((int)((inX - x - Constants.GROUND_PIT)/sectionLength) + 2);
        } else if (!pit1&&!pit2&&(inX-x)<Constants.GROUND_WIDTH*2) {
            point[0] = ((int)((inX - x - Constants.GROUND_PIT)/sectionLength) + 2);
        }

        if (inY<Constants.LAYOUT_Y_ONE + 4) {
            point[1] = 0;
        } else {
            point[1] = 1;
        }

        return point;
    }

    public static void update(float delta) {
        x +=velocity.x*delta;
    }
}
