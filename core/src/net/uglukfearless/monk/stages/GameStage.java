package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.uglukfearless.monk.actors.gameplay.Background;
import net.uglukfearless.monk.actors.gameplay.Columns;
import net.uglukfearless.monk.actors.gameplay.Ground;
import net.uglukfearless.monk.actors.gameplay.Lump;
import net.uglukfearless.monk.actors.Runner;
import net.uglukfearless.monk.actors.RunnerStrike;
import net.uglukfearless.monk.actors.gameplay.Pit;
import net.uglukfearless.monk.box2d.RunnerUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.enums.GameState;
import net.uglukfearless.monk.listeners.GameContactListener;
import net.uglukfearless.monk.screens.GameScreen;
import net.uglukfearless.monk.utils.gameplay.BodyUtils;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.gameplay.DangersHandler;
import net.uglukfearless.monk.utils.file.ScoreCounter;
import net.uglukfearless.monk.utils.gameplay.SpaceTable;
import net.uglukfearless.monk.utils.gameplay.WorldUtils;
import net.uglukfearless.monk.utils.gameplay.pools.PoolsHandler;

import java.util.Random;

import static net.uglukfearless.monk.enums.GameState.*;


/**
 * Created by Ugluk on 17.05.2016.
 */
public class GameStage extends Stage {

    public static final int VIEWPORT_WIDTH = Constants.GAME_WIDTH;
    public static int VIEWPORT_HEIGHT = Constants.GAME_HEIGHT;

    private GameScreen screen;

    private World world;
    private Ground ground1;
    private Ground ground2;
    private Runner runner;
    private Background background;

    private Array<Body> bodies;

    private boolean pit;

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

    private GameState mState;

    private Array<Columns> mColumns1;
    private Array<Columns> mColumns2;

    private Pit simplePit1;
    private Pit simplePit2;
    private Pit columnsPit1;
    private Pit columnsPit2;

    private float timeMid;
    private float runTime;
    private int coint = 0;


    public GameStage(GameScreen screen, float yViewportHeight) {

        super(new FitViewport(VIEWPORT_WIDTH, yViewportHeight,
                new OrthographicCamera(VIEWPORT_WIDTH, yViewportHeight)));

        VIEWPORT_HEIGHT = (int)yViewportHeight;

        this.screen = screen;

        mState = START;

        bodies = new Array<Body>();

        setUpWorld();
        setupCamera();
        setupTouchControlAreas();
        renderer = new Box2DDebugRenderer();

        runTime = 0;
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
        setUpGround();
        setUpColumns();
        setUpPits();
        setUpRunner();
        setUpRunnerStrike();
        setUpDangersHandler();
    }

    private void setUpPits() {

        if (Constants.PRIORITY_PIT>0) {
            simplePit1 = new Pit(WorldUtils.createPit(world, Constants.GROUND_PIT, Constants.GROUND_HEIGHT/2f));
            simplePit2 = new Pit(WorldUtils.createPit(world, Constants.GROUND_PIT, Constants.GROUND_HEIGHT/2f));
        }

        if (Constants.PRIORITY_COLUMNS>0) {
            columnsPit1 = new Pit(WorldUtils.createPit(world,
                    Constants.COLUMNS_QUANTITY*Constants.COLUMNS_WIDTH
                            + Constants.COLUMNS_PIT * (Constants.COLUMNS_QUANTITY + 1)
            , Constants.GROUND_HEIGHT/2f));
            columnsPit2 = new Pit(WorldUtils.createPit(world,
                    Constants.COLUMNS_QUANTITY*Constants.COLUMNS_WIDTH
                            + Constants.COLUMNS_PIT * (Constants.COLUMNS_QUANTITY + 1)
            , Constants.GROUND_HEIGHT/2f));
        }
    }

    private void setUpColumns() {
        if (Constants.PRIORITY_COLUMNS>0) {
            mColumns1 = new Array<Columns>();
            mColumns2 = new Array<Columns>();
            for (int i=0; i<Constants.COLUMNS_QUANTITY; i++) {
                mColumns1.add(new Columns(WorldUtils.createColumns(world)));
                mColumns2.add(new Columns(WorldUtils.createColumns(world)));
            }
        }
    }

    private void setUpDangersHandler() {
        dangersHandler = new DangersHandler(this);
        dangersHandler.init();
        if (mColumns1!=null) {
            dangersHandler.setColumns(mColumns1, mColumns2);
        }
    }

