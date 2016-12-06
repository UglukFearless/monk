package net.uglukfearless.monk.actors.gameplay.bonuses;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 02.12.2016.
 */

public class DragonFormBonus extends GameBonus {

    public DragonFormBonus(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        mRegion = AssetLoader.bonusesAtlas.findRegion(Constants.BONUS_DRAGON_FORM_REGION);
        mName = AssetLoader.sBundle.get("PLAY_BONUS_DRAGON");
        mActiveTitle = AssetLoader.sBundle.format("PLAY_BONUS_ACTIVE_TITLE", mName);
        mWorkingTime = 20;

    }

    @Override
    public void activation() {
        mStage.getDragon().takeShape();
        mStage.getRunner().setDragon(true);
    }

    @Override
    public void deactivation() {
        mStage.getRunner().setDragon(false);
        mStage.getDragon().trend();
    }
}
