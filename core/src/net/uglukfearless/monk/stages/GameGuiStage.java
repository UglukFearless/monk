package net.uglukfearless.monk.stages;

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

    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static int VIEWPORT_HEIGHT;

    private GameScreen mGameScreen;
    private GameStage mGameStage;

    private GameState mGameState;

    private Label mStartLabel;
    private Label mCurrentScore, mHighScore, mCurrentTime;
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


    public GameGuiStage(GameScreen screen, GameStage gameStage, float yViewportHeight) {
        super(new FillViewport(VIEWPORT_WIDTH, yViewportHeight,
                new OrthographicCamera(VIEWPORT_WIDTH, yViewportHeight)));

        VIEWPORT_HEIGHT = (int) yViewportHeight;
        mGameScreen = screen;
        mGameStage = gameStage;

        mNewAchieveListObj = new Array<Actor>();

        setupLabels();
        setupButtons();

    }

    private void setupButtons() {

        mMenyButton = new TextButton("Go to Menu", AssetLoader.sGuiSkin);
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

        mReplayButton = new TextButton("REPLAY", AssetLoader.sGuiSkin);
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

        mStartLabel = new Label("PROTECT MONASTERY", AssetLoader.sGuiSkin);
        mStartLabel.setPosition(VIEWPORT_WIDTH / 2 - mStartLabel.getWidth() / 2,
                VIEWPORT_HEIGHT * 0.65f);
        mStartLabel.setVisible(false);
        addActor(mStartLabel);

        mCurrentScore = new Label(String.valueOf(ScoreCounter.getScore()), AssetLoader.sGuiSkin);
        mCurrentScore.setPosition(0, VIEWPORT_HEIGHT - mCurrentScore.getHeight());
        mGuiGroup.addActor(mCurrentScore);

        mCurrentTime = new Label(mTimeString, AssetLoader.sGuiSkin);
        mCurrentTime.setPosition(VIEWPORT_WIDTH / 2 - mCurrentTime.getWidth() / 2, VIEWPORT_HEIGHT - mCurrentTime.getHeight());
        mGuiGroup.addActor(mCurrentTime);

        mHighScore = new Label((String.valueOf(ScoreCounter.getHighScore())), AssetLoader.sGuiSkin);
        mHighScore.setPosition(VIEWPORT_WIDTH - mHighScore.getWidth(), VIEWPORT_HEIGHT - mHighScore.getHeight());
        mGuiGroup.addActor(mHighScore);

        addActor(mGuiGroup);

        //setupDeathGui
        mLabelDeathArray = new Array<Label>();
        mLabelDeathValueArray = new Array<Label>();

        mDeathLabel = new Label("You Dead!", AssetLoader.sGuiSkin);

        mScore = new Label("Score:", AssetLoader.sGuiSkin);
        mLabelDeathArray.add(mScore);
        mScoreValue = new Label(String.valueOf(ScoreCounter.getScore()), AssetLoader.sGuiSkin);
        mLabelDeathValueArray.add(mScoreValue);

        mTime = new Label("Total time:", AssetLoader.sGuiSkin);
        mLabelDeathArray.add(mTime);
        mTimeValue = new Label(String.valueOf(ScoreCounter.getTime()), AssetLoader.sGuiSkin);
        mLabelDeathValueArray.add(mTimeValue);

        mKilled = new Label("Killed enemies:", AssetLoader.sGuiSkin);
        mLabelDeathArray.add(mKilled);
        mKilledValue = new Label(String.valueOf(ScoreCounter.getKilled()), AssetLoader.sGuiSkin);
        mLabelDeathValueArray.add(mKilledValue);

        mDestroyed = new Label("Destroyed obstacles:", AssetLoader.sGuiSkin);
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
        mMainTableDeath.setBounds(100, VIEWPORT_HEIGHT / 5, VIEWPORT_WIDTH * 0.75f
                , VIEWPORT_HEIGHT - VIEWPORT_HEIGHT / 4);
        mMainTableDeath.add(mScrollPaneDeath).fill().expand();
        mMainTableDeath.setVisible(false);

        addActor(mMainTableDeath);

        mPauseLabel = new Label("PAUSE", AssetLoader.sGuiSkin, "title");
        mPauseLabel.setPosition(VIEWPORT_WIDTH/2 - mPauseLabel.getWidth()/2
                , VIEWPORT_HEIGHT/2 - mPauseLabel.getHeight()/2);
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
        mHighScore.setText(String.valueOf(ScoreCounter.getHighScore()));

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
                    mTimeValue.setText(String.valueOf(ScoreCounter.getTime()));
                    mKilledValue.setText(String.valueOf(ScoreCounter.getKilled()));
                    mDestroyedValue.setText(String.valueOf(ScoreCounter.getDestroyed()));

                    for (String achieve : ScoreCounter.getNewAchieve()) {
                        mLabel = new Label(achieve, AssetLoader.sGuiSkin);
                        mContainerDeath.add(mLabel).align(Align.left).fill().expand().padBottom(5).padTop(5);
                        mNewAchieveListObj.add(mLabel);

                        mImage = new Image(AssetLoader.sAchieveRegions.get(achieve));
                        mContainerDeath.add(mImage).align(Align.right).prefWidth(60).prefHeight(60).padBottom(5).padTop(5);
                        mNewAchieveListObj.add(mImage);

                        mContainerDeath.row();
                        System.out.println(achieve);
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

        mGameState = mGameStage.getState();

    }

    public void setGameStage(GameStage gameStage) {
        mGameStage = gameStage;
    }
}
