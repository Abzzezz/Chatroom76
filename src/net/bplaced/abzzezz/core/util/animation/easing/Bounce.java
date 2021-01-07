/*
 * @author Roman
 * Last modified: 04.01.21, 20:11 by kursc
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


import net.bplaced.abzzezz.game.util.animation.Animation;

public class Bounce extends Animation {

    @Override
    public float easeIn(float t, float b, float c, float d) {
        return c - easeOut(d - t, 0, c, d) + b;
    }

    @Override
    public float easeIn(float t, float b, float c, float d, float s) {
        return 0;
    }

    @Override
    public float easeOut(float t, float b, float c, float d) {
        if ((t /= d) < (1 / 2.75f)) {
            return c * (7.5625f * t * t) + b;
        } else if (t < (2 / 2.75f)) {
            return c * (7.5625f * (t -= (1.5f / 2.75f)) * t + .75f) + b;
        } else if (t < (2.5 / 2.75)) {
            return c * (7.5625f * (t -= (2.25f / 2.75f)) * t + .9375f) + b;
        } else {
            return c * (7.5625f * (t -= (2.625f / 2.75f)) * t + .984375f) + b;
        }
    }

    @Override
    public float easeOut(float t, float b, float c, float d, float s) {
        return 0;
    }

    @Override
    public float easeInOut(float t, float b, float c, float d) {
        if (t < d / 2) return easeIn(t * 2, 0, c, d) * .5f + b;
        else return easeOut(t * 2 - d, 0, c, d) * .5f + c * .5f + b;
    }

    @Override
    public float easeInOut(float t, float b, float c, float d, float s) {
        return 0;
    }

}
