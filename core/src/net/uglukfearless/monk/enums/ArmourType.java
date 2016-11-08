package net.uglukfearless.monk.enums;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 04.11.2016.
 */
public enum ArmourType {
    ARMOUR_TYPE1("Light armor", "Легкая броня", 1, 1, 1),
    ARMOUR_TYPE2("Middle armor", "Средняя броня", 2, 2, 2),
    ARMOUR_TYPE3("Heavy armor", "Тяжолая броня", 3, 3, 3);

    private final String sEnName;
    private final String sRuName;
    private final int sStrength;
    private final int sPrice;
    private TextureRegion sImage;
    private final int sGrade;


    ArmourType(String enName, String ruName, int strength, int price, int grade) {
        this.sEnName = enName;
        this.sRuName = ruName;
        this.sPrice = price;
        this.sGrade = grade;
        this.sStrength = strength;
        this.sImage = AssetLoader.armoursAtlas.findRegion("armour".concat(String.valueOf(grade)));
    }

    public static void init() {
        for (ArmourType armourType: ArmourType.values()) {
            armourType.setImage(AssetLoader.armoursAtlas.findRegion("armour".concat(String.valueOf(armourType.getGrade()))));
        }
    }

    public String getEnName() {
        return sEnName;
    }

    public String getRuName() {
        return sRuName;
    }

    public int getStrength() {
        return sStrength;
    }

    public int getPrice() {
        return sPrice;
    }

    public TextureRegion getImage() {
        return sImage;
    }

    public void setImage(TextureRegion sImage) {
        this.sImage = sImage;
    }

    public int getGrade() {
        return sGrade;
    }
}
