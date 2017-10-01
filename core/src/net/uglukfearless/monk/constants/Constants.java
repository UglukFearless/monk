package net.uglukfearless.monk.constants;

import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.utils.gameplay.models.LevelConstants;

import java.util.Random;

/**
 * Created by Ugluk on 17.05.2016.
 */
public class Constants {

    public static final Random RANDOM = new Random();

    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 480;
    public static final int GAME_WIDTH = 25;
    public static final int GAME_HEIGHT = APP_HEIGHT/(APP_WIDTH/GAME_WIDTH);


    public static final int BUTTON_MENU_WIDTH = 190;
    public static final int BUTTON_MENU_HEIGHT = 90;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);
    public static Vector2 WORLD_STATIC_VELOCITY_INIT = new Vector2(-15f,0);
    public static final Vector2 NULL_VELOCITY = new Vector2(0, 0);
    public static final float BACKGROUND_VELOCITY_COF = 0.30f;
    public static final float BACKGROUND_VELOCITY_COF_2 = 0.45f;

    public static final float BACKGROUND_WIDTH = 50;
    public static final float BACKGROUND_HEIGHT = 13;

    public static final float GROUND_X = 0;
    public static final float GROUND_Y = 0;
    public static float GROUND_WIDTH_INIT = 44f;
    public static final float GROUND_HEIGHT = 6f;
    public static float GROUND_HEIGHT_FIX_INIT = 1f;
    public static final float GROUND_DENSITY = 10f;
    public static final Vector2 GROUND_LINEAR_VELOCITY = WORLD_STATIC_VELOCITY_INIT;

    public static float DECORATION_ONE_OFFSET_INIT = 0f;
    public static float DECORATION_TWO_OFFSET_INIT = 0f;
    public static float DECORATION_NEAR_SPEED_INIT = 1f;
    public static float DECORATION_FURTHER_SPEED_INIT = 0.75f;

    public static float DECORATION_NEAR_STEP_INIT;
    public static float DECORATION_NEAR_STEP_MIN_INIT;
    public static float DECORATION_FURTHER_STEP_INIT;
    public static float DECORATION_FURTHER_STEP_MIN_INIT;

    public static final int PRIORITY_GROUND = 15;
    public static final int PRIORITY_PIT = 20;
    public static final int PRIORITY_COLUMNS = 12;

