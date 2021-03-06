package net.uglukfearless.monk.constants;


import com.badlogic.gdx.physics.box2d.Filter;

import net.uglukfearless.monk.utils.gameplay.bodies.FilterHandler;

/**
 * Created by Ugluk on 01.06.2016.
 */
public class FilterConstants {

    public static final short CATEGORY_GHOST = 0;
    public static final short CATEGORY_STATIC = 0x0001;
    public static final short CATEGORY_RUNNER = 0x0002;
    public static final short CATEGORY_RUNNER_STRIKE = 0x0004;
    public static final short CATEGORY_ENEMY = 0x0008;
    public static final short CATEGORY_ENEMY_STRIKE = 0x00010; //16d 10000b
    public static final short CATEGORY_OBSTACLE_SIMPLE = 0x00020; //32d 100000b
    public static final short CATEGORY_OBSTACLE_TRAP = 0x00040; //64d 1000000b
    public static final short CATEGORY_LUMP = 0x00080; //128d 10000000b
    public static final short CATEGORY_ENEMY_STRIKE_FLIP = 0x00100; //256d 100000000b
    public static final short CATEGORY_OBSTACLE_ARMOUR = 0x00200; //512d 1000000000b
    public static final short CATEGORY_RUNNER_STRIKE_STRONG = 0x00400; //1024d 10000000000b
    public static final short CATEGORY_PIT = 0x00800; //2048d 100000000000b


    public static final short MASK_GHOST = 0;

    public static final short MASK_STATIC = -1;

    public static final short MASK_RUNNER = CATEGORY_STATIC | CATEGORY_ENEMY
            | CATEGORY_ENEMY_STRIKE | CATEGORY_OBSTACLE_SIMPLE | CATEGORY_OBSTACLE_TRAP
            | CATEGORY_OBSTACLE_ARMOUR;

    public static final short MASK_RUNNER_WINGS = MASK_RUNNER | CATEGORY_PIT;

    public static final short MASK_PIT = CATEGORY_RUNNER;

    public static final short MASK_RUNNER_STRIKE  =  CATEGORY_ENEMY | CATEGORY_LUMP
            | CATEGORY_OBSTACLE_SIMPLE| CATEGORY_ENEMY_STRIKE| CATEGORY_OBSTACLE_ARMOUR;

    public static final short MASK_RUNNER_STRIKE_STRONG  =  CATEGORY_ENEMY | CATEGORY_LUMP
            | CATEGORY_OBSTACLE_SIMPLE| CATEGORY_ENEMY_STRIKE | CATEGORY_OBSTACLE_ARMOUR
            | CATEGORY_OBSTACLE_TRAP;

    public static final short MASK_ENEMY_DEAD = CATEGORY_STATIC | CATEGORY_RUNNER_STRIKE
            | CATEGORY_OBSTACLE_SIMPLE | CATEGORY_OBSTACLE_TRAP | CATEGORY_ENEMY
            | CATEGORY_OBSTACLE_ARMOUR | CATEGORY_RUNNER_STRIKE_STRONG;

    public static final short MASK_ENEMY = CATEGORY_STATIC | CATEGORY_RUNNER
            | CATEGORY_RUNNER_STRIKE | CATEGORY_OBSTACLE_SIMPLE | CATEGORY_OBSTACLE_TRAP
            | CATEGORY_ENEMY | CATEGORY_ENEMY_STRIKE_FLIP| CATEGORY_OBSTACLE_ARMOUR
            | CATEGORY_RUNNER_STRIKE_STRONG;

    public static final short MASK_ENEMY_STRIKE = CATEGORY_RUNNER | CATEGORY_STATIC
            | CATEGORY_OBSTACLE_SIMPLE | CATEGORY_OBSTACLE_TRAP
            | CATEGORY_RUNNER_STRIKE | CATEGORY_ENEMY_STRIKE_FLIP
            | CATEGORY_OBSTACLE_ARMOUR| CATEGORY_RUNNER_STRIKE_STRONG;

    public static final short MASK_ENEMY_STRIKE_FLIP = CATEGORY_ENEMY | CATEGORY_STATIC
            | CATEGORY_OBSTACLE_SIMPLE | CATEGORY_OBSTACLE_TRAP | CATEGORY_ENEMY_STRIKE
            | CATEGORY_OBSTACLE_ARMOUR;

    public static final short MASK_LUMP = CATEGORY_STATIC | CATEGORY_LUMP
            | CATEGORY_OBSTACLE_SIMPLE | CATEGORY_OBSTACLE_ARMOUR;

