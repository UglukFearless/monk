package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.GameState;
import net.uglukfearless.monk.screens.GameScreen;
import net.uglukfearless.monk.utils.BonusShow;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.ScoreCounter;
import net.uglukfearless.monk.utils.gameplay.achievements.Achievement;

import java.util.HashMap;

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

    private Label mScore, mTime, mKilled, mDestroyed, mKillPercent, mKillingSpeed, mDeathLabel;
    private Label mScoreValue, mTimeValue, mKilledValue, mKillPercentValue, mKillingSpeedValue, mDestroyedValue;
    private Array<Label> mLabelDeathArray;
    private Array<Label> mLabelDeathValueArray;
    private Table mContainerDeath, mMainTableDeath;
    private ScrollPane mScrollPaneDeath;

    private Array<Actor> mNewAchieveListObj;

    private Label mPauseLabel;

    private CharSequence mTimeString = "0";

    private TextButton mMenyButton, mReplayButton;

    private float mLabelStateTime;
    private float mLabelDuration;
    private Label mBonusLabel;
    private float mYGameHeight;

    private Label mRevivalLabel;
    private int mCurrentHighScoreValue;

    private Table mMainTableAchieve, mTableAchieve;
    private Table mTableAchieveHelp;
    private ScrollPane mAchieveScroll;
    private Label mUnlockTitle, mAchieveName, mUpgradeTitle;
    private Image mAchieveImage;
    private Array<Table> mTableAchieveList;

    private TextButton mJumpButton, mStrikeButton;
    private BonusShow mBonusShowWindow;

    private Table mMainWaitTable;
    private Button mGameOverButton;
    private TextButton mShowVideoButton, mPayTreasuresButton;
    private Label mBalance;
    private Image mTreasuresImage;

    private TextButton mShopButton;


    public GameGuiStage(GameScreen screen, GameStage gameStage, float yViewportHeight) {

        super(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));

        VIEWPORT_WIDTH = Gdx.graphics.getWidth();
        VIEWPORT_HEIGHT = Gdx.graphics.getHeight();
        mYGameHeight = yViewportHeight;
        mGameScreen = screen;
        mGameStage = gameStage;

        mNewAchieveListObj = new Array<Actor>();

        setupLabels();
        setupButtons();
        setupAchieveWindow(ScoreCounter.getAchieveList().get(6));
        setupWaitMenu();
        setupShopButton();

        mLabelStateTime = 0;

    }

    private void setupShopButton() {

        mShopButton = new TextButton(AssetLoader.sBundle.get("MENU_GAME_BUTTON_SHOP"),AssetLoader.sGuiSkin);
        mShopButton.setPosition(VIEWPORT_WIDTH/2f - mShopButton.getWidth()/2f, 0);
        mShopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mGameScreen.setShop();
            }
        });
        addActor(mShopButton);
        mShopButton.setVisible(false);
    }

    private void setupWaitMenu() {

        mMainWaitTable = new Table();
        mMainWaitTable.setSize(VIEWPORT_WIDTH * 0.7f, VIEWPORT_HEIGHT * 0.4f);
        mMainWaitTable.setPosition(VIEWPORT_WIDTH / 2 - mMainWaitTable.getWidth() / 2f, VIEWPORT_HEIGHT / 2 - mMainWaitTable.getHeight() / 2f);
        mMainWaitTable.background(new NinePatchDrawable(AssetLoader.broadbord));
        addActor(mMainWaitTable);

        Table helpTable = new Table();

        mBalance = new Label(String.valueOf(PreferencesManager.getTreasures()), AssetLoader.sGuiSkin);
        helpTable.add(mBalance);
        mTreasuresImage = new Image(AssetLoader.bonusesAtlas.findRegion("rupee"));
        helpTable.add(mTreasuresImage);
        mMainWaitTable.add(helpTable);

        mMainWaitTable.row();

        helpTable = new Table();
        mGameOverButton = new Button(AssetLoader.sGuiSkin, "game_over");
        mGameOverButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mGameStage.realGameOver();
            }
        });
        helpTable.add(mGameOverButton).prefWidth(mMainWaitTable.getHeight()*0.55f).prefHeight(mMainWaitTable.getHeight() * 0.55f);

        mShowVideoButton = new TextButton("watch ads", AssetLoader.sGuiSkin);
        helpTable.add(mShowVideoButton).prefWidth(mMainWaitTable.getHeight()*0.55f).prefHeight(mMainWaitTable.getHeight()*0.55f);

        mPayTreasuresButton = new TextButton("-1 ", AssetLoader.sGuiSkin);
        mPayTreasuresButton.add(new Image(AssetLoader.bonusesAtlas.findRegion("rupee")));
        mPayTreasuresButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (PreferencesManager.purchase(1)) {
                    mGameStage.setRevivalRunner(true);
                    mGameStage.setState(GameState.RUN);
                    mBalance.setText(String.valueOf(PreferencesManager.getTreasures()));
                }
            }
        });
        helpTable.add(mPayTreasuresButton).prefWidth(mMainWaitTable.getHeight()*0.55f).prefHeight(mMainWaitTable.getHeight() * 0.55f);

        mMainWaitTable.add(helpTable);
        mMainWaitTable.setVisible(false);
    }

    private void setupAchieveWindow(Achievement achieve) {

        mTableAchieveList = new Array<Table>();

        mTableAchieve = new Table();

        mUnlockTitle = new Label(AssetLoader.sBundle.get("MENU_ACHIEVE_UNLOCK_TITLE"), AssetLoader.sGuiSkin);
        mTableAchieve.add(mUnlockTitle).padTop(10).padBottom(10);
        mTableAchieve.row();

        mTableAchieveHelp = new Table();
        mAchieveImage = new Image(achieve.getRegion());
        mTableAchieveHelp.add(mAchieveImage).align(Align.right).prefWidth(60).prefHeight(60).pad(15).padBottom(5).padTop(5);

        mAchieveName = new Label(achieve.getName(), AssetLoader.sGuiSkin);
        mTableAchieveHelp.add(mAchieveName).align(Align.left).fill().expand().padBottom(5).padTop(5);

        achieve.setNew(false);

        mTableAchieve.add(mTableAchieveHelp);
        mTableAchieve.row();

        mUpgradeTitle = new Label(AssetLoader.sBundle.get("MENU_ACHIEVE_UPGRADE_TITLE"), AssetLoader.sGuiSkin);


        mAchieveScroll = new ScrollPane(mTableAchieve);
        mAchieveScroll.setScrollPercentY(0);

        mMainTableAchieve = new Table();
        mMainTableAchieve.background(new NinePatchDrawable(AssetLoader.broadbord));
        mMainTableAchieve.pad(15);
        mMainTableAchieve.setBounds(100, VIEWPORT_HEIGHT / 5f, VIEWPORT_WIDTH * 0.75f
                , VIEWPORT_HEIGHT - VIEWPORT_HEIGHT / 4f);
        mMainTableAchieve.add(mAchieveScroll).fill().expand();
        mMainTableAchieve.setVisible(false);

        mMainTableAchieve.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                System.out.println(checkNewAchieve().size);
                if (checkNewAchieve().size>0) {
                    showAchievement(checkNewAchieve().get(0));
                } else {
                    mMainTableAchieve.setVisible(false);
                    mMainTableDeath.setVisible(true);
                    mMenyButton.setVisible(true);
                    mReplayButton.setVisible(true);
                }

            }
        });

        addActor(mMainTableAchieve);
    }

    private Array<Achievement> checkNewAchieve() {
        Array<Achievement> achievements = new Array<Achievement>();

        for (Achievement achievement:ScoreCounter.getAchieveList()) {
            if (achievement.isNew()) {
                achievements.add(achievement);
            }
        }

        return achievements;
    }

    private void showAchievement(Achievement achieve) {

        clearUpdates();

        mTableAchieve.add(mUnlockTitle).padTop(10).padBottom(10);
        mTableAchieve.row();

        mTableAchieveHelp = new Table();
        mAchieveImage.setDrawable(new TextureRegionDrawable(achieve.getRegion()));
        mTableAchieveHelp.add(mAchieveImage).align(Align.right).prefWidth(60).prefHeight(60).pad(15).padBottom(5).padTop(5);

        mAchieveName.setText(achieve.getName());
        mTableAchieveHelp.add(mAchieveName).align(Align.left).fill().expand().padBottom(5).padTop(5);

        achieve.setNew(false);

        mTableAchieve.add(mTableAchieveHelp);
        mTableAchieve.row();

        HashMap<String,String> updates = achieve.getUpdateList();

        if (updates!=null) {
            mTableAchieve.add(mUpgradeTitle).padTop(10).padBottom(10);
            mTableAchieve.row();

            Table updateTable = new Table();
            for (String key:updates.keySet()) {

                Image  image = new Image(AssetLoader.bonusesAtlas.findRegion(key));

                updateTable.add(image).align(Align.right).prefWidth(60).prefHeight(60);

                Label label = new Label(updates.get(key), AssetLoader.sGuiSkin);
                label.setWrap(true);
                label.setAlignment(Align.right, Align.top);

                updateTable.add(label).align(Align.right).width(VIEWPORT_WIDTH * 0.55f - image.getWidth());

                updateTable.row();
            }
            mTableAchieve.add(updateTable).align(Align.center).pad(10);
            mTableAchieveList.add(updateTable);
            mTableAchieve.row();
        }

    }

    private void clearUpdates() {

        mTableAchieve.clear();

        mTableAchieveList.clear();
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
                mGameStage.getLevelModel().getDifficultyHandler().reset();
                mGameScreen.newGame();
            }
        });

        addActor(mReplayButton);

        float helpButtonWidth = 130;

        mJumpButton = new TextButton(AssetLoader.sBundle.get("MENU_GAME_BUTTON_JUMP"), AssetLoader.sGuiSkin, "help");
