package net.uglukfearless.monk.constants;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ugluk on 17.05.2016.
 */
public class Constants {
    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 480;
    public static final int GAME_WIDTH = 25;
    public static final int GAME_HEIGHT = APP_HEIGHT/(APP_WIDTH/GAME_WIDTH);
    public static final float WORLD_TO_SCREEN = (APP_WIDTH/GAME_WIDTH);

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
    public static final Vector2 WORLD_STATIC_VELOCITY = new Vector2(-15f,0);

    public static final int BACKGROUND_OFFSET_Y = 1;

    public static final float GROUND_X = 0;
    public static final float GROUND_Y = 0;
    public static final float GROUND_WIDTH = 50f;
    public static final float GROUND_HEIGHT = 4f;
    public static final float GROUND_DENSITY = 10f;
    public static final Vector2 GROUND_LINEAR_VELOCITY = WORLD_STATIC_VELOCITY;

    public static final float LAYOUT_Y_STEP = 4;
    public static final float LAYOUT_Y_ONE = GROUND_Y + GROUND_HEIGHT;
    public static final float LAYOUT_Y_TWO = GROUND_Y + GROUND_HEIGHT + LAYOUT_Y_STEP;
    public static final float STEP_OF_DANGERS = GROUND_WIDTH/3f;

    public static final float GROUND_PIT = 6f;

    public static final float RUNNER_X = 4;
    public static final float RUNNER_Y = GROUND_Y + GROUND_HEIGHT - 0.4f;
    public static final float RUNNER_WIDTH = 1.5f;
    public static final float RUNNER_HEIGHT = 3f;
    public static final float RUNNER_DENSITY = 0.5f;
    public static final float RUNNER_DODGE_X = 3f;
    public static final float RUNNER_DODGE_Y = 2.8f;
    public static final float RUNNER_GRAVITY_SCALE = 5f;
    public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 40f);
    public static final float RUNNER_HIT_ANGULAR_IMPULSE = 20f;

    public static final float ENEMY_X = 25f;
    public static final float RUNNING_SHORT_ENEMY_Y = 3f;
    public static final float RUNNING_LONG_ENEMY_Y = 3f;
    public static final float FLYING_ENEMY_Y = 5f;
    public static final float ENEMY_DENSITY = RUNNER_DENSITY;
    public static final Vector2 ENEMY_LINEAR_VELOCITY = new Vector2(-20f,0);
    public static final Vector2 ENEMY_JUMPING_LINEAR_IMPULSE = new Vector2(0, 15f);;

    public static final float LUMP_GRAVITY_SCALE = 5f;

    public static final String BACKGROUND_IMAGE_PATH = "background.png";
    public static final String GROUND_IMAGE_PATH = "ground.png";
    public static final String CHARACTERS_ATLAS_PATH = "characters.atlas";
    public static final String [] RUNNER_RUNNING_REGION_NAMES = new String []
            {"alienGreen_run1", "alienGreen_run2"};
    public static final String RUNNER_HIT_REGION_NAME = "alienGreen_hit";
    public static final String RUNNER_JUMPING_REGION_NAME = "alienGreen_jump";

    public static final String [] RUNNING_SMALL_ENEMY_REGION_NAMES = new String []
            {"ladyBug_walk1", "ladyBug_walk2"};
    public static final String [] RUNNING_WIDE_ENEMY_REGION_NAMES = new String[]
            {"worm_walk1", "worm_walk2"};
    public static final String [] RUNNING_LONG_ENEMY_REGION_NAMES = new String[]
            {"barnacle_bite1", "barnacle_bite2"};
    public static final String [] RUNNING_BIG_ENEMY_REGION_NAMES = new String []
            {"spider_walk1", "spider_walk2"};
    public static final String [] FLYING_SMALL_ENEMY_REGION_NAMES = new String[]
            {"bee_fly1", "bee_fly2"};
    public static final String [] FLYING_WIDE_ENEMY_REGION_NAMES = new String[]
            {"fly_fly1", "fly_fly2"};
}
