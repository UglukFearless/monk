package net.uglukfearless.monk.actors.gameplay.bonuses;


import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 11.09.2016.
 */
public class ThunderFistBonus extends GameBonus {

    public ThunderFistBonus(GameStage gameStage, float gameHeight) {
        super(gameStage, gameHeight);

        mRegion = AssetLoader.bonusesAtlas.findRegion("thunder_fist");
        mName = AssetLoader.sBundle.get("PLAY_BONUS_THUNDER_FIST");
        mWorkingTime = 15;
    }

    @Override
    public void activation() {
        mStage.getRunner().setThunderFist(true);
    }

    @Override
    public void deactivation() {
        mStage.getRunner().setThunderFist(false);
    }
}
