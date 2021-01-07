/*
 * @author Roman
 * Last modified: 04.01.21, 20:09 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.util.animation;

public abstract class Animation {

    public abstract float easeIn(float t, float b, float c, float d);

    public abstract float easeIn(float t, float b, float c, float d, float s);

    public abstract float easeOut(float t, float b, float c, float d);

    public abstract float easeOut(float t, float b, float c, float d, float s);

    public abstract float easeInOut(float t, float b, float c, float d);

    public abstract float easeInOut(float t, float b, float c, float d, float s);
}
