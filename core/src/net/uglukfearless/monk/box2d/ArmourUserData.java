package net.uglukfearless.monk.box2d;

import net.uglukfearless.monk.enums.ArmourType;
import net.uglukfearless.monk.enums.UserDataType;
import net.uglukfearless.monk.utils.file.PreferencesManager;

/**
 * Created by Ugluk on 07.11.2016.
 */
public class ArmourUserData extends UserData {

    private int mTotalStrength;
    private int mCurrentStrength;
    private ArmourType mType;

    private boolean isActive;
    private boolean isHit;
    private boolean isWait;
    private boolean mHitted;

    public ArmourUserData(float width, float height) {
        super(width, height);

        if (PreferencesManager.getArmour()!=null) {
            mType = PreferencesManager.getArmour();
            mTotalStrength = mType.getStrength();
            mCurrentStrength = mTotalStrength;
            isActive = true;
        } else {
            mTotalStrength = 0;
            mCurrentStrength = mTotalStrength;
            isActive = false;
        }

        isHit = false;

        userDataType = UserDataType.ARMOUR;
    }

    public int getTotalStrength() {
        return mTotalStrength;
    }

    public int getCurrentStrength() {
        return mCurrentStrength;
    }

    public ArmourType getType() {
        return mType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void deductStrength() {
        mCurrentStrength -=1;
        isHit = true;
    }

    public void destroyArmour() {
        isActive = false;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean isHit) {
        this.isHit = isHit;
        if (isHit) {
            mHitted=true;
            deductStrength();
        }
    }

    public boolean isWait() {
        return isWait;
    }

    public void setWait(boolean isWait) {
        this.isWait = isWait;
    }

    public boolean getHitted() {
        return mHitted;
    }

    public void setHitted(boolean hitted) {
        mHitted = hitted;
    }
}
