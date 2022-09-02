package pl.kithard.core.util;

public final class MathUtil {

    private MathUtil() {}

    public static double round(double value, int decimals) {
        double p = Math.pow(10.0, decimals);
        return Math.round(value * p) / p;
    }

}
