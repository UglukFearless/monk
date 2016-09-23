package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import net.uglukfearless.monk.box2d.EnemyUserData;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.enums.EnemyType;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.file.ScoreCounter;
import net.uglukfearless.monk.utils.gameplay.BodyUtils;
import net.uglukfearless.monk.utils.gameplay.Movable;
import net.uglukfearless.monk.utils.gameplay.ai.Situation;
import net.uglukfearless.monk.utils.gameplay.ai.SpaceTable;
import net.uglukfearless.monk.utils.gameplay.WorldUtils;
import net.uglukfearless.monk.utils.gameplay.pools.PoolsHandler;


/**
 * Created by Ugluk on 20.05.2016.
 */
public class Enemy extends GameActor implements Pool.Poolable, Movable {


    private Animation mAnimation;
    private Animation mStayAnimation;
    private Animation mRunAnimation;
    private Animation mJumpAnimation;
    private Animation mStrikeAnimation;
    private Animation mDieAnimation;

    private Array<Animation> mAnimations;
    private Array<TextureRegion> mRegions;

    private boolean mStopSeek;
    private TextureRegion mShellRegion;
    private TextureRegion mCurrentRegion;

    private StringBuilder mStringBuilder;
    private int mRegionNum;

    private float stateTime;
    private float deadTime;

    private float strikeTime;
    private Vector2 jumpImpulse;

    private boolean starting;

    private Situation mSituation;
    private float mPreviousVelocity;


