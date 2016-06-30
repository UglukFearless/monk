package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.uglukfearless.monk.actors.Background;
import net.uglukfearless.monk.actors.Columns;
import net.uglukfearless.monk.actors.Enemy;
import net.uglukfearless.monk.actors.GameActor;
import net.uglukfearless.monk.actors.Ground;
import net.uglukfearless.monk.actors.Lump;
import net.uglukfearless.monk.actors.Obstacle;
import net.uglukfearless.monk.actors.Runner;
import net.uglukfearless.monk.actors.RunnerStrike;
import net.uglukfearless.monk.box2d.RunnerUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.listeners.GameContactListener;
import net.uglukfearless.monk.screens.GameScreen;
import net.uglukfearless.monk.utils.AssetLoader;
import net.uglukfearless.monk.utils.BodyUtils;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.DangersHandler;
import net.uglukfearless.monk.utils.ScoreCounter;
import net.uglukfearless.monk.utils.WorldUtils;

import java.util.Random;


/**
 * Created by Ugluk on 17.05.2016.
 */
public class GameStage extends Stage {

    public static final int VIEWPORT_WIDTH = Constants.GAME_WIDTH;
    public static final int VIEWPORT_HEIGHT = Constants.GAME_HEIGHT;

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

    public GameStage(GameScreen screen) {

        super(new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT,
                new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));

        this.screen = screen;

        bodies = new Array<Body>();

        setUpWorld();
        setupCamera();
        setupTouchControlAreas();
        renderer = new Box2DDebugRenderer();

    }

    private void setupTouchControlAreas() {
        touchPoint = new Vector3();
        screenRightSide = new Rectangle(getCamera().viewportWidth/2, 0,
                getCamera().viewportWidth/2, getCamera().viewportHeight);
        screenLeftSide = new Rectangle(0, 0,getCamera().viewportWidth/2,getCamera().viewportHeight);
        Gdx.input.setInputProcessor(this);
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        world.QueryAABB(null, -150, -150, 150, 150); //странное дело, добавил строчку, но ничего не измениловь! ))
        gameContactListener = new GameContactListener(this);
        world.setContactListener(gameContactListener);
        setUpBackground();
        setUpGround();
        setUpRunner();
        setUpRunnerStrike();
        setUpDangersHandler();
    }

    private void setUpDangersHandler() {
        dangersHandler = new DangersHandler(this);
        dangersHandler.init();
    }

    private void setUpRunnerStrike() {
        RunnerStrike runnerStrike = new RunnerStrike(WorldUtils.createRunnerStrike(world), runner);
        addActor(runnerStrike);
    }

    private void setUpBackground() {
        background = new Background();
        addActor(background);
    }

    private void setUpRunner() {
        runner = new Runner(WorldUtils.createRunner(world));
        addActor(runner);
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
        super.act(delta);

        float time = 1f / delta;
        if (time<50) {
            System.out.println("###########################################");
            System.out.println(time);
        }


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

//        ObstaclesMap.update(delta);
    }

    private void update(Body body) {
        if (!BodyUtils.bodyInBounds(body)) {
            if (BodyUtils.bodyIsEnemy(body)||BodyUtils.bodyIsObstacle(body)
                    ||BodyUtils.bodyIsColumns(body)) {
                ((UserData)body.getUserData()).setDestroy(true);
            } else if (BodyUtils.bodyIsRunner(body)) {
                ((RunnerUserData) body.getUserData()).setDead(true);
            }
            if (body!=null&&BodyUtils.bodyIsGround(body)) {
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


    public void createLump(Body parent) {
        Lump lump = new Lump(WorldUtils.createLupm(world, parent));
        addActor(lump);
    }


    @Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);

        this.getBatch().begin();
        AssetLoader.font.draw(this.getBatch(),String.valueOf(ScoreCounter.getScore()), 0, Constants.GAME_HEIGHT);
        AssetLoader.font.draw(this.getBatch(), String.valueOf(ScoreCounter.getHighScore()),
                Constants.GAME_WIDTH - String.valueOf(ScoreCounter.getHighScore()).length() , Constants.GAME_HEIGHT);
        this.getBatch().end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        translateScreenToWorldCoordinates(screenX, screenY);

        if (rightSideTouched(touchPoint.x, touchPoint.y)) {
            runner.strike();
        } else if (leftSideTouched(touchPoint.x, touchPoint.y)) {
            runner.jump();
        }

        return super.touchDown(screenX, screenY, pointer, button);
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

        if (keyCode== Input.Keys.SPACE) {
            runner.jump();
        } else if (keyCode==Input.Keys.N) {
            screen.newGame();
        }  else if (keyCode==Input.Keys.SHIFT_LEFT) {
            runner.strike();
        }

        return super.keyDown(keyCode);
    }
}
