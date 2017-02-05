package net.uglukfearless.monk.enums;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 26.11.2016.
 */
public enum WeaponType {
    WEAPON_TYPE1("Sword", "Меч", WeaponDistance.MIDDLE, 1, 1, 0.5f, 1.5f);
//    WEAPON_TYPE2("Spear", "Копье", WeaponDistance.LONG, 2, 2);

    private final String sEnName;
    private final String sRuName;
    private final WeaponDistance sDistance;
    private final int sPrice;
    private TextureRegion sImage;
    private final int sGrade;

    private final float xOffset;
    private final float yOffset;

    WeaponType(String enName, String ruName, WeaponDistance distance, int price, int grade, float xOffset, float yOffset) {
        this.sEnName = enName;
        this.sRuName = ruName;
        this.sDistance = distance;
        this.sPrice = price;
        this.sGrade = grade;
        this.sImage = AssetLoader.itemsAtlas.findRegion("weapon".concat(String.valueOf(grade)));
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public static void init() {
        for (WeaponType weaponType: WeaponType.values()) {
            weaponType.setImage(AssetLoader.itemsAtlas.findRegion("weapon".concat(String.valueOf(weaponType.getGrade()))));
        }
    }

    public int getGrade() {
        return sGrade;
    }

    public void setImage(TextureRegion sImage) {
        this.sImage = sImage;
    }

    public TextureRegion getImage() {
        return sImage;
    }

    public int getPrice() {
        return sPrice;
    }

    public WeaponDistance getDistance() {
        return sDistance;
    }

    public String getEnName() {
        return sEnName;
    }

    public String getRuName() {
        return sRuName;
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }
}
