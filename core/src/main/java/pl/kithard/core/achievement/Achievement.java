package pl.kithard.core.achievement;

public class Achievement {

    private final int id;

    private final AchievementType type;
    private final AchievementReward reward;
    private final long required;

    public Achievement(int id, AchievementType type, AchievementReward reward, long required) {
        this.id = id;
        this.type = type;
        this.reward = reward;
        this.required = required;
    }

    public static AchievementBuilder builder() {
        return new AchievementBuilder();
    }

    public int getId() {
        return id;
    }

    public AchievementType getType() {
        return type;
    }

    public AchievementReward getReward() {
        return reward;
    }

    public long getRequired() {
        return required;
    }

    public static class AchievementBuilder {
        private int id;
        private AchievementType type;
        private AchievementReward reward;
        private long required;

        public AchievementBuilder id(int id) {
            this.id = id;
            return this;
        }

        public AchievementBuilder type(AchievementType type) {
            this.type = type;
            return this;
        }

        public AchievementBuilder reward(AchievementReward reward) {
            this.reward = reward;
            return this;
        }

        public AchievementBuilder required(long required) {
            this.required = required;
            return this;
        }

        public Achievement build() {
            return new Achievement(id, type, reward, required);
        }
    }
}