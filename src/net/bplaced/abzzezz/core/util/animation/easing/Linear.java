/*
 * @author Roman
 * Last modified: 04.01.21, 20:15 by kursc
 *
 *
 *
 */

/*
 * @author Roman P.
 * Abzzezz Util is used to automate easy tasks.
 *
 */

package net.bplaced.abzzezz.core.util.animation.easing;



public class Linear extends net.bplaced.abzzezz.game.util.animation.Animation {

    public float easeNone(float t, float b, float c, float d) {
        return c * t / d + b;
    }

    @Override
    public float easeIn(float t, float b, float c, float d) {
        return c * t / d + b;
    }

    @Override
    public float easeIn(float t, float b, float c, float d, float s) {
        return 0;
    }

    @Override
    public float easeOut(float t, float b, float c, float d) {
        return c * t / d + b;
    }

    @Override
    public float easeOut(float t, float b, float c, float d, float s) {
        return 0;
    }

    @Override
    public float easeInOut(float t, float b, float c, float d) {
        return c * t / d + b;
    }

    @Override
    public float easeInOut(float t, float b, float c, float d, float s) {
        return 0;
    }

}
