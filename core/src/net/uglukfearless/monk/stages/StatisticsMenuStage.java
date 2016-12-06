package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.uglukfearless.monk.actors.ParticleActor;
import net.uglukfearless.monk.actors.menu.MenuBackground;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.screens.MainMenuScreen;
import net.uglukfearless.monk.ui.BackButton;
import net.uglukfearless.monk.utils.BonusShow;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.ScoreCounter;
import net.uglukfearless.monk.utils.gameplay.achievements.Achievement;

import java.text.DecimalFormat;
import java.util.HashMap;

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

    private Label mHighScore, mTime, mBestTime, mAverageTime, mKilled, mKillingRate, mDestroyed
            ,mKillPercent , mDeaths, mTreasures, mEfficiency;

    private Label mHighScoreValue, mTimeValue, mBestTimeValue, mAverageTimeValue, mKilledValue
            ,mKillingRateValue, mDestroyedValue,mKillPercentValue, mDeathsValue, mTreasuresValue ,mEfficiencyValue;

    //по смертям
    private Label mDeathCourse;

    //по бонусам
    private Label mBonuses;
    private Label mBuddha, mGhost, mRetribution, mRevival, mThunder, mStrong, mWings, mDragon;
    private Label mBuddhaValue, mGhostValue, mRetributionValue, mRevivalValue, mThunderValue
            , mStrongValue, mWingsValue, mDragonValue;

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

    private String mHelpString;

    private Window mDescribeWindow;
    private Rectangle mDescribeWindowBounds;
    private Label mTitleDescribe, mMainDescribe, mCondition, mBonusUpdate;
    private Image mBonusImage, mAchieveImage;
    private Rectangle mDescribeRectangle;

    private Table mTableDescribe;
    private ScrollPane mDescribeScroll;

    private Array<Table> mUpdateTables;
    private Vector3 touchPoint;


    public StatisticsMenuStage(MainMenuScreen screen, float yViewportHeight) {
        super(new FitViewport(VIEWPORT_WIDTH, yViewportHeight,
                new OrthographicCamera(VIEWPORT_WIDTH, yViewportHeight)));
        VIEWPORT_HEIGHT = (int) yViewportHeight;

        mScreen = screen;

        addActor(new MenuBackground(AssetLoader.menuBackgroundTexture1, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, yViewportHeight, true));
        setupParticles(AssetLoader.sSnowParticleBack);
        addActor(new MenuBackground(AssetLoader.menuBackgroundTexture2, VIEWPORT_WIDTH, Constants.APP_HEIGHT, yViewportHeight, false));
        setupParticles(AssetLoader.sSnowParticle);
        Gdx.input.setInputProcessor(this);

        mTouchPoint = new Vector3();

        setupTitle(AssetLoader.sBundle.get("MENU_STATISTICS"));

        setupLocalStats();
        setupGlobalStats();
        setupAchievement();

        setupButtons();

        setupDescribe();



        mBackButton = new BackButton(AssetLoader.sGuiSkin, screen);
        mBackButton.setSize(100, 62);
        mBackButton.setPosition(0, VIEWPORT_HEIGHT - mBackButton.getHeight() - 50);
        addActor(mBackButton);

        mResized = false;

        touchPoint = new Vector3();

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }

    private void setupParticles(ParticleEffect particleEffect) {
        ParticleActor particleActor = new ParticleActor(particleEffect);
        particleActor.setPosition(VIEWPORT_WIDTH/2, VIEWPORT_HEIGHT);
        addActor(particleActor);
        particleActor.start();
    }

    private void setupDescribe() {

        mDescribeWindow = new Window("",AssetLoader.sGuiSkin);
        mDescribeWindow.setSize(VIEWPORT_WIDTH / 1.8f, VIEWPORT_HEIGHT / 2);
        mDescribeWindow.setPosition(VIEWPORT_WIDTH / 2 - mDescribeWindow.getWidth() / 2,
                VIEWPORT_HEIGHT / 2 - mDescribeWindow.getHeight() / 2);
        mDescribeWindowBounds = new Rectangle(mDescribeWindow.getX(), mDescribeWindow.getY(),
                mDescribeWindow.getWidth(), mDescribeWindow.getHeight());

        mDescribeWindow.setMovable(false);
        mDescribeWindow.padTop(15);
        mDescribeWindow.padLeft(10);
        mDescribeWindow.padRight(10);
        mDescribeWindow.setVisible(false);

        mTableDescribe = new Table();

        Table table1 = new Table();

        mAchieveImage = new Image(AssetLoader.achieveAtlas.findRegion("lock"));
        table1.add(mAchieveImage).align(Align.right).prefWidth(60).prefHeight(60);

        mTitleDescribe = new Label("Title!", AssetLoader.sGuiSkin);
        mTitleDescribe.setAlignment(Align.center, Align.top);

        table1.add(mTitleDescribe).align(Align.center).width(mDescribeWindow.getWidth() - 50 - mAchieveImage.getWidth()).pad(5);

        mTableDescribe.add(table1).align(Align.center).width(mDescribeWindow.getWidth() - 40).pad(5);
        mTableDescribe.row();

        mMainDescribe = new Label("Describe! Describe! Describe!", AssetLoader.sGuiSkin);
        mMainDescribe.setAlignment(Align.center, Align.left);
        mMainDescribe.setWrap(true);
        mTableDescribe.add(mMainDescribe).align(Align.center).width(mDescribeWindow.getWidth() - 30).pad(5);
        mTableDescribe.row();

        mCondition = new Label("Condition! Condition! Condition!", AssetLoader.sGuiSkin);
        mCondition.setAlignment(Align.center, Align.left);
        mCondition.setWrap(true);
        mTableDescribe.add(mCondition).align(Align.center).width(mCondition.getWidth() - 30).pad(5);
        mTableDescribe.row();


        mUpdateTables = new Array<Table>();

        mBonusUpdate = new Label("+ 0 нихуя к ничему", AssetLoader.sGuiSkin);
        mBonusUpdate.setWrap(true);
        mBonusUpdate.setAlignment(Align.right, Align.top);

        mBonusImage = new Image(AssetLoader.bonusesAtlas.findRegion("buddha"));

        Table table2 = new Table();
        table2.add(mBonusImage).align(Align.right).prefWidth(60).prefHeight(60);

        table2.add(mBonusUpdate).align(Align.right).width(mDescribeWindow.getWidth() - mBonusImage.getWidth() - 40);

        mUpdateTables.add(table2);

        mTableDescribe.add(table2).align(Align.center).width(mDescribeWindow.getWidth() - 40).pad(5);

        mDescribeScroll = new ScrollPane(mTableDescribe);

        Table mainDescribe = new Table();
        mainDescribe.pad(10);
        mainDescribe.setBounds(mDescribeWindow.getX() + 5, mDescribeWindow.getY() + 5, mDescribeWindow.getWidth() -10
                , mDescribeWindow.getHeight() - 10);
        mainDescribe.add(mDescribeScroll).width(mainDescribe.getWidth());

        mDescribeWindow.add(mainDescribe).width(mDescribeWindow.getWidth()-30);

        addActor(mDescribeWindow);

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

        for (final Achievement achieve : ScoreCounter.getAchieveList()) {
            if (achieve.isUnlock()&&!Achievement.isUnlock(achieve.getImprovementKey())) {

                mLabel = new Label(achieve.getName(), AssetLoader.sGuiSkin);
                mCell = mContainerAch.add(mLabel).align(Align.left).fill().expand().padBottom(5).padTop(5);

                mImage = new Image(achieve.getRegion());
                mImage.addListener(new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);

                        showDescribe(achieve, false);
                    }
                });
                mContainerAch.add(mImage).align(Align.right).prefWidth(60).prefHeight(60).padBottom(5).padTop(5);

                mContainerAch.row();
            }
        }

        for (final Achievement achieve : ScoreCounter.getAchieveList()) {
            if (!achieve.isUnlock()) {

                mLabel = new Label(achieve.getName(), AssetLoader.sGuiSkin);
                mCell = mContainerAch.add(mLabel).align(Align.left).fill().expand().padBottom(5).padTop(5);

                mImage = new Image(AssetLoader.achieveAtlas.findRegion(Achievement.LOCK_IMAGE_NAME));
                mImage.addListener(new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);

                        showDescribe(achieve, true);
                    }
                });
                mContainerAch.add(mImage).align(Align.right).prefWidth(60).prefHeight(60).padBottom(5).padTop(5);

                mContainerAch.row();
            }
        }

        mScrollPaneAch = new ScrollPane(mContainerAch);

        mMainTableAch = new Table();
        mMainTableAch.background(new NinePatchDrawable(AssetLoader.broadbord));
        mMainTableAch.pad(10);
        mMainTableAch.setBounds(100, VIEWPORT_HEIGHT / 9, VIEWPORT_WIDTH * 0.75f
                , VIEWPORT_HEIGHT / 2 - VIEWPORT_HEIGHT / 9);
        mMainTableAch.add(mScrollPaneAch).fill().expand();

        mMainTableAch.setVisible(false);
        addActor(mMainTableAch);
    }

    private void showDescribe(Achievement achieve, boolean lock) {

        mTitleDescribe.setText(achieve.getName());

        if (lock) {
            mAchieveImage.setDrawable(new TextureRegionDrawable(AssetLoader.achieveAtlas.findRegion(Constants.ACHIEVE_LOCK_REGION)));
        } else {
            mAchieveImage.setDrawable(new TextureRegionDrawable(achieve.getRegion()));
        }

        mAchieveImage.setSize(60, 60);

        mMainDescribe.setText(achieve.getDescribe());

        mCondition.setText(achieve.getConditionString());

        for (Table table: mUpdateTables) {
            mTableDescribe.getCell(table).reset();
            mTableDescribe.removeActor(table);
        }
        mUpdateTables.clear();

        HashMap<String,String> updates = achieve.getUpdateList();

        if (updates!=null) {
            mTableDescribe.row();
            Table updateTable = new Table();
            for (String key:updates.keySet()) {

                Image  image = new Image(AssetLoader.bonusesAtlas.findRegion(key));

                updateTable.add(image).align(Align.right).prefWidth(60).prefHeight(60);

                Label label = new Label(updates.get(key), AssetLoader.sGuiSkin);
                label.setWrap(true);
                label.setAlignment(Align.right, Align.top);

                updateTable.add(label).align(Align.right).width(mDescribeWindow.getWidth() - image.getWidth() - 40);

                updateTable.row();
            }
            mUpdateTables.add(updateTable);
            mTableDescribe.add(updateTable).align(Align.center).width(mDescribeWindow.getWidth() - 40).pad(5);
            mTableDescribe.row();
        }

        mDescribeScroll.setScrollPercentY(0);

        mDescribeWindow.setVisible(true);

    }

    private void setupGlobalStats() {
        mContainerGlob = new Table();

        mScrollPaneGlob = new ScrollPane(mContainerGlob);

        mMainTableGlob = new Table();
        mMainTableGlob.background(new NinePatchDrawable(AssetLoader.broadbord));
        mMainTableGlob.pad(10);
        mMainTableGlob.setBounds(100, VIEWPORT_HEIGHT / 9, VIEWPORT_WIDTH * 0.75f
                , VIEWPORT_HEIGHT / 2 - VIEWPORT_HEIGHT / 9);
        mMainTableGlob.add(mScrollPaneGlob).fill().expand();

        mMainTableGlob.setVisible(false);
        addActor(mMainTableGlob);
    }

    private void setupLocalStats() {

        mContainerLoc = new Table();

        mLabelArray = new Array<Label>();
        mLabelValueArray = new Array<Label>();

        mHighScore = new Label(AssetLoader.sBundle.get("MENU_STATS_HIGH_SCORE"), AssetLoader.sGuiSkin);
        mLabelArray.add(mHighScore);
        mHighScoreValue = new Label(String.valueOf(PreferencesManager.getHighScore()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mHighScoreValue);

        mBestTime = new Label(AssetLoader.sBundle.get("MENU_StATS_BEST_TIME"), AssetLoader.sGuiSkin);
        mLabelArray.add(mBestTime);
        mBestTimeValue = new Label(String.valueOf(PreferencesManager.getBestTime()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mBestTimeValue);


        mTime = new Label(AssetLoader.sBundle.get("MENU_STATS_TOTAL_TIME"), AssetLoader.sGuiSkin);
        mLabelArray.add(mTime);
        mTimeValue = new Label(getTimeString(), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mTimeValue);

        mAverageTime = new Label(AssetLoader.sBundle.get("MENU_STATS_AVERAGE_TIME"), AssetLoader.sGuiSkin);
        mLabelArray.add(mAverageTime);

        //Это страшненько, но тут скорость не критична и я сейчас другим занят
        String pattern = "##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String format = decimalFormat.format(PreferencesManager.getAverageTime());
        format = format.replace("," ,".").concat(AssetLoader.sBundle.get("MENU_SECONDS"));
        mAverageTimeValue = new Label(String.format(format), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mAverageTimeValue);

        mKilled = new Label(AssetLoader.sBundle.get("MENU_STATS_KILLED"), AssetLoader.sGuiSkin);
        mLabelArray.add(mKilled);
        mKilledValue = new Label(String.valueOf(PreferencesManager.getKilled()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mKilledValue);

        mKillingRate = new Label(AssetLoader.sBundle.get("MENU_STATS_KILLING_RATE"), AssetLoader.sGuiSkin);
        mLabelArray.add(mKillingRate);
        mKillingRateValue = new Label(String.valueOf(PreferencesManager.getKillingRate()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mKillingRateValue);

        mKillPercent = new Label(AssetLoader.sBundle.get("MENU_STATS_KILL_PERCENT"), AssetLoader.sGuiSkin);
        mLabelArray.add(mKillPercent);
        mKillPercentValue = new Label(String.valueOf(PreferencesManager.getKillPercent()).concat("%"), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mKillPercentValue);

        mDestroyed = new Label(AssetLoader.sBundle.get("MENU_STATS_DESTROYED"), AssetLoader.sGuiSkin);
        mLabelArray.add(mDestroyed);
        mDestroyedValue = new Label(String.valueOf(PreferencesManager.getDestroyed()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mDestroyedValue);

        mDeaths = new Label(AssetLoader.sBundle.get("MENU_STATS_DEATHS"), AssetLoader.sGuiSkin);
        mLabelArray.add(mDeaths);
        mDeathsValue = new Label(String.valueOf(PreferencesManager.getDeaths()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mDeathsValue);

        addLabelsToTable();

        addDeathStats();
        addLabelsToTable();

        addBonusStats();


        mTreasures = new Label(AssetLoader.sBundle.get("MENU_STATS_TREASURES"), AssetLoader.sGuiSkin);
        mLabelArray.add(mTreasures);
        mTreasuresValue = new Label(String.valueOf(PreferencesManager.getTreasuresTotal()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mTreasuresValue);

        mEfficiency = new Label(AssetLoader.sBundle.get("MENU_STATS_EFFICIENCY"), AssetLoader.sGuiSkin);
        mLabelArray.add(mEfficiency);
        mEfficiencyValue = new Label(String.valueOf(PreferencesManager.getEfficiency()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mEfficiencyValue);

        addLabelsToTable();

        mScrollPaneLoc = new ScrollPane(mContainerLoc);

        mMainTableLoc = new Table();
        mMainTableLoc.background(new NinePatchDrawable(AssetLoader.broadbord));
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

    private void addDeathStats() {

        mDeathCourse = new Label(AssetLoader.sBundle.get("MENU_STATS_WAS_KILLED"), AssetLoader.sGuiSkin);
        mCell = mContainerLoc.add(mDeathCourse).fill().expand();
        mCell.align(Align.left);
        mCell.padBottom(5);
        mCell.padTop(15);
        mContainerLoc.row();

        for (String key : PreferencesManager.getDangersKeys().keySet()) {
            if (PreferencesManager.getDeathCausePercent(key)>0) {
                Label labelName = new Label(AssetLoader.sBundle.format("MENU_STATS_KILLER_NAME",
                        PreferencesManager.getEnName(key), PreferencesManager.getRuName(key))
                        , AssetLoader.sGuiSkin);
                mLabelArray.add(labelName);
                Label labelValue = new Label(String.valueOf(PreferencesManager.getDeathCausePercent(key)).concat("%"), AssetLoader.sGuiSkin);
                mLabelValueArray.add(labelValue);
            }
        }

        if (PreferencesManager.getDeathCausePercent(PreferencesConstants.STATS_CRASHED_DEATH_KEY)>0) {
            Label fallLabel = new Label(AssetLoader.sBundle.get("MENU_STATS_KILLER_FALL"), AssetLoader.sGuiSkin);
            mLabelArray.add(fallLabel);
            Label fallValue = new Label(String.valueOf(PreferencesManager.getDeathCausePercent
                    (PreferencesConstants.STATS_CRASHED_DEATH_KEY)).concat("%"), AssetLoader.sGuiSkin);
            mLabelValueArray.add(fallValue);
        }

        if (PreferencesManager.getDeathCausePercent(PreferencesConstants.STATS_BEHIND_DEATH_KEY)>0) {
            Label behindLabel = new Label(AssetLoader.sBundle.get("MENU_STATS_KILLER_BEHIND"), AssetLoader.sGuiSkin);
            mLabelArray.add(behindLabel);
            Label behindValue = new Label(String.valueOf(PreferencesManager.getDeathCausePercent
                    (PreferencesConstants.STATS_BEHIND_DEATH_KEY)).concat("%"), AssetLoader.sGuiSkin);
            mLabelValueArray.add(behindValue);
        }
    }

    private void addLabelsToTable() {

        for (int i = 0; i <mLabelArray.size; i++) {
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

        mLabelArray.clear();
        mLabelValueArray.clear();
    }

    private void addDeathLabelsToTable() {

        for (int i = 0; i <mLabelArray.size; i++) {
            mLabel = mLabelArray.get(i);
            mLabel.setVisible(false);
            mCell = mContainerLoc.add(mLabel).size(0, 0);
            mCell.align(Align.left);
            mCell.padBottom(0);
            mCell.padTop(0);


            mLabel = mLabelValueArray.get(i);
            mLabel.setVisible(false);
            mCell = mContainerLoc.add(mLabel).size(0, 0);
            mCell.align(Align.right);

            mContainerLoc.row();
        }

        mLabelArray.clear();
        mLabelValueArray.clear();
    }

    private void addBonusStats() {

        mBonuses = new Label(AssetLoader.sBundle.get("MENU_STATS_WAS_USED"), AssetLoader.sGuiSkin);
        mCell = mContainerLoc.add(mBonuses).fill().expand();
        mCell.align(Align.left);
        mCell.padBottom(5);
        mCell.padTop(15);
        mContainerLoc.row();

        mBuddha = new Label(AssetLoader.sBundle.get("MENU_STATS_USE_BUDDHA"), AssetLoader.sGuiSkin);
        mLabelArray.add(mBuddha);
        mBuddhaValue = new Label(String.valueOf(PreferencesManager.getBuddha()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mBuddhaValue);

        mGhost = new Label(AssetLoader.sBundle.get("MENU_STATS_USE_GHOST"), AssetLoader.sGuiSkin);
        mLabelArray.add(mGhost);
        mGhostValue = new Label(String.valueOf(PreferencesManager.getGhost()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mGhostValue);

        mRetribution = new Label(AssetLoader.sBundle.get("MENU_STATS_USE_RETRIBUTION"), AssetLoader.sGuiSkin);
        mLabelArray.add(mRetribution);
        mRetributionValue = new Label(String.valueOf(PreferencesManager.getRetribution()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mRetributionValue);

        mRevival = new Label(AssetLoader.sBundle.get("MENU_STATS_USE_REVIVAL"), AssetLoader.sGuiSkin);
        mLabelArray.add(mRevival);
        mRevivalValue = new Label(String.valueOf(PreferencesManager.getRevival()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mRevivalValue);

        mThunder = new Label(AssetLoader.sBundle.get("MENU_STATS_USE_THUNDER"), AssetLoader.sGuiSkin);
        mLabelArray.add(mThunder);
        mThunderValue = new Label(String.valueOf(PreferencesManager.getThunder()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mThunderValue);

        mStrong = new Label(AssetLoader.sBundle.get("MENU_STATS_USE_STRONG"), AssetLoader.sGuiSkin);
        mLabelArray.add(mStrong);
        mStrongValue = new Label(String.valueOf(PreferencesManager.getStrong()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mStrongValue);

        mWings = new Label(AssetLoader.sBundle.get("MENU_STATS_USE_WINGS"), AssetLoader.sGuiSkin);
        mLabelArray.add(mWings);
        mWingsValue = new Label(String.valueOf(PreferencesManager.getWings()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mWingsValue);

        mDragon = new Label(AssetLoader.sBundle.get("MENU_STATS_USE_DRAGON"), AssetLoader.sGuiSkin);
        mLabelArray.add(mDragon);
        mDragonValue = new Label(String.valueOf(PreferencesManager.getDragon()), AssetLoader.sGuiSkin);
        mLabelValueArray.add(mDragonValue);
    }

    private String getTimeString() {
        mHelpString="";
        long time = (long) PreferencesManager.getTime();

        Long hours = (time % 86400) / 3600;
        Long minutes = (time % 3600) / 60;
        Long seconds = time % 60;

        if (hours>0) {
            mHelpString = mHelpString.concat(hours.toString());
            mHelpString = mHelpString.concat(AssetLoader.sBundle.get("MENU_HOURS"));
            mHelpString = mHelpString.concat(" ");
        }
        if (minutes>0) {
            mHelpString = mHelpString.concat(minutes.toString());
            mHelpString = mHelpString.concat(AssetLoader.sBundle.get("MENU_MINUTE"));
            mHelpString = mHelpString.concat(" ");
        }
        if (seconds>0) {
            mHelpString = mHelpString.concat(seconds.toString());
            mHelpString = mHelpString.concat(AssetLoader.sBundle.get("MENU_SECONDS"));
        }

        return mHelpString.equals("") ? "0c" : mHelpString;
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        translateScreenToStageCoordinates(screenX, screenY);

        if (mDescribeWindow.isVisible()&&!mDescribeWindowBounds.contains(touchPoint.x, touchPoint.y)) {
            mDescribeWindow.setVisible(false);
        }

        return super.touchDown(screenX, screenY, pointer, button);

    }

    private void translateScreenToStageCoordinates(int screenX, int screenY) {
        getCamera().unproject(touchPoint.set(screenX, screenY, 0));
    }
}
