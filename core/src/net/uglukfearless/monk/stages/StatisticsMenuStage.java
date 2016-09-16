package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.uglukfearless.monk.actors.menu.MenuBackground;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.screens.MainMenuScreen;
import net.uglukfearless.monk.ui.BackButton;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.ScoreCounter;

/**
 * Created by Ugluk on 08.08.2016.
 */
public class StatisticsMenuStage extends Stage {
    public static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    public static  int VIEWPORT_HEIGHT;

    private MainMenuScreen mScreen;

    private Vector3 mTouchPoint;

    private BackButton mBackButton;

    private Label mTitle;

    private Label mHighScore, mTime, mKilled, mDestroyed, mDeaths, mEfficiency;
    private Label mHighScoreValue, mTimeValue, mKilledValue, mDestroyedValue, mDeathsValue, mEfficiencyValue;

    private Cell<Label> mCell;
    private Label mLabel;
    private Image mImage;

    private Array<Label> mLabelArray;
    private Array<Label> mLabelValueArray;

    private Table mContainerLoc, mMainTableLoc;
    private ScrollPane mScrollPaneLoc;

    private Table mContainerGlob, mMainTableGlob;
    private ScrollPane mScrollPaneGlob;

    private Table mContainerAch, mMainTableAch;
    private ScrollPane mScrollPaneAch;

    private TextButton mLocalButton, mGlobalButton, mAchieveButton;
    private boolean mResized;

    public StatisticsMenuStage(MainMenuScreen screen, float yViewportHeight) {
        super(new FitViewport(VIEWPORT_WIDTH, yViewportHeight,
                new OrthographicCamera(VIEWPORT_WIDTH, yViewportHeight)));
        VIEWPORT_HEIGHT = (int) yViewportHeight;

        mScreen = screen;

        addActor(new MenuBackground());
        Gdx.input.setInputProcessor(this);

        mTouchPoint = new Vector3();

        setupTitle(AssetLoader.sBundle.get("MENU_STATISTICS"));

        setupLocalStats();
        setupGlobalStats();
        setupAchievement();

        setupButtons();

        mBackButton = new BackButton(AssetLoader.sGuiSkin, screen);
        mBackButton.setSize(100, 62);
        mBackButton.setPosition(0, VIEWPORT_HEIGHT - mBackButton.getHeight() - 50);
        addActor(mBackButton);

        mResized = false;

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }

