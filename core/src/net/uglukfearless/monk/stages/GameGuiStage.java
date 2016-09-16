package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.GameState;
import net.uglukfearless.monk.screens.GameScreen;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.ScoreCounter;

/**
 * Created by Ugluk on 22.08.2016.
 */
public class GameGuiStage extends Stage {

    private static int VIEWPORT_WIDTH;
    private static int VIEWPORT_HEIGHT;

    private GameScreen mGameScreen;
    private GameStage mGameStage;

    private GameState mGameState;

    private Label mStartLabel;
    private Label mCurrentScore, mCurrentHighScore, mCurrentTime;
    private Group mGuiGroup;

    private Label mScore, mTime, mKilled, mDestroyed, mDeathLabel;
    private Label mScoreValue, mTimeValue, mKilledValue, mDestroyedValue;
    private Array<Label> mLabelDeathArray;
    private Array<Label> mLabelDeathValueArray;
    private Table mContainerDeath, mMainTableDeath;
    private ScrollPane mScrollPaneDeath;

    private Array<Actor> mNewAchieveListObj;

    private Label mPauseLabel;

    private CharSequence mTimeString = "0";

    private Label mLabel;
    private Image mImage;

    private TextButton mMenyButton, mReplayButton;

    private float mLabelStateTime;
    private float mLabelDuration;
    private Label mBonusLabel;
    private float mYGameHeight;

    private Label mRevivalLabel;
    private int mCurrentHighScoreValue;


    public GameGuiStage(GameScreen screen, GameStage gameStage, float yViewportHeight) {

        super(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));

        VIEWPORT_WIDTH = (int) Gdx.graphics.getWidth();
        VIEWPORT_HEIGHT = (int) Gdx.graphics.getHeight();
        mYGameHeight = yViewportHeight;
        mGameScreen = screen;
        mGameStage = gameStage;

        mNewAchieveListObj = new Array<Actor>();

        setupLabels();
        setupButtons();

