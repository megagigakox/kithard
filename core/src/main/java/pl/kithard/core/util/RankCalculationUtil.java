package pl.kithard.core.util;

public final class RankCalculationUtil {

    public static int getChangeBetweenTwoScores(int killer, int victim) {
        if (killer - victim > 300) return 1;

        double actualScore = 1.35;

        // calculate expected outcome
        double exponent = (double) (victim - killer) / 400;
        double expectedOutcome = (1 / (1 + (Math.pow(10, exponent))));

        // K-factor
        int multiplier = multiplier(killer);

        // calculate new rating
        return (int) Math.round(multiplier * (actualScore - expectedOutcome));
    }

    public static int multiplier(int rating) {
        int K;

        if (rating < 1400) {
            K = 50;
        } else if (rating >= 1400 && rating < 2000) {
            K = 42;
        } else if (rating >= 2000 && rating < 2400) {
            K = 36;
        } else if (rating >= 2400 && rating < 28000) {
            K = 28;
        } else {
            K = 20;
        }

        return K;
    }

}
