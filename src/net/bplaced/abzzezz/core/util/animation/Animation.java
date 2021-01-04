package net.bplaced.abzzezz.core.util.animation;

public abstract class Animation {

    public abstract float easeIn(float t, float b, float c, float d);

    public abstract float easeIn(float t, float b, float c, float d, float s);

    public abstract float easeOut(float t, float b, float c, float d);

    public abstract float easeOut(float t, float b, float c, float d, float s);

    public abstract float easeInOut(float t, float b, float c, float d);

    public abstract float easeInOut(float t, float b, float c, float d, float s);
}
