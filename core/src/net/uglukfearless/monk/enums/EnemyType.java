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
import net.uglukfearless.monk.utils.gameplay.models.EnemyModel;
import net.uglukfearless.monk.utils.gameplay.models.LevelModel;

import java.util.Random;


/**
 * Created by Ugluk on 20.05.2016.
 */
public enum EnemyType implements Danger {

    ENEMY_1(),

    ENEMY_2(),

    ENEMY_3(),

    ENEMY_4(),

    ENEMY_5(),

    ENEMY_6(),

    ENEMY_7(),

    ENEMY_8(),

    ENEMY_9(),

    ENEMY_10();

    private Random rand = new Random();

    private String name;
    private String enName;
    private String ruName;
    private String KEY;

    private float width;
    private float height;
    private float y;
    private float density;
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
    private int number = 0;
    private float basicXVelocity;

    //для анимации
    private Array<Animation> mAnimations;
    private Array<TextureRegion> mRegions;
    private StringBuilder mStringBuilder;
    private boolean mStopSeek;
    private int mRegionNum;
    private TextureRegion mCurrentRegion;
    private TextureRegion mShellRegion;
    private Animation mStayAnimation;
    private Animation mRunAnimation;
    private Animation mJumpAnimation;
    private Animation mStrikeAnimation;
    private Animation mDieAnimation;
    private Animation mAnimation;
    private boolean mBlock;


    EnemyType() {

//        this.name = name;
//        this.ruName = ruName;
//        this.enName = enName;
//        this.KEY = PreferencesConstants.GENERAL_DANGER_KEY.concat(this.name);

//        PreferencesManager.putDangerKey(KEY, this.enName, this.ruName);

        setupProhibitionMap();
        this.density = Constants.ENEMY_DENSITY;
//        this.y = y;
//        this.height = height;
//        this.width = width;
//        this.linearVelocity = new Vector2(Constants.WORLD_STATIC_VELOCITY_INIT.x +linearVelocity, 0);
//        basicXVelocity = linearVelocity;
//        this.gravityScale = gravityScale;
//        if (gravityScale==0) {
//            categoryBit = (short)(categoryBit|PlacingCategory.CATEGORY_PLACING_ENEMY_FLYING);
//        }  else {
//            categoryBit = (short)(categoryBit|PlacingCategory.CATEGORY_PLACING_ENEMY_OVERLAND);
//        }
//        this.armour = armour;
//        if (armour) {
//            categoryBit = (short)(categoryBit|PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR);
//
//            prohibitionsMap[0][1] = (short) (prohibitionsMap[0][1] | PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR
//                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP);
//            prohibitionsMap[1][1] = PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR
//                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP;
//            prohibitionsMap[1][0] = PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP
//                    | PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR;
//        }
//
//        this.jumper = jumper;
//        this.shouter = shouter;
//        this.striker = striker;
//        this.jumpingImpulse = jumpingImpulse;
//
//        priority = prior;
//
//        this.textureScaleX = scaleX;
//        this.textureScaleY = scaleY;
//        this.textureOffsetX = offsetX;
//        this.textureOffsetY = offsetY;
//
//        this.number = number;
    }

