package net.uglukfearless.monk.utils.gameplay;

import com.badlogic.gdx.scenes.scene2d.Stage;


import java.util.Random;


/**
 * Created by Ugluk on 13.10.2016.
 */
public class Shake {

    private float mTime;
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
        mPassive = true;
    }

    public void shake(float power, float time) {
        mPower = power;
        mTime = time;
        mPassive = false;
        mCurrentTime = 0;
    }

    public void tick(float delta, Stage stage, float viewportX, float viewportY){

        if(mCurrentTime <= mTime) {
            mCurrentPower = mPower * ((mTime - mCurrentTime) / mTime);

            y = (float) (1.4*mCurrentPower*Math.cos((double)18.84f*(mCurrentTime/mTime)));


            stage.getCamera().translate(0, -y, 0);
            stage.getCamera().update();
            mCurrentTime += delta;
        } else if (!mPassive) {
            stage.getCamera().position.set(viewportX/2f, viewportY/2f, 0f);
            stage.getCamera().update();
            mPassive = true;
        }
    }
}