//        mJumpButton.setBounds(0, 0, VIEWPORT_WIDTH/2, VIEWPORT_HEIGHT);
        mJumpButton.setBounds(0, 20, helpButtonWidth, 80);
        mJumpButton.setColor(1, 1, 1, 0.5f);
        mJumpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("jump click");
                super.clicked(event, x, y);
                event.reset();
                mGameStage.touchDown((int) event.getStageX(), (int) event.getStageY(), event.getPointer(), Input.Buttons.LEFT);
            }
        });
        addActor(mJumpButton);

        mStrikeButton = new TextButton(AssetLoader.sBundle.get("MENU_GAME_BUTTON_STRIKE"), AssetLoader.sGuiSkin, "help");
//        mStrikeButton.setBounds(VIEWPORT_WIDTH/2, 0, VIEWPORT_WIDTH/2, VIEWPORT_HEIGHT);
        mStrikeButton.setBounds(VIEWPORT_WIDTH - helpButtonWidth, 20, helpButtonWidth, 80);
        mStrikeButton.setColor(1, 1, 1, 0.5f);
        mStrikeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                event.reset();
                mGameStage.touchDown((int) event.getStageX(), (int) event.getStageY(), event.getPointer(), Input.Buttons.LEFT);

            }
        });
        addActor(mStrikeButton);
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

        mKillPercent = new Label(AssetLoader.sBundle.get("MENU_STATS_KILL_PERCENT"), AssetLoader.sGuiSkin); //использованы строки из экрана общ.статистики
        mLabelDeathArray.add(mKillPercent);
        mKillPercentValue = new Label(String.valueOf(ScoreCounter.getKilled()), AssetLoader.sGuiSkin);
        mLabelDeathValueArray.add(mKillPercentValue);

        mKillingSpeed = new Label(AssetLoader.sBundle.get("MENU_STATS_KILLING_RATE"), AssetLoader.sGuiSkin); //использованы строки из экрана общ.статистики
        mLabelDeathArray.add(mKillingSpeed);
        mKillingSpeedValue = new Label(String.valueOf(ScoreCounter.getKilled()), AssetLoader.sGuiSkin);
        mLabelDeathValueArray.add(mKillingSpeedValue);

        mDestroyed = new Label(AssetLoader.sBundle.get("MENU_DEATH_DESTROYED"), AssetLoader.sGuiSkin);
        mLabelDeathArray.add(mDestroyed);
        mDestroyedValue = new Label(String.valueOf(ScoreCounter.getDestroyed()), AssetLoader.sGuiSkin);
        mLabelDeathValueArray.add(mDestroyedValue);

        mContainerDeath = new Table();
        mContainerDeath.add(mDeathLabel).align(Align.center).fill().expand()
                .padBottom(10).padTop(15);
        mContainerDeath.row();

        for (int i = 0; i <mLabelDeathArray.size; i++) {
            mContainerDeath.add(mLabelDeathArray.get(i)).fill().expand()
                    .align(Align.left).padBottom(5).padTop(5);

            mContainerDeath.add(mLabelDeathValueArray.get(i)).align(Align.right);

            mContainerDeath.row();
        }


        mScrollPaneDeath = new ScrollPane(mContainerDeath);

        mMainTableDeath = new Table();
        mMainTableDeath.background(new NinePatchDrawable(AssetLoader.broadbord));
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

    public void startResizeAction(int x) {
        if (x<VIEWPORT_WIDTH/2&&mJumpButton!=null) {
            resizeAction(mJumpButton, false);
        } else if (x>=VIEWPORT_WIDTH/2&&mStrikeButton!=null) {
            resizeAction(mStrikeButton, true);
        }
    }

    private void resizeAction(TextButton resizeButton, boolean right) {
        float offset = 0;
        if (right) {
            offset = VIEWPORT_WIDTH/2f;
        }
        resizeButton.addAction(Actions.moveTo(0 + offset, 0, 0.1f));
        resizeButton.addAction(Actions.sizeTo(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT, 0.3f));
        resizeButton.addAction(Actions.sequence(Actions.alpha(0, 0.4f), Actions.removeActor(resizeButton)));

        resizeButton = null;
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
                    mShopButton.setVisible(false);
                    mMainWaitTable.setVisible(false);
                    mStartLabel.setVisible(false);
                    mMainTableDeath.setVisible(false);
                    mMenyButton.setVisible(false);
                    mReplayButton.setVisible(false);
                    mPauseLabel.setVisible(false);
                    mMainTableAchieve.setVisible(false);

                    if (mJumpButton!=null) {
                        mJumpButton.setVisible(true);
                    }

                    if (mStrikeButton!=null) {
                        mStrikeButton.setVisible(true);
                    }

                    if (mBonusShowWindow!=null) {
                        mBonusShowWindow.removeTable();
                    }
                    break;
                case START:
                    mShopButton.setVisible(true);
                    mMainWaitTable.setVisible(false);
                    mStartLabel.setText(AssetLoader.sBundle.get("PLAY_START_LABEL"));
                    mStartLabel.setSize(mStartLabel.getPrefWidth(), mStartLabel.getPrefHeight());
                    mStartLabel.setPosition(VIEWPORT_WIDTH / 2f - mStartLabel.getWidth() / 2f,
                            VIEWPORT_HEIGHT * 0.65f);
                    mStartLabel.setVisible(true);
                    mMainTableDeath.setVisible(false);
                    mMenyButton.setVisible(false);
                    mReplayButton.setVisible(false);
                    mPauseLabel.setVisible(false);

                    mMainTableAchieve.setVisible(false);

                    if (mJumpButton!=null) {
                        mJumpButton.setVisible(true);
                    }

                    if (mStrikeButton!=null) {
                        mStrikeButton.setVisible(true);
                    }

                    for (Actor actor:mNewAchieveListObj) {
                        mContainerDeath.getCell(actor).reset();
                        mContainerDeath.removeActor(actor);
                        actor.remove();
                    }
                    mNewAchieveListObj.clear();

                    break;
                case WAIT:
                    mBalance.setText(String.valueOf(PreferencesManager.getTreasures()));
                    mMainWaitTable.setVisible(true);
                    break;
                case GAME_OVER:
                    mMainWaitTable.setVisible(false);
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
                    mKillPercentValue.setText(String.valueOf(ScoreCounter.getKillPercent()).concat("%"));
                    mKillingSpeedValue.setText(String.valueOf(ScoreCounter.getKillingSpeed()));
                    mDestroyedValue.setText(String.valueOf(ScoreCounter.getDestroyed()));

                    mScrollPaneDeath.layout();
                    mScrollPaneDeath.setScrollPercentY(0);

                    if (checkNewAchieve().size>0) {
                        showAchievement(checkNewAchieve().get(0));
                        mMainTableAchieve.setVisible(true);
                    } else {
                        mMainTableDeath.setVisible(true);
                        mMenyButton.setVisible(true);
                        mReplayButton.setVisible(true);
                    }
                    mStartLabel.setVisible(false);
                    mPauseLabel.setVisible(false);

                    if (mJumpButton!=null) {
                        mJumpButton.setVisible(false);
                    }

                    if (mStrikeButton!=null) {
                        mStrikeButton.setVisible(false);
                    }

                    break;
                case PAUSE:

                    mPauseLabel.setVisible(true);

                    if (mJumpButton!=null) {
                        mJumpButton.setVisible(false);
                    }

                    if (mStrikeButton!=null) {
                        mStrikeButton.setVisible(false);
                    }
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
        mBonusLabel.setPosition(x * (float) (Gdx.graphics.getWidth() / Constants.GAME_WIDTH),
                y * (float) (Gdx.graphics.getHeight() / mYGameHeight));
    }

    public void disableBonusTimer() {
        mBonusLabel.setVisible(false);
    }

    public void enableRevivalLabel() {
        mRevivalLabel.setVisible(true);
    }

    public void setRevivalLabel(float x, float y,int revivalValue) {
        mRevivalLabel.setText(String.valueOf(revivalValue));
        mRevivalLabel.setPosition(x * (float) (Gdx.graphics.getWidth() / Constants.GAME_WIDTH),
                y * (float) (Gdx.graphics.getHeight() / mYGameHeight));
    }

    public void disableRevivalLabel() {
        mRevivalLabel.setVisible(false);
    }

    public void showHourBonus() {
        mBonusShowWindow = BonusShow.showBonus(
                this, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, AssetLoader.sBundle.get("MENU_SHOW_BONUS_EVERY_HOUR"), 1, "revival");
    }
}
