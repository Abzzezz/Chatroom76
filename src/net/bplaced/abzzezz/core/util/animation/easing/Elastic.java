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


import net.bplaced.abzzezz.core.util.animation.Animation;

public class Elastic extends Animation {
    @Override
    public float easeIn(float t, float b, float c, float d) {
        if (t == 0) return b;
        if ((t /= d) == 1) return b + c;
        float p = d * .3f;
        float s = p / 4;
        return -(c * (float) Math.pow(2, 10 * (t -= 1)) * (float) Math.sin((t * d - s) * (2 * (float) Math.PI) / p)) + b;
    }

    @Override
    public float easeIn(float t, float b, float c, float d, float s) {
        return 0;
    }

    public float easeIn(float t, float b, float c, float d, float a, float p) {
        float s;
        if (t == 0) return b;
        if ((t /= d) == 1) return b + c;
        if (a < Math.abs(c)) {
            a = c;
            s = p / 4;
        } else {
            s = p / (2 * (float) Math.PI) * (float) Math.asin(c / a);
        }
        return -(a * (float) Math.pow(2, 10 * (t -= 1)) * (float) Math.sin((t * d - s) * (2 * Math.PI) / p)) + b;
    }

    @Override
    public float easeOut(float t, float b, float c, float d) {
        if (t == 0) return b;
        if ((t /= d) == 1) return b + c;
        float p = d * .3f;
        float a = c;
        float s = p / 4;
        return (a * (float) Math.pow(2, -10 * t) * (float) Math.sin((t * d - s) * (2 * (float) Math.PI) / p) + c + b);
    }

    @Override
    public float easeOut(float t, float b, float c, float d, float s) {
        return 0;
    }

    public float easeOut(float t, float b, float c, float d, float a, float p) {
        float s;
        if (t == 0) return b;
        if ((t /= d) == 1) return b + c;
        if (a < Math.abs(c)) {
            a = c;
            s = p / 4;
        } else {
            s = p / (2 * (float) Math.PI) * (float) Math.asin(c / a);
        }
        return (a * (float) Math.pow(2, -10 * t) * (float) Math.sin((t * d - s) * (2 * (float) Math.PI) / p) + c + b);
    }

    @Override
    public float easeInOut(float t, float b, float c, float d) {
        if (t == 0) return b;
        if ((t /= d / 2) == 2) return b + c;
        float p = d * (.3f * 1.5f);
        float a = c;
        float s = p / 4;
        if (t < 1)
            return -.5f * (a * (float) Math.pow(2, 10 * (t -= 1)) * (float) Math.sin((t * d - s) * (2 * (float) Math.PI) / p)) + b;
        return a * (float) Math.pow(2, -10 * (t -= 1)) * (float) Math.sin((t * d - s) * (2 * (float) Math.PI) / p) * .5f + c + b;
    }

    @Override
    public float easeInOut(float t, float b, float c, float d, float s) {
        return 0;
    }

    public float easeInOut(float t, float b, float c, float d, float a, float p) {
        float s;
        if (t == 0) return b;
        if ((t /= d / 2) == 2) return b + c;
        if (a < Math.abs(c)) {
            a = c;
            s = p / 4;
        } else {
            s = p / (2 * (float) Math.PI) * (float) Math.asin(c / a);
        }
        if (t < 1)
            return -.5f * (a * (float) Math.pow(2, 10 * (t -= 1)) * (float) Math.sin((t * d - s) * (2 * (float) Math.PI) / p)) + b;
        return a * (float) Math.pow(2, -10 * (t -= 1)) * (float) Math.sin((t * d - s) * (2 * (float) Math.PI) / p) * .5f + c + b;
    }

}
