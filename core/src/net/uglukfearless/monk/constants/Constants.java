package net.uglukfearless.monk.constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

/**
 * Created by Ugluk on 17.05.2016.
 */
public class Constants {
    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 480;
    public static final int GAME_WIDTH = 25;
    public static final int GAME_HEIGHT = APP_HEIGHT/(APP_WIDTH/GAME_WIDTH);


    public static final int BUTTON_MENU_WIDTH = 160;
    public static final int BUTTON_MENU_HEIGHT = 90;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
    public static final Vector2 WORLD_STATIC_VELOCITY = new Vector2(-15f,0);
    public static final Vector2 NULL_VELOCITY = new Vector2(0, 0);
    public static final float BACKGROUND_VELOCITY_COFF = 0.67f;

    public static final int BACKGROUND_OFFSET_Y = 0;

    public static final float BACKGROUND_WIDTH = 50;
    public static final float BACKGROUND_HEIGHT = 13;
    public static final float BACKGROUND_X = BACKGROUND_WIDTH/2;
    public static final float BACKGROUND_Y = BACKGROUND_HEIGHT/2;

    public static final float GROUND_X = 0;
    public static final float GROUND_Y = 0;
    public static final float GROUND_WIDTH = 38f;
    public static final float GROUND_HEIGHT = 6f;
    public static final float GROUND_DENSITY = 10f;
    public static final Vector2 GROUND_LINEAR_VELOCITY = WORLD_STATIC_VELOCITY;

    public static final int PRIORITY_GROUND = 15;
    public static final int PRIORITY_PIT = 20;
    public static final int PRIORITY_COLUMNS = 12;

//    public static final int PRIORITY_GROUND = 0;
//    public static final int PRIORITY_PIT = 0;
//    public static final int PRIORITY_COLUMNS = 1;

    public static final float COLUMNS_Y = GROUND_Y + 2;
    public static final float COLUMNS_WIDTH = 1f;
    public static final float COLUMNS_PIT = 2f;
    public static final float COLUMNS_HEIGHT = GROUND_HEIGHT;
    public static final float COLUMNS_DENSITY = GROUND_DENSITY;
    public static final Vector2 COLUMNS_LINEAR_VELOCITY = WORLD_STATIC_VELOCITY;
    public static final int COLUMNS_QUANTITY = 4;

    public static final float LAYOUT_Y_STEP = 4;
    public static final float LAYOUT_Y_ONE = GROUND_Y + GROUND_HEIGHT/2;
    public static final float LAYOUT_Y_TWO = LAYOUT_Y_ONE + LAYOUT_Y_STEP;
    public static final float STEP_OF_DANGERS = GROUND_WIDTH/2f;

    public static final float GROUND_PIT = 6f;

    public static final float RUNNER_X = 4;
    public static final float RUNNER_Y = GROUND_Y + GROUND_HEIGHT/2; // - 1f;
    public static final float RUNNER_WIDTH = 1.5f;
    public static final float RUNNER_HEIGHT = 3f;
    public static final float RUNNER_DENSITY = 0.5f;
    public static final float RUNNER_GRAVITY_SCALE = 5f;
    public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 40f);
    public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE_ONE = new Vector2(0, 65f);
    public static final float RUNNER_HIT_ANGULAR_IMPULSE = 20f;

    public static final float DANDER_X = 25f;

    public static final int DANGERS_PRIORITY_NEVER = 0;
    public static final int DANGERS_PRIORITY_VERY_SELDOM = 3;
    public static final int DANGERS_PRIORITY_SELDOM = 5;
    public static final int DANGERS_PRIORITY_NORMAL = 7;
    public static final int DANGERS_PRIORITY_OFTEN = 9;
    public static final int DANGERS_PRIORITY_VERY_OFTEN = 11;

//    public static final int DANGERS_PRIORITY_NEVER = 0;
//    public static final int DANGERS_PRIORITY_VERY_SELDOM = 0;
//    public static final int DANGERS_PRIORITY_SELDOM = 0;
//    public static final int DANGERS_PRIORITY_NORMAL = 0;
//    public static final int DANGERS_PRIORITY_OFTEN = 0;
//    public static final int DANGERS_PRIORITY_VERY_OFTEN = 0;

    public static final int DANGERS_PROBABILITY = 80;
    public static final int DANGERS_PROBABILITY_LIMIT = 100;

    public static final float OVERLAND_ENEMY_Y = 0f;
    public static final float FLYING_ENEMY_Y = 2.6f;
    public static final float ENEMY_DENSITY = RUNNER_DENSITY;
    public static final float ENEMY_LINEAR_VELOCITY =  -10f;
    public static final float ENEMY_JUMPING_COFF = 8f;

    public static final float LUMP_GRAVITY_SCALE = 5f;

    public static final float BONUS_RADIUS = 1f;

    public static final String BACKGROUND_IMAGE_PATH = "background.png";
    public static final String GROUND_IMAGE_PATH = "ground.png";
    public static final String CHARACTERS_ATLAS_PATH = "characters.atlas";
    public static final String [] RUNNER_RUNNING_REGION_NAMES = new String []
            {"monk_run1", "monk_run2"};
    public static final String RUNNER_HIT_REGION_NAME = "monk_hit";
    public static final String RUNNER_JUMPING_REGION_NAME = "monk_jump";
    public static final String RUNNER_STRIKING_REGION_NAME = "monk_kick";

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

    public static final String [] ENEMY1_REGION_NAMES = new String []
            {"enemy1_run1", "enemy1_run2"};

    public static final String [] ENEMY_ANIMATION_GROUP_NAMES = new String[]
            {"_stay", "_run", "_jump", "_strike", "_hit"};

    public static final String [] ACHIEVE_NAMES = new String []
            {"mushroom", "hands_in_blood",
            "neophyte_kung_fu", "adept_kung_fu", "master_kung_fu",
            "neophyte_tamesivari", "adept_tamesivari", "master_tamesivari",
            "mortal", "bound_in_flesh", "wheel_of_samsara", "lock"};

    public static final String BUTTON_MENU_ON = "button_on";
    public static final String BUTTON_MENU_OFF = "button_off";
}