    private void setupButtons() {



        mLocalButton = new TextButton(AssetLoader.sBundle.get("MENU_STATS_BUTTON_LOC"), AssetLoader.sGuiSkin);
        mLocalButton.setBounds(100, mMainTableLoc.getY() + mMainTableLoc.getHeight(), VIEWPORT_WIDTH * 0.75f / 3f,
                (mTitle.getY() - mMainTableLoc.getY() - mMainTableLoc.getHeight()) * 0.65f);
        mLocalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mMainTableLoc.setVisible(true);
                mMainTableGlob.setVisible(false);
                mMainTableAch.setVisible(false);

                resizeAction();
            }
        });
        addActor(mLocalButton);

        mGlobalButton = new TextButton(AssetLoader.sBundle.get("MENU_STATS_BUTTON_GL"), AssetLoader.sGuiSkin);
        mGlobalButton.setBounds(mLocalButton.getX() + mLocalButton.getWidth(),
                mMainTableLoc.getY() + mMainTableLoc.getHeight(), VIEWPORT_WIDTH * 0.75f / 3f,
                (mTitle.getY() - mMainTableLoc.getY() - mMainTableLoc.getHeight()) * 0.65f);
        mGlobalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mMainTableLoc.setVisible(false);
                mMainTableGlob.setVisible(true);
                mMainTableAch.setVisible(false);

                resizeAction();
            }
        });
        addActor(mGlobalButton);

        mAchieveButton = new TextButton(AssetLoader.sBundle.get("MENU_STATS_BUTTON_ACH"), AssetLoader.sGuiSkin);
        mAchieveButton.setBounds(mGlobalButton.getX() + mGlobalButton.getWidth(),
                mMainTableLoc.getY() + mMainTableLoc.getHeight(), VIEWPORT_WIDTH * 0.75f / 3f,
                (mTitle.getY() - mMainTableLoc.getY() - mMainTableLoc.getHeight()) * 0.65f);
        mAchieveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mMainTableLoc.setVisible(false);
                mMainTableGlob.setVisible(false);
                mMainTableAch.setVisible(true);

                resizeAction();
            }
        });
        addActor(mAchieveButton);

    }

    private void resizeAction() {
        if (!mResized) {
            float actionDuration = 0.2f;

            mMainTableLoc.addAction(Actions.sizeTo(mMainTableLoc.getWidth()
                    , VIEWPORT_HEIGHT - 30 - mLocalButton.getHeight() - VIEWPORT_HEIGHT / 9, actionDuration));
            mLocalButton.addAction(Actions.moveTo(mLocalButton.getX(), VIEWPORT_HEIGHT - 30 - mLocalButton.getHeight(), actionDuration));

            mMainTableGlob.addAction(Actions.sizeTo(mMainTableGlob.getWidth()
                    , VIEWPORT_HEIGHT - 30 - mGlobalButton.getHeight() - VIEWPORT_HEIGHT / 9, actionDuration));
            mGlobalButton.addAction(Actions.moveTo(mGlobalButton.getX(), VIEWPORT_HEIGHT - 30 - mGlobalButton.getHeight(), actionDuration));

            mMainTableAch.addAction(Actions.sizeTo(mMainTableAch.getWidth()
                    , VIEWPORT_HEIGHT - 30 - mAchieveButton.getHeight() - VIEWPORT_HEIGHT / 9, actionDuration));
            mAchieveButton.addAction(Actions.moveTo(mAchieveButton.getX(), VIEWPORT_HEIGHT - 30 - mAchieveButton.getHeight(), actionDuration));

            mResized = true;
        }
    }

    private void setupAchievement() {
        mContainerAch = new Table();

        for (String achieve : ScoreCounter.getCurrentAchieve()) {
            mLabel = new Label(AssetLoader.sBundle.get(achieve), AssetLoader.sGuiSkin);
            mCell = mContainerAch.add(mLabel).align(Align.left).fill().expand().padBottom(5).padTop(5);

            mImage = new Image(AssetLoader.sAchieveRegions.get(achieve));
            mContainerAch.add(mImage).align(Align.right).prefWidth(60).prefHeight(60).padBottom(5).padTop(5);

            mContainerAch.row();
        }

        for (String achieve : ScoreCounter.getLockAchieve()) {
            mLabel = new Label(AssetLoader.sBundle.get(achieve), AssetLoader.sGuiSkin);
            mCell = mContainerAch.add(mLabel).align(Align.left).fill().expand().padBottom(5).padTop(5);

            mImage = new Image(AssetLoader.achieveAtlas.findRegion(Constants.ACHIEVE_NAMES[Constants.ACHIEVE_NAMES.length - 1]));
            mContainerAch.add(mImage).align(Align.right).prefWidth(60).prefHeight(60).padBottom(5).padTop(5);

            mContainerAch.row();
        }

        mScrollPaneAch = new ScrollPane(mContainerAch);

        mMainTableAch = new Table();
        mMainTableAch.background(new NinePatchDrawable(new NinePatch(AssetLoader.broadbord)));
        mMainTableAch.pad(10);
        mMainTableAch.setBounds(100, VIEWPORT_HEIGHT / 9, VIEWPORT_WIDTH * 0.75f
                , VIEWPORT_HEIGHT / 2 - VIEWPORT_HEIGHT / 9);
        mMainTableAch.add(mScrollPaneAch).fill().expand();

        mMainTableAch.setVisible(false);
        addActor(mMainTableAch);
    }

    private void setupGlobalStats() {
        mContainerGlob = new Table();

        mScrollPaneGlob = new ScrollPane(mContainerGlob);

        mMainTableGlob = new Table();
        mMainTableGlob.background(new NinePatchDrawable(new NinePatch(AssetLoader.broadbord)));
        mMainTableGlob.pad(10);
        mMainTableGlob.setBounds(100, VIEWPORT_HEIGHT / 9, VIEWPORT_WIDTH * 0.75f
                , VIEWPORT_HEIGHT / 2 - VIEWPORT_HEIGHT / 9);
        mMainTableGlob.add(mScrollPaneGlob).fill().expand();

        mMainTableGlob.setVisible(false);
        addActor(mMainTableGlob);
    }

    private void setupLocalStats() {

        mLabelArray = new Array<Label>();
        mLabelValueArray = new Array<Label>();

        mHighScore = new Label(AssetLoader.sBundle.get("MENU_STATS_HIGH_SCORE"), AssetLoader.sGuiSkin);
        mLabelArray.add(mHighScore);
        mHighScoreValue = new Label(String.valueOf(PreferencesManager.getHighScore()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mHighScoreValue);

        mTime = new Label(AssetLoader.sBundle.get("MENU_STATS_TOTAL_TIME"), AssetLoader.sGuiSkin);
        mLabelArray.add(mTime);
        mTimeValue = new Label(String.valueOf(PreferencesManager.getTime()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mTimeValue);

        mKilled = new Label(AssetLoader.sBundle.get("MENU_STATS_KILLED"), AssetLoader.sGuiSkin);
        mLabelArray.add(mKilled);
        mKilledValue = new Label(String.valueOf(PreferencesManager.getKilled()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mKilledValue);

        mDestroyed = new Label(AssetLoader.sBundle.get("MENU_STATS_DESTROYED"), AssetLoader.sGuiSkin);
        mLabelArray.add(mDestroyed);
        mDestroyedValue = new Label(String.valueOf(PreferencesManager.getDestroyed()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mDestroyedValue);

        mDeaths = new Label(AssetLoader.sBundle.get("MENU_STATS_DEATHS"), AssetLoader.sGuiSkin);
        mLabelArray.add(mDeaths);
        mDeathsValue = new Label(String.valueOf(PreferencesManager.getDeaths()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mDeathsValue);

        mEfficiency = new Label(AssetLoader.sBundle.get("MENU_STATS_EFFICIENCY"), AssetLoader.sGuiSkin);
        mLabelArray.add(mEfficiency);
        mEfficiencyValue = new Label(String.valueOf(PreferencesManager.getEfficiency()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mEfficiencyValue);


        mContainerLoc = new Table();

        for (int i = 0; i <6; i++) {
            mLabel = mLabelArray.get(i);
            mCell = mContainerLoc.add(mLabel).fill().expand();
            mCell.align(Align.left);
            mCell.padBottom(5);
            mCell.padTop(5);


            mLabel = mLabelValueArray.get(i);
            mCell = mContainerLoc.add(mLabel);
            mCell.align(Align.right);

            mContainerLoc.row();
        }

        mScrollPaneLoc = new ScrollPane(mContainerLoc);

        mMainTableLoc = new Table();
        mMainTableLoc.background(new NinePatchDrawable(new NinePatch(AssetLoader.broadbord)));
        mMainTableLoc.pad(10);
        mMainTableLoc.setBounds(100, VIEWPORT_HEIGHT / 9, VIEWPORT_WIDTH * 0.75f
                , VIEWPORT_HEIGHT / 2 - VIEWPORT_HEIGHT / 9);
        mMainTableLoc.add(mScrollPaneLoc).fill().expand();

        mMainTableLoc.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                resizeAction();
            }
        });

        addActor(mMainTableLoc);
    }

    public void setupTitle(String title) {
        mTitle = new Label(title , AssetLoader.sGuiSkin, "title");
        mTitle.setPosition(VIEWPORT_WIDTH/2f - mTitle.getWidth()/2f, VIEWPORT_HEIGHT - 30 - mTitle.getHeight());
        addActor(mTitle);
    }


    private void translateScreenToWorldCoordinates(int screenX, int screenY) {
        getCamera().unproject(mTouchPoint.set(screenX, screenY, 0));
    }

    @Override
    public boolean keyDown(int keyCode) {


        if (keyCode== Input.Keys.BACK||keyCode== Input.Keys.MENU) {
            mScreen.mainMenu();
        }
        return super.keyDown(keyCode);
    }
}
