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


/**
 * Created by Ugluk on 10.09.2016.
 */
public abstract class GameBonus extends Actor {

    protected String mName;
    protected GameStage mStage;
    private float mSpeed = Constants.WORLD_STATIC_VELOCITY.x;

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

    private String mActiveTitle;


    public GameBonus(GameStage gameStage, float gameHeight) {
        mStage = gameStage;
        mGameHeight = gameHeight;

        this.setSize(2f,2f);
        mTouched = false;
        mAddsActions = false;
        mActive = false;
        mActiveTime = 0;
        mTimingBalance = 3;

        mActiveTitle = AssetLoader.sBundle.get("PLAY_BONUS_ACTIVE_TITLE");

        mRunnerBounds = new Rectangle(Constants.RUNNER_X, Constants.RUNNER_Y
                , Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT);
        mBonusBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (this.getX()<Constants.GAME_WIDTH&&this.getX()+getWidth()>0) {
            if (!mTouched) {

                if (mStage.getState()!= GameState.GAME_OVER&&!mStage.getRunner().getUserData().isDead()) {

                    this.setX(this.getX() + mSpeed*delta);

                    mRunnerBounds.setPosition(mStage.getRunner().getBody().getPosition().x
                            - ((RunnerUserData) mStage.getRunner().getBody().getUserData()).getWidth() / 2f
                            , mStage.getRunner().getBody().getPosition().y
                            - ((RunnerUserData) mStage.getRunner().getBody().getUserData()).getHeight() / 2f);
                    mBonusBounds.setPosition(getX(), getY());

                    if (mRunnerBounds.overlaps(mBonusBounds)) {
                        mTouched = true;
                    }
                }

            } else if (!mAddsActions){
                activation();
                mGuiStage.setLabel(mName + mActiveTitle, 2f);
                AssetLoader.getBonus.play(SoundSystem.getSoundValue());
                this.addAction(Actions.sequence(Actions.sizeBy(1.15f, 1.15f, 0.08f)
                        , Actions.sizeBy(-1.15f, -1.15f, 0.08f)));
                this.addAction(Actions.moveTo(3f, mGameHeight - getHeight(), 0.2f));
                mAddsActions = true;
                mActiveTime = 0;
                mGuiStage.enableBonusTimer();
                mGuiStage.setBonusTimer(getX(), getY(), (int) (mWorkingTime - mActiveTime));
            } else {
                mActiveTime += delta;
                float timeBalance = (mWorkingTime - mActiveTime);
                mGuiStage.setBonusTimer(getX(), getY(), (int) timeBalance);

                if (timeBalance<mTimingBalance) {
                    this.setVisible(!this.isVisible());
                    mTimingBalance -=0.5f;
                }

                if (mActiveTime>mWorkingTime) {
                    AssetLoader.balanceBonus.play(SoundSystem.getSoundValue());
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
        mGuiStage.disableBonusTimer();
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
}