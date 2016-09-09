package net.uglukfearless.monk.constants;

/**
 * Created by Ugluk on 11.06.2016.
 */
public class PlacingCategory {

    //Категории используются для составления карты запретов при размещении препятствий
    //Так же категории используются для назначения индивидуальных запретов для каждого препятствия или противника

    public static final short CATEGORY_PLACING_ENEMY_OVERLAND = 0x0001;
    public static final short CATEGORY_PLACING_ENEMY_ARMOUR = 0x0002;
    public static final short CATEGORY_PLACING_ENEMY_FLYING = 0x0004;

    public static final short CATEGORY_PLACING_OBSTACLE_OVERLAND = 0x0008;
    public static final short CATEGORY_PLACING_OBSTACLE_ARMOUR = 0x00010;
    public static final short CATEGORY_PLACING_OBSTACLE_TRAP = 0x00020;
    public static final short CATEGORY_PLACING_OBSTACLE_FLYING = 0x00040;


    public static final short CATEGORY_PLACING_PIT = 0x00080;
    public static final short CATEGORY_PLACING_COLUMNS = 0x00100;

    public static final short[] CATEGORY_MAP_PROHIBITION_PIT = new short[] {
            0 , CATEGORY_PLACING_OBSTACLE_TRAP
    };

    public static final short CATEGORY_ANY_OBJECT = CATEGORY_PLACING_ENEMY_OVERLAND|
            CATEGORY_PLACING_ENEMY_ARMOUR|CATEGORY_PLACING_ENEMY_FLYING|
            CATEGORY_PLACING_OBSTACLE_OVERLAND|CATEGORY_PLACING_OBSTACLE_ARMOUR|
            CATEGORY_PLACING_OBSTACLE_TRAP|CATEGORY_PLACING_OBSTACLE_FLYING;

    public static final short CATEGORY_ANY_OBSTACLE = CATEGORY_PLACING_OBSTACLE_OVERLAND |
            CATEGORY_PLACING_OBSTACLE_ARMOUR| CATEGORY_PLACING_OBSTACLE_TRAP|
            CATEGORY_PLACING_OBSTACLE_FLYING;

    public static final short ANY_PIT = CATEGORY_PLACING_PIT|CATEGORY_PLACING_COLUMNS;

}
