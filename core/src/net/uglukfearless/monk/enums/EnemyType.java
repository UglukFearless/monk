package net.uglukfearless.monk.enums;

import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.Danger;
import net.uglukfearless.monk.constants.PlacingCategory;


/**
 * Created by Ugluk on 20.05.2016.
 */
public enum EnemyType implements Danger {

    RUNNING_SMALL(1f,1f, Constants.OVERLAND_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_SMALL_ENEMY_REGION_NAMES, Constants.ENEMY_LINEAR_VELOCITY, 1,
            false, true, false, false, Constants.ENEMY_JUMPING_LINEAR_IMPULSE,
            Constants.DANGERS_PRIORITY_NORMAL),
    RUNNING_WIDE(2f,1f, Constants.OVERLAND_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_WIDE_ENEMY_REGION_NAMES, Constants.ENEMY_LINEAR_VELOCITY, 1,
            false, true, false, false, Constants.ENEMY_JUMPING_LINEAR_IMPULSE,
            Constants.DANGERS_PRIORITY_NORMAL),
    RUNNING_LONG(1f,2f, Constants.OVERLAND_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_LONG_ENEMY_REGION_NAMES, Constants.ENEMY_LINEAR_VELOCITY, 1,
            false, true, false, false, Constants.ENEMY_JUMPING_LINEAR_IMPULSE,
            Constants.DANGERS_PRIORITY_NORMAL),
    RUNNING_BIG(2f,2f, Constants.OVERLAND_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_BIG_ENEMY_REGION_NAMES, Constants.ENEMY_LINEAR_VELOCITY, 1,
            false, true, false, false, Constants.ENEMY_JUMPING_LINEAR_IMPULSE,
            Constants.DANGERS_PRIORITY_NORMAL),
    FLYING_SMALL(1f,1f, Constants.FLYING_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.FLYING_SMALL_ENEMY_REGION_NAMES, Constants.ENEMY_LINEAR_VELOCITY, 0,
            false, false, false, false, Constants.ENEMY_JUMPING_LINEAR_IMPULSE,
            Constants.DANGERS_PRIORITY_SELDOM),
    FLYING_WIDE(2f,1f, Constants.FLYING_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.FLYING_WIDE_ENEMY_REGION_NAMES, Constants.ENEMY_LINEAR_VELOCITY, 0,
            false, false, false, false, Constants.ENEMY_JUMPING_LINEAR_IMPULSE,
            Constants.DANGERS_PRIORITY_NORMAL);

    private float width;
    private float height;
    private float x;
    private float y;
    private float density;
    private String [] regions;
    private Vector2 linearVelocity;
    private Vector2 jumpingImpulse;
    private int gravityScale;
    private boolean armour;
    private boolean jumper;
    private boolean shouter;
    private boolean striker;

    public short categoryBit = 0;
    public short[][] prohibitionsMap = new short[2][2];
    public int priority = Constants.DANGERS_PRIORITY_NEVER;


    EnemyType(float width, float height, float y,  float density, String [] regions
            , Vector2 linearVelocity, int gravityScale, boolean armour, boolean jumper,
              boolean shouter, boolean striker, Vector2 jumpingImpulse, int prior) {
        setupProhibitionMap();
        this.density = density;
        this.y = y;
        this.height = height;
        this.width = width;
        this.regions = regions;
        this.linearVelocity = linearVelocity;
        this.gravityScale = gravityScale;
        if (gravityScale==0) {
            categoryBit = (short)(categoryBit|PlacingCategory.CATEGORY_PLACING_ENEMY_FLYING);
        }  else {
            categoryBit = (short)(categoryBit|PlacingCategory.CATEGORY_PLACING_ENEMY_OVERLAND);
        }
        this.armour = armour;
        if (armour) {
            categoryBit = (short)(categoryBit|PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR);

            prohibitionsMap[0][1] = (short) (prohibitionsMap[0][1] | PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR
                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP);
            prohibitionsMap[1][1] = PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR
                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP;
            prohibitionsMap[1][0] = PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP
                    | PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR;
        }

        this.jumper = jumper;
        this.shouter = shouter;
        this.striker = striker;
        this.jumpingImpulse = jumpingImpulse;

        priority = prior;
    }

    private void setupProhibitionMap() {
        prohibitionsMap[0][1] = PlacingCategory.CATEGORY_PLACING_ENEMY_OVERLAND
                | PlacingCategory.CATEGORY_PLACING_OBSTACLE_OVERLAND;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public short getCategoryBit() {
        return categoryBit;
    }

    @Override
    public short[][] getProhibitionsMap() {
        return prohibitionsMap;
    }

    @Override
    public boolean checkResolve(short codeOfForbidden) {
        return (categoryBit&codeOfForbidden)==0;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getDensity() {
        return density;
    }

    public String[] getRegions() {
        return regions;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }


    public int getGravityScale() {
        return gravityScale;
    }


    public boolean isArmour() {
        return armour;
    }


    public boolean isJumper() {
        return jumper;
    }


    public boolean isShouter() {
        return shouter;
    }


    public boolean isStriker() {
        return striker;
    }


    public Vector2 getJumpingImpulse() {
        return jumpingImpulse;
    }

}
