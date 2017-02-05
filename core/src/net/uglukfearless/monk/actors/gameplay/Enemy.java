package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;

import net.uglukfearless.monk.box2d.EnemyUserData;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.enums.EnemyStateFight;
import net.uglukfearless.monk.enums.EnemyStateMain;
import net.uglukfearless.monk.enums.EnemyStateMove;
import net.uglukfearless.monk.enums.EnemyType;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.utils.file.ScoreCounter;
import net.uglukfearless.monk.utils.file.SoundSystem;
import net.uglukfearless.monk.utils.gameplay.bodies.BodyUtils;
import net.uglukfearless.monk.utils.gameplay.Movable;
import net.uglukfearless.monk.utils.gameplay.Retributable;
import net.uglukfearless.monk.utils.gameplay.ai.Situation;
import net.uglukfearless.monk.utils.gameplay.ai.SpaceTable;
import net.uglukfearless.monk.utils.gameplay.bodies.WorldUtils;
import net.uglukfearless.monk.utils.gameplay.pools.PoolsHandler;

import java.util.Random;


/**
 * Created by Ugluk on 20.05.2016.
 */
public class Enemy extends GameActor implements Pool.Poolable, Movable, Retributable {

    private GameStage mStage;

    private Animation mAnimation;
    private Animation mStayAnimation;
    private Animation mRunAnimation;
    private Animation mJumpAnimation;
    private Animation mStrikeAnimation;
    private Animation mDieAnimation;

    private Animation mSelectedAnimation;

    private TextureRegion mShellRegion;

    private float stateTime;
    private float deadTime;
    private float strikeTime;
    private float shootPause;
    private final float SHOOTRATE;

    private Vector2 jumpImpulse;

    private Situation mSituation;
    private float mPreviousVelocity;

    public static Random mRand = new Random();

    EnemyStateMain mMainState;
    EnemyStateMove mMoveState;
    EnemyStateFight mFightState;

    float mCurrentSpeed;
    float mBasicSpeed;
    float mCanonSpeed;
    private boolean mCryed;


    public Enemy(World world, EnemyType enemyType) {
        super(WorldUtils.createEnemy(world, enemyType));


        mShellRegion = getUserData().getShellRegion();

        mStayAnimation = getUserData().getStayAnimation();
        mRunAnimation = getUserData().getRunAnimation();
        mJumpAnimation = getUserData().getJumpAnimation();
        mStrikeAnimation = getUserData().getStrikeAnimation();
        mDieAnimation = getUserData().getDieAnimation();

        if (mStayAnimation==null) {
            mStayAnimation = mRunAnimation;
        }

        mAnimation = getUserData().getAnimation();




        stateTime = 0f;
        deadTime = 0f;
        strikeTime = 0f;
        shootPause = 10f;
        SHOOTRATE = 1f;


        jumpImpulse = new Vector2(0, getUserData().getWidth()*getUserData().getHeight()
                *getUserData().getJumpingImpulse());

        mSituation = new Situation();

        mSelectedAnimation = mAnimation;
        mMainState = EnemyStateMain.WAIT;
        mMoveState = EnemyStateMove.STAY;
        mFightState = EnemyStateFight.FREE;
    }