    public void init(EnemyModel enemyModel) {


        this.name = enemyModel.name;
        this.ruName = enemyModel.ruName;
        this.enName = enemyModel.enName;
        this.KEY = PreferencesConstants.GENERAL_DANGER_KEY.concat(this.name);

        PreferencesManager.putDangerKey(KEY, this.enName, this.ruName);

        setupProhibitionMap();
        this.y = enemyModel.y;
        this.height = enemyModel.height;
        this.width = enemyModel.width;
        this.linearVelocity = new Vector2(Constants.WORLD_STATIC_VELOCITY_INIT.x +enemyModel.basicXVelocity, 0);
        basicXVelocity = enemyModel.basicXVelocity;
        this.gravityScale = enemyModel.gravityScale;
        if (gravityScale==0) {
            categoryBit = (short)(categoryBit|PlacingCategory.CATEGORY_PLACING_ENEMY_FLYING);
        }  else {
            categoryBit = (short)(categoryBit|PlacingCategory.CATEGORY_PLACING_ENEMY_OVERLAND);
        }
        armour = false;
        if (armour) {
            categoryBit = (short)(categoryBit|PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR);

            prohibitionsMap[0][1] = (short) (prohibitionsMap[0][1] | PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR
                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP);
            prohibitionsMap[1][1] = PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR
                    | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP;
            prohibitionsMap[1][0] = PlacingCategory.CATEGORY_PLACING_OBSTACLE_TRAP
                    | PlacingCategory.CATEGORY_PLACING_ENEMY_ARMOUR | PlacingCategory.CATEGORY_PLACING_OBSTACLE_ARMOUR;
        }

        prohibitionsMap[0][1] = (short) (prohibitionsMap[0][1] | enemyModel.prohibitionsMap [0][1]);
        prohibitionsMap[1][1] = (short) (prohibitionsMap[1][1] | enemyModel.prohibitionsMap [1][1]);
        prohibitionsMap[1][0] = (short) (prohibitionsMap[1][0] | enemyModel.prohibitionsMap [1][0]);

        this.jumper = enemyModel.jumper;
        this.shouter = enemyModel.shouter;
        this.striker = enemyModel.striker;
        this.jumpingImpulse = enemyModel.jumpingImpulse;

        priority = enemyModel.priority;

        this.textureScaleX = enemyModel.textureScaleX;
        this.textureScaleY = enemyModel.textureScaleY;
        this.textureOffsetX = enemyModel.textureOffsetX;
        this.textureOffsetY = enemyModel.textureOffsetY;

        mBlock = enemyModel.block;

        this.number = enemyModel.number;

        init();
    }

    public void init() {

            mAnimations = new Array<Animation>();
            mRegions = new Array<TextureRegion>();
            mStringBuilder = new StringBuilder();

            for (int ii=0; ii<Constants.ENEMY_ANIMATION_GROUP_NAMES.length; ii++) {
                mStopSeek = false;
                mRegions.clear();
                mStringBuilder.append("enemy").append(number);
                mStringBuilder.append(Constants.ENEMY_ANIMATION_GROUP_NAMES[ii]);
                mRegionNum = 0;

                while (!mStopSeek) {
                    mRegionNum++;

                    mCurrentRegion = (AssetLoader.enemiesAtlas.findRegion(mStringBuilder.toString()
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
                    mAnimations.add(new Animation(0.12f, mRegions));
                } else {
                    mAnimations.add(null);
                }

                mStringBuilder.delete(0, mStringBuilder.length());
            }

            mShellRegion = AssetLoader.enemiesAtlas.findRegion("enemy" + number + "_shell1");

            mStayAnimation = mAnimations.get(0);
            mRunAnimation = mAnimations.get(1);
            mJumpAnimation = mAnimations.get(2);
            mStrikeAnimation = mAnimations.get(3);
            mDieAnimation = mAnimations.get(4);

            if (mStayAnimation!=null) {
                mAnimation = mStayAnimation;
            } else {
                mAnimation = mRunAnimation;
            }

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

    public String getKEY() {
        return KEY;
    }

    //геттеры по анимациям
    public TextureRegion getShellRegion() {
        return mShellRegion;
    }

    public Animation getStayAnimation() {
        return mStayAnimation;
    }

    public Animation getRunAnimation() {
        return mRunAnimation;
    }

    public Animation getJumpAnimation() {
        return mJumpAnimation;
    }

    public Animation getStrikeAnimation() {
        return mStrikeAnimation;
    }

    public Animation getDieAnimation() {
        return mDieAnimation;
    }

    public Animation getAnimation() {
        return mAnimation;
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
}
