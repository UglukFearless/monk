package net.uglukfearless.monk.enums;

import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.gameplay.dangers.Danger;
import net.uglukfearless.monk.constants.PlacingCategory;

import java.util.Random;

/**
 * Created by Ugluk on 07.06.2016.
 */
public enum ObstacleType implements Danger {
    BOX("BOX", "Box", "Ящик"
            ,4f, 4f, 0, 0.5f,new String[] { "box.png"},
            0, 1, false, false, false,
            Constants.DANGERS_PRIORITY_OFTEN, 1, 1, 0, 0),
//            Constants.DANGERS_PRIORITY_NEVER, 1, 1, 0, 0),
    STONE("STONE", "Stone", "Камень"
        ,4f, 4f, 0, 6f, new String[] { "stone.png"},
            0, 2, true, false, false,
            Constants.DANGERS_PRIORITY_VERY_OFTEN, 1, 1, 0, 0),
//            Constants.DANGERS_PRIORITY_NEVER, 1, 1, 0, 0),
    BLADES("BLADES", "Blades", "Лезвия"
        ,4f, 4f, -2, 1f, null,
            0, 0, true, true, true,
            Constants.DANGERS_PRIORITY_NORMAL, 1, 1, 0, 0);
//            Constants.DANGERS_PRIORITY_NEVER, 1, 1, 0, 0);

    private Random rand = new Random();

    private String name;
    private String enName;
    private String ruName;
    private String KEY;

    private float width;
    private float height;
    private float y;
    private float density;
    private String [] regions;
    private Vector2 linearVelocity;
    private int gravityScale;
    private boolean armour;
    private boolean isTrap;
    private boolean isSphere;

    private float textureScaleX = 1;
    private float textureScaleY = 1;
    private float textureOffsetX = 0;
    private float textureOffsetY = 0;

    public short categoryBit = 0;
    public short[][] prohibitionsMap = new short[2][2];
    public int priority = Constants.DANGERS_PRIORITY_NEVER;

    private int mCurrentPriority = 0;

    ObstacleType(String name, String enName, String ruName
            ,float width, float height, float y, float density, String[] regions,
                 float linearVelocity, int gravityScale, boolean armour, boolean isTrap,
                 boolean isSphere, int prior, float scaleX, float scaleY
            , float offsetX, float offsetY) {

        this.name = name;
        this.ruName = ruName;
        this.enName = enName;
        this.KEY = PreferencesConstants.GENERAL_DANGER_KEY.concat(this.name);

        PreferencesManager.putDangerKey(KEY, this.enName, this.ruName);

        this.width = width;
        this.height = height;

        this.y = y;

        this.density = density;
        this.regions = regions;
        this.linearVelocity = new Vector2(Constants.WORLD_STATIC_VELOCITY.x + linearVelocity,
                Constants.WORLD_STATIC_VELOCITY.y);
        this.gravityScale = gravityScale;

        if (gravityScale==0) {
            categoryBit = (short) (categoryBit| PlacingCategory.CATEGORY_PLACING_OBSTACLE_FLYING);

            prohibitionsMap[0][1] = (short) (prohibitionsMap[0][1] | PlacingCategory.CATEGORY_PLACING_ENEMY_OVERLAND
                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_OVERLAND);
        } else {
            categoryBit = (short) (categoryBit| PlacingCategory.CATEGORY_PLACING_OBSTACLE_OVERLAND);
        }

        this.armour = armour;
        if (armour) {
            categoryBit = (short) (categoryBit| PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR);

            prohibitionsMap[0][1] = (short) (prohibitionsMap[0][1] | PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR
                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP);
            prohibitionsMap[1][1] = PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR
                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP;
            prohibitionsMap[1][0] = PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP
                    | PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR;
        }

        this.isTrap = isTrap;
        if (isTrap) {
            categoryBit = (short) (categoryBit| PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP);

            prohibitionsMap[0][1] = (short) (prohibitionsMap[0][1] | PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR
                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP
                    | PlacingCategory.CATEGORY_PLACING_ENEMY_OVERLAND | PlacingCategory.CATEGORY_PLACING_OBSTACLE_OVERLAND);
            prohibitionsMap[1][1] = PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR
                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP;
            prohibitionsMap[1][0] = PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP
                    | PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR;
        }

        this.isSphere = isSphere;

        priority = prior;

        this.textureScaleX = scaleX;
        this.textureScaleY = scaleY;
        this.textureOffsetX = offsetX;
        this.textureOffsetY = offsetY;
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

    public int getGravityScale() {
        return gravityScale;
    }

    public boolean isArmour() {
        return armour;
    }

    public boolean isTrap() {
        return isTrap;
    }

    public boolean isSphere() {
        return isSphere;
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

    public String getKEY() {
        return KEY;
    }
}
