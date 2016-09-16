package net.uglukfearless.monk.actors.gameplay.bonuses;

import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 10.09.2016.
 */
public class GhostBonus extends GameBonus {

    public GhostBonus(GameStage gameStage, float gameHeight) {
        super(gameStage,gameHeight);
        mRegion = AssetLoader.bonusesAtlas.findRegion("ghost");
        mName = AssetLoader.sBundle.get("PLAY_BONUS_GHOST");
        mWorkingTime = 10;
    }

    @Override
    public void activation() {
        mStage.getRunner().getBody().getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_GHOST);
        mStage.getRunner().setAlpha(0.25f);
    }

    @Override
    public void deactivation() {
        mStage.getRunner().getBody().getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER);
        mStage.getRunner().setAlpha(1f);
    }
}
