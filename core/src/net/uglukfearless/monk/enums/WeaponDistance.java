package net.uglukfearless.monk.enums;

/**
 * Created by Ugluk on 26.11.2016.
 */
public enum WeaponDistance {

    SHORT(1.2f),
    MIDDLE(1.8f),
    LONG(2.3f);

    private final float DISTANCE;

    WeaponDistance(float distance) {
        DISTANCE = distance;
    }

    public float getDISTANCE() {
        return DISTANCE;
    }
}
