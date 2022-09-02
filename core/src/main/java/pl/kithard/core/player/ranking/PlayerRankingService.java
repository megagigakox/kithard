package pl.kithard.core.player.ranking;

import pl.kithard.core.achievement.AchievementType;
import pl.kithard.core.player.CorePlayer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayerRankingService {

    private final List<CorePlayer> playerPointsRanking = new ArrayList<>();
    private final List<CorePlayer> playerMinedStoneRanking = new ArrayList<>();
    private final List<CorePlayer> playerOpenedCaseRanking = new ArrayList<>();
    private final List<CorePlayer> playerConqueredPointsRanking = new ArrayList<>();
    private final List<CorePlayer> playerKillsRanking = new ArrayList<>();
    private final List<CorePlayer> playerDeathsRanking = new ArrayList<>();
    private final List<CorePlayer> playerThrownEnderPearlsRanking = new ArrayList<>();
    private final List<CorePlayer> playerEatenGoldenApplesRanking = new ArrayList<>();
    private final List<CorePlayer> playerEatenEnchantedGoldenApplesRanking = new ArrayList<>();
    private final List<CorePlayer> playerSpendTimeRanking = new ArrayList<>();

    public void add(CorePlayer corePlayer) {
        this.playerPointsRanking.add(corePlayer);
        this.playerMinedStoneRanking.add(corePlayer);
        this.playerOpenedCaseRanking.add(corePlayer);
        this.playerConqueredPointsRanking.add(corePlayer);
        this.playerKillsRanking.add(corePlayer);
        this.playerDeathsRanking.add(corePlayer);
        this.playerThrownEnderPearlsRanking.add(corePlayer);
        this.playerEatenGoldenApplesRanking.add(corePlayer);
        this.playerEatenEnchantedGoldenApplesRanking.add(corePlayer);
        this.playerSpendTimeRanking.add(corePlayer);
    }

    public void sort() {
        this.playerPointsRanking.sort((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints()));
        this.playerMinedStoneRanking.sort((Comparator.comparingLong(o -> o.getAchievementProgress(AchievementType.MINED_STONE))));
        this.playerOpenedCaseRanking.sort((Comparator.comparingLong(o -> o.getAchievementProgress(AchievementType.OPENED_CASE))));
        this.playerConqueredPointsRanking.sort((Comparator.comparingLong(o -> o.getAchievementProgress(AchievementType.CONQUERED_POINTS))));
        this.playerKillsRanking.sort((Comparator.comparingLong(o -> o.getAchievementProgress(AchievementType.KILLS))));
        this.playerDeathsRanking.sort((Comparator.comparingLong(o -> o.getAchievementProgress(AchievementType.DEATHS))));
        this.playerThrownEnderPearlsRanking.sort((Comparator.comparingLong(o -> o.getAchievementProgress(AchievementType.THROWN_PEARLS))));
        this.playerEatenGoldenApplesRanking.sort((Comparator.comparingLong(o -> o.getAchievementProgress(AchievementType.EATEN_GOLDEN_APPLES))));
        this.playerEatenEnchantedGoldenApplesRanking.sort((Comparator.comparingLong(o -> o.getAchievementProgress(AchievementType.EATEN_ENCHANTED_GOLDEN_APPLES))));
        this.playerSpendTimeRanking.sort((Comparator.comparingLong(o -> o.getAchievementProgress(AchievementType.SPEND_TIME))));

    }

    public int getPlayerPointsRankingPlace(CorePlayer corePlayer, List<CorePlayer> list) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).equals(corePlayer)) {
                return i + 1;
            }
        }
        return 0;
    }

    public List<CorePlayer> getPlayerPointsRanking() {
        return playerPointsRanking;
    }

    public List<CorePlayer> getPlayerMinedStoneRanking() {
        return playerMinedStoneRanking;
    }

    public List<CorePlayer> getPlayerOpenedCaseRanking() {
        return playerOpenedCaseRanking;
    }

    public List<CorePlayer> getPlayerConqueredPointsRanking() {
        return playerConqueredPointsRanking;
    }

    public List<CorePlayer> getPlayerKillsRanking() {
        return playerKillsRanking;
    }

    public List<CorePlayer> getPlayerDeathsRanking() {
        return playerDeathsRanking;
    }

    public List<CorePlayer> getPlayerThrownEnderPearlsRanking() {
        return playerThrownEnderPearlsRanking;
    }

    public List<CorePlayer> getPlayerEatenGoldenApplesRanking() {
        return playerEatenGoldenApplesRanking;
    }

    public List<CorePlayer> getPlayerEatenEnchantedGoldenApplesRanking() {
        return playerEatenEnchantedGoldenApplesRanking;
    }

    public List<CorePlayer> getPlayerSpendTimeRanking() {
        return playerSpendTimeRanking;
    }
}
