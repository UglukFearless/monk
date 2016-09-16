package net.uglukfearless.monk.enums;

import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.gameplay.Danger;
import net.uglukfearless.monk.constants.PlacingCategory;

import java.util.Random;


/**
 * Created by Ugluk on 20.05.2016.
 */
public enum EnemyType implements Danger {

    ENEMY_1(1.3f,3f, Constants.OVERLAND_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.ENEMY1_REGION_NAMES, Constants.ENEMY_LINEAR_VELOCITY, 2,
            false, true, false, true, Constants.ENEMY_JUMPING_COFF,
            Constants.DANGERS_PRIORITY_OFTEN, true,1.8f, 1.1f,-0.2f,0, 1),

    ENEMY_2(1.5f,3.2f, Constants.OVERLAND_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_WIDE_ENEMY_REGION_NAMES, Constants.ENEMY_LINEAR_VELOCITY, 2,
            false, true, true, true, Constants.ENEMY_JUMPING_COFF,
            Constants.DANGERS_PRIORITY_OFTEN, true,1.43f, 1.05f,0,0, 2),

    ENEMY_3(1f,2f, Constants.OVERLAND_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_LONG_ENEMY_REGION_NAMES, Constants.ENEMY_LINEAR_VELOCITY, 2,
            false, false, false, true, Constants.ENEMY_JUMPING_COFF,
            Constants.DANGERS_PRIORITY_SELDOM, false,1.2f, 1.1f,0,0, 3),

    ENEMY_4(2f,2f, Constants.OVERLAND_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.RUNNING_BIG_ENEMY_REGION_NAMES, Constants.ENEMY_LINEAR_VELOCITY, 2,
            false, false, false, true, Constants.ENEMY_JUMPING_COFF,
            Constants.DANGERS_PRIORITY_SELDOM, false,1.2f, 1.1f,0,0, 4),

    ENEMY_5(1f,1f, Constants.FLYING_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.FLYING_SMALL_ENEMY_REGION_NAMES, Constants.ENEMY_LINEAR_VELOCITY, 0,
            false, false, false, true, Constants.ENEMY_JUMPING_COFF,
            Constants.DANGERS_PRIORITY_VERY_SELDOM, false,1.2f, 1.1f,0,0, 5),

    ENEMY_6(2f,1f, Constants.FLYING_ENEMY_Y, Constants.ENEMY_DENSITY,
            Constants.FLYING_WIDE_ENEMY_REGION_NAMES, Constants.ENEMY_LINEAR_VELOCITY, 0,
            false, false, false, true, Constants.ENEMY_JUMPING_COFF,
            Constants.DANGERS_PRIORITY_SELDOM, false,1.2f, 1.1f,0,0, 6);

    private Random rand = new Random();

    private float width;
    private float height;
    private float y;
    private float density;
    private String [] regions;
    private Vector2 linearVelocity;
    private float jumpingImpulse;
    private int gravityScale;
    private boolean armour;
    private boolean jumper;
    private boolean shouter;
    private boolean striker;

    private float textureScaleX = 1;
    private float textureScaleY = 1;
    private float textureOffsetX = 0;
    private float textureOffsetY = 0;

    public short categoryBit = 0;
    public short[][] prohibitionsMap = new short[2][2];
    public int priority = Constants.DANGERS_PRIORITY_NEVER;

    private int mCurrentPriority = 0;

    //временное
    private boolean newAtlas = false;
    public int number = 0;
    private float basicXVelocity;


    EnemyType(float width, float height, float y,  float density, String [] regions
            , float linearVelocity, int gravityScale, boolean armour, boolean jumper,
              boolean shouter, boolean striker, float jumpingImpulse, int prior, boolean newAtlas,
                float scaleX, float scaleY, float offsetX, float offsetY, int number) {
        setupProhibitionMap();
        this.density = density;
        this.y = y;
        this.height = height;
        this.width = width;
        this.regions = regions;
        this.linearVelocity = new Vector2(Constants.WORLD_STATIC_VELOCITY.x +linearVelocity, 0);
        basicXVelocity = linearVelocity;
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

        this.textureScaleX = scaleX;
        this.textureScaleY = scaleY;
        this.textureOffsetX = offsetX;
        this.textureOffsetY = offsetY;

        //временное
        this.newAtlas = newAtlas;
        this.number = number;
    }

    private void setupProhibitionMap() {
        prohibitionsMap[0][1] = PlacingCategory.CATEGORY_PLACING_ENEMY_OVERLAND
                | PlacingCategory.CATEGORY_PLACING_OBSTACLE_OVERLAND;
    }

    public void calcPriority() {
        mCurrentPriority = ((rand.nextInt(priority)));
    }

    public int getCurrentPriority() {
        return mCurrentPriority;
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
        return new Vector2(linearVelocity);
    }

    public float  getBasicXVelocity() {
        return basicXVelocity;
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


    public float getJumpingImpulse() {
        return jumpingImpulse;
    }

    public float getTextureScaleX() {
        return textureScaleX;
    }

    public float getTextureScaleY() {
        return textureScaleY;
    }

    public float getTextureOffsetX() {
        return textureOffsetX;
    }

    public float getTextureOffsetY() {
        return textureOffsetY;
    }

    //временное

    public boolean isNewAtlas() {
        return newAtlas;
    }

}
