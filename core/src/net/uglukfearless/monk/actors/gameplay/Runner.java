package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Filter;

import net.uglukfearless.monk.box2d.EnemyUserData;
import net.uglukfearless.monk.box2d.RunnerUserData;
import net.uglukfearless.monk.box2d.ShellUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.FilterConstants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.enums.ArmourType;
import net.uglukfearless.monk.enums.GameState;
import net.uglukfearless.monk.enums.RunnerState;
import net.uglukfearless.monk.enums.WeaponType;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.SoundSystem;
import net.uglukfearless.monk.utils.gameplay.bodies.BodyUtils;
import net.uglukfearless.monk.utils.gameplay.CircularList;
import net.uglukfearless.monk.utils.gameplay.pools.PoolsHandler;

import java.util.Random;

/**
 * Created by Ugluk on 18.05.2016.
 */
public class Runner extends GameActor {


    private static final float STRIKE_DURATION = 0.2f;
    private RunnerUserData data;

    private float stateTime;
    private float strikeTime;
    private float deadTime;
    private float comboTime;

    private boolean doubleStrike;

    private Animation mAnimStay;
    private Animation mAnimRun;
    private Animation mAnimJump;
    private Animation mAnimDie;

    private CircularList<Animation> mCurrentStrikeList;

    private Animation mCurrentAnimation;

    private Ground mGround1, mGround2;

    private Color mColor;
    private float mAlpha;

    private boolean mWings;
//    private float mWingsTimer;
//    private float mWingsThreshold;

    private boolean mRetribution;
    private int mRetributionLevel;

    private boolean mThunderFist;
    private int mThunderFistLevel;
    private boolean mBuddha;

    private float mBuddhaTimer;
    private float mBuddhaThreshold;

    private String mCurrentKillerKey;
    private boolean mUseBuddhaThreshold;
    private boolean mGhost;
    private boolean mStrongBeat;
    private Random mRand;

    private boolean mArmour;
    private Filter mCustomFilter;
    private Filter mRevivalFilter;
    private Armour mArmourActor;

    private int mWingsRevival;
    private boolean mWingsLanding;

    private WeaponType mWeaponType;

    private ShaderProgram mBuddhaShader;


    public Runner(Body body) {
        super(body);
        stateTime = 0f;
        data = (RunnerUserData) userData;

        mAnimStay = AssetLoader.playerStay;
        mAnimRun = AssetLoader.playerRun;
        mAnimJump = AssetLoader.playerJump;
        mAnimDie = AssetLoader.playerHit;

        setCurrentAnimation(mAnimStay, true);

        mAlpha = 1f;
        mWings = false;
//        mWingsTimer = mWingsThreshold + 1;
//        mWingsThreshold = 2;

        mRetribution = false;
        mBuddhaTimer = 0;
        mBuddhaThreshold = 0;
        mUseBuddhaThreshold = false;

        mArmour = false;

        mCustomFilter = FilterConstants.FILTER_RUNNER;
        mRevivalFilter = FilterConstants.FILTER_RUNNER_GHOST;

        mWingsRevival = 0;
        mWingsLanding = false;

        mWeaponType = PreferencesManager.getWeapon();
        changeClothes(PreferencesManager.getArmour());

        mRand = new Random();

        setupShader();
    }

