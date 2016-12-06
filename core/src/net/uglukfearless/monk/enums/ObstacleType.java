package net.uglukfearless.monk.enums;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.gameplay.dangers.Danger;
import net.uglukfearless.monk.constants.PlacingCategory;
import net.uglukfearless.monk.utils.gameplay.models.ObstacleModel;

import java.util.Random;

/**
 * Created by Ugluk on 07.06.2016.
 */
public enum ObstacleType implements Danger {
    OBSTACLE_1(),
    OBSTACLE_2(),
    OBSTACLE_3(),
    OBSTACLE_4(),
    OBSTACLE_5(),
    OBSTACLE_6(),
    OBSTACLE_7(),
    OBSTACLE_8(),
    OBSTACLE_9(),
    OBSTACLE_10();

    private Random rand = new Random();

    private String name;
    private String enName;
    private String ruName;
    private String KEY;

    private float width;
    private float height;
    private float y;
    private float y_offset;
    private float density;
    private Vector2 linearVelocity;
    private Vector2 hitExecution;
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
    private int mNumber;

    //для анимаций
    private Array<Animation> mAnimations;
    private Array<TextureRegion> mRegions;
    private StringBuilder mStringBuilder;
    private boolean mStopSeek;
    private int mRegionNum;
    private TextureRegion mCurrentRegion;
    private Animation mStayAnimation;
    private Animation mDieAnimation;
    private boolean mBlock;
    private boolean isConteiner;

    ObstacleType() {
    }

    public void init(ObstacleModel obstacleModel) {

        this.name = obstacleModel.name;
        this.ruName = obstacleModel.ruName;
        this.enName = obstacleModel.enName;
        this.KEY = PreferencesConstants.GENERAL_DANGER_KEY.concat(this.name);

        PreferencesManager.putDangerKey(KEY, this.enName, this.ruName);

        this.width = obstacleModel.width;
        this.height = obstacleModel.height;

        this.y = obstacleModel.y;
        this.y_offset = obstacleModel.y_offset;

        this.density = obstacleModel.density;

        this.linearVelocity = new Vector2(Constants.WORLD_STATIC_VELOCITY_INIT.x + obstacleModel.linearVelocity,
                Constants.WORLD_STATIC_VELOCITY_INIT.y);

        this.hitExecution = obstacleModel.hitExecution;

        this.gravityScale = obstacleModel.gravityScale;

        if (gravityScale==0) {
            categoryBit = (short) (categoryBit| PlacingCategory.CATEGORY_PLACING_OBSTACLE_FLYING);

            prohibitionsMap[0][1] = (short) (prohibitionsMap[0][1] | PlacingCategory.CATEGORY_PLACING_ENEMY_OVERLAND
                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_OVERLAND);
        } else {
            categoryBit = (short) (categoryBit| PlacingCategory.CATEGORY_PLACING_OBSTACLE_OVERLAND);
        }

        this.armour = obstacleModel.armour;
        if (armour) {
            categoryBit = (short) (categoryBit| PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR);

            prohibitionsMap[0][1] = (short) (prohibitionsMap[0][1] | PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR
                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP);
            prohibitionsMap[1][1] = PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR
                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP;
            prohibitionsMap[1][0] = PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP
                    | PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR;
        }

        this.isTrap = obstacleModel.isTrap;
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

        prohibitionsMap[0][1] = (short) (prohibitionsMap[0][1] | obstacleModel.prohibitionsMap [0][1]);
        prohibitionsMap[1][1] = (short) (prohibitionsMap[1][1] | obstacleModel.prohibitionsMap [1][1]);
        prohibitionsMap[1][0] = (short) (prohibitionsMap[1][0] | obstacleModel.prohibitionsMap [1][0]);

        this.isSphere = obstacleModel.isSphere;

        this.isConteiner = obstacleModel.isContainer;

        priority = obstacleModel.priority;

        this.textureScaleX = obstacleModel.textureScaleX;
        this.textureScaleY = obstacleModel.textureScaleY;
        this.textureOffsetX = obstacleModel.textureOffsetX;
        this.textureOffsetY = obstacleModel.textureOffsetY;

        mBlock = obstacleModel.block;

        mNumber = obstacleModel.mNumber;

        init();
    }

    public void init() {

        mAnimations = new Array<Animation>();
        mRegions = new Array<TextureRegion>();
        mStringBuilder = new StringBuilder();

        for (int ii=0; ii<Constants.OBSTACLE_ANIMATION_GROUP_NAMES.length; ii++) {
            mStopSeek = false;
            mRegions.clear();
            mStringBuilder.append("obstacle").append(mNumber);
            mStringBuilder.append(Constants.OBSTACLE_ANIMATION_GROUP_NAMES[ii]);
            mRegionNum = 0;

            while (!mStopSeek) {
                mRegionNum++;

                mCurrentRegion = (AssetLoader.obstaclesAtlas.findRegion(mStringBuilder.toString()
                        + String.valueOf(mRegionNum)));


                if (mCurrentRegion!=null) {
                    mRegions.add(mCurrentRegion);
                } else {
                    mStopSeek = true;
                    mRegionNum = 0;
                }
            }

            mStopSeek = false;

            if (mRegions.size>0) {
                mAnimations.add(new Animation(0.03f, mRegions));
            } else {
                mAnimations.add(null);
            }

            mStringBuilder.delete(0, mStringBuilder.length());
        }

        mStayAnimation = mAnimations.get(0);
        mDieAnimation = mAnimations.get(1);

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

    public float getY_offset() {
        return y_offset;
    }

    public float getDensity() {
        return density;
    }

    public Vector2 getLinearVelocity() {
        return new Vector2(linearVelocity);
    }

    public Vector2 getHitExecution() {
        return hitExecution;
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

    public boolean isConteiner() {
        return isConteiner;
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

    //геттеры анимаций
    public Animation getStayAnimation() {
        return mStayAnimation;
    }

    public Animation getDieAnimation() {
        return mDieAnimation;
    }

    //управление параметрами

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isBlock() {
        return mBlock;
    }

    public void setBlock(boolean block) {
        mBlock = block;
    }

    public String getRuName() {
        return ruName;
    }

    public String getName() {
        return name;
    }

    public String getEnName() {
        return enName;
    }
}
