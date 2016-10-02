package net.uglukfearless.monk.utils.gameplay.models;

import net.uglukfearless.monk.constants.Constants;

/**
 * Created by Ugluk on 24.09.2016.
 */
public class EnemyModel {

    public String name;
    public String enName;
    public String ruName;

    public float width;
    public float height;
    public float y;
    public float jumpingImpulse;
    public int gravityScale;

    public boolean armour;
    public boolean jumper;
    public boolean shouter;
    public boolean striker;

    public float textureScaleX = 1;
    public float textureScaleY = 1;
    public float textureOffsetX = 0;
    public float textureOffsetY = 0;

    public short[][] prohibitionsMap = new short[2][2];
    public int priority = Constants.DANGERS_PRIORITY_NEVER;

    public float basicXVelocity;
    public boolean block;
    public int number = 0;

}
