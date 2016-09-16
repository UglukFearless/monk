package net.uglukfearless.monk.actors.gameplay.bonuses;

import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 11.09.2016.
 */
public class RetributionBonus extends GameBonus {

    public RetributionBonus(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        mRegion = AssetLoader.bonusesAtlas.findRegion("retribution");
        mName = AssetLoader.sBundle.get("PLAY_BONUS_RETRIBUTION");
        mWorkingTime = 10;
    }

    @Override
    public void activation() {
        mStage.getRunner().setRetribution(true);
    }

    @Override
    public void deactivation() {
        mStage.getRunner().setRetribution(false);
    }
}
