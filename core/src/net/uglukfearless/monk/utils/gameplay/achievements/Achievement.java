package net.uglukfearless.monk.utils.gameplay.achievements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.HashMap;

/**
 * Created by Ugluk on 18.09.2016.
 */
public abstract class Achievement {

    public static final String LOCK_IMAGE_NAME = "lock";

    public static boolean isUnlock(String key) {
        if (key!=null) {
            return PreferencesManager.checkAchieve(key);
        }

        return false;
    }

    protected String mKey;
    protected String mNameKey;
    protected String mDescribeKey;
    protected String mImprovementKey;
    protected String mConditionKey;

    protected HashMap<String, String> mUpdateList;
    protected HashMap<String, String> mUpdateSelf;

    protected boolean mNew;

    protected String mRegion;

    public boolean isNew() {
        return mNew;
    }

    public void setNew(boolean aNew) {
        mNew = aNew;
    }

    public boolean isUnlock() {
        return PreferencesManager.checkAchieve(mKey);
    }

    public void unlock() {
        PreferencesManager.unlockAchieve(mKey);
        mNew = true;
    }

    public String getName() {
        return AssetLoader.sBundle.get(mNameKey);
    }

    public String getDescribe() {
        return AssetLoader.sBundle.get(mDescribeKey);
    }

    public String getImprovementKey() {
        return mImprovementKey;
    }

    public TextureRegion getRegion() {
        return AssetLoader.achieveAtlas.findRegion(mRegion);
    }

    public HashMap<String, String> getUpdateList() {
        return mUpdateList;
    }

    public HashMap<String, String> getUpdateSelf() {
        return mUpdateSelf;
    }

    public void checkAchieve() {
        if (checkCondition()) {
            unlock();
        }
    }

    public String getConditionString() {
        return AssetLoader.sBundle.get(mConditionKey);
    }

    public abstract boolean checkCondition();


}
