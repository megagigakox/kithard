package pl.kithard.core.player.achievement;

import org.bukkit.inventory.ItemStack;

public class AchievementReward {

    private final String friendlyName;
    private final transient ItemStack itemStack;

    public AchievementReward(String friendlyName, ItemStack itemStack) {
        this.friendlyName = friendlyName;
        this.itemStack = itemStack;
    }

    public static AchievementRewardBuilder builder() {
        return new AchievementRewardBuilder();
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static class AchievementRewardBuilder {
        private String friendlyName;
        private ItemStack itemStack;

        public AchievementRewardBuilder friendlyName(String friendlyName) {
            this.friendlyName = friendlyName;
            return this;
        }

        public AchievementRewardBuilder itemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }

        public AchievementReward build() {
            return new AchievementReward(friendlyName, itemStack);
        }
    }
}
