package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.uglukfearless.monk.actors.gameplay.Armour;
import net.uglukfearless.monk.actors.gameplay.Background;
import net.uglukfearless.monk.actors.gameplay.BuddhasBody;
import net.uglukfearless.monk.actors.gameplay.Columns;
import net.uglukfearless.monk.actors.gameplay.Dragon;
import net.uglukfearless.monk.actors.gameplay.GameDecoration;
import net.uglukfearless.monk.actors.gameplay.Ground;
import net.uglukfearless.monk.actors.gameplay.Lump;
import net.uglukfearless.monk.actors.gameplay.Runner;
import net.uglukfearless.monk.actors.gameplay.RunnerStrike;
import net.uglukfearless.monk.actors.gameplay.Pit;
import net.uglukfearless.monk.actors.gameplay.bonuses.DragonFormBonus;
import net.uglukfearless.monk.actors.gameplay.bonuses.item.ArmourItem;
import net.uglukfearless.monk.actors.gameplay.bonuses.BuddhaBonus;
import net.uglukfearless.monk.actors.gameplay.bonuses.GameBonus;
import net.uglukfearless.monk.actors.gameplay.bonuses.GhostBonus;
import net.uglukfearless.monk.actors.gameplay.bonuses.RetributionBonus;
import net.uglukfearless.monk.actors.gameplay.bonuses.RevivalAvatar;
import net.uglukfearless.monk.actors.gameplay.bonuses.RevivalBonus;
import net.uglukfearless.monk.actors.gameplay.bonuses.StrongBeatBonus;
import net.uglukfearless.monk.actors.gameplay.bonuses.ThunderFistBonus;
import net.uglukfearless.monk.actors.gameplay.bonuses.Treasures;
import net.uglukfearless.monk.actors.gameplay.bonuses.WingsAvatar;
import net.uglukfearless.monk.actors.gameplay.bonuses.WingsBonus;
import net.uglukfearless.monk.actors.gameplay.bonuses.item.WeaponItem;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.enums.GameState;
import net.uglukfearless.monk.enums.WeaponDistance;
import net.uglukfearless.monk.listeners.GameContactListener;
import net.uglukfearless.monk.screens.GameScreen;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.SoundSystem;
import net.uglukfearless.monk.utils.fortween.Value;
import net.uglukfearless.monk.utils.fortween.ValueAccessor;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.gameplay.Retributable;
import net.uglukfearless.monk.utils.gameplay.Shake;
import net.uglukfearless.monk.utils.gameplay.achievements.Achievement;
import net.uglukfearless.monk.utils.gameplay.dangers.DangersHandler;
import net.uglukfearless.monk.utils.file.ScoreCounter;
import net.uglukfearless.monk.utils.gameplay.Movable;
import net.uglukfearless.monk.utils.gameplay.ai.SpaceTable;
import net.uglukfearless.monk.utils.gameplay.bodies.WorldUtils;
import net.uglukfearless.monk.utils.gameplay.models.GameProgressModel;
import net.uglukfearless.monk.utils.gameplay.models.LevelModel;
import net.uglukfearless.monk.utils.gameplay.pools.PoolsHandler;

import java.util.Random;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import static net.uglukfearless.monk.enums.GameState.*;


/**
 * Created by Ugluk on 17.05.2016.
 */
public class GameStage extends Stage {

    public static final int VIEWPORT_WIDTH = Constants.GAME_WIDTH;
    public static float VIEWPORT_HEIGHT = Constants.GAME_HEIGHT;

    private GameScreen screen;
    private GameGuiStage mGameGuiStage;

    private World world;
    private Background background1;
    private Background background2;
    private Ground ground1;
    private Ground ground2;
    private Runner runner;

    private RunnerStrike mRunnerStrikeShort;
    private RunnerStrike mRunnerStrikeMiddle;
    private RunnerStrike mRunnerStrikeLong;

    private final float TIME_STEP = 1/300f;
    private float accumulator = 0f;
    private Random rand = new Random();

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private Rectangle screenRightSide;
    private Rectangle screenLeftSide;

    private Vector3 touchPoint;

