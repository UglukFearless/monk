package net.uglukfearless.monk.utils.gameplay;

import com.badlogic.gdx.scenes.scene2d.Stage;


import java.util.Random;


/**
 * Created by Ugluk on 13.10.2016.
 */
public class Shake {

    private float mTime;
    private Random mRandom;
    private float x, y;
    private float mCurrentTime;
    private float mPower;
    private float mCurrentPower;
    private boolean mPassive;

    public Shake() {

        mTime = 0;
        mCurrentTime = 10;
        mPower = 0;
        mCurrentPower = 0;
        mRandom = new Random();
        mPassive = true;
    }

    public void shake(float power, float time) {
        mRandom = new Random();
        mPower = power;
        mTime = time;
        mPassive = false;
        mCurrentTime = 0;
    }

    public void tick(float delta, Stage stage, float viewportX, float viewportY){

        if(mCurrentTime <= mTime) {
            mCurrentPower = mPower * ((mTime - mCurrentTime) / mTime);

            x = (mRandom.nextFloat() - 0.5f) * 2f * mCurrentPower;
            y = (mRandom.nextFloat() - 0.5f) * 2f * mCurrentPower;


            stage.getCamera().translate(-x, -y, 0);
            stage.getCamera().update();
            mCurrentTime += delta;
        } else if (!mPassive) {
            stage.getCamera().position.set(viewportX/2f, viewportY/2f, 0f);
            stage.getCamera().update();
            mPassive = true;
        }
    }
}
