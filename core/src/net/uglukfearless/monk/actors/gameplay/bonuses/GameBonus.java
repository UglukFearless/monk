package net.uglukfearless.monk.actors.gameplay.bonuses;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


import net.uglukfearless.monk.box2d.RunnerUserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.GameState;
import net.uglukfearless.monk.stages.GameGuiStage;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.SoundSystem;
import net.uglukfearless.monk.utils.gameplay.Movable;


/**
 * Created by Ugluk on 10.09.2016.
 */
public abstract class GameBonus extends Actor implements Movable {

    protected String mName;
    protected GameStage mStage;
    protected boolean mQuantum;
    protected float mSpeed = Constants.WORLD_STATIC_VELOCITY_INIT.x;

    private boolean mTouched;
    private boolean mAddsActions;
    private boolean mActive;

    private float mActiveTime;

    private Rectangle mRunnerBounds;
    private Rectangle mBonusBounds;

    protected TextureRegion mRegion;
    protected float mWorkingTime;
    private float mTimingBalance;
    private GameGuiStage mGuiStage;
    private float mGameHeight;

    protected String mActiveTitle;


    public GameBonus(GameStage gameStage, float gameHeight) {
        mStage = gameStage;
        mGameHeight = gameHeight;

        this.setSize(2f,2f);
        mTouched = false;
        mAddsActions = false;
        mActive = false;
        mQuantum = false;
        mActiveTime = 0;
        mTimingBalance = 3;

        mActiveTitle = AssetLoader.sBundle.format("PLAY_BONUS_ACTIVE_TITLE", mName);

        mRunnerBounds = new Rectangle(Constants.RUNNER_X, Constants.RUNNER_Y
                , Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT);
        mBonusBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (this.getX()<Constants.GAME_WIDTH&&this.getX()+getWidth()>0) {
            if (!mTouched) {

                this.setX(this.getX() + mSpeed*delta);

                if (mStage.getState()!= GameState.GAME_OVER&&!mStage.getRunner().getUserData().isDead()
                        && (mStage.getRunner().getBody()!=null)) {

                    mRunnerBounds
                            .setPosition(
                                    mStage
                                            .getRunner()
                                            .getBody()
                                            .getPosition().x

//                            - ((RunnerUserData) mStage.getRunner().getBody()
//                                            .getUserData())
//                                            .getWidth() / 2f
                                    - Constants.RUNNER_WIDTH/2f
                            , mStage.getRunner().getBody().getPosition().y
//                            - ((RunnerUserData) mStage.getRunner().getBody().getUserData()).getHeight() / 2f);
                                            - Constants.RUNNER_HEIGHT/2f);
                    mBonusBounds.setPosition(getX(), getY());

                    if (mRunnerBounds.overlaps(mBonusBounds)) {
                        mTouched = true;
                    }
                }

            } else if (!mAddsActions){
                activation();
                mGuiStage.setLabel(mActiveTitle, 2f);
                AssetLoader.getBonusSound.play(SoundSystem.getSoundValue());
                this.addAction(Actions.sequence(Actions.sizeBy(1.15f, 1.15f, 0.08f)
                        , Actions.sizeBy(-1.15f, -1.15f, 0.08f)));
                this.addAction(Actions.moveTo(3f, mGameHeight - getHeight(), 0.2f));
                mAddsActions = true;
                mActiveTime = 0;
                if (!mQuantum) {
                    mGuiStage.enableBonusTimer();
                }
                mGuiStage.setBonusTimer(getX(), getY(), (int) (mWorkingTime - mActiveTime));
            } else {
                mActiveTime += delta;
                float timeBalance = (mWorkingTime - mActiveTime);
                if (!mQuantum) {
                    mGuiStage.setBonusTimer(getX(), getY(), (int) timeBalance);
                }

                if (timeBalance<mTimingBalance) {
                    this.setVisible(!this.isVisible());
                    mTimingBalance -=0.5f;
                }

                if (mActiveTime>mWorkingTime) {
                    if (!mQuantum) {
                        AssetLoader.balanceBonusSound.play(SoundSystem.getSoundValue());
                    }
                    deactivation();
                    disabling();
                }
            }
        } else if (this.getX()>Constants.GAME_WIDTH) {
            this.setX(this.getX() + mSpeed*delta);
        } else {
            disabling();
        }

    }

    public void disabling() {
        if (!mQuantum) {
            mGuiStage.disableBonusTimer();
        }
        setVisible(false);
        mTouched = false;
        mAddsActions = false;
        mActive = false;
        mActiveTime = 0;
        mTimingBalance = 3;
        this.remove();
    }

    public void init(float x, float y) {
        mActive = true;
        this.setPosition(x + 1.5f, y + 1.5f);
        this.setVisible(true);
        mStage.addActor(this);
        mSpeed = mStage.getCurrentVelocity().x;
    }

    public abstract void activation();

    public abstract void deactivation();

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(mRegion, getX(), getY(), getWidth(), getHeight());
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        mActive = active;
    }

    public void setGui(GameGuiStage gui) {
        mGuiStage = gui;
    }

    public void changingStaticSpeed(float speedScale) {

        mSpeed = speedScale;
    }
}