    public static final short MASK_OBSTACLE_SIMPLE = CATEGORY_STATIC | CATEGORY_RUNNER
            | CATEGORY_ENEMY | CATEGORY_RUNNER_STRIKE | CATEGORY_ENEMY_STRIKE
            | CATEGORY_OBSTACLE_SIMPLE | CATEGORY_OBSTACLE_TRAP | CATEGORY_ENEMY_STRIKE_FLIP
            | CATEGORY_OBSTACLE_ARMOUR| CATEGORY_RUNNER_STRIKE_STRONG;

    public static final short MASK_OBSTACLE_TRAP = CATEGORY_STATIC | CATEGORY_RUNNER
            | CATEGORY_ENEMY | CATEGORY_OBSTACLE_SIMPLE | CATEGORY_OBSTACLE_TRAP
            | CATEGORY_ENEMY_STRIKE | CATEGORY_ENEMY_STRIKE_FLIP | CATEGORY_OBSTACLE_ARMOUR ;

    public static final short MASK_OBSTACLE_ARMOUR = CATEGORY_STATIC | CATEGORY_RUNNER
            | CATEGORY_ENEMY | CATEGORY_OBSTACLE_SIMPLE | CATEGORY_OBSTACLE_TRAP
            | CATEGORY_ENEMY_STRIKE | CATEGORY_ENEMY_STRIKE_FLIP | CATEGORY_RUNNER_STRIKE_STRONG;

    public static final short MASK_RUNNER_GHOST = CATEGORY_STATIC;

    public static final short MASK_RUNNER_WINGS_GHOST = CATEGORY_STATIC | CATEGORY_PIT;


    public static final Filter FILTER_GHOST = FilterHandler.getFilter(CATEGORY_GHOST, MASK_GHOST);
    public static final Filter FILTER_STATIC = FilterHandler.getFilter(CATEGORY_STATIC, MASK_STATIC);
    public static final Filter FILTER_RUNNER = FilterHandler.getFilter(CATEGORY_RUNNER, MASK_RUNNER);
    public static final Filter FILTER_RUNNER_STRIKE = FilterHandler.getFilter(CATEGORY_RUNNER_STRIKE, MASK_RUNNER_STRIKE);
    public static final Filter FILTER_ENEMY = FilterHandler.getFilter(CATEGORY_ENEMY, MASK_ENEMY);
    public static final Filter FILTER_ENEMY_DEAD = FilterHandler.getFilter(CATEGORY_ENEMY, MASK_ENEMY_DEAD);
    public static final Filter FILTER_ENEMY_STRIKE = FilterHandler.getFilter(CATEGORY_ENEMY_STRIKE, MASK_ENEMY_STRIKE);
    public static final Filter FILTER_ENEMY_STRIKE_FLIP = FilterHandler.getFilter(CATEGORY_ENEMY_STRIKE_FLIP, MASK_ENEMY_STRIKE_FLIP);
    public static final Filter FILTER_LUMP = FilterHandler.getFilter(CATEGORY_LUMP, MASK_LUMP);
    public static final Filter FILTER_OBSTACLE_SIMPLE = FilterHandler.getFilter(CATEGORY_OBSTACLE_SIMPLE, MASK_OBSTACLE_SIMPLE);
    public static final Filter FILTER_OBSTACLE_TRAP = FilterHandler.getFilter(CATEGORY_OBSTACLE_TRAP, MASK_OBSTACLE_TRAP);
    public static final Filter FILTER_OBSTACLE_ARMOUR = FilterHandler.getFilter(CATEGORY_OBSTACLE_ARMOUR, MASK_OBSTACLE_ARMOUR);


    public static final Filter FILTER_RUNNER_GHOST = FilterHandler.getFilter(CATEGORY_LUMP , MASK_RUNNER_GHOST);
    public static final Filter FILTER_RUNNER_STRIKE_STRONG = FilterHandler.getFilter(CATEGORY_RUNNER_STRIKE_STRONG, MASK_RUNNER_STRIKE_STRONG);

    public static final Filter FILTER_RUNNER_WINGS = FilterHandler.getFilter(CATEGORY_RUNNER, MASK_RUNNER_WINGS);
    public static final Filter FILTER_RUNNER_WINGS_GHOST = FilterHandler.getFilter(CATEGORY_RUNNER, MASK_RUNNER_WINGS_GHOST);
    public static final Filter FILTER_PIT = FilterHandler.getFilter(CATEGORY_PIT, MASK_PIT);

    public static final Filter FILTER_DRAGON = FilterHandler.getFilter(CATEGORY_ENEMY_STRIKE_FLIP, MASK_RUNNER_STRIKE_STRONG);

}
