package net.uglukfearless.monk.actors.gameplay.bonuses;

import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 13.09.2016.
 */
public class RevivalBonus extends GameBonus {

    public RevivalBonus(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        mRegion = AssetLoader.bonusesAtlas.findRegion("revival");
        mName = AssetLoader.sBundle.get("PLAY_BONUS_REVIVAL");
        mWorkingTime = 1.5f;
    }

    @Override
    public void activation() {
        mStage.addRevival();
    }

    @Override
    public void deactivation() {

    }
}