    public void init(Stage stage, float x, float y) {

        mStage = (GameStage)stage;

        mCryed = false;

        getUserData().setDead(false);
        getUserData().setStruck(false);
        getUserData().setTerribleDeath(false);

        if (mStayAnimation!=null) {
            mAnimation = mStayAnimation;
        } else if (mRunAnimation!=null){
            mAnimation = mRunAnimation;
        }

        body.setTransform(x, getUserData().getBasicY() + y + getUserData().getHeight() / 2f, 0);
        body.setLinearVelocity(mStage.getCurrentVelocity());
        body.setActive(true);
        mStage.addActor(this);
        mPreviousVelocity = mStage.getCurrentVelocity().x;
        mStage.addMovable(this);
        mStage.addRetributable(this);

        mSelectedAnimation = mAnimation;
        mMainState = EnemyStateMain.WAIT;
        mMoveState = EnemyStateMove.STAY;
        mFightState = EnemyStateFight.FREE;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();

        EnemyUserData data = getUserData();

        batch.draw(mAnimation.getKeyFrame(stateTime, true),
                body.getPosition().x - (data.getWidth() * data.getScaleX() / 2) + data.getOffsetX(),
                body.getPosition().y - (data.getHeight() / 2) + data.getOffsetY(),
                getUserData().getWidth() * data.getScaleX() * 0.5f, getUserData().getHeight() * data.getScaleY() * 0.5f,
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

        switch (mMainState) {
            case DIE:
                dead(delta);
                break;
            case WAIT:
                if (mayStart()) {
                    start(true);
                }
                break;
            case ACTIVE:

                if (getUserData().isDead()) {
                    mMainState = EnemyStateMain.DIE;
                    break;
                } else if (!BodyUtils.bodyInFall(body)) {
                    mMainState = EnemyStateMain.DIE;
                    getUserData().setStruck(true);
                    break;
                 } else if (!BodyUtils.bodyInBounds(body)) {
                    this.remove();
                    PoolsHandler.sEnemiesPools.get(getUserData().getEnemyType().name()).free(this);
                    break;
                 }

                mSituation = SpaceTable.getSituation(body.getPosition().x, body.getPosition().y
                            - getUserData().getEnemyType().getY() - getUserData().getHeight() / 2f
                            , getUserData().getWidth(), getUserData().getEnemyType().getCategoryBit(), mSituation);

                if (shootPause<=SHOOTRATE) {
                    shootPause +=delta;
                }

                switch (mMoveState) {
                    case STAY:
                        fight(delta);
                        //ситуации движения
                        if (mSituation.jump) {
                            jump();
                        }

                        if (Math.abs(body.getLinearVelocity().y)>0.01&&body.getGravityScale()!=0) {
                            checkFall();
                        }

                        if (mSituation.startFly&&getUserData().isFly()) {
                            start(false);
                        } else if (mSituation.start&&!getUserData().isFly()) {
                            start(false);
                        }

                        //ситуации драки
                        if (mSituation.strike) {
                            strike();
                        } else if (mSituation.shoot) {
                            shoot();
                        }
                        break;
                    case RUN:
                        fight(delta);
                        //ситуации движения
                        if (mSituation.jump) {
                            jump();
                        }

                        if (Math.abs(body.getLinearVelocity().y)>0.01&&body.getGravityScale()!=0) {
                            checkFall();
                        }

                        if (mSituation.stopFly&&getUserData().isFly()&&body.getPosition().x<Constants.GAME_WIDTH) {
                            standing();
                        } else if (mSituation.stop&&!getUserData().isFly()&&body.getPosition().x<Constants.GAME_WIDTH) {
                            standing();
                        }

                        checkSpeed();

                        //ситуации драки
                        if (mSituation.strike) {
                            strike();
                        } else if (mSituation.shoot) {
                            shoot();
                        }
                        break;
                    case JUMP:
                        if (mSituation.strike) {
                            strike();
                        } else if (mSituation.shoot) {
                            shoot();
                        }

                        if (Math.abs(body.getLinearVelocity().y)<0.02&&body.getGravityScale()!=0) {
                            landed();
                        }
                        break;
                    case FALL:
                        if (mSituation.strike) {
                            strike();
                        } else if (mSituation.shoot) {
                            shoot();
                        }

                        if (Math.abs(body.getLinearVelocity().y)<0.02&&body.getGravityScale()!=0) {
                            landed();
                        }
                        break;
                }

                break;
        }

        selectAnimation();
    }

    private void selectAnimation() {
        switch (mMainState) {
            case DIE:
                mSelectedAnimation = mDieAnimation;
                break;
            case ACTIVE:
            case WAIT:
                if (mFightState!=EnemyStateFight.FREE) {
                    mSelectedAnimation = mStrikeAnimation;
                    break;
                }
                switch (mMoveState) {
                    case JUMP:
                    case FALL:
                        mSelectedAnimation = mJumpAnimation;
                        break;
                    case RUN:
                        mSelectedAnimation = mRunAnimation;
                        break;
                    case STAY:
                        mSelectedAnimation = mStayAnimation;
                        break;
                }
                break;
        }

        if (mSelectedAnimation!=null&&!mAnimation.equals(mSelectedAnimation)) {
            mAnimation = mSelectedAnimation;
            stateTime = 0;
        }
    }

    private void checkSpeed() {
        mCurrentSpeed = body.getLinearVelocity().x;
        mBasicSpeed = getUserData().getEnemyType().getBasicXVelocity();
        mCanonSpeed = mStage.getCurrentVelocity().x + mBasicSpeed;

        if (mMoveState==EnemyStateMove.RUN) {
            if (mBasicSpeed>0&&mCurrentSpeed<mCanonSpeed) {
                body.setLinearVelocity(mCurrentSpeed+0.2f, 0f);
            } else if (mBasicSpeed<0&&mCurrentSpeed>mCanonSpeed) {
                body.setLinearVelocity(mCurrentSpeed-0.2f, 0f);
            }
        }
    }

    private void checkFall() {
        if (mMoveState!=EnemyStateMove.JUMP) {
            mMoveState = EnemyStateMove.FALL;
        }
    }

    private void fight(float delta) {

        switch (mFightState) {
            case FREE:
                break;
            case STRIKE:
                strikeTime += delta;
                if (strikeTime>0.3f) {
                    mFightState = EnemyStateFight.FREE;
                    stateTime = 0;
                    strikeTime = 0;
                }
                break;
            case SHOOT:
                strikeTime += delta;
                if (strikeTime>0.3f) {
                    mFightState = EnemyStateFight.FREE;
                    stateTime = 0;
                    strikeTime = 0;
                }
                strikeTime += delta;
                break;
        }
    }

    private boolean mayStart() {
        return ((body.getPosition().x < Constants.GAME_WIDTH)
                ||(body.getPosition().x < Constants.GAME_WIDTH + mRand.nextInt(8) + 10
                &&body.getPosition().y - userData.getHeight()/2 < Constants.LAYOUT_Y_TWO*0.93f));
    }

    private void start(boolean first) {
        if (first||(mMoveState!=EnemyStateMove.JUMP&&mMoveState!=EnemyStateMove.FALL)) {

            if (getUserData().getEnemyType().getBasicXVelocity()==0) {
                mMoveState = EnemyStateMove.STAY;
            } else {
                mMoveState = EnemyStateMove.RUN;
            }
            body.setLinearVelocity(mStage.getCurrentVelocity().x + getUserData().getEnemyType().getBasicXVelocity(),
                    body.getLinearVelocity().y);

            if ((mStage.getRunner()!=null||!mStage.getRunner().getUserData().isDead())&&first) {
                ScoreCounter.increaseEnemies();
            }

            mMainState = EnemyStateMain.ACTIVE;
        }

    }


    private void landed() {

        if (body.getLinearVelocity().x - mStage.getCurrentVelocity().x==0) {
            mMoveState = EnemyStateMove.STAY;
        } else {
            mMoveState = EnemyStateMove.RUN;
        }
    }

    private void shoot() {

        if (mFightState==EnemyStateFight.FREE&&getUserData().getEnemyType().isShouter()&&shootPause>SHOOTRATE) {


            getUserData().setShoot(true);
            if (mShellRegion!=null) {
                PoolsHandler.sShellPool.obtain().init(mStage, body.getPosition()
                        , body.getLinearVelocity().x, mShellRegion, getUserData().getKEY());
            } else {
                PoolsHandler.sShellPool.obtain().init(mStage, body.getPosition()
                        , body.getLinearVelocity().x, getUserData().getKEY());
            }

            mFightState = EnemyStateFight.SHOOT;
            shootPause = 0;
        }
    }

    private void strike() {

        if (mFightState==EnemyStateFight.FREE&&getUserData().getEnemyType().isStriker()) {
            getUserData().setStrike(true);
            mFightState = EnemyStateFight.STRIKE;
        }
    }

    private void jump() {
        if (getUserData().getEnemyType().isJumper()) {
            body.applyLinearImpulse(jumpImpulse , body.getWorldCenter(), true);
            getUserData().setJumping(true);

            mMoveState = EnemyStateMove.JUMP;
        }
    }

    private void standing() {
        if (mMoveState!=EnemyStateMove.JUMP&&mMoveState!=EnemyStateMove.FALL) {
            body.setLinearVelocity(new Vector2(mStage.getCurrentVelocity().x, getUserData().getLinearVelocity().y));
            mMoveState = EnemyStateMove.STAY;
        }
    }

    public void dead(float delta) {
        deadTime +=delta;

        if (getUserData().isDemon()&&!getUserData().isStruck()) {
            body.setFixedRotation(true);

            if (body.getPosition().x>Constants.RUNNER_X) {
                body.applyForceToCenter(1000*getUserData().getWidth()*getUserData().getHeight(), 0, false);
            }

            if (getUserData().getEnemyType().getGravityScale()==0) {
                body.setGravityScale(0);
            }

        } else if (!mCryed){
            playDeathSound();
            mCryed = true;
        }
        body.applyForceToCenter(500, 50, false);

        if ((deadTime>0.2f||getUserData().isTerribleDeath())&&getStage()!=null) {
            if (getUserData().isDemon()&&!getUserData().isStruck()&&!getUserData().isTerribleDeath()) {
                getUserData().setDead(false);
                getUserData().setStruck(true);
                body.setTransform(body.getPosition(), 0);
                body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY);
                mMainState = EnemyStateMain.ACTIVE;
                mMoveState = EnemyStateMove.RUN;
                start(false);
            } else {
                //exp
                if (AssetLoader.sFreeParticleBlood.size>0) {
                    ParticleEffect effect = AssetLoader.sFreeParticleBlood.get(AssetLoader.sFreeParticleBlood.size -1);
                    AssetLoader.sFreeParticleBlood.removeIndex(AssetLoader.sFreeParticleBlood.size -1);
                    effect.getEmitters().first().setPosition(body.getPosition().x, body.getPosition().y);
                    effect.start();
                    AssetLoader.sWorkParticleBlood.add(effect);
                }

                GameStage stage = (GameStage) getStage();
                stage.removeMovable(this);
                stage.removeRetributable(this);
                stage.createLump(body, 4, AssetLoader.lumpsAtlas.findRegion("lump1"));
                PoolsHandler.sEnemiesPools.get(getUserData().getEnemyType().name()).free(this);
                this.remove();

                ScoreCounter.increaseScore(1);
                ScoreCounter.increaseKilled(getUserData().getKEY());
            }


        }

    }

