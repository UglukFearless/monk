package net.uglukfearless.monk.utils.gameplay.models;

/**
 * Created by Ugluk on 09.12.2016.
 */

public class GameProgressModel {

    private int mRevival;
    private int mWingsRevival;
    private int mCurrentGrade;

    public GameProgressModel(int revival, int wingsRevival, int currentGrade) {
        mRevival = revival;
        mWingsRevival = wingsRevival;
        mCurrentGrade = currentGrade;
    }

    public int getRevival() {
        return mRevival;
    }

    public void setRevival(int revival) {
        mRevival = revival;
    }

    public int getWingsRevival() {
        return mWingsRevival;
    }

    public void setWingsRevival(int wingsRevival) {
        mWingsRevival = wingsRevival;
    }

    public int getCurrentGrade() {
        return mCurrentGrade;
    }

    public void setCurrentGrade(int currentGrade) {
        mCurrentGrade = currentGrade;
    }

}