    private void setUpRunnerStrike() {
        RunnerStrike runnerStrike = new RunnerStrike(WorldUtils.createRunnerStrike(world), runner);
        addActor(runnerStrike);
    }

    private void setUpBackground() {
        background = new Background(world, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        addActor(background);
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
    }

    private void setupCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }


    @Override
    public void act(float delta) {
        float time = 1f / delta;
        if (mState!=PAUSE&&time>3) {
            super.act(delta);
        }

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


        if (mState!=PAUSE&&time>3) {

            bodies.clear();
            world.getBodies(bodies);

            for (Body body:bodies) {
                update(body);
            }

            accumulator += delta;
            while (accumulator>=delta) {
                world.step(TIME_STEP,6,2);
                accumulator -= TIME_STEP;
            }

            bodies.clear();

            SpaceTable.leaf();

        }
//        ObstaclesMap.update(delta);
    }

    private void update(Body body) {
        if (!BodyUtils.bodyInBounds(body)) {
            if (BodyUtils.bodyIsRunner(body)) {
                ((RunnerUserData) body.getUserData()).setDead();
                this.saveTimePoint();
            } else if (body!=null&&BodyUtils.bodyIsGround(body)) {
                repositionGround();
            }

        }

        UserData data = (UserData) body.getUserData();
        if(data!=null &&  data.isDestroy()){
            if (!world.isLocked()) {
                world.destroyBody(body);
            }
        }

    }

    private void repositionGround() {

        dangersHandler.createDangers(ground1, ground2);

    }


    public void createLump(Body parent, int quantity, TextureRegion textureRegion) {
        for (int i=0;i<quantity;i++) {
            Lump lump = null;
            lump = (Lump) PoolsHandler.sLumpsPool.obtain();
            if (lump!=null) {
                lump.init(this, parent, textureRegion);
            }
        }
    }


    @Override
    public void draw() {
//        ground1.setZIndex(100);
//        ground2.setZIndex(100);
//        if (mState!=PAUSE) {
            super.draw();
//            renderer.render(world, camera.combined);
//            System.out.println("render Calls - " + ((SpriteBatch) getBatch()).renderCalls);
//        }



    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        coint++;
        System.out.println("click" + coint);
        translateScreenToWorldCoordinates(screenX, screenY);

        if (mState == START) {

            this.start();

        } else if( mState == PAUSE) {

            mState = RUN;

        } else if (rightSideTouched(touchPoint.x, touchPoint.y)) {
            System.out.println("strike" + coint);
            runner.strike();

        } else if (leftSideTouched(touchPoint.x, touchPoint.y)) {

            runner.jump();

        }


        return super.touchDown(screenX, screenY, pointer, button);

    }

    private void start() {
        mState= RUN;
        runner.start();
        ground1.setVelocity(Constants.GROUND_LINEAR_VELOCITY);
        ground2.setVelocity(Constants.GROUND_LINEAR_VELOCITY);
        background.setSpeed(Constants.WORLD_STATIC_VELOCITY.x * Constants.BACKGROUND_VELOCITY_COFF);
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


    @Override
    public void dispose() {
        super.dispose();
    }

    //для тестирования
    @Override
    public boolean keyDown(int keyCode) {

        if (keyCode==Input.Keys.ESCAPE) {
            screen.setMenu();
        } else if (mState == GAME_OVER) {
            System.out.println("***********************************");
            System.out.println(timeMid);
            System.out.println("***********************************");
            screen.newGame();
        } else if (mState == START) {

            this.start();

        } else if(mState==PAUSE) {

            mState = RUN;

        } else if (keyCode == Input.Keys.D) {
            mState = PAUSE;
        }  else if (keyCode== Input.Keys.SPACE) {
            runner.jump();
        } else if (keyCode==Input.Keys.N) {
            System.out.println("***********************************");
            System.out.println(timeMid);
            System.out.println("***********************************");
            screen.newGame();
        }  else if (keyCode==Input.Keys.SHIFT_LEFT||keyCode==Input.Keys.ENTER) {
            runner.strike();
        }

        return super.keyDown(keyCode);
    }

    public void saveTimePoint() {
        ScoreCounter.setTime((int)runTime);
    }

    public void gameOver() {
        mState = GAME_OVER;
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

    //временное
    public float getTimeMid() {
        return timeMid;
    }
}
