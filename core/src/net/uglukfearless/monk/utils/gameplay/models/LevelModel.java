package net.uglukfearless.monk.utils.gameplay.models;


import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.gameplay.DifficultyHandler;

/**
 * Created by Ugluk on 25.09.2016.
 */
public class LevelModel {

    private String LEVEL_NAME;

    private LevelConstants mLevelConstants;

    private GameStage mStage;
    private DifficultyHandler mDifficultyHandler;

    public int mPriorityGround;
    public int mPriorityPit;
    public int mPriorityColumns;

    public int mDangersProbability;
    private EnemyModel[] mEnemiesModels;
    private ObstacleModel[] mObstaclesModels;


    public LevelModel() {

        mDifficultyHandler = new DifficultyHandler();

        mPriorityGround = 1;
        mPriorityPit = 1;
        mPriorityColumns = 1;

        mDangersProbability = 0;
    }

    public void init() {
        Constants.init(mLevelConstants);
        AssetLoader.initLevel(LEVEL_NAME);
    }

    public void setStage(GameStage stage) {

        mStage = stage;
        mDifficultyHandler.init(mStage, this);
    }

    public void act(float delta) {

        mDifficultyHandler.act(delta);
    }

    public EnemyModel[] getEnemiesModels() {
        return mEnemiesModels;
    }

    public ObstacleModel[] getObstaclesModels() {
        return mObstaclesModels;
    }

    public DifficultyHandler getDifficultyHandler() {
        return mDifficultyHandler;
    }

    public String getLEVEL_NAME() {
        return LEVEL_NAME;
    }
}
