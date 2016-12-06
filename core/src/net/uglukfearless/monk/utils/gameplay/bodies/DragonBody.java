package net.uglukfearless.monk.utils.gameplay.bodies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

/**
 * Created by Ugluk on 04.12.2016.
 */

public class DragonBody {

    private Body mHead;
    private Body mHeadAnchor;
    private Body mFirstSegment;
    private Body [] mSpine;
    private Body mAnchor;

    private RevoluteJoint mHHAnchorJoin;

    private final static float FOLLOW_SPEED = 50f;
    private final static Vector2 UP_FORCE = new Vector2(0,10000);
    private final static Vector2 DOWN_FORCE = new Vector2(0,-10000);

    private float mTrend = 1;

    public DragonBody() {
        mSpine = new Body[5];
    }

    public void setHead(Body head) {
        mHead = head;
    }

    public void setHeadAnchor(Body headAnchor) {
        mHeadAnchor = headAnchor;
    }

    public void setFirstSegment(Body firstSegment) {
        mFirstSegment = firstSegment;
    }

    public void setSpine(Body[] spine) {
        mSpine = spine;
    }

    public void setAnchor(Body anchor) {
        mAnchor = anchor;
    }

    public void follow(Body body) {
        mTrend = (body.getPosition().y - mHeadAnchor.getPosition().y + 1)/Math.abs(body.getPosition().y - mHeadAnchor.getPosition().y);
        mHeadAnchor.setLinearVelocity(0,FOLLOW_SPEED*mTrend);
    }

    public void setFilter(Filter filter) {

        mHead.getFixtureList().get(0).setFilterData(filter);
        mFirstSegment.getFixtureList().get(0).setFilterData(filter);
        for (Body body:mSpine) {
            body.getFixtureList().get(0).setFilterData(filter);
        }
        mAnchor.getFixtureList().get(0).setFilterData(filter);
    }

    public Body getHead() {
        return mHead;
    }

    public Body getHeadAnchor() {
        return mHeadAnchor;
    }

    public Body getFirstSegment() {
        return mFirstSegment;
    }

    public Body[] getSpine() {
        return mSpine;
    }

    public void headUp() {
//        mHeadAnchor.applyForceToCenter(UP_FORCE, true);
//        mHeadAnchor.getLinearVelocity().add(new Vector2(0f, 20f));
        mHeadAnchor.setLinearVelocity(new Vector2(mHeadAnchor.getLinearVelocity().x, 30f));
//        if (mHHAnchorJoin.getJointAngle()>0) {
//            mHead.applyAngularImpulse(-1f, true);
//        }
    }

    public void headDown() {
//        mHeadAnchor.applyForceToCenter(DOWN_FORCE, true);
//        mHeadAnchor.getLinearVelocity().add(new Vector2(0f, -20f));
        mHeadAnchor.setLinearVelocity(new Vector2(mHeadAnchor.getLinearVelocity().x, -30f));
//        if (mHHAnchorJoin.getJointAngle()<0) {
//            mHead.applyAngularImpulse(1f, true);
//        }
    }

    public void setHHAnchorJoin(RevoluteJoint HHAnchorJoin) {
        mHHAnchorJoin = HHAnchorJoin;
    }
}
