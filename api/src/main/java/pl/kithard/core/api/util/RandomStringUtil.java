package pl.kithard.core.api.util;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomStringUtil {

    private RandomStringUtil() {
    }

    private static final char[] alphabetLowerCase = "abcdefghijklmnopqrstuwxyz".toCharArray();
    private static final char[] alphabetUpperCase = "ABCDEFGHIJKLMNOPQRSTUWXYZ".toCharArray();


    public static String get(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(
                    ThreadLocalRandom.current().nextBoolean()
                            ? alphabetLowerCase[Math.abs(ThreadLocalRandom.current().nextInt() % alphabetLowerCase.length)]
                            : alphabetUpperCase[Math.abs(ThreadLocalRandom.current().nextInt() % alphabetUpperCase.length)]);
        }

        builder.append("#");
        return builder.toString();
    }

}