    public Enemy(World world, EnemyType enemyType) {
        super(WorldUtils.createEnemy(world, enemyType));

        TextureRegion [] runningFrames = new TextureRegion[getUserData().getTextureRegions().length];
        if (getUserData().atlas) {
            for (int i=0;i<getUserData().getTextureRegions().length;i++) {
                runningFrames[i] = AssetLoader.enemiesAtlas
                        .findRegion(getUserData().getTextureRegions()[i]);
            }

            mAnimations = new Array<Animation>();
            mRegions = new Array<TextureRegion>();
            mStringBuilder = new StringBuilder();

//            for (int i=1; i<3; i++) {



                for (int ii=0; ii<Constants.ENEMY_ANIMATION_GROUP_NAMES.length; ii++) {
                    mStopSeek = false;
                    mRegions.clear();
                    mStringBuilder.append("enemy").append(getUserData().enemyType.number);
                    mStringBuilder.append(Constants.ENEMY_ANIMATION_GROUP_NAMES[ii]);
                    mRegionNum = 0;

                    while (!mStopSeek) {
                        mRegionNum++;

                        mCurrentRegion = (AssetLoader.enemiesAtlas.findRegion(mStringBuilder.toString()
                                + String.valueOf(mRegionNum)));

                        System.out.println(mStringBuilder.toString() + String.valueOf(mRegionNum));
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

                mShellRegion = AssetLoader.enemiesAtlas.findRegion("enemy" + getUserData().enemyType.number + "_shell");

//            }

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

        } else {
            for (int i=0;i<getUserData().getTextureRegions().length;i++) {
                runningFrames[i] = AssetLoader.charactersAtlas
                        .findRegion(getUserData().getTextureRegions()[i]);
            }

            mAnimation = new Animation(0.1f, runningFrames);
        }



        stateTime = 0f;
        deadTime = 0f;
        strikeTime = 0f;

        starting = false;

        jumpImpulse = new Vector2(0, getUserData().getWidth()*getUserData().getHeight()
                *getUserData().getJumpingImpulse());

        mSituation = new Situation();
    }

    public void init(Stage stage, float x, float y) {

        if (mStayAnimation!=null) {
            mAnimation = mStayAnimation;
        } else if (mRunAnimation!=null){
            mAnimation = mRunAnimation;
        }

        body.setTransform(x, getUserData().getBasicY() + y + getUserData().getHeight() / 2f, 0);
        body.setLinearVelocity(((GameStage) stage).getCurrentVelocity());
        body.setActive(true);
        starting = false;
        stage.addActor(this);
        mPreviousVelocity = ((GameStage)stage).getCurrentVelocity().x;
        ((GameStage)stage).addMovable(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();

        EnemyUserData data = getUserData();

        batch.draw(mAnimation.getKeyFrame(stateTime, true),
                body.getPosition().x - (data.getWidth() * data.getScaleX() / 2) + data.getOffsetX(),
                body.getPosition().y - (data.getHeight() / 2) + data.getOffsetY(),
                getUserData().getWidth() * 0.5f, getUserData().getHeight() * 0.5f,
                data.getWidth() * data.getScaleX(), data.getHeight() * data.getScaleY()
                , 1f, 1f, (float) Math.toDegrees(body.getAngle()));

    }

    @Override
    public EnemyUserData getUserData() {
        return (EnemyUserData) userData;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (body.getPosition().x < Constants.GAME_WIDTH&&!starting) {
                start();
        }

        if (getUserData().isDead()) {
            dead(delta);
        } else if (!BodyUtils.bodyInBounds(body)) {
            this.remove();
            PoolsHandler.sEnemiesPools.get(getUserData().enemyType.name()).free(this);
        } else {

            if (body.getPosition().x < Constants.GAME_WIDTH) {

                mSituation = SpaceTable.getSituation(body.getPosition().x, body.getPosition().y
                        - getUserData().enemyType.getY() - getUserData().getHeight() / 2f
                        , getUserData().getWidth(), getUserData().enemyType.getCategoryBit(), mSituation);

                if (mSituation.jump
                        &&body.getLinearVelocity().y==0
                        &&getUserData().isJumper()) {

                        jump();

                } else if (body.getLinearVelocity().y==0) {

                    if (getUserData().getGravityScale()!=0&&mSituation.stop) {
                        standing();
                    } else if (getUserData().getGravityScale()==0&&mSituation.stopFly){
                        standing();
                    }

                }

                if (mSituation.strike) {
                    strike();
                } else if (mSituation.shoot) {
                    shoot();
                }

                if (body.getLinearVelocity().x>Constants.WORLD_STATIC_VELOCITY.x)  {
                    body.applyForceToCenter(Constants.WORLD_STATIC_VELOCITY.x, 0, true);
                }

                if (getUserData().isStrike()||mAnimation==mStrikeAnimation) {
                    strikeTime += delta;

                    if (strikeTime>0.3f) {
                        if (mRunAnimation!=null) {
                            mAnimation = mRunAnimation;
                        } else if (mStayAnimation!=null) {
                            mAnimation = mStayAnimation;
                        }

                        stateTime = 0;
                        strikeTime = 0;
                    }
                }


                if (mJumpAnimation!=null&&(mAnimation!=mStrikeAnimation&&mAnimation!=mDieAnimation
                        &&mAnimation!=mJumpAnimation)&&body.getLinearVelocity().y!=0) {
                    mAnimation = mJumpAnimation;
                    stateTime = 0;
                    System.out.println(getUserData().enemyType.name() + " jumpAnim");
                }
            }

        }


    }

    private void start() {
        if (mRunAnimation!=null&&getUserData().getLinearVelocity().x<Constants.WORLD_STATIC_VELOCITY.x) {
            mAnimation = mRunAnimation;
            stateTime = 0;
            System.out.println(getUserData().enemyType.name() + " runAnim");
        }
        System.out.println(getUserData().enemyType.name() + " START");
        body.setLinearVelocity(body.getLinearVelocity().x + getUserData().enemyType.getBasicXVelocity(),
                body.getLinearVelocity().y);
        starting = true;

        if (((GameStage)getStage()).getRunner()!=null||!((GameStage)getStage()).getRunner().getUserData().isDead()) {
            ScoreCounter.increaseEnemies();
        }

    }

    private void shoot() {
        if (getUserData().isShouter()&&!getUserData().isShoot()) {

            if (mStrikeAnimation!=null) {
                mAnimation = mStrikeAnimation;
                strikeTime = 0;
                stateTime = 0;
                System.out.println(getUserData().enemyType.name() + " shootAnim");
            }

            System.out.println(getUserData().enemyType.name() + " SHOOT");
            getUserData().setShoot(true);
            if (mShellRegion!=null) {
                PoolsHandler.sShellPool.obtain().init(getStage(), body.getPosition()
                        , body.getLinearVelocity().x, mShellRegion, getUserData().getKEY());
            } else {
                PoolsHandler.sShellPool.obtain().init(getStage(), body.getPosition()
                        , body.getLinearVelocity().x, getUserData().getKEY());
            }
        }
    }

    private void strike() {
        if (getUserData().isStriker()&&!getUserData().isStrike()) {

            if (mStrikeAnimation!=null) {
                mAnimation = mStrikeAnimation;
                strikeTime = 0;
                stateTime = 0;
                System.out.println(getUserData().enemyType.name() + " strikeAnim");
            }

            System.out.println(getUserData().enemyType.name() + " STRIKE");
            getUserData().setStrike(true);

        }
    }

    private void jump() {
        if (!getUserData().isJumping()) {
            System.out.println(getUserData().enemyType.name() + " JUMP");
            body.applyLinearImpulse(jumpImpulse , body.getWorldCenter(), true);
            getUserData().setJumping(true);
        }
    }

    private void standing() {
        System.out.println(getUserData().enemyType.name() + " STAND");
        body.setLinearVelocity(new Vector2(Constants.WORLD_STATIC_VELOCITY.x, getUserData().getLinearVelocity().y));
        if (mStayAnimation!=null&&mAnimation!=mStrikeAnimation&&mAnimation!=mJumpAnimation&&mAnimation!=mStayAnimation) {
            mAnimation = mStayAnimation;
            System.out.println(getUserData().enemyType.name() + " stayAnim");
            stateTime = 0;
        }
    }

    public void dead(float delta) {
        deadTime +=delta;
        body.applyForceToCenter(500, 50, false);

        if (mDieAnimation!=null) {
            mAnimation = mDieAnimation;
            System.out.println(getUserData().enemyType.name() + " dieAnim");
            stateTime = 0;
        }


        if (deadTime>0.2f&&getStage()!=null) {
            GameStage stage = (GameStage) getStage();
            stage.removeMovable(this);
            stage.createLump(body, 4, AssetLoader.lumpsAtlas.findRegion("lump1"));
            this.remove();
            PoolsHandler.sEnemiesPools.get(getUserData().enemyType.name()).free(this);
        }

        System.out.println(getUserData().enemyType.name() + " DIE");
    }

    @Override
    public void reset() {
        if (getStage()!=null) {
            ((GameStage)getStage()).removeMovable(this);
        }
        stateTime = 0f;
        deadTime = 0f;
        strikeTime = 0f;
        getUserData().setDead(false);
        getUserData().setJumping(false);
        getUserData().setStrike(false);
        getUserData().setShoot(false);
        getUserData().setDoll(false);
        starting = false;
        body.setActive(false);
        body.setGravityScale(getUserData().enemyType.getGravityScale());
        body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY);
        body.setFixedRotation(true);
        body.setTransform(-10, -10, 0);
    }

    @Override
    public void changingStaticSpeed(float speedScale) {

        body.setLinearVelocity(body.getLinearVelocity().x
                + (speedScale-(mPreviousVelocity)), body.getLinearVelocity().y);
        mPreviousVelocity  = speedScale;
    }
}
