package net.uglukfearless.monk.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import net.uglukfearless.monk.box2d.ColumnsUserData;
import net.uglukfearless.monk.box2d.EnemyUserData;
import net.uglukfearless.monk.box2d.GroundUserData;
import net.uglukfearless.monk.box2d.LumpUserData;
import net.uglukfearless.monk.box2d.ObstacleUserData;
import net.uglukfearless.monk.box2d.RunnerStrikeUserData;
import net.uglukfearless.monk.box2d.RunnerUserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.enums.EnemyType;
import net.uglukfearless.monk.enums.ObstacleType;

import java.util.Random;

/**
 * Created by Ugluk on 17.05.2016.
 */
public class WorldUtils {

    private static Random rand = new Random();

    public static World createWorld() {

        return new World(Constants.WORLD_GRAVITY, false);
    }

    public static Body createGround(World world, boolean isSecond) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        if (isSecond==true) {
            bodyDef.position.set(new Vector2(Constants.GROUND_X + Constants.GROUND_WIDTH,
                    Constants.GROUND_Y));
        } else {
            bodyDef.position.set(new Vector2(Constants.GROUND_X, Constants.GROUND_Y));
        }
        Body body =  world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.GROUND_WIDTH / 2, Constants.GROUND_HEIGHT / 2);
        body.createFixture(shape, Constants.GROUND_DENSITY);
        body.setGravityScale(0);
        body.getFixtureList().get(0).setFriction(0);
        body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_STATIC);
        body.resetMassData();
        body.setUserData(new GroundUserData(Constants.GROUND_WIDTH, Constants.GROUND_HEIGHT));
        shape.dispose();
        return body;
    }

    public static Body createRunner(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(Constants.RUNNER_X, Constants.RUNNER_Y + Constants.RUNNER_HEIGHT/2));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.RUNNER_WIDTH / 2, Constants.RUNNER_HEIGHT / 2);
        Body body = world.createBody(bodyDef);
        body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
        body.createFixture(shape, Constants.RUNNER_DENSITY);
        body.getFixtureList().get(0).setFriction(0);
        body.setFixedRotation(true);
        body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER);
        body.resetMassData();
        body.setUserData(new RunnerUserData(Constants.RUNNER_WIDTH, Constants.RUNNER_HEIGHT));
        shape.dispose();
        return body;
    }

    public static Body createEnemy(World world) {
        EnemyType enemyType = RandomUtils.getRandomEnemyType();
        EnemyUserData userData = new EnemyUserData(enemyType);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(enemyType.getX(), enemyType.getY()));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(userData.getWidth() / 2, userData.getHeight() / 2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, userData.getDensity());
        body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY);
        body.setGravityScale(userData.getGravityScale());
        body.setFixedRotation(true);
        body.resetMassData();
        body.setUserData(userData);
        shape.dispose();
        return body;
    }

    public static Body createEnemy(World world, float x, float y, EnemyType enemyType) {
        EnemyUserData userData = new EnemyUserData(enemyType);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(x, enemyType.getY() + y + enemyType.getHeight()/2));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(userData.getWidth() / 2, userData.getHeight() / 2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, userData.getDensity());
        body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY);
        body.setGravityScale(userData.getGravityScale());
        body.setFixedRotation(true);
        body.resetMassData();
        body.setUserData(userData);
        shape.dispose();
        return body;
    }

    public static Body createObstacle(World world) {
        ObstacleType obstacleType = RandomUtils.getRandomObstacleType();
        ObstacleUserData userData = new ObstacleUserData(obstacleType);
        BodyDef bodyDef = new BodyDef();

        if (userData.isTrap()) {
            bodyDef.type = BodyDef.BodyType.KinematicBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        bodyDef.position.set(new Vector2(userData.getX(), userData.getY()));

        Shape shape;
        if (userData.isSphere()) {
            shape = new CircleShape();
            shape.setRadius(userData.getWidth() / 2);
        } else {
            shape = new PolygonShape();
            ((PolygonShape)shape).setAsBox(userData.getWidth() / 2, userData.getHeight() / 2);
        }

        Body body = world.createBody(bodyDef);
        body.createFixture(shape, userData.getDensity());
        if (userData.isTrap()||userData.isArmour()) {
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_OBSTACLE_TRAP);
        } else {
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_OBSTACLE_SIMPLE);
        }
        body.setGravityScale(userData.getGravityScale());
        body.resetMassData();
        body.setUserData(userData);
        shape.dispose();
        return body;
    }

    public static Body createObstacle(World world, float x, float y, ObstacleType obstacleType) {

        ObstacleUserData userData = new ObstacleUserData(obstacleType);
        BodyDef bodyDef = new BodyDef();

        if (userData.isTrap()) {
            bodyDef.type = BodyDef.BodyType.KinematicBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        bodyDef.position.set(new Vector2(x, userData.getY() + y + obstacleType.getHeight()/2));

        Shape shape;
        if (userData.isSphere()) {
            shape = new CircleShape();
            shape.setRadius(userData.getWidth() / 2);
        } else {
            shape = new PolygonShape();
            ((PolygonShape)shape).setAsBox(userData.getWidth() / 2, userData.getHeight() / 2);
        }

        Body body = world.createBody(bodyDef);
        body.createFixture(shape, userData.getDensity());
        if (userData.isTrap()||userData.isArmour()) {
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_OBSTACLE_TRAP);
        } else {
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_OBSTACLE_SIMPLE);
        }
        body.setGravityScale(userData.getGravityScale());
        body.resetMassData();
        body.setUserData(userData);
        shape.dispose();
        return body;
    }


    public static Body createRunnerStrike(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(Constants.RUNNER_X, -10));
        CircleShape shape = new CircleShape();
        shape.setRadius(Constants.RUNNER_WIDTH * 1.2f);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Constants.RUNNER_DENSITY);
        body.setGravityScale(0);
        body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_STRIKE);
        body.resetMassData();
        body.setUserData(new RunnerStrikeUserData());
        shape.dispose();
        return body;
    }

    public static Body createLupm(World world, Body parent) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(parent.getPosition());
        CircleShape shape = new CircleShape();
        shape.setRadius(0.4f); ///!!!!
        Body body = world.createBody(bodyDef);
        body.setGravityScale(Constants.LUMP_GRAVITY_SCALE);
        body.createFixture(shape, Constants.ENEMY_DENSITY);
        body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_LUMP);
        body.getFixtureList().get(0).setRestitution(0.7f);
        body.setLinearVelocity(new Vector2(rand.nextFloat() * 15 - 8, rand.nextFloat() * 15 + 1));
        body.resetMassData();
        body.setUserData(new LumpUserData(0.8f, 0.8f));
        shape.dispose();
        return body;
    }

    public static Body createColumns(World world, float startX) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(startX + Constants.COLUMNS_WIDTH/2, Constants.COLUMNS_Y));
        Body body =  world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.COLUMNS_WIDTH / 2, Constants.COLUMNS_HEIGHT / 2);
        body.createFixture(shape, Constants.COLUMNS_DENSITY);
        body.setGravityScale(0);
        body.getFixtureList().get(0).setFriction(0);
        body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_STATIC);
        body.resetMassData();
        body.setUserData(new ColumnsUserData(Constants.COLUMNS_WIDTH, Constants.COLUMNS_HEIGHT));
        shape.dispose();
        return body;
    }
}