    private void setupShader() {
        String VERTEX = Gdx.files.internal("shader/vert1.glsl").readString();
        String FRAGMENT = Gdx.files.internal("shader/frag1.glsl").readString();

        //отключаем стандартный набор атрибутов
        ShaderProgram.pedantic = false;

        mBuddhaShader = new ShaderProgram(VERTEX, FRAGMENT);

        if (!mBuddhaShader.isCompiled()) {
            System.err.println(mBuddhaShader.getLog());
            System.exit(0);
        }

        if (mBuddhaShader.getLog().length()!=0) {
            System.out.println(mBuddhaShader.getLog());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        float x = body.getPosition().x - (data.getWidth()*0.1f) - data.getWidth()/2;
        float y = body.getPosition().y - data.getHeight()/2;

        mColor = batch.getColor();
        batch.setColor(mColor.r,mColor.g,mColor.b, mAlpha);

        stateTime += ((GameStage) getStage()).getDeltaTime();

        if (isBuddha()) {
            float color_value = getColorValue();
            mBuddhaShader.begin();
            mBuddhaShader.setUniformf("ourColor", color_value, color_value, 0f, 1.0f);
            mBuddhaShader.end();
            batch.setShader(mBuddhaShader);
            batch.setColor(mColor.r,mColor.g,mColor.b, 0.2f);
            batch.draw((TextureRegion) mCurrentAnimation.getKeyFrame(stateTime),
                    x - 0.6f,
                    y,
                    getUserData().getWidth()  * 0.5f, getUserData().getHeight()  * 0.5f,
                    data.getWidth() *3.2f, data.getHeight() * 1.84f
                    , 1f, 1f, (float) Math.toDegrees(body.getAngle()));
            batch.setColor(mColor.r,mColor.g,mColor.b, 0.4f);
            batch.draw((TextureRegion) mCurrentAnimation.getKeyFrame(stateTime),
                    x - 0.3f,
                    y,
                    getUserData().getWidth()  * 0.5f, getUserData().getHeight()  * 0.5f,
                    data.getWidth() *3.2f, data.getHeight() * 1.84f
                    , 1f, 1f, (float) Math.toDegrees(body.getAngle()));

            batch.setColor(mColor.r,mColor.g,mColor.b, mAlpha);
            batch.draw((TextureRegion) mCurrentAnimation.getKeyFrame(stateTime),
                    x,
                    y,
                    getUserData().getWidth()  * 0.5f, getUserData().getHeight()  * 0.5f,
                    data.getWidth() *3.2f, data.getHeight() * 1.84f
                    , 1f, 1f, (float) Math.toDegrees(body.getAngle()));

            batch.setShader(null);
        } else {
            batch.setColor(mColor.r,mColor.g,mColor.b, mAlpha);
            batch.draw((TextureRegion) mCurrentAnimation.getKeyFrame(stateTime),
                    x,
                    y,
                    getUserData().getWidth()  * 0.5f, getUserData().getHeight()  * 0.5f,
                    data.getWidth() *3.2f, data.getHeight() * 1.84f
                    , 1f, 1f, (float) Math.toDegrees(body.getAngle()));
        }


        batch.setColor(mColor);

    }

    private float getColorValue() {
        return (float) ((0.1f*(Math.sin(30*stateTime + Math.random()) / 2f) + 0.05f) + 0.9f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (data.getState().equals(RunnerState.THE_DRAGON)) {
            body.setTransform(Constants.RUNNER_X, ((GameStage) getStage()).getDragon().getDragonY(), 0);
        } else {

            if (!data.isDead()) {
                body.setLinearVelocity(0, body.getLinearVelocity().y);
                if (!body.getWorld().isLocked()) {
                    float x = body.getPosition().x - data.getWidth() / 2;
                    if (x > mGround1.getBody().getPosition().x
                            && x < mGround1.getBody().getPosition().x + Constants.GROUND_WIDTH_INIT / 2
                            && x > mGround2.getBody().getPosition().x - Constants.GROUND_WIDTH_INIT / 2 - data.getWidth() * 1.5f) {
                        body.setTransform(Constants.RUNNER_X, body.getPosition().y, 0);
                    }
                }

                if (body.getPosition().y > 15) {
                    body.setLinearVelocity(body.getLinearVelocity().x, 0);
                }

                if (mBuddha) {
                    mBuddhaTimer += delta;
                    if (mBuddhaTimer > mBuddhaThreshold && !mUseBuddhaThreshold) {

                        ((GameStage) getStage()).changingSpeed(((GameStage) getStage()).getCurrentVelocity().x
                                / Constants.BUDDHA_SPEED_SCALE);
                        mUseBuddhaThreshold = true;
                    }

                }

//                if (mWingsTimer<mWingsThreshold) {
//                    mWingsTimer += delta;
//                }
//
//                mWings = mWingsTimer<mWingsThreshold;

                if (BodyUtils.runnerIsFallDown(body)) {
                    if (mWings) {
                        body.setTransform(Constants.RUNNER_X, Constants.RUNNER_Y, 0);
                        ((GameStage) getStage()).startRebutRunner();
                    } else if (mWingsLanding && startGround()) {
                        body.setTransform(Constants.RUNNER_X, Constants.RUNNER_Y, 0);
                        ((GameStage) getStage()).startRebutRunner();
                        mWingsLanding = false;
                        System.out.println("Крылья сработали!");
                    } else if (mWingsRevival > 0 && !mWingsLanding) {
                        mWingsRevival--;
                        mWingsLanding = true;
                        ((GameStage) getStage()).checkWingsRevival(mWingsRevival);
//                        mWings = true;
//                        mWingsTimer = 0;
                    } else if (!mWingsLanding) {
                        hit(PreferencesConstants.STATS_CRASHED_DEATH_KEY);
                        if (mArmour) {
                            if (mArmourActor != null) {
                                mArmourActor.getUserData().destroyArmour();
                            }
                            unarmoured();
                        }
                    }

                } else if (BodyUtils.runnerIsBehind(body)) {
                    if (mWings) {
                        body.setTransform(Constants.RUNNER_X, Constants.RUNNER_Y, 0);
                        ((GameStage) getStage()).startRebutRunner();
                    } else if (mWingsLanding && startGround()) {
                        body.setTransform(Constants.RUNNER_X, Constants.RUNNER_Y, 0);
                        ((GameStage) getStage()).startRebutRunner();
                        mWingsLanding = false;
                        System.out.println("Крылья сработали!");
                    } else if (mWingsRevival > 0 && !mWingsLanding) {
                        mWingsRevival--;
                        mWingsLanding = true;
                        ((GameStage) getStage()).checkWingsRevival(mWingsRevival);
                    } else if (!mWingsLanding) {
                        hit(PreferencesConstants.STATS_CRASHED_DEATH_KEY);
                        if (mArmour) {
                            if (mArmourActor != null) {
                                mArmourActor.getUserData().destroyArmour();
                            }
                            unarmoured();
                        }
                    }
                }
            }

            switch (data.getState()) {
                case DIE:
                    dead(delta);
                    if (mBuddha&&!mUseBuddhaThreshold) {
                        ((GameStage) getStage()).changingSpeed(((GameStage) getStage()).getCurrentVelocity().x/Constants.BUDDHA_SPEED_SCALE);
                        mBuddha = false;
                    }
                    break;
                case JUMP:
                case JUMP_DOUBLE:
                    if (comboTime < 0.3) {
                        comboTime += delta;
                    } else {
                        mCurrentStrikeList.resetIndex();
                    }
                    break;
                case RUN:
                    if (!body.getWorld().isLocked()) {
                        float x = body.getPosition().x - data.getWidth() / 2;
                        if (x > mGround1.getBody().getPosition().x - Constants.GROUND_WIDTH_INIT / 2
                                && x < mGround1.getBody().getPosition().x + Constants.GROUND_WIDTH_INIT / 2) {
                            body.setTransform(Constants.RUNNER_X, body.getPosition().y, 0);
                        }
                    }
                    if (comboTime < 0.3) {
                        comboTime += delta;
                    } else {
                        mCurrentStrikeList.resetIndex();
                    }
                    break;
                case RUN_STRIKE:
                    strikeTime += delta;
                    if (strikeTime > STRIKE_DURATION) {
                        if (doubleStrike) {
                            setCurrentAnimation(mCurrentStrikeList.getNext(), false);
                            playStrike();
                            checkBonusStrike();
                            doubleStrike = false;
                            strikeTime = 0f;
                        } else {
                            data.setState(RunnerState.RUN);
                            setCurrentAnimation(mAnimRun, true);
                            comboTime = 0;
                        }
//                        stateTime = 0;
                        strikeTime = 0;
                    }
                    break;
                case JUMP_STRIKE:
                    strikeTime += delta;
                    if (strikeTime > STRIKE_DURATION) {
                        if (doubleStrike) {
                            setCurrentAnimation(mCurrentStrikeList.getNext(), false);
                            playStrike();
                            checkBonusStrike();
                            doubleStrike = false;
                            strikeTime = 0f;
                        } else {
                            data.setState(RunnerState.JUMP);
                            mCurrentAnimation = mAnimJump;
                            comboTime = 0;
                        }
//                        stateTime = 0;
                        strikeTime = 0;
                    }
                    break;
                case JUMP_DOUBLE_STRIKE:
                    strikeTime += delta;
                    if (strikeTime > STRIKE_DURATION) {
                        if (doubleStrike) {
                            setCurrentAnimation(mCurrentStrikeList.getNext(), false);
                            playStrike();
                            checkBonusStrike();
                            doubleStrike = false;
                            strikeTime = 0f;
                        } else {
                            data.setState(RunnerState.JUMP_DOUBLE);
                            mCurrentAnimation = mAnimJump;
                            comboTime = 0;
                        }
                        data.setState(RunnerState.JUMP_DOUBLE);
                        mCurrentAnimation = mAnimJump;
//                        stateTime = 0;
                        strikeTime = 0;
                    }
                    break;
            }

            if (data != null && data.isDestroy()) {
                if (!body.getWorld().isLocked()) {
                    body.getWorld().destroyBody(body);
                }
            }

        }
    }

    private boolean startGround() {
        return ((mGround1.getPosition().x - mGround1.getWidth()/2f<0.3f&&mGround1.getPosition().x - mGround1.getWidth()/2f>-1f)
                ||(mGround2.getPosition().x - mGround2.getWidth()/2f<0.3f&&mGround2.getPosition().x - mGround2.getWidth()/2f>-1f));
    }

    private void dead(float delta) {
        deadTime +=delta;
        if (deadTime>0.3f&&getStage()!=null) {
            disarm();

            if (AssetLoader.sFreeParticleBlood.size>0) {
                ParticleEffect effect = AssetLoader.sFreeParticleBlood.get(AssetLoader.sFreeParticleBlood.size -1);
                AssetLoader.sFreeParticleBlood.removeIndex(AssetLoader.sFreeParticleBlood.size -1);
                effect.getEmitters().first().setPosition(body.getPosition().x, body.getPosition().y);
                effect.start();
                AssetLoader.sWorkParticleBlood.add(effect);
            }

            ((GameStage)getStage()).deactivationBonuses();
            GameStage stage = (GameStage) getStage();
            stage.createLump(body, 4, AssetLoader.lumpsAtlas.findRegion("lump1"));
            ((UserData)body.getUserData()).setDestroy(true);
            ((GameStage) getStage()).setWingsBuffer(mWingsRevival);
            ((GameStage) getStage()).gameOver(mCurrentKillerKey);
            this.remove();

            mBuddhaShader.dispose();
        }
    }

    private void disarm() {
        PreferencesManager.clearWeapon();
        mWeaponType = null;
        changeClothes(mArmourActor.getUserData().getType());
    }

    @Override
    public RunnerUserData getUserData() {
        return (RunnerUserData) userData;
    }

    public void jump() {

        switch (data.getState()) {
            case DIE:
                break;
            case JUMP_DOUBLE:
            case JUMP_DOUBLE_STRIKE:
                if (mWings&&body.getPosition().y<15) {
                    body.applyLinearImpulse(data.getJumpingLinearImpulseOne()
                            , body.getWorldCenter(), true);
                }
                break;
            case RUN:
                data.setState(RunnerState.JUMP);
                body.applyLinearImpulse(data.getJumpingLinearImpulse()
                        , body.getWorldCenter(), true);
                setCurrentAnimation(mAnimJump, false);
                break;
            case RUN_STRIKE:
                body.applyLinearImpulse(data.getJumpingLinearImpulse()
                        , body.getWorldCenter(), true);
                data.setState(RunnerState.JUMP_STRIKE);
                break;
            case JUMP:
                data.setState(RunnerState.JUMP_DOUBLE);
                body.applyLinearImpulse(data.getJumpingLinearImpulse()
                        , body.getWorldCenter(), true);
                break;
            case JUMP_STRIKE:
                data.setState(RunnerState.JUMP_DOUBLE_STRIKE);
                body.applyLinearImpulse(data.getJumpingLinearImpulse()
                        , body.getWorldCenter(), true);
                break;
        }
    }

    public void landed() {

        switch (data.getState()) {
            case DIE:
            case RUN:
            case RUN_STRIKE:
                break;
            case JUMP_STRIKE:
            case JUMP_DOUBLE_STRIKE:
                data.setState(RunnerState.RUN_STRIKE);
            case JUMP:
            case JUMP_DOUBLE:
                data.setState(RunnerState.RUN);
                setCurrentAnimation(mAnimRun, true);
                break;
        }
    }


    public void strike() {
        switch (data.getState()) {
            case DIE:
                break;
            case RUN_STRIKE:
                doubleStrike = true;
                break;
            case RUN:
                data.setState(RunnerState.RUN_STRIKE);
                playStrike();
                if (comboTime>0.3) {
//                    mCurrentStrikeList.setIndex(mRand.nextInt(2)*3);
                    mCurrentStrikeList.setIndex(mRand.nextInt(mCurrentStrikeList.size));
                }
                setCurrentAnimation(mCurrentStrikeList.getNext(), false);
                strikeTime = 0f;
                checkBonusStrike();
                break;
            case JUMP_STRIKE:
                doubleStrike = true;
                break;
            case JUMP:
                data.setState(RunnerState.JUMP_STRIKE);
                playStrike();
                if (comboTime>0.3) {
//                    mCurrentStrikeList.setIndex(mRand.nextInt(2)*3);
                    mCurrentStrikeList.setIndex(mRand.nextInt(mCurrentStrikeList.size));
                }
                setCurrentAnimation(mCurrentStrikeList.getNext(), false);
                strikeTime = 0f;
                checkBonusStrike();
                break;
            case JUMP_DOUBLE_STRIKE:
                doubleStrike = true;
                break;
            case JUMP_DOUBLE:
                data.setState(RunnerState.JUMP_DOUBLE_STRIKE);
                playStrike();
                if (comboTime>0.3) {
//                    mCurrentStrikeList.setIndex(mRand.nextInt(2)*3);
                    mCurrentStrikeList.setIndex(mRand.nextInt(mCurrentStrikeList.size));
                }
                setCurrentAnimation(mCurrentStrikeList.getNext(), false);
                strikeTime = 0f;
                checkBonusStrike();
                break;
        }
    }

    private void checkBonusStrike() {
        if (mRetribution) {
            ((GameStage)getStage()).retribution(mRetributionLevel);
        } else if (mThunderFist) {
            PoolsHandler.sRunnerShellPool.obtain().init(getStage(), body.getPosition(), mThunderFistLevel);
        }
    }

    private void playStrike() {
        if (mWeaponType==null) {
            AssetLoader.monkStrikeSounds.get(mRand.nextInt(AssetLoader.monkStrikeSounds.size)).play(SoundSystem.getSoundValue());
        } else {
            AssetLoader.monkWeaponStrikeSounds.get(mRand.nextInt(AssetLoader.monkWeaponStrikeSounds.size)).play(SoundSystem.getSoundValue());
        }

    }

    public void hit(String key) {
        mCurrentKillerKey = key;
        setCurrentAnimation(mAnimDie, false);
        body.setFixedRotation(false);
        body.applyAngularImpulse(data.getHitAngularImpulse(), true);
        getUserData().setState(RunnerState.DIE);
        ((GameStage) getStage()).prepareTransition(255, 255, 255, .2f);
        ((GameStage) getStage()).screenShake(0.1f, 0.2f);
//        AssetLoader.deathSound.play(SoundSystem.getSoundValue());

        if (mAlpha<1) {
            System.out.println("CurrentFilter " + mCustomFilter);
            System.out.println("RevivalFilter " + mRevivalFilter);
            System.out.println("isGhost " + isGhost());
            System.out.println("isBuddha " + isBuddha());
            System.out.println("isArmour " + isArmour());
            System.out.println("isDragon " + isDragon());
        }
    }


    public void start() {
        setCurrentAnimation(mAnimRun, true);
        getUserData().setState(RunnerState.RUN);
    }

    public void setGrounds(Ground ground1, Ground ground2) {
        mGround1 = ground1;
        mGround2 = ground2;
    }

    public void setAlpha(float alpha) {
        mAlpha = alpha;
    }

    public void setWings(boolean wings) {
        mWings = wings;
        if (mArmour) {
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_WINGS_GHOST);
        } else {
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_WINGS);
        }
    }

