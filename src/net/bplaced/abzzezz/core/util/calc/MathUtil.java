/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

/*
 * @author Roman P.
 * Abzzezz Util is used to automate easy tasks.
 *
 */

package net.bplaced.abzzezz.core.util.calc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * Util for simple math tasks
 */
public class MathUtil {

    public static final NumberFormat NUMBER_FORMAT_TWO_INTEGERS = new DecimalFormat("00");

    public static final NumberFormat NUMBER_FORMAT_TWO_DECIMALS = new DecimalFormat("00.00");

    public static float abs(final float num) {
        return (num < 0) ? 0 - num : num;
    }

    public static double abs(final double num) {
        return (num < 0) ? 0 - num : num;
    }

    public static float nabs(final float num) {
        return (abs(num) * -1.0F);
    }

    public static double nabs(final double num) {
        return (abs(num) * -1.0);
    }

    public static double sinForCircle(float num, int radius) {
        return Math.sin(num * Math.PI / 180) * radius;
    }

    public static double cosForCircle(float num, int radius) {
        return Math.cos(num * Math.PI / 180) * radius;
    }

    public static int getBiggest(int num1, int num2) {
        return Math.max(num1, num2);
    }

    public static int getLowest(int num1, int num2) {
        return Math.min(num1, num2);
    }

    public static int getDifference(int num1, int num2) {
        return getBiggest(num1, num2) - getLowest(num1, num2);
    }

    public static int getRandomInt(int bound) {
        return new Random().nextInt(bound);
    }

    public static float mapFloat(float n, float nMin, float nMax, float min, float max) {
        return (n - nMin) / (nMax - nMin) * (max - min) + min;
    }

    public static int mapInt(int n, int nMin, int nMax, int min, int max) {
        return (n - nMin) / (nMax - nMin) * (max - min) + min;
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static boolean isInRange(final float current, final float min, final float max) {
        return (current > min && current < max);
    }

    public static boolean inMaxBound(final float current, final float max) {
        return (current >= 0 && current < max);
    }

}
