package pl.kithard.core.trade;

import java.util.Arrays;
import java.util.List;

public class TradeConfiguration {

    public static List<Integer> LEFT_SLOTS = Arrays.asList(
            0, 1, 2,
            9, 10, 11, 12,
            18, 19, 20, 21,
            27, 28, 29, 30,
            36, 37, 38, 39,
            45, 46, 47, 48
    );
    public static List<Integer> RIGHT_SLOTS = Arrays.asList(
            6, 7, 8,
            14, 15, 16, 17,
            23, 24, 25, 26,
            32, 33, 34, 35,
            41, 42, 43, 44,
            50, 51, 52, 53
    );

}