//    public static final int PRIORITY_GROUND = 3;
//    public static final int PRIORITY_PIT = 3;
//    public static final int PRIORITY_COLUMNS = 3;

    public static final float COLUMNS_Y = GROUND_Y + 2;
    public static float COLUMNS_WIDTH_INIT = 1f;
    public static float COLUMNS_PIT_INIT = 2f;
    public static float COLUMNS_HEIGHT_INIT = GROUND_HEIGHT;
    public static float COLUMNS_HEIGHT_FIX_INIT = 1f;
    public static final float COLUMNS_DENSITY = GROUND_DENSITY;
    public static final Vector2 COLUMNS_LINEAR_VELOCITY = WORLD_STATIC_VELOCITY_INIT;
    public static int COLUMNS_QUANTITY_INIT = 4;

    public static float GROUND_PIT_INIT = 6f;

    public static final float LAYOUT_Y_STEP = 4;
    public static final float LAYOUT_Y_ONE = GROUND_Y + GROUND_HEIGHT/2;
    public static final float LAYOUT_Y_TWO = LAYOUT_Y_ONE + LAYOUT_Y_STEP;
    public static final float DANGERS_START_OFFSET = 13;
    public static float STEP_OF_DANGERS = (GROUND_WIDTH_INIT - DANGERS_START_OFFSET)/2f;

    public static final float RUNNER_X = 4;
    public static final float RUNNER_Y = GROUND_Y + GROUND_HEIGHT/2; // - 1f;
    public static final float RUNNER_WIDTH = 1.5f;
    public static final float RUNNER_HEIGHT = 3f;
    public static final float RUNNER_DENSITY = 0.5f;
    public static final float RUNNER_GRAVITY_SCALE = 5f;
    public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 40f);
    public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE_ONE = new Vector2(0, 65f);
    public static final float RUNNER_HIT_ANGULAR_IMPULSE = 20f;
    public static final float DRAGON_X = -4f;
    public static final float BUDDHA_SPEED_SCALE = 1.8f;

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
    public static final float ENEMY_LINEAR_VELOCITY = -10f;
    public static final float ENEMY_JUMPING_COFF = 8f;

    public static final float LUMP_GRAVITY_SCALE = 5f;


    public static final String [] RUNNER_RUNNING_REGION_NAMES = new String []
            {"monk_run1", "monk_run2"};
    public static final String RUNNER_HIT_REGION_NAME = "monk_hit";
    public static final String RUNNER_JUMPING_REGION_NAME = "monk_jump";
    public static final String RUNNER_STRIKING_REGION_NAME = "monk_kick";

    public static final String [] RUNNER_ANIMATION_GROUP_NAMES = new String[]
            {"stay", "run", "jump", "hit"};

    public static final String [] RUNNER_ANIMATION_STRIKE_NAMES = new String[]
            {"strike", "jump_strike"};
    public static final String FRAME_STRING = "_frame";


    public static final String [] ENEMY_ANIMATION_GROUP_NAMES = new String[]
            {"_stay", "_run", "_jump", "_strike", "_hit"};

    public static final String [] OBSTACLE_ANIMATION_GROUP_NAMES = new String[]
            {"_stay", "_hit"};


    public static final String ACHIEVE_MUSHROOM_REGION = "mushroom";
    public static final String ACHIEVE_HIB_REGION = "hands_in_blood";
    public static final String ACHIEVE_NKF_REGION = "neophyte_kun_fu";
    public static final String ACHIEVE_AKF_REGION = "adept_kun_fu";
    public static final String ACHIEVE_MKF_REGION = "master_kun_fu";
    public static final String ACHIEVE_GKF_REGION = "genius_kun_fu";
    public static final String ACHIEVE_NT_REGION = "neophyte_tamesivari";
    public static final String ACHIEVE_AT_REGION = "adept_tamesivari";
    public static final String ACHIEVE_MT_REGION = "master_tamesivari";
    public static final String ACHIEVE_GT_REGION = "genius_tamesivari";
    public static final String ACHIEVE_MORTAL_REGION = "mortal";
    public static final String ACHIEVE_BIF_REGION = "bound_in_flesh";
    public static final String ACHIEVE_REBORN_REGION = "reborn";
    public static final String ACHIEVE_WOS_REGION = "wheel_of_samsara";
    public static final String ACHIEVE_LOCK_REGION = "lock";
    public static final String ACHIEVE_RUTHLESS_REGION = "ruthless";

    public static final String BONUS_BUDDHA_REGION = "buddha";
    public static final String BONUS_WINGS_REGION = "faith_wings";
    public static final String BONUS_GHOST_REGION = "ghost";
    public static final String BONUS_RETRIBUTION_REGION = "retribution";
    public static final String BONUS_REVIVAL_REGION = "revival";
    public static final String BONUS_STRONG_BEAT_REGION = "strong_beat";
    public static final String BONUS_THUNDER_FIST_REGION = "thunder_fist";
    public static final String BONUS_DRAGON_FORM_REGION = "dragon";

    public static void init(LevelConstants levelConstants) {

        WORLD_STATIC_VELOCITY_INIT = levelConstants.WORLD_STATIC_VELOCITY_INIT;
        GROUND_WIDTH_INIT = levelConstants.GROUND_WIDTH_INIT;
        GROUND_PIT_INIT = levelConstants.GROUND_PIT_INIT;
        COLUMNS_WIDTH_INIT = levelConstants.COLUMNS_WIDTH_INIT;
        COLUMNS_HEIGHT_INIT = levelConstants.COLUMNS_HEIGHT_INIT;
        COLUMNS_HEIGHT_FIX_INIT = levelConstants.COLUMNS_HEIGHT_FIX_INIT;
        COLUMNS_PIT_INIT = levelConstants.COLUMNS_PIT_INIT;
        COLUMNS_QUANTITY_INIT = levelConstants.COLUMNS_QUANTITY_INIT;

        STEP_OF_DANGERS = (GROUND_WIDTH_INIT - GROUND_PIT_INIT)/2f;
        STEP_OF_DANGERS = (GROUND_WIDTH_INIT - DANGERS_START_OFFSET)/2f;

        GROUND_HEIGHT_FIX_INIT = levelConstants.GROUND_HEIGHT_FIX_INIT;

        DECORATION_ONE_OFFSET_INIT = levelConstants.DECORATION_ONE_OFFSET_INIT;
        DECORATION_TWO_OFFSET_INIT = levelConstants.DECORATION_TWO_OFFSET_INIT;
        DECORATION_NEAR_SPEED_INIT = levelConstants.DECORATION_NEAR_SPEED_INIT;
        DECORATION_FURTHER_SPEED_INIT = levelConstants.DECORATION_FURTHER_SPEED_INIT;

        DECORATION_NEAR_STEP_INIT = levelConstants.DECORATION_NEAR_STEP_INIT;
        DECORATION_NEAR_STEP_MIN_INIT = levelConstants.DECORATION_NEAR_STEP_MIN_INIT;
        DECORATION_FURTHER_STEP_INIT = levelConstants.DECORATION_FURTHER_STEP_INIT;
        DECORATION_FURTHER_STEP_MIN_INIT = levelConstants.DECORATION_FURTHER_STEP_MIN_INIT;
    }
}
