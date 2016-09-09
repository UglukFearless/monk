package net.uglukfearless.monk.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

import net.uglukfearless.monk.actors.gameplay.Ground;
import net.uglukfearless.monk.box2d.RunnerUserData;
import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.RunnerState;
import net.uglukfearless.monk.stages.GameStage;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.ScoreCounter;
import net.uglukfearless.monk.utils.file.SoundSystem;

/**
 * Created by Ugluk on 18.05.2016.
 */
public class Runner extends net.uglukfearless.monk.actors.gameplay.GameActor {


    private RunnerUserData data;

    private float stateTime;
    private float strikeTime;
    private float deadTime;

    private boolean stay;

    private Animation mAnimStay;
    private Animation mAnimRun;
    private Animation mAnimStrike;
    private Animation mAnimJump;
    private Animation mAnimDie;

    private Animation mCurrentAnimation;

    private Ground mGround1, mGround2;


    public Runner(Body body) {
        super(body);
        stateTime = 0f;
        data = (RunnerUserData) userData;
        stay = true;

        mAnimStay = AssetLoader.playerStay;
        mAnimRun = AssetLoader.playerRun;
        mAnimStrike = AssetLoader.playerStrike;
        mAnimJump = AssetLoader.playerJump;
        mAnimDie = AssetLoader.playerHit;

        mCurrentAnimation = mAnimStay;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        float x = body.getPosition().x - (data.getWidth()*0.1f) - data.getWidth()/2;
        float y = body.getPosition().y - data.getHeight()/2;
        float width = data.getWidth()*1.2f;

//        if (data.isDead()) {
//            batch.draw(AssetLoader.playerHit,x, y, width*0.5f, data.getHeight()*0.5f, width,
//                    data.getHeight(), 1f, 1f,(float) Math.toDegrees(body.getAngle()));
//        }  else if (data.isStriking()) {
//            batch.draw(AssetLoader.playerStrike, x + width/3.3f, y, width*1.5f, data.getHeight()*1.05f);
//        } else if (data.isJumping1()) {
//            batch.draw(AssetLoader.playerJump, x, y, width, data.getHeight());
//        }else if (stay) {
//            batch.draw(AssetLoader.playerRun.getKeyFrames()[0],x, y,
//                    width, data.getHeight());
//        } else {
//            stateTime += Gdx.graphics.getDeltaTime();
//            batch.draw(AssetLoader.playerRun.getKeyFrame(stateTime, true),x, y,
//                    width, data.getHeight());
//        }
//        stateTime += Gdx.graphics.getDeltaTime();
//        if (!data.isStriking()) {
//            batch.draw(mCurrentAnimation.getKeyFrame(stateTime, true),x, y, width*0.5f, data.getHeight()*0.5f, width,
//                    data.getHeight(), 1f, 1f,(float) Math.toDegrees(body.getAngle()));
//        }  else if (!data.isDead()) {
//            batch.draw(mCurrentAnimation.getKeyFrame(stateTime, true), x + width/3.3f, y, width*1.5f, data.getHeight()*1.05f);
//        }

        stateTime += Gdx.graphics.getDeltaTime();
        if (data.getState()==RunnerState.RUN_STRIKE||
            data.getState()==RunnerState.JUMP_STRIKE||
            data.getState()==RunnerState.JUMP_DOUBLE_STRIKE) {
            batch.draw(mCurrentAnimation.getKeyFrame(stateTime, true), x + width/3.3f, y, width*1.5f, data.getHeight()*1.05f);
        } else {
            batch.draw(mCurrentAnimation.getKeyFrame(stateTime, true),x, y, width*0.5f, data.getHeight()*0.5f, width,
                    data.getHeight(), 1f, 1f,(float) Math.toDegrees(body.getAngle()));
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

//        if (data.isStriking()==true) {
//            strikeTime +=delta;
//            if (strikeTime > 0.2f) {
//                data.setStriking(false);
//                if (!data.isDead()) {
//                    if (data.isJumping1()||data.isJumping2()) {
//                        mCurrentAnimation = mAnimJump;
//                    } else {
//                        mCurrentAnimation = mAnimRun;
//                        System.out.println("!strike RUN");
//                    }
//                }
//            }
//        }
//
        if (!data.isDead()) {
            body.setLinearVelocity(0,body.getLinearVelocity().y);
            if (!body.getWorld().isLocked()) {
                float x = body.getPosition().x - data.getWidth()/2;
                if (x>mGround1.getBody().getPosition().x
                        &&x<mGround1.getBody().getPosition().x + Constants.GROUND_WIDTH/2
                        &&x>mGround2.getBody().getPosition().x - Constants.GROUND_WIDTH/2 - data.getWidth()*1.5f) {
                    body.setTransform(Constants.RUNNER_X, body.getPosition().y, 0);
                }
            }
        }
//
//        if (!body.getWorld().isLocked()) {
//            if (body.getUserData()!=null&&!getUserData().isDead()&&
//                    body.getPosition().y - Constants.RUNNER_HEIGHT/2>=
//                            Constants.GROUND_Y + Constants.GROUND_HEIGHT/2 + 2) {
//                body.setTransform(Constants.RUNNER_X, body.getPosition().y, 0);
//            }
//        }

        switch (data.getState()) {
            case JUMP:
            case JUMP_DOUBLE:
                break;
            case RUN:
                if (!body.getWorld().isLocked()) {
                    float x = body.getPosition().x - data.getWidth()/2;
                    if (x>mGround1.getBody().getPosition().x - Constants.GROUND_WIDTH/2
                            &&x<mGround1.getBody().getPosition().x + Constants.GROUND_WIDTH/2) {
                        body.setTransform(Constants.RUNNER_X, body.getPosition().y, 0);
                    }
                }
                break;
            case RUN_STRIKE:
                strikeTime +=delta;
                if (strikeTime > 0.2f) {
                    data.setState(RunnerState.RUN);
                    mCurrentAnimation = mAnimRun;
                    stateTime = 0;
                    strikeTime = 0;
                }
                break;
            case JUMP_STRIKE:
                strikeTime +=delta;
                if (strikeTime > 0.2f) {
                    data.setState(RunnerState.JUMP);
                    mCurrentAnimation = mAnimJump;
                    stateTime = 0;
                    strikeTime = 0;
                }
                break;
            case JUMP_DOUBLE_STRIKE:
                strikeTime +=delta;
                if (strikeTime > 0.2f) {
                    data.setState(RunnerState.JUMP_DOUBLE);
                    mCurrentAnimation = mAnimJump;
                    stateTime = 0;
                    strikeTime = 0;
                }
                break;
            case DIE:
                dead(delta);
                break;

        }
    }

    private void dead(float delta) {
        deadTime +=delta;
        if (deadTime>0.3f&&getStage()!=null) {
            GameStage stage = (GameStage) getStage();
            stage.createLump(body, 4, AssetLoader.lumpsAtlas.findRegion("lump1"));
            ((UserData)body.getUserData()).setDestroy(true);
            ScoreCounter.death();
            ScoreCounter.checkScore();
            ScoreCounter.saveCalcStats();
            ScoreCounter.checkAchieve();
            ((GameStage) getStage()).gameOver();
            this.remove();
        }
    }

    @Override
    public RunnerUserData getUserData() {
        return (RunnerUserData) userData;
    }

    public void jump() {

//        if (!((data.isJumping1() && data.isJumping2())||data.isDead())) {
//            body.applyLinearImpulse(data.getJumpingLinearImpulse()
//                    , body.getWorldCenter(), true);
//            if (!data.isJumping1()) {
//                data.setJumping1(true);
//                if (!data.isStriking()) {
//                    mCurrentAnimation = mAnimJump;
//                }
//            } else {
//                data.setJumping2(true);
//            }
//        }

        switch (data.getState()) {
            case DIE:
            case JUMP_DOUBLE:
            case JUMP_DOUBLE_STRIKE:
                break;
            case RUN:
                data.setState(RunnerState.JUMP);
                body.applyLinearImpulse(data.getJumpingLinearImpulse()
                        , body.getWorldCenter(), true);
                mCurrentAnimation = mAnimJump;
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
//        data.setJumping1(false);
//        data.setJumping2(false);

//        if (!data.isDead()&&!data.isStriking()&&!stay) {
//            mCurrentAnimation = mAnimRun;
//            System.out.println("landed RUN");
//        }

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
                mCurrentAnimation = mAnimRun;
                break;
        }
    }


    public void strike() {
//        if (!data.isStriking()&&!data.isDead()) {
//            data.setStriking(true);
//            AssetLoader.monkStrike.play(SoundSystem.getSoundValue());
//            strikeTime = 0f;
//            mCurrentAnimation = mAnimStrike;
//        }
        switch (data.getState()) {
            case DIE:
                break;
            case RUN_STRIKE:
            case RUN:
                data.setState(RunnerState.RUN_STRIKE);
                AssetLoader.monkStrike.play(SoundSystem.getSoundValue());
                mCurrentAnimation = mAnimStrike;
                strikeTime = 0f;
                break;
            case JUMP_STRIKE:
            case JUMP:
                data.setState(RunnerState.JUMP_STRIKE);
                AssetLoader.monkStrike.play(SoundSystem.getSoundValue());
                mCurrentAnimation = mAnimStrike;
                strikeTime = 0f;
                break;
            case JUMP_DOUBLE_STRIKE:
            case JUMP_DOUBLE:
                data.setState(RunnerState.JUMP_DOUBLE_STRIKE);
                AssetLoader.monkStrike.play(SoundSystem.getSoundValue());
                mCurrentAnimation = mAnimStrike;
                strikeTime = 0f;
                break;
        }
    }

    public void hit() {
        mCurrentAnimation = mAnimDie;
        ((GameStage)getStage()).saveTimePoint();
        body.applyAngularImpulse(data.getHitAngularImpulse(), true);
        body.setFixedRotation(false);
//        data.setDead(true);
        System.out.println("hit");
        getUserData().setState(RunnerState.DIE);
    }


    public void start() {
        stay = false;
        mCurrentAnimation = mAnimRun;
        getUserData().setState(RunnerState.RUN);
    }

    public void setGrounds(Ground ground1, Ground ground2) {
        mGround1 = ground1;
        mGround2 = ground2;
    }
}
