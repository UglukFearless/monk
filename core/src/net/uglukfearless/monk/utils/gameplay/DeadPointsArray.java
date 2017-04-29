package net.uglukfearless.monk.utils.gameplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Ugluk on 08.02.2017.
 */

public class DeadPointsArray {
    private ArrayList<Integer> points;
    private int index;

    public DeadPointsArray() {
        points = new ArrayList<Integer>();
        index = 0;
    }

    public void resetPoints() {
        points.clear();
        index = 0;
    }

    public void addPoint(int point) {
        points.add(point);
        if (points.size()>1) {
            Collections.sort(points, new Comparator<Integer>() {
                public int compare(Integer int1, Integer int2) {
                    return int1.compareTo(int2);
                }
            });
        }
        index = 0;
    }

    public void resetIndex() {
        index = 0;
    }

    public boolean checkPoint(int time) {
        if (points.size()>=1&&points.size()>index) {
            return (points.get(index)<=(time + 4));
        }
        return false;
    }


    public int getCurrentPointOffset(int time) {
        return (points.get(index) - time)*6;
    }

    public void nextPoint() {
        index++;
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }
}