    public void setRetribution(boolean retribution, int level) {
        mRetribution = retribution;
        mRetributionLevel = level;
    }

    public void setThunderFist(boolean thunderFist, int level) {
        mThunderFist = thunderFist;
        mThunderFistLevel = level;
    }

    public void setBuddha(boolean buddha) {
        mBuddha = buddha;
        mUseBuddhaThreshold = false;
        if (!buddha) {
            mBuddhaTimer=0;
            if (mArmour) {
                mArmourActor.unhide();
            }
        } else if (mArmour) {
            mArmourActor.hide();
        }
    }

    public boolean isBuddha() {
        return mBuddha;
    }

    public void setBuddha(boolean buddha, float workingTime, float speed) {
        mBuddha = buddha;
        mUseBuddhaThreshold = false;
        mBuddhaThreshold = workingTime*0.8f;
        System.out.println("runner set buddha change speed " + speed);
        ((GameStage) getStage()).changingSpeed(speed);
        if (!buddha) {
            mBuddhaTimer=0;
            if (mArmour) {
                mArmourActor.unhide();
            }
        } else if (mArmour){
            mArmourActor.hide();
        }
    }

    public boolean isUseBuddhaThreshold() {
        return mUseBuddhaThreshold;
    }

    public String getCurrentKillerKey() {
        return mCurrentKillerKey;
    }