    private GameContactListener gameContactListener;
    private DangersHandler dangersHandler;
    private Array<GameBonus> mBonuses;

    private GameState mState;

    private Array<Columns> mColumns1;
    private Array<Columns> mColumns2;

    private Pit simplePit1;
    private Pit simplePit2;
    private Pit columnsPit1;
    private Pit columnsPit2;

    private float timeMid;
    private float runTime;
    private Array<Movable> mMovableArray;
    private Vector2 mCurrentVelocity;
    private int mRevival;
    private boolean mRevivalRunner;
    private float mReturnTimer;
    private boolean mReturnFilter;
    private RevivalAvatar mRevivalAvatar;
    private BuddhasBody mBuddhaBonus;
    private int mStartRevival;
    private int mLimitRevival;

    private LevelModel mLevelModel;

    //to tween
    private TweenManager manager;
    private Value alpha;
    private ShapeRenderer shapeRenderer;
    private Color transitionColor;

    private Shake mShake;
    private boolean mAddedTreasures;
    private String mLastKillerKey;
    private float mWaitThreshold;

    private Armour mArmour;
    private WingsAvatar mWingsAvatar;
    private int mWingsBuffer;
    private ArmourItem mArmourBonus;
    private WeaponItem mWeaponBonus;
    private Array<Retributable> mRetributableList;

    private Dragon mDragon;

    private int mFinishCount;
    private float startTime;
    private boolean mInfinityGame;

    private Group mDecorationGroupNear;
    private Group mDecorationGroupFurther;
    private GameDecoration mFinishDecoration;

    private float deltaTime;

    public GameStage(GameScreen screen, float yViewportHeight, LevelModel levelModel, int revival, int wingsRevival) {

        super(new FitViewport(VIEWPORT_WIDTH, yViewportHeight,
                new OrthographicCamera(VIEWPORT_WIDTH, yViewportHeight)));

        VIEWPORT_HEIGHT = yViewportHeight;

        this.screen = screen;

        mState = START;

        mMovableArray = new Array<Movable>();
        mRetributableList = new Array<Retributable>();

        mCurrentVelocity = Constants.WORLD_STATIC_VELOCITY_INIT;

        mStartRevival = revival;

        setUpLevel(levelModel);
        setUpWorld();
        setupCamera();
        setupTouchControlAreas();
        setupTweenEffect();

        renderer = new Box2DDebugRenderer();

        runner.setWingsRevival(wingsRevival);

        runTime = 0;
        startTime = ScoreCounter.getTime();
        mInfinityGame = false;

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);

