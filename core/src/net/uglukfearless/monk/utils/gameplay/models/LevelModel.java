package net.uglukfearless.monk.utils.gameplay.models;


import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.GameState;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.gameplay.DifficultyHandler;

/**
 * Created by Ugluk on 25.09.2016.
 */
public class LevelModel {

    private String LEVEL_NAME;
    private String EN_NAME;
    private String RU_NAME;

    private LevelConstants mLevelConstants;

    private GameStage mStage;
    private DifficultyHandler mDifficultyHandler;

    public int mPriorityGround;
    public int mPriorityPit;
    public int mPriorityColumns;

    public int mDangersProbability;
    private EnemyModel[] mEnemiesModels;
    private ObstacleModel[] mObstaclesModels;
    private DecorationModel[] mNearDecorationsModels;
    private DecorationModel[] mFurtherDecorationsModels;

    private int mOvertime;


    public LevelModel() {

        mDifficultyHandler = new DifficultyHandler();

        mPriorityGround = 1;
        mPriorityPit = 1;
        mPriorityColumns = 1;

        mDangersProbability = 0;
    }

    public void initAssets(boolean finishedLoad) {
        AssetLoader.initLevelManager(LEVEL_NAME, finishedLoad);
    }

    public void init() {
        Constants.init(mLevelConstants);
        AssetLoader.initLevel(LEVEL_NAME);
    }

    public void setStage(GameStage stage) {
        mStage = stage;
        mDifficultyHandler.init(mStage, this);
    }

    public void act(float delta, float runTime, boolean infinity) {
        if (!infinity) {
            if (mStage.getState() == GameState.RUN&&checkEndTime(runTime)) {
                mStage.setState(GameState.SLOWDOWN);
                mStage.finishRetribution();
            } else if (mStage.getState()== GameState.SLOWDOWN) {
                float velocity = mStage.getCurrentVelocity().x;
                if (velocity<-12) {
                    float changeVelocity = 0.13f*delta;
                    mStage.changingSpeed(velocity - velocity*changeVelocity);
                }

                if (mStage.reachFinish()) {
                    mStage.setState(GameState.FINISH);
                    mStage.deactivationBonuses();
                    mStage.changingSpeedHandler(0f);
                    mStage.unlockNextLevel();
                }
            } else if (mStage.getState()== GameState.RUN) {
                mDifficultyHandler.act(delta);
            }
        } else {
            if (mStage.getState()== GameState.RUN){
                mDifficultyHandler.act(delta);
            }
        }
    }

    public void setOvertime(int overtime) {
        mOvertime = overtime;
    }

    public EnemyModel[] getEnemiesModels() {
        return mEnemiesModels;
    }

    public ObstacleModel[] getObstaclesModels() {
        return mObstaclesModels;
    }

    public DecorationModel[] getNearDecorationsModels() {
        return mNearDecorationsModels;
    }

    public DecorationModel[] getFurtherDecorationsModels() {
        return mFurtherDecorationsModels;
    }

    public DifficultyHandler getDifficultyHandler() {
        return mDifficultyHandler;
    }

    public String getLEVEL_NAME() {
        return LEVEL_NAME;
    }

    public String getRU_NAME() {
        return RU_NAME;
    }

    public String getEN_NAME() {
        return EN_NAME;
    }

    public boolean checkUnlock() {
        if (mLevelConstants.FOREVER_UNLOCK) {
            return true;
        } else {
            return PreferencesManager.checkUnlockLevel(LEVEL_NAME);
        }
    }

    public float getDuration() {
        return mLevelConstants.DURATION + mOvertime;
    }

    public float getTimeBalance(float runTime) {
        return (getDuration() - runTime>0) ? (getDuration() - runTime) : 0;
    }

    private boolean checkEndTime(float runTime) {
        return (getTimeBalance(runTime)==0);
    }

    public int getGrade() {
        return mLevelConstants.GRADE;
    }

    public void secondStart() {
        mDifficultyHandler.applyLastStep();
    }

    public int getOvertime() {
        return mOvertime;
    }
}