    public void setGhost(boolean ghost) {
        mGhost = ghost;
        if (!ghost&&mArmour) {
            mArmourActor.unhide();
        } else if (mArmour) {
            mArmourActor.hide();
        }
    }

    public boolean isGhost() {
        return mGhost;
    }

    public void setStrongBeat(boolean strongBeat) {
        mStrongBeat = strongBeat;
    }

    public void beatBody(Body bBody, Contact contact, boolean enemy, boolean obstacle, boolean shell, boolean close) {

        bBody.setFixedRotation(false);
        if (!mStrongBeat) {
            bBody.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY_DEAD);
            if (bBody.getGravityScale()==0) {
                bBody.setGravityScale(10);
            }
            if (enemy&&mWeaponType!=null&&close) {
                ((EnemyUserData) bBody.getUserData()).setStruck(true);
            }
        } else if (bBody!=null&&((GameStage)getStage()).getRunnerStrike()!=null) {

            float coff = -1;
            Vector2 velocityBBody = bBody.getLinearVelocityFromWorldPoint(contact.getWorldManifold().getPoints()[0]);
            Vector2 velocityRunnerStrike = ((GameStage)getStage()).getRunnerStrike().getBody()
                        .getLinearVelocityFromWorldPoint(contact.getWorldManifold().getPoints()[0]);
            Vector2 impactVelocity = velocityBBody.add(velocityRunnerStrike.x * -1, velocityRunnerStrike.y * -1);


            if (bBody.getPosition().x>body.getPosition().x) {
                coff=-1;
            } else {
                coff=1;
            }

            if (shell) {
                coff*=2;
                ((ShellUserData) bBody.getUserData()).setStrong(true);
            }

            bBody.setLinearVelocity(coff*4f * impactVelocity.x, 2.5f * impactVelocity.y);
            bBody.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_ENEMY_STRIKE_FLIP);
            ((UserData)bBody.getUserData()).setLaunched(true);
            if (bBody.getGravityScale()==0) {
                bBody.setGravityScale(3);
            }

