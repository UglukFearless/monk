package net.uglukfearless.monk.actors.gameplay.bonuses;

import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 10.09.2016.
 */
public class WingsBonus extends GameBonus {

    public WingsBonus(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        mRegion = AssetLoader.bonusesAtlas.findRegion("faith_wings");
        mName = AssetLoader.sBundle.get("PLAY_BONUS_WINGS");
        mWorkingTime = 15;
    }

    @Override
    public void activation() {
        mStage.getRunner().setWings(true);
    }

    @Override
    public void deactivation() {
        mStage.getRunner().setWings(false);
    }
}
