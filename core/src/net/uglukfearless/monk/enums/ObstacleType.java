package net.uglukfearless.monk.enums;

import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.Danger;
import net.uglukfearless.monk.constants.PlacingCategory;

/**
 * Created by Ugluk on 07.06.2016.
 */
public enum ObstacleType implements Danger {
    BOX(4f, 4f, 25f, Constants.GROUND_Y + Constants.GROUND_HEIGHT, 0.5f,new String[] { "box.png"},
            Constants.WORLD_STATIC_VELOCITY, 1, false, false, false),
    STONE(4f, 4f, 25f, Constants.GROUND_Y + Constants.GROUND_HEIGHT, 6f, new String[] { "stone.png"},
            Constants.WORLD_STATIC_VELOCITY, 2, true, false, false),
    BLADES(4f, 4f, 25f, Constants.GROUND_Y + Constants.GROUND_HEIGHT/2, 1f, null,
            Constants.WORLD_STATIC_VELOCITY, 0, true, true, true);


    private float width;
    private float height;
    private float x;
    private float y;
    private float density;
    private String [] regions;
    private Vector2 linearVelocity;
    private int gravityScale;
    private boolean armour;
    private boolean isTrap;
    private boolean isSphere;

    public short categoryBit = 0;
    public short[][] prohibitionsMap = new short[2][2];

    ObstacleType(float width, float height, float x, float y, float density, String[] regions,
                 Vector2 linearVelocity, int gravityScale, boolean armour, boolean isTrap,
                 boolean isSphere) {

        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.density = density;
        this.regions = regions;
        this.linearVelocity = linearVelocity;
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

    public boolean isTrap() {
        return isTrap;
    }

    public boolean isSphere() {
        return isSphere;
    }
}
