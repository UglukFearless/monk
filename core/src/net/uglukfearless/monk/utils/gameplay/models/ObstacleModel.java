package net.uglukfearless.monk.utils.gameplay.models;

import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.constants.Constants;

/**
 * Created by Ugluk on 30.09.2016.
 */
public class ObstacleModel {

    public String name;
    public String enName;
    public String ruName;

    public float width;
    public float height;
    public float y;
    public float y_offset;
    public float density;
    public float linearVelocity;
    public Vector2 hitExecution;


    public int gravityScale;

    public boolean armour;
    public boolean isTrap;
    public boolean isSphere;

    public float textureScaleX = 1;
    public float textureScaleY = 1;
    public float textureOffsetX = 0;
    public float textureOffsetY = 0;

    public short[][] prohibitionsMap = new short[2][2];
    public int priority = Constants.DANGERS_PRIORITY_NEVER;

    public boolean block;
    public int mNumber;
}