        mWaitThreshold = 50;

    }

    private void setupTweenEffect() {
        alpha = new Value();
        transitionColor = new Color();
        prepareTransition(255,255,255,.3f);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(getCamera().combined);
    }

    private void setupTouchControlAreas() {
        touchPoint = new Vector3();
        screenRightSide = new Rectangle(getCamera().viewportWidth/2f, 0,
                getCamera().viewportWidth/2f, getCamera().viewportHeight);
        screenLeftSide = new Rectangle(0, 0,getCamera().viewportWidth/2f,getCamera().viewportHeight);
        Gdx.input.setInputProcessor(this);
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        world.QueryAABB(null, -150, -150, 150, 150); //странное дело, добавил строчку, но ничего не измениловь! ))
        gameContactListener = new GameContactListener(this);
        world.setContactListener(gameContactListener);

        PoolsHandler.initPools(world);

        setUpBackground();
        setUpDecorationFurther();
        setUpGround();
        setUpDecorationNear();
        setUpColumns();
        setUpPits();
        setUpRunner();
        setUpRunnerStrike();
        setUpArmour();
        setUpBuddhaBody();
        setUpBonuses();
        setUpDangersHandler();

        setUpShake();

    }

    private void setUpDecorationNear() {
        mDecorationGroupNear = new Group();
        addActor(mDecorationGroupNear);
    }

    private void setUpDecorationFurther() {
        mDecorationGroupFurther = new Group();
        addActor(mDecorationGroupFurther);
    }

    private void setUpArmour() {

        mArmour = new Armour(WorldUtils.createArmour(world), runner);
        addActor(mArmour);
    }

    private void setUpShake() {
        mShake = new Shake();
    }

    private void setUpLevel(LevelModel levelModel) {
        mLevelModel = levelModel;
        mLevelModel.setStage(this);

        ScoreCounter.levelInit();
    }

    private void setUpBuddhaBody() {
        mBuddhaBonus = new BuddhasBody(WorldUtils.createBuddhasBody(world), runner);
        addActor(mBuddhaBonus);

        mDragon = new Dragon(WorldUtils.createDragon(world), this);
        mDragon.trend();
        addActor(mDragon);
    }

    public void setGuiStage(GameGuiStage guiStage) {
        mGameGuiStage = guiStage;
        for (GameBonus bonus : mBonuses) {
            bonus.setGui(mGameGuiStage);
        }
        mArmourBonus.setGui(mGameGuiStage);
        mWeaponBonus.setGui(mGameGuiStage);

        setUpRevival();
        setUpWings();
    }

    private void setUpBonuses() {
        mBonuses = new Array<GameBonus>();
        mBonuses.add(new WingsBonus(this, VIEWPORT_HEIGHT));
        mBonuses.add(new GhostBonus(this, VIEWPORT_HEIGHT));
        mBonuses.add(new StrongBeatBonus(this, VIEWPORT_HEIGHT));
        mBonuses.add(new RetributionBonus(this, VIEWPORT_HEIGHT));
        mBonuses.add(new ThunderFistBonus(this, VIEWPORT_HEIGHT));
        mBonuses.add(new RevivalBonus(this, VIEWPORT_HEIGHT));
        mBonuses.add(new BuddhaBonus(this, VIEWPORT_HEIGHT));
        mBonuses.add(new DragonFormBonus(this, VIEWPORT_HEIGHT));
//        mBonuses.add(monk Treasures(this,  VIEWPORT_HEIGHT));

        mArmourBonus = new ArmourItem(this, VIEWPORT_HEIGHT);
        mWeaponBonus = new WeaponItem(this, VIEWPORT_HEIGHT);

        for (GameBonus gameBonus : mBonuses) {
            addMovable(gameBonus);
        }

    }

    private void setUpRevival() {
        mRevivalAvatar = new RevivalAvatar(this, VIEWPORT_HEIGHT);

        mLimitRevival = 1;

        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_REBORN_KEY)) {
            mLimitRevival+=2;
        }
        if (PreferencesManager.getTime()/(60*60)>PreferencesManager.getReceivedTimeBonuses()) {
            addStartRevival();
            PreferencesManager.increaseReceivedTimeBonuses();
            mGameGuiStage.showHourBonus();
        }

        if (PreferencesManager.checkAchieve(PreferencesConstants.ACHIEVE_WOS_KEY)) {
            addStartRevival();
        }

        addActor(mRevivalAvatar);

        mRevival = mStartRevival;
        mRevivalRunner = false;

        if (mRevival>0) {
            mRevivalAvatar.setVisible(true);
            mGameGuiStage.enableRevivalLabel();
            mGameGuiStage.setRevivalLabel(mRevivalAvatar.getX(), mRevivalAvatar.getY(), mRevival);
        }
    }

    private void setUpWings() {
        mWingsAvatar = new WingsAvatar(this, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        addActor(mWingsAvatar);
        checkWingsRevival(runner.getWingsRevival());
    }

    private void setUpPits() {

            simplePit1 = new Pit(WorldUtils.createPit(world, Constants.GROUND_PIT_INIT, Constants.GROUND_HEIGHT/2f));
            simplePit2 = new Pit(WorldUtils.createPit(world, Constants.GROUND_PIT_INIT, Constants.GROUND_HEIGHT/2f));
            addMovable(simplePit1);
            addMovable(simplePit2);


            columnsPit1 = new Pit(WorldUtils.createPit(world,
                    Constants.COLUMNS_QUANTITY_INIT *Constants.COLUMNS_WIDTH_INIT
                            + Constants.COLUMNS_PIT_INIT * (Constants.COLUMNS_QUANTITY_INIT + 1)
            , Constants.GROUND_HEIGHT/2f));
            columnsPit2 = new Pit(WorldUtils.createPit(world,
                    Constants.COLUMNS_QUANTITY_INIT *Constants.COLUMNS_WIDTH_INIT
                            + Constants.COLUMNS_PIT_INIT * (Constants.COLUMNS_QUANTITY_INIT + 1)
            , Constants.GROUND_HEIGHT/2f));
            addMovable(columnsPit1);
            addMovable(columnsPit2);


    }

    private void setUpColumns() {

            mColumns1 = new Array<Columns>();
            mColumns2 = new Array<Columns>();
            for (int i=0; i<Constants.COLUMNS_QUANTITY_INIT; i++) {
                Columns columns1 = new Columns(WorldUtils.createColumns(world));
                Columns columns2 = new Columns(WorldUtils.createColumns(world));
                mColumns1.add(columns1);
                mColumns2.add(columns2);
                addMovable(columns1);
                addMovable(columns2);
            }
    }

    private void setUpDangersHandler() {
        dangersHandler = new DangersHandler(this, mBonuses, mLevelModel);
        dangersHandler.init();

        dangersHandler.setColumns(mColumns1, mColumns2);

    }

    private void setUpRunnerStrike() {

        mRunnerStrikeShort = new RunnerStrike(WorldUtils.createRunnerStrike(world, WeaponDistance.SHORT), runner);
        addActor(mRunnerStrikeShort);

        mRunnerStrikeMiddle = new RunnerStrike(WorldUtils.createRunnerStrike(world, WeaponDistance.MIDDLE), runner);
        addActor(mRunnerStrikeMiddle);

        mRunnerStrikeLong = new RunnerStrike(WorldUtils.createRunnerStrike(world, WeaponDistance.LONG), runner);
        addActor(mRunnerStrikeLong);
    }

    private void setUpBackground() {
        background1 = new Background(world, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, Constants.BACKGROUND_VELOCITY_COF, true);
        addActor(background1);
        addMovable(background1);

        background2 = new Background(world, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, Constants.BACKGROUND_VELOCITY_COF_2, false);
        addActor(background2);
        addMovable(background2);
    }

    private void setUpRunner() {
        runner = new Runner(WorldUtils.createRunner(world));
        addActor(runner);
        runner.setGrounds(ground1, ground2);
    }

    private void setUpGround() {
        ground1 = new Ground(WorldUtils.createGround(world, false));
        ground2 = new Ground(WorldUtils.createGround(world, true));
        addActor(ground1);
        addActor(ground2);
        addMovable(ground1);
        addMovable(ground2);
    }

    private void setupCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }


    @Override
    public void act(float delta) {

//        delta /=3.5f;

        deltaTime = delta;
        float time = 1f / delta;

        if (time<50) {
            System.out.println("###########################################");
            System.out.println(time);
        }

        if (mState == RUN) {
            runTime +=delta;
        }

        if (timeMid==0) {
            timeMid = time;
        } else {
            timeMid = (timeMid + time)/2f;
        }

        if (mState!=PAUSE&&mState!=WAIT&&time>6) {

            mLevelModel.act(delta, runTime, mInfinityGame);

            super.act(delta);

            checkRevivalRunner(delta);

            accumulator += delta;
            while (accumulator>=delta) {
                world.step(TIME_STEP,6,2);
                accumulator -= TIME_STEP;
            }

            SpaceTable.leaf();


            particlesAct(delta);

            drawTransition(delta);

            mShake.tick(delta, this, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

            if (getRunTime()>30&&!mAddedTreasures) {
                Treasures treasures = new Treasures(this, VIEWPORT_HEIGHT);
                treasures.setGui(mGameGuiStage);
                mBonuses.add(treasures);
                addMovable(treasures);
                mAddedTreasures = true;
            }
        }
    }

    @Override
    public void draw() {
//        ground1.setZIndex(100);
//        ground2.setZIndex(100);
//        if (mState!=PAUSE) {
        super.draw();
//        System.out.println("render Calls - " + ((SpriteBatch) getBatch()).renderCalls);
        getBatch().begin();
//        effect.draw(getBatch());
        for (ParticleEffect effect : AssetLoader.sWorkParticleBlood) {
            effect.draw(getBatch());
        }

        for (ParticleEffect effect : AssetLoader.sWorkParticleDust) {
            effect.draw(getBatch());
        }

        AssetLoader.sHitParticle.draw(getBatch());

        getBatch().end();
//        renderer.render(world, camera.combined);
//            System.out.println("render Calls - " + ((SpriteBatch) getBatch()).renderCalls);
//        }
    }

    private void particlesAct(float delta) {

        for (ParticleEffect effect : AssetLoader.sWorkParticleBlood) {
            effect.update(delta);
            if (effect.isComplete()) {
                AssetLoader.sWorkParticleBlood.removeValue(effect, true);
                AssetLoader.sFreeParticleBlood.add(effect);
            }
        }

        for (ParticleEffect effect : AssetLoader.sWorkParticleDust) {
            effect.update(delta);
            if (effect.isComplete()) {
                AssetLoader.sWorkParticleDust.removeValue(effect, true);
                AssetLoader.sFreeParticleDust.add(effect);
            }
        }

        AssetLoader.sHitParticle.update(delta);
    }


    private void checkRevivalRunner(float delta) {
        if (mRevivalRunner
                &&((Math.abs(ground1.getPosition().x)<0.3f)
                ||(Math.abs(ground2.getPosition().x)<0.3f))) {
            runner = new Runner(WorldUtils.createRunner(world));
            runner.setRevivalFilter();
            runner.setAlpha(0.3f);
            runner.setGrounds(ground1, ground2);
            mRunnerStrikeShort.setRunner(runner);
            mRunnerStrikeMiddle.setRunner(runner);
            mRunnerStrikeLong.setRunner(runner);
            mBuddhaBonus.setRunner(runner);
            mArmour.setRunnerPassive(runner);
            runner.setArmour(mArmour);
            runner.start();
            addActor(runner);
            mRevivalRunner = false;
            mReturnTimer = 0;
            mReturnFilter = true;
            runner.setWingsRevival(mWingsBuffer);
            checkWingsRevival(mWingsBuffer);
        } else if (mReturnFilter) {
            mReturnTimer +=delta;
            if (mReturnTimer>3&&runner.getBody()!=null&&!runner.getUserData().isDead()) {

                runner.setCustomFilter();


                if (!runner.isGhost()) {
                    runner.setAlpha(1f);
                }

                mReturnTimer=0;
                mReturnFilter = false;
            }
        }
    }


    public void repositionGround() {
        dangersHandler.createDangers(ground1, ground2, mState==SLOWDOWN);
        if (mState==SLOWDOWN) {
            mFinishCount++;
        }
    }


    public void createLump(Body parent, int quantity, TextureRegion textureRegion) {
        for (int i=0;i<quantity;i++) {
            Lump lump = null;
            lump = PoolsHandler.sLumpsPool.obtain();
            if (lump!=null) {
                lump.init(this, parent, textureRegion);
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (mState==RUN) {
            mGameGuiStage.startResizeAction(screenX);
        }

        translateScreenToWorldCoordinates(screenX, screenY);

        if (mState == START) {

            this.start();

        } else if( mState == PAUSE) {

            mState = RUN;

        } else if (rightSideTouched(touchPoint.x, touchPoint.y)) {
            runner.strike();
            mDragon.headDown();

        } else if (leftSideTouched(touchPoint.x, touchPoint.y)) {

            runner.jump();
            mDragon.headUp();
        }

        return super.touchDown(screenX, screenY, pointer, button);

    }

    private void start() {
        mState = RUN;
        runner.start();
        ground1.setVelocity(Constants.GROUND_LINEAR_VELOCITY);
        ground2.setVelocity(Constants.GROUND_LINEAR_VELOCITY);
        background1.setSpeed(Constants.WORLD_STATIC_VELOCITY_INIT.x * background1.getSpeedCof());
        background2.setSpeed(Constants.WORLD_STATIC_VELOCITY_INIT.x * background2.getSpeedCof());
    }

    private void translateScreenToWorldCoordinates(int screenX, int screenY) {
        getCamera().unproject(touchPoint.set(screenX, screenY, 0));
    }

    private boolean rightSideTouched(float x, float y) {
        return screenRightSide.contains(x,y);
    }

    private boolean leftSideTouched(float x, float y) {
        return screenLeftSide.contains(x,y);
    }

    public Runner getRunner() {
        return runner;
    }

    public World getWorld() {
        return world;
    }


    //для тестирования
    @Override
    public boolean keyDown(int keyCode) {

        if (keyCode==Input.Keys.ESCAPE||keyCode==Input.Keys.BACK||keyCode==Input.Keys.MENU) {

            if (mState==RUN) {
                mState = PAUSE;
            } else if (mState==WAIT) {
                realGameOver(mLastKillerKey);
                screen.setMenu();
            } else {
                if (mRevival==0&&runner.getUserData().isDead()) {
                    PreferencesManager.addDeath();
                    gameOver(runner.getCurrentKillerKey());
                }

                screen.setMenu();
            }

        }
        else if (mState == START&&keyCode!=Input.Keys.BACK&&keyCode!=Input.Keys.MENU
                &&keyCode!=Input.Keys.VOLUME_UP&&keyCode!=Input.Keys.VOLUME_DOWN) {

            this.start();

        } else if(mState==PAUSE&&keyCode!=Input.Keys.BACK&&keyCode!=Input.Keys.MENU
                &&keyCode!=Input.Keys.VOLUME_UP&&keyCode!=Input.Keys.VOLUME_DOWN) {

            mState = RUN;

        } else if (keyCode == Input.Keys.D) {
            mState = PAUSE;
        }  else if (keyCode== Input.Keys.SPACE) {
            runner.jump();
            mDragon.headUp();
        } else if (keyCode==Input.Keys.N) {
            System.out.println("***********************************");
            System.out.println(timeMid);
            System.out.println("***********************************");
            mLevelModel.getDifficultyHandler().reset();
            screen.newGame();
        }  else if (keyCode==Input.Keys.SHIFT_LEFT||keyCode==Input.Keys.ENTER) {
            runner.strike();
            mDragon.headDown();
        }

        return super.keyDown(keyCode);
    }

    public void deactivationBonuses() {
        for (GameBonus bonus:mBonuses) {
            bonus.deactivation();
            bonus.disabling();
        }
    }

    public void saveTimePoint() {
        ScoreCounter.setTime((int)(runTime+startTime));
    }

    public void gameOver(String currentKillerKey) {
        if (mRevival<1) {
            //todo: сделать проверку наличия интернета и количества монет
            if (PreferencesManager.getTreasures()>=1&&runTime> mWaitThreshold) {
                mState = WAIT;
                mLastKillerKey = currentKillerKey;
                mWaitThreshold = runTime + 50;
            } else {
                realGameOver(currentKillerKey);
            }

        } else {
            mRevival--;
            mRevivalRunner = true;
            if (mRevival<1) {
                mRevivalAvatar.setVisible(false);
                mGameGuiStage.disableRevivalLabel();
            }
            mGameGuiStage.setRevivalLabel(mRevivalAvatar.getX(), mRevivalAvatar.getY(), mRevival);
        }

    }

    public void realGameOver() {
        realGameOver(mLastKillerKey);
    }

    public void realGameOver(String currentKillerKey) {

        mGameGuiStage.disableWingsLabel();
        mWingsAvatar.remove();

        PreferencesManager.addDeath();
        saveTimePoint();
        ScoreCounter.checkScore();
        ScoreCounter.saveCalcStats(currentKillerKey, mLevelModel.getLEVEL_NAME());

        for (Achievement achievement: ScoreCounter.getAchieveList()) {
            achievement.checkAchieve();
        }

        mState = GAME_OVER;
        mRevivalAvatar.setVisible(false);
        mGameGuiStage.disableRevivalLabel();
        mGameGuiStage.setRevivalLabel(mRevivalAvatar.getX(), mRevivalAvatar.getY(), mRevival);
    }

    public GameState getState() {
        return mState;
    }

    public Pit getSimplePit() {
        return simplePit1.getBody().isActive() ? simplePit2 : simplePit1;
    }

    public Pit getColumnsPit() {
        return columnsPit1.getBody().isActive() ? columnsPit2 : columnsPit1;
    }

    public float getRunTime() {
        return runTime;
    }

    public float getCollectTime() {
        return runTime + startTime;
    }

    public float getTimeBalance() {
        return mLevelModel.getTimeBalance(runTime);
    }

    public String getTimeString() {
        return !mInfinityGame ?
                String.valueOf((int) getTimeBalance()).concat("/").concat(String.valueOf((int) getCollectTime()))
                : String.valueOf((int) getCollectTime());
    }

    public RunnerStrike getRunnerStrike() {

        if (runner.getWeaponType()!=null) {
            if (mRunnerStrikeMiddle.getDistance().equals(runner.getWeaponType().getDistance())) {
                return mRunnerStrikeMiddle;
            } else if (mRunnerStrikeLong.getDistance().equals(runner.getWeaponType().getDistance())) {
                return mRunnerStrikeLong;
            }
        }

        return mRunnerStrikeShort;
    }

    public void setStrikeFilter(Filter filter) {
        mRunnerStrikeShort.getBody().getFixtureList().get(0).setFilterData(filter);
        mRunnerStrikeShort.getRunnerDefBody().getFixtureList().get(0).setFilterData(filter);
        mRunnerStrikeMiddle.getBody().getFixtureList().get(0).setFilterData(filter);
        mRunnerStrikeMiddle.getRunnerDefBody().getFixtureList().get(0).setFilterData(filter);
        mRunnerStrikeLong.getBody().getFixtureList().get(0).setFilterData(filter);
        mRunnerStrikeLong.getRunnerDefBody().getFixtureList().get(0).setFilterData(filter);
    }

    //временное
    public float getTimeMid() {
        return timeMid;
    }

    public void retribution(int retributionLevel) {

        AssetLoader.retributionBonusSound.play(SoundSystem.getSoundValue());
        screenShake(0.1f, 0.15f);

        for (Retributable retributable : mRetributableList) {
            retributable.punish(retributionLevel);
        }
        prepareTransition(255, 50, 0, .1f);

    }

    public void retributionSilence(int retributionLevel) {
        screenShake(0.1f, 0.15f);
        for (Retributable retributable : mRetributableList) {
            retributable.punish(retributionLevel);
        }
        prepareTransition(255, 50, 0, .1f);
    }

    public void finishRetribution() {
        for (Retributable retributable : mRetributableList) {
            retributable.punish(3);
        }
    }

    public void changingSpeed(float speedScale) {
        mCurrentVelocity = new Vector2(speedScale, 0);
        for (Movable movable:mMovableArray) {
            movable.changingStaticSpeed(speedScale);
        }
        System.out.println("speed = " + mCurrentVelocity);
    }

    public void changingSpeedHandler(float speedScale) {
        if (runner.isBuddha()&&!runner.isUseBuddhaThreshold()) {
            speedScale*=Constants.BUDDHA_SPEED_SCALE;
        }
        changingSpeed(speedScale);

        if (speedScale==0f) {
            runner.stop();
        }
    }

    public Vector2 getCurrentVelocity() {
        return mCurrentVelocity;
    }

    public void addMovable(Movable movable) {
        mMovableArray.add(movable);
    }

    public void removeMovable(Movable movable) {
        mMovableArray.removeValue(movable, false);
    }



    public void addRevival() {
        if (mRevival<mLimitRevival) {
            mRevival++;
        }

        if (mRevival>0) {
            mRevivalAvatar.setVisible(true);
            mGameGuiStage.enableRevivalLabel();
            mGameGuiStage.setRevivalLabel(mRevivalAvatar.getX(), mRevivalAvatar.getY(), mRevival);
        }
    }

    public void addStartRevival() {
        if (mStartRevival<mLimitRevival) {
            mStartRevival++;
        }
    }

    public int getRevival() {
        return mRevival;
    }

    public int getLimitRevival() {
        return mLimitRevival;
    }

    public void pause() {
        if (mState==RUN) {
            mState = PAUSE;
        }
    }

    public void startRebutRunner() {
//        runner.getBody().getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_WINGS_GHOST);
        runner.setRevivalFilter();
        mReturnTimer = 0;
        mReturnFilter = true;
    }

    public DangersHandler getDangersHandler() {
        return dangersHandler;
    }

    public void prepareTransition(int r, int g, int b, float duration) {
        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
        alpha.setValue(1);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, duration).target(0)
                .ease(TweenEquations.easeOutQuad).start(manager);
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g,
                    transitionColor.b, alpha.getValue());
            shapeRenderer.rect(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
            shapeRenderer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void screenShake(float power, float duration) {
        mShake.shake(power, duration);
    }

    public LevelModel getLevelModel() {
        return mLevelModel;
    }

    public void setRevivalRunner(boolean revivalRunner) {
        mRevivalRunner = revivalRunner;
    }

    public void setState(GameState state) {
        mState = state;
    }

    public int getWingsRevival() {
         if (runner!=null) {
             return runner.getWingsRevival();
         } else {
             return 0;
         }
    }

    public void checkWingsRevival(int wings) {

        if (wings>0) {
            mGameGuiStage.enableWingsLabel();
            mGameGuiStage.setWingsLabel(mWingsAvatar.getX(), mWingsAvatar.getY(), wings);
        } else {
            mGameGuiStage.disableWingsLabel();
            mGameGuiStage.setWingsLabel(mWingsAvatar.getX(), mWingsAvatar.getY(), wings);
        }

    }

    public void setWingsBuffer(int wingsBuffer) {
        mWingsBuffer = wingsBuffer;
    }

    public void initItem(float x, float y) {
        if (rand.nextInt(100)>95) {
            if (rand.nextBoolean()) {
                if (!mWeaponBonus.isInitialized()&&runner.getWeaponType()==null) {
                    mWeaponBonus.init(x, y);
                }
            } else {
                if (!mArmourBonus.isInitialized()&&!runner.isArmour()) {
                    mArmourBonus.init(x, y);
                }
            }

        }

    }

    public void addRetributable(Retributable retributable) {
        mRetributableList.add(retributable);
    }

    public void removeRetributable(Retributable retributable) {
        mRetributableList.removeValue(retributable, false);
    }

    public Dragon getDragon() {
        return mDragon;
    }

    public void setReturnFilter(boolean returnFilter) {
        mReturnFilter = returnFilter;
    }

    public boolean reachFinish() {

        if (mFinishDecoration!=null) {
            return mFinishDecoration.getX() + mFinishDecoration.getWidth()<Constants.GAME_WIDTH;
        } else {
            return false;
        }
    }

    public int getFinishCount() {
        return mFinishCount;
    }

    public boolean isInfinityGame() {
        return mInfinityGame;
    }

    public void setInfinityGame(boolean infinityGame) {
        mInfinityGame = infinityGame;
    }

    public void checkLevelScore() {
        ScoreCounter.checkLevelScore(mLevelModel.getLEVEL_NAME());
    }

    public GameProgressModel getProgress() {
        return new GameProgressModel(mRevival, runner.getWingsRevival(), mLevelModel.getGrade() + 1);
    }

    public void unlockNextLevel() {
        PreferencesManager.unlockLevel(mLevelModel.getGrade() + 1);
    }

    public void secondStart() {
        mInfinityGame = true;
        mState = RUN;
        mLevelModel.secondStart();
    }

    public void addDecoration(GameDecoration decoration, boolean isFinish, boolean isNear) {
        if (isNear) {
            mDecorationGroupNear.addActor(decoration);
        } else {
            mDecorationGroupFurther.addActor(decoration);
        }
        addMovable(decoration);

        if (isFinish) {
            mFinishDecoration = decoration;
        }
    }

    public void addToDecorationLayout(Actor actor) {
        mDecorationGroupNear.addActor(actor);
    }

    public void removeDecoration(GameDecoration decoration, boolean isNear) {
        if (isNear) {
            mDecorationGroupNear.removeActor(decoration);
        } else {
            mDecorationGroupFurther.removeActor(decoration);
        }
        mMovableArray.removeValue(decoration, true);
        PoolsHandler.sDecorationPool.free(decoration);
    }

    public float getDeltaTime() {
        return deltaTime;
    }
}