            if (enemy) {
                ((EnemyUserData) bBody.getUserData()).setStruck(true);
            }

            ((GameStage) getStage()).screenShake(0.1f, 0.15f);
        }
    }

    public boolean isStrongBeat() {
        return mStrongBeat;
    }

    public boolean isWings() {
        return mWings;
    }

    public boolean isArmour() {
        return mArmour;
    }

    public void armouring(Armour armour) {
        mArmour = true;
        mCustomFilter = FilterConstants.FILTER_RUNNER_GHOST;
        mRevivalFilter = FilterConstants.FILTER_RUNNER_GHOST;
        setCustomFilter();
        mArmourActor = armour;
        changeClothes(armour.getUserData().getType());
    }

    public void armouring(ArmourType armourType) {
        if (armourType!=null) {
            mArmour = true;
            mCustomFilter = FilterConstants.FILTER_RUNNER_GHOST;
            mRevivalFilter = FilterConstants.FILTER_RUNNER_GHOST;
            setCustomFilter();
            mArmourActor.activate(armourType);
            changeClothes(mArmourActor.getUserData().getType());
            getStage().addActor(mArmourActor);
            mArmourActor.setRunner(this);
            if (!isGhost()&&!isBuddha()&&!isDragon()) {
                mArmourActor.unhide();
            }
        } else {
            changeClothes(null);

            mArmour = false;
            mCustomFilter = FilterConstants.FILTER_RUNNER;
            mRevivalFilter = FilterConstants.FILTER_RUNNER_GHOST;

            mArmourActor.hide();

            setCustomFilter();
        }
    }

    public void unarmoured() {

        changeClothes(null);

        mArmour = false;
        mCustomFilter = FilterConstants.FILTER_RUNNER;
        mRevivalFilter = FilterConstants.FILTER_RUNNER_GHOST;

        if (getStage()!=null) {
            ((GameStage) getStage()).createLump(body, 4, AssetLoader.lumpsAtlas.findRegion("lump2"));
        }
    }

    public void changeClothes(ArmourType armourType) {

        AssetLoader.loadMonkAnimations(armourType, mWeaponType);

            if (mCurrentAnimation==mAnimStay) {
                replaceAnimation(AssetLoader.playerStay, true);
            } else if (mCurrentAnimation==mAnimRun) {
                replaceAnimation(AssetLoader.playerRun, true);
            } else if (mCurrentAnimation==mAnimJump) {
                replaceAnimation(AssetLoader.playerJump, false);
            }else if (mCurrentAnimation==mAnimDie) {
                replaceAnimation(AssetLoader.playerHit, false);
            }

            mAnimStay = AssetLoader.playerStay;
            mAnimRun = AssetLoader.playerRun;
            mAnimJump = AssetLoader.playerJump;
            mAnimDie = AssetLoader.playerHit;

            mCurrentStrikeList = AssetLoader.playerStrikeList;
    }

    public void setCustomFilter() {
        if (mArmour&&!isGhost()&&!isDragon()&&!isBuddha()&&mArmourActor!=null) {
            if (mWings) {
                body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_WINGS_GHOST);
                mArmourActor.unhide();
            } else {
                mArmourActor.unhide();
            }

        } if (!isGhost()&&!isDragon()&&!isBuddha()) {
            if (mWings&&!mArmour) {
                body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_WINGS);

            } else if (!getUserData().isDead()&&body!=null&&body.getFixtureList().size>0) {
                body.getFixtureList().get(0).setFilterData(mCustomFilter);
            }
        }

    }

    public void setRevivalFilter() {
        if (mArmour) {
            mArmourActor.getUserData().setOnlyHit(true);
        }
        if (mWings) {
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_WINGS_GHOST);
        } else {
            body.getFixtureList().get(0).setFilterData(mRevivalFilter);
        }
    }

    public void addWingsRevival() {
        mWingsRevival++;
        ((GameStage) getStage()).checkWingsRevival(mWingsRevival);
    }


    public void setWingsRevival(int wingsRevival) {
        mWingsRevival = wingsRevival;
    }

    public int getWingsRevival() {
        return mWingsRevival;
    }

    public WeaponType getWeaponType() {
        return mWeaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        mWeaponType = weaponType;
        if (mArmour) {
            changeClothes(mArmourActor.getUserData().getType());
        } else {
            changeClothes(null);
        }

    }

    public void setArmour(Armour armour) {
        mArmourActor = armour;
    }

    public boolean isDragon() {
        return data.getState()==RunnerState.THE_DRAGON;
    }

    public void setDragon(boolean dragon) {
        if (dragon) {
            ((GameStage)getStage()).retributionSilence(2);
            data.setState(RunnerState.THE_DRAGON);
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_GHOST);
            body.setGravityScale(0);
            if (mArmour) {
                mArmourActor.hide();
            }
            setAlpha(0f);
        } else {
            if (!data.isDead()&&((GameStage)getStage()).getState()==GameState.RUN) {
                data.setState(RunnerState.RUN);
                ((GameStage)getStage()).retributionSilence(2);
            }
            setCurrentAnimation(mAnimRun, true);
            body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
            body.getFixtureList().get(0).setFilterData(FilterConstants.FILTER_RUNNER_WINGS_GHOST);
            ((GameStage) getStage()).setReturnFilter(true);
            setAlpha(1f);
        }
    }

    public void stop() {
        switch (data.getState()) {
            case DIE:
            case STAY:
                break;
            default:
                data.setState(RunnerState.STAY);
                setCurrentAnimation(mAnimStay, true);
                stateTime = 0;
                break;
        }
    }

    public void setCurrentAnimation(Animation animation, boolean loop) {
        replaceAnimation(animation, loop);
        stateTime = 0;
    }

    public void replaceAnimation(Animation animation, boolean loop) {
        mCurrentAnimation = animation;
        if (loop) {
            mCurrentAnimation.setPlayMode(Animation.PlayMode.LOOP);
        } else {
            mCurrentAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        }
    }


}