        mLabelStateTime = 0;

    }

    private void setupButtons() {

        mMenyButton = new TextButton(AssetLoader.sBundle.get("MENU_DEATH_BUTTON_GO_MENU"), AssetLoader.sGuiSkin);
        mMenyButton.setBounds(100, mMainTableDeath.getY(), 200, 100);
        mMenyButton.setVisible(false);
        mMenyButton.setColor(100, 200, 0, 1);
        mMenyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                System.out.println("***********************************");
                System.out.println("средний fps" + mGameStage.getTimeMid());
                System.out.println("***********************************");
                mGameScreen.setMenu();
            }
        });
        addActor(mMenyButton);

        mReplayButton = new TextButton(AssetLoader.sBundle.get("MENU_DEATH_BUTTON_REPLAY"), AssetLoader.sGuiSkin);
        mReplayButton.setBounds(100 + mMainTableDeath.getWidth() - 200, mMainTableDeath.getY(), 200, 100);
        mReplayButton.setVisible(false);
        mReplayButton.setColor(100, 200, 0, 1);
        mReplayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                System.out.println("***********************************");
                System.out.println("средний fps" + mGameStage.getTimeMid());
                System.out.println("***********************************");
                mGameScreen.newGame();
            }
        });

        addActor(mReplayButton);
    }

    private void setupLabels() {
        mGuiGroup = new Group();

        mStartLabel = new Label(AssetLoader.sBundle.get("PLAY_START_LABEL"), AssetLoader.sGuiSkin);
        mStartLabel.setPosition(VIEWPORT_WIDTH / 2f - mStartLabel.getWidth() / 2f,
                VIEWPORT_HEIGHT * 0.65f);
        mStartLabel.setVisible(false);
        addActor(mStartLabel);

        mBonusLabel = new Label("00", AssetLoader.sGuiSkin, "normal_red");
        mBonusLabel.setVisible(false);
        addActor(mBonusLabel);

        mRevivalLabel = new Label("0", AssetLoader.sGuiSkin, "normal_red");
        mRevivalLabel.setVisible(false);
        addActor(mRevivalLabel);

        mCurrentScore = new Label(String.valueOf(ScoreCounter.getScore()), AssetLoader.sGuiSkin);
        mCurrentScore.setPosition(0, VIEWPORT_HEIGHT - mCurrentScore.getHeight());
        mGuiGroup.addActor(mCurrentScore);

        mCurrentTime = new Label(mTimeString, AssetLoader.sGuiSkin);
        mCurrentTime.setPosition(VIEWPORT_WIDTH / 2f - mCurrentTime.getWidth() / 2f, VIEWPORT_HEIGHT - mCurrentTime.getHeight());
        mGuiGroup.addActor(mCurrentTime);

        mCurrentHighScoreValue = 0;
        mCurrentHighScore = new Label(String.valueOf(mCurrentHighScoreValue), AssetLoader.sGuiSkin);
        mCurrentHighScore.setPosition(VIEWPORT_WIDTH - mCurrentHighScore.getWidth(), VIEWPORT_HEIGHT - mCurrentHighScore.getHeight());
        mGuiGroup.addActor(mCurrentHighScore);

        addActor(mGuiGroup);

        //setupDeathGui
        mLabelDeathArray = new Array<Label>();
        mLabelDeathValueArray = new Array<Label>();

        mDeathLabel = new Label(AssetLoader.sBundle.get("MENU_DEATH_DEAD_LABEL"), AssetLoader.sGuiSkin);

        mScore = new Label(AssetLoader.sBundle.get("MENU_DEATH_SCORE"), AssetLoader.sGuiSkin);
        mLabelDeathArray.add(mScore);
        mScoreValue = new Label(String.valueOf(ScoreCounter.getScore()), AssetLoader.sGuiSkin);
        mLabelDeathValueArray.add(mScoreValue);

        mTime = new Label(AssetLoader.sBundle.get("MENU_DEATH_TIME"), AssetLoader.sGuiSkin);
        mLabelDeathArray.add(mTime);
        mTimeValue = new Label(String.valueOf(ScoreCounter.getTime()), AssetLoader.sGuiSkin);
        mLabelDeathValueArray.add(mTimeValue);

        mKilled = new Label(AssetLoader.sBundle.get("MENU_DEATH_KILLED"), AssetLoader.sGuiSkin);
        mLabelDeathArray.add(mKilled);
        mKilledValue = new Label(String.valueOf(ScoreCounter.getKilled()), AssetLoader.sGuiSkin);
        mLabelDeathValueArray.add(mKilledValue);

        mDestroyed = new Label(AssetLoader.sBundle.get("MENU_DEATH_DESTROYED"), AssetLoader.sGuiSkin);
        mLabelDeathArray.add(mDestroyed);
        mDestroyedValue = new Label(String.valueOf(ScoreCounter.getDestroyed()), AssetLoader.sGuiSkin);
        mLabelDeathValueArray.add(mDestroyedValue);

        mContainerDeath = new Table();
        mContainerDeath.add(mDeathLabel).align(Align.center).fill().expand()
                .padBottom(10).padTop(15);
        mContainerDeath.row();

        for (int i = 0; i <4; i++) {
            mContainerDeath.add(mLabelDeathArray.get(i)).fill().expand()
                    .align(Align.left).padBottom(5).padTop(5);

            mContainerDeath.add(mLabelDeathValueArray.get(i)).align(Align.right);

            mContainerDeath.row();
        }


        mScrollPaneDeath = new ScrollPane(mContainerDeath);

        mMainTableDeath = new Table();
        mMainTableDeath.background(new NinePatchDrawable(new NinePatch(AssetLoader.broadbord)));
        mMainTableDeath.pad(15);
        mMainTableDeath.padBottom(100);
        mMainTableDeath.setBounds(100, VIEWPORT_HEIGHT / 5f, VIEWPORT_WIDTH * 0.75f
                , VIEWPORT_HEIGHT - VIEWPORT_HEIGHT / 4f);
        mMainTableDeath.add(mScrollPaneDeath).fill().expand();
        mMainTableDeath.setVisible(false);

        addActor(mMainTableDeath);

        mPauseLabel = new Label(AssetLoader.sBundle.get("PLAY_PAUSE_LABEL"), AssetLoader.sGuiSkin, "title");
        mPauseLabel.setPosition(VIEWPORT_WIDTH/2f - mPauseLabel.getWidth()/2f
                , VIEWPORT_HEIGHT/2f - mPauseLabel.getHeight()/2f);
        mPauseLabel.setVisible(false);
        addActor(mPauseLabel);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        mTimeString = String.valueOf((int) mGameStage.getRunTime());

        mCurrentScore.setText(String.valueOf(ScoreCounter.getScore()));
        mCurrentTime.setText(mTimeString);
        mCurrentTime.setPosition(VIEWPORT_WIDTH / 2 - mCurrentTime.getWidth() / 2, VIEWPORT_HEIGHT - mCurrentTime.getHeight());

        if(mGameState!=mGameStage.getState()) {

            switch (mGameStage.getState()) {
                case RUN:
                    mStartLabel.setVisible(false);
                    mMainTableDeath.setVisible(false);
                    mMenyButton.setVisible(false);
                    mReplayButton.setVisible(false);
                    mPauseLabel.setVisible(false);
                    break;
                case START:
                    mStartLabel.setText(AssetLoader.sBundle.get("PLAY_START_LABEL"));
                    mStartLabel.setSize(mStartLabel.getPrefWidth(), mStartLabel.getPrefHeight());
                    mStartLabel.setPosition(VIEWPORT_WIDTH / 2f - mStartLabel.getWidth() / 2f,
                            VIEWPORT_HEIGHT * 0.65f);
                    mStartLabel.setVisible(true);
                    mMainTableDeath.setVisible(false);
                    mMenyButton.setVisible(false);
                    mReplayButton.setVisible(false);
                    mPauseLabel.setVisible(false);

                    for (Actor actor:mNewAchieveListObj) {
                        mContainerDeath.getCell(actor).reset();
                        mContainerDeath.removeActor(actor);
                        actor.remove();
                    }
                    mNewAchieveListObj.clear();

                    break;
                case GAME_OVER:

                    mScoreValue.setText(String.valueOf(ScoreCounter.getScore()));
                    if (ScoreCounter.getScore()>mCurrentHighScoreValue) {
                        mCurrentHighScoreValue = ScoreCounter.getScore();
                        mCurrentHighScore.setText(String.valueOf(mCurrentHighScoreValue));
                        mCurrentHighScore.setSize(mCurrentHighScore.getPrefWidth(), mCurrentHighScore.getPrefHeight());
                        mCurrentHighScore.setPosition(VIEWPORT_WIDTH - mCurrentHighScore.getWidth(),
                                mCurrentHighScore.getY());
                    }
                    mTimeValue.setText(String.valueOf(ScoreCounter.getTime()));
                    mKilledValue.setText(String.valueOf(ScoreCounter.getKilled()));
                    mDestroyedValue.setText(String.valueOf(ScoreCounter.getDestroyed()));

                    for (String achieve : ScoreCounter.getNewAchieve()) {
                        mLabel = new Label(AssetLoader.sBundle.get(achieve), AssetLoader.sGuiSkin);
                        mContainerDeath.add(mLabel).align(Align.left).fill().expand().padBottom(5).padTop(5);
                        mNewAchieveListObj.add(mLabel);

                        mImage = new Image(AssetLoader.sAchieveRegions.get(achieve));
                        mContainerDeath.add(mImage).align(Align.right).prefWidth(60).prefHeight(60).padBottom(5).padTop(5);
                        mNewAchieveListObj.add(mImage);

                        mContainerDeath.row();
                    }
                    mScrollPaneDeath.layout();
                    mScrollPaneDeath.setScrollPercentY(0);

                    mMainTableDeath.setVisible(true);
                    mMenyButton.setVisible(true);
                    mReplayButton.setVisible(true);
                    mStartLabel.setVisible(false);
                    mPauseLabel.setVisible(false);
                    break;
                case PAUSE:
                    mPauseLabel.setVisible(true);
                    break;
            }
        }

        if (mStartLabel.isVisible()&&mGameStage.getState()!=GameState.START) {
            mLabelStateTime +=delta;
            if (mLabelStateTime>mLabelDuration) {
                mStartLabel.setVisible(false);
            }
        }

        mGameState = mGameStage.getState();

    }

    public void setGameStage(GameStage gameStage) {
        mGameStage = gameStage;
    }

    public void setLabel(String label, float duration) {
        mStartLabel.setText(label);
        mStartLabel.setSize(mStartLabel.getPrefWidth(), mStartLabel.getPrefHeight());
        mStartLabel.setPosition(VIEWPORT_WIDTH / 2f - mStartLabel.getWidth() / 2f,
                VIEWPORT_HEIGHT * 0.65f);
        mStartLabel.setVisible(true);
        mLabelStateTime = 0f;
        mLabelDuration = duration;
    }

    public void enableBonusTimer() {
        mBonusLabel.setVisible(true);
    }

    public void setBonusTimer(float x, float y,int timerValue) {
        mBonusLabel.setText(String.valueOf(timerValue));
        mBonusLabel.setPosition(x*(float)(Gdx.graphics.getWidth()/Constants.GAME_WIDTH),
                y*(float)(Gdx.graphics.getHeight()/mYGameHeight));
    }

    public void disableBonusTimer() {
        mBonusLabel.setVisible(false);
    }

    public void enableRevivalLabel() {
        mRevivalLabel.setVisible(true);
    }

    public void setRevivalLabel(float x, float y,int revivalValue) {
        mRevivalLabel.setText(String.valueOf(revivalValue));
        mRevivalLabel.setPosition(x*(float)(Gdx.graphics.getWidth()/Constants.GAME_WIDTH),
                y*(float)(Gdx.graphics.getHeight()/mYGameHeight));
    }

    public void disableRevivalLabel() {
        mRevivalLabel.setVisible(false);
    }
}
