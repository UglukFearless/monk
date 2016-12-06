package net.uglukfearless.monk.actors.gameplay.bonuses.item;

import com.badlogic.gdx.graphics.g2d.Batch;

import net.uglukfearless.monk.actors.gameplay.bonuses.GameBonus;
import net.uglukfearless.monk.enums.WeaponType;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

import java.util.Random;

/**
 * Created by Ugluk on 27.11.2016.
 */
public class WeaponItem extends GameBonus {

    private Random mRandom = new Random();
    private WeaponType mWeaponType;
    private boolean mInitialized;

    public WeaponItem(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        this.setSize(3f,3f);

        mWorkingTime = 0.2f;
        mQuantum = true;
    }

    public void init(float x, float y) {
//        int index = mRandom.nextInt(WeaponType.values().length);
//        mWeaponType = WeaponType.values() [index];
        mWeaponType = WeaponType.WEAPON_TYPE1;
        mRegion = mWeaponType.getImage();
        mName = mWeaponType.getEnName();
        mActiveTitle = AssetLoader.sBundle.format("PLAY_BONUS_ITEM_TITLE", mWeaponType.getEnName(), mWeaponType.getRuName());
        super.init(x, y);
        this.setPosition(x - 1.5f, y - 1.5f);
        mInitialized=true;
    }

    @Override
    public void activation() {
        mStage.getRunner().setWeaponType(mWeaponType);
        PreferencesManager.setWeapon(mWeaponType);
        mInitialized=false;
    }

    @Override
    public void deactivation() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //todo: Нужно бы подправить вычисление координат
        batch.draw(mRegion, getX()+0.5f, getY()+0.5f, getWidth()-1.2f, getHeight()-0.7f);
    }

    @Override
    public void disabling() {
        super.disabling();
        mInitialized = false;
    }

    public boolean isInitialized() {
        return mInitialized;
    }
}