    private void playDeathSound() {
        AssetLoader.enemyDeathSounds.get(mRand.nextInt(AssetLoader.enemyDeathSounds.size)).play(SoundSystem.getSoundValue());
    }

    @Override
    public void reset() {
        mCryed = false;
        if (getStage()!=null) {
            ((GameStage)getStage()).removeMovable(this);
            ((GameStage)getStage()).removeRetributable(this);
        }
        stateTime = 0f;
        deadTime = 0f;
        strikeTime = 0f;
        getUserData().setDead(false);
        getUserData().setJumping(false);
        getUserData().setStrike(false);
        getUserData().setShoot(false);
        getUserData().setDoll(false);
        getUserData().setLaunched(false);
        getUserData().setStruck(false);
        getUserData().setTerribleDeath(false);

        body.setActive(false);
        body.setAngularVelocity(0f);
        body.setGravityScale(getUserData().getEnemyType().getGravityScale());
        body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY);
        body.setFixedRotation(true);
        body.setTransform(-10, -10, 0);


        mMainState = EnemyStateMain.WAIT;
        mMoveState = EnemyStateMove.STAY;
        mFightState = EnemyStateFight.FREE;
    }

    @Override
    public void changingStaticSpeed(float speedScale) {

        body.setLinearVelocity(body.getLinearVelocity().x
                + (speedScale-(mPreviousVelocity)), body.getLinearVelocity().y);
        mPreviousVelocity  = speedScale;
    }

    @Override
    public void punish(int retributionLevel) {
        switch (retributionLevel) {
            case 0:
            case 1:
            case 2:
                if (getBody().getPosition().x<Constants.GAME_WIDTH&&!getUserData().isDead()) {
                    getUserData().setDead(true);
                }
                break;
            case 3:
                getUserData().setDead(true);
                getUserData().setStruck(true);
                getUserData().setTerribleDeath(true);
                break;
        }
    }
}
