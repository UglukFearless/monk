package net.uglukfearless.monk.actors.gameplay;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.uglukfearless.monk.box2d.UserData;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.enums.ArmourType;
import net.uglukfearless.monk.utils.gameplay.bodies.BodyUtils;
import net.uglukfearless.monk.utils.sort.Sortable;

/**
 * Created by Ugluk on 18.05.2016.
 */
public abstract class GameActor extends Actor {

    protected Body body;
    protected UserData userData;


    public GameActor(Body body) {
        this.body = body;
        this.userData = (UserData) body.getUserData();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (body.getUserData()==null) {
            remove();
        }
    }


    public abstract UserData getUserData();

    public Body getBody() {
        return body;
    }

    protected boolean inFrame() {
        return (body.getPosition().x + userData.getWidth()/2f + 3 > 0)
                &&(body.getPosition().x - userData.getWidth()/2f - 3 < Constants.GAME_WIDTH)
                &&(body.getPosition().y + userData.getHeight()/2f + 3 > 0)
                &&(body.getPosition().y - userData.getHeight()/2f < 20);
    }
}
