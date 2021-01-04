/*
 * @author Roman P.
 * Abzzezz Util is used to automate easy tasks.
 *
 */

package net.bplaced.abzzezz.core.util.animation.easing;

import net.bplaced.abzzezz.core.util.animation.Animation;

public class Back extends Animation {

    @Override
    public float easeIn(float t, float b, float c, float d) {
        float s = 1.70158f;
        return c * (t /= d) * t * ((s + 1) * t - s) + b;
    }

    @Override
    public float easeIn(float t, float b, float c, float d, float s) {
        return c * (t /= d) * t * ((s + 1) * t - s) + b;
    }

    @Override
    public float easeOut(float t, float b, float c, float d) {
        float s = 1.70158f;
        return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
    }

    @Override
    public float easeOut(float t, float b, float c, float d, float s) {
        return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
    }

    @Override
    public float easeInOut(float t, float b, float c, float d) {
        float s = 1.70158f;
        if ((t /= d / 2) < 1) return c / 2 * (t * t * (((s *= (1.525f)) + 1) * t - s)) + b;
        return c / 2 * ((t -= 2) * t * (((s *= (1.525f)) + 1) * t + s) + 2) + b;
    }

    @Override
    public float easeInOut(float t, float b, float c, float d, float s) {
        if ((t /= d / 2) < 1) return c / 2 * (t * t * (((s *= (1.525f)) + 1) * t - s)) + b;
        return c / 2 * ((t -= 2) * t * (((s *= (1.525f)) + 1) * t + s) + 2) + b;
    }

}
