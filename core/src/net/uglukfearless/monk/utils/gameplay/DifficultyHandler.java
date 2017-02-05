package net.uglukfearless.monk.utils.gameplay;

import com.badlogic.gdx.utils.Array;

import net.uglukfearless.monk.enums.EnemyType;
import net.uglukfearless.monk.enums.GameState;
import net.uglukfearless.monk.enums.ObstacleType;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.gameplay.models.LevelModel;
import net.uglukfearless.monk.utils.gameplay.models.StepModel;


/**
 * Created by Ugluk on 25.09.2016.
 */
public class DifficultyHandler {

    private float mStepTime;
    private float mRuntime;
    private Array<StepModel> mSteps;
    private int currentStep;
    private GameStage mStage;
    private LevelModel mLevelModel;

    private StepModel mStep;

    public DifficultyHandler() {

        mStepTime=15;
        mRuntime=16;
        currentStep=0;
        mSteps = new Array<StepModel>();
    }

    public void init(GameStage stage, LevelModel levelModel) {
        mStage = stage;
        mLevelModel = levelModel;
    }

    public void act(float delta) {

        if (mStage.getState() == GameState.RUN) {
            mRuntime+=delta;
            if (mRuntime>mStepTime) {
                mRuntime=0;
                applyNewStep();
            }
        }
    }

    public void applyStep(int stepNumber) {

        StepModel step = mSteps.get(stepNumber);
        System.out.println("step change speed " + step.worldSpeed);
        mStage.changingSpeedHandler(step.worldSpeed);
        mLevelModel.mPriorityGround = step.priorityGround;
        mLevelModel.mPriorityPit = step.priorityPit;
        mLevelModel.mPriorityColumns = step.priorityColumns;

        for (int i=0;i<10;i++) {
            EnemyType.values()[i].setPriority(step.enemiesPriority[i]);
        }
        for (int i=0;i<3;i++) {
            ObstacleType.values()[i].setPriority(step.obstaclesPriority[i]);
        }

        mStage.getDangersHandler().init();

        mLevelModel.mDangersProbability = step.dangersProbability;
        currentStep = stepNumber++;

    }

    private void applyNewStep() {

        if (currentStep<(mSteps.size)) {
            StepModel step = mSteps.get(currentStep);
            System.out.println("new step change speed " + step.worldSpeed);
            mStage.changingSpeedHandler(step.worldSpeed);
            mLevelModel.mPriorityGround = step.priorityGround;
            mLevelModel.mPriorityPit = step.priorityPit;
            mLevelModel.mPriorityColumns = step.priorityColumns;

            for (int i=0;i<10;i++) {
                EnemyType.values()[i].setPriority(step.enemiesPriority[i]);
            }
            for (int i=0;i<3;i++) {
                ObstacleType.values()[i].setPriority(step.obstaclesPriority[i]);
            }

            mStage.getDangersHandler().init();

            mLevelModel.mDangersProbability = step.dangersProbability;
            currentStep++;
        }
    }

    public void applyLastStep() {
        currentStep--;
        StepModel step = mSteps.get(currentStep);
        System.out.println("last step change speed " + step.worldSpeed);
        mStage.changingSpeedHandler(step.worldSpeed);
        mLevelModel.mPriorityGround = step.priorityGround;
        mLevelModel.mPriorityPit = step.priorityPit;
        mLevelModel.mPriorityColumns = step.priorityColumns;

        for (int i=0;i<10;i++) {
            EnemyType.values()[i].setPriority(step.enemiesPriority[i]);
        }
        for (int i=0;i<3;i++) {
            ObstacleType.values()[i].setPriority(step.obstaclesPriority[i]);
        }

        mStage.getDangersHandler().init();

        mLevelModel.mDangersProbability = step.dangersProbability;
        currentStep++;
    }

    public void reset() {
        mRuntime = mStepTime + 1;
        applyStep(0);
    }
}
