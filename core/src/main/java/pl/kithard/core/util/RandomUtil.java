package pl.kithard.core.util;

import java.util.Random;

public final class RandomUtil {

    private RandomUtil() {}

    private static final Random random = new Random();

    public static int getRandInt(int min, int max) throws IllegalArgumentException {
        return random.nextInt(max - min + 1) + min;
    }

    public static Double getRandDouble(double min, double max) throws IllegalArgumentException {
        return random.nextDouble() * (max - min) + min;
    }

    public static Float getRandFloat(float min, float max) throws IllegalArgumentException {
        return random.nextFloat() * (max - min) + min;
    }

    public static boolean getChance(double chance) {
        return chance >= 100.0 || chance >= getRandDouble(0.0, 100.0);
    }
}
