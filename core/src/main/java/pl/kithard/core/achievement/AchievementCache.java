package pl.kithard.core.achievement;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.ItemStackBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AchievementCache {

    private final Map<AchievementType, List<Achievement>> achievements = new HashMap<>();

    public List<Achievement> findByType(AchievementType type) {
        return this.achievements.get(type);
    }

    public void init() {

        this.achievements.put(
                AchievementType.MINED_STONE,
                Arrays.asList(
                        Achievement.builder()
                                .id(1)
                                .type(AchievementType.MINED_STONE)
                                .reward(AchievementReward.builder()
                                        .friendlyName("x6 Perly")
                                        .itemStack(ItemStackBuilder.of(Material.ENDER_PEARL, 6).asItemStack())
                                        .build())
                                .required(1000)
                                .build(),
                        Achievement.builder()
                                .id(2)
                                .type(AchievementType.MINED_STONE)
                                .reward(AchievementReward.builder()
                                        .friendlyName("48x Blokow emeraldow")
                                        .itemStack(ItemStackBuilder.of(Material.EMERALD_BLOCK, 48).asItemStack())
                                        .build())
                                .required(5000)
                                .build(),
                        Achievement.builder()
                                .id(3)
                                .type(AchievementType.MINED_STONE)
                                .reward(AchievementReward.builder()
                                        .friendlyName("1x Magiczna skrzynka")
                                        .itemStack(ItemStackBuilder.of(CustomRecipe.MAGIC_CHEST.getItem()).asItemStack())
                                        .build())
                                .required(10000)
                                .build(),
                        Achievement.builder()
                                .id(4)
                                .type(AchievementType.MINED_STONE)
                                .reward(AchievementReward.builder()
                                        .friendlyName("3x Magiczna skrzynka")
                                        .itemStack(ItemStackBuilder.of(CustomRecipe.MAGIC_CHEST.getItem().clone()).amount(3).asItemStack())
                                        .build())
                                .required(25000)
                                .build(),
                        Achievement.builder()
                                .id(5)
                                .type(AchievementType.MINED_STONE)
                                .reward(AchievementReward.builder()
                                        .friendlyName("5x Kox")
                                        .itemStack(ItemStackBuilder.of(new ItemStack(Material.GOLDEN_APPLE, 5, (short) 2)).asItemStack())
                                        .build())
                                .required(50000)
                                .build(),
                        Achievement.builder()
                                .id(6)
                                .type(AchievementType.MINED_STONE)
                                .reward(AchievementReward.builder()
                                        .friendlyName("1x Turbodrop voucher na 30min")
                                        .itemStack(ItemStackBuilder.of(CustomRecipe.TURBO_DROP_VOUCHER_30MIN.getItem()).asItemStack())
                                        .build())
                                .required(100000)
                                .build(),
                        Achievement.builder()
                                .id(7)
                                .type(AchievementType.MINED_STONE)
                                .reward(AchievementReward.builder()
                                        .friendlyName("1x Turbodrop voucher na 60min")
                                        .itemStack(ItemStackBuilder.of(CustomRecipe.TURBO_DROP_VOUCHER_60MIN.getItem()).asItemStack())
                                        .build())
                                .required(250000)
                                .build(),
                        Achievement.builder()
                                .id(8)
                                .type(AchievementType.MINED_STONE)
                                .reward(AchievementReward.builder()
                                        .friendlyName("32x Kox")
                                        .itemStack(ItemStackBuilder.of(new ItemStack(Material.GOLDEN_APPLE, 32, (short) 2)).asItemStack())
                                        .build())
                                .required(500000)
                                .build(),
                        Achievement.builder()
                                .id(9)
                                .type(AchievementType.MINED_STONE)
                                .reward(AchievementReward.builder()
                                        .friendlyName("32x Magiczna skrzynka")
                                        .itemStack(ItemStackBuilder.of(CustomRecipe.MAGIC_CHEST.getItem().clone()).amount(32).asItemStack()).build())
                                .required(1250000)
                                .build()
                )
        );

        this.achievements.put(
                AchievementType.SPEND_TIME,
                Arrays.asList(
                        Achievement.builder()
                                .id(1)
                                .type(AchievementType.SPEND_TIME)
                                .reward(AchievementReward.builder()
                                        .friendlyName("1x Magiczna skrzynka")
                                        .itemStack(ItemStackBuilder.of(CustomRecipe.MAGIC_CHEST.getItem()).asItemStack())
                                        .build())
                                .required(TimeUnit.HOURS.toMillis(1))
                                .build(),
                        Achievement.builder()
                                .id(2)
                                .type(AchievementType.SPEND_TIME)
                                .reward(AchievementReward.builder()
                                        .friendlyName("2x Magiczna skrzynka")
                                        .itemStack(ItemStackBuilder.of(CustomRecipe.MAGIC_CHEST.getItem().clone()).amount(2).asItemStack())
                                        .build())
                                .required(TimeUnit.HOURS.toMillis(4))
                                .build(),
                        Achievement.builder()
                                .id(3)
                                .type(AchievementType.SPEND_TIME)
                                .reward(AchievementReward.builder()
                                        .friendlyName("1x Turbodrop voucher na 30min")
                                        .itemStack(ItemStackBuilder.of(CustomRecipe.TURBO_DROP_VOUCHER_30MIN.getItem()).asItemStack())
                                        .build())
                                .required(TimeUnit.HOURS.toMillis(8))
                                .build(),
                        Achievement.builder()
                                .id(4)
                                .type(AchievementType.SPEND_TIME)
                                .reward(AchievementReward.builder()
                                        .friendlyName("64x Refil")
                                        .itemStack(ItemStackBuilder.of(Material.GOLDEN_APPLE).amount(64).asItemStack())
                                        .build())
                                .required(TimeUnit.HOURS.toMillis(12))
                                .build(),
                        Achievement.builder()
                                .id(5)
                                .type(AchievementType.SPEND_TIME)
                                .reward(AchievementReward.builder()
                                        .friendlyName("1x Turbodrop voucher na 60min")
                                        .itemStack(ItemStackBuilder.of(CustomRecipe.TURBO_DROP_VOUCHER_60MIN.getItem()).asItemStack())
                                        .build())
                                .required(TimeUnit.HOURS.toMillis(18))
                                .build(),
                        Achievement.builder()
                                .id(6)
                                .type(AchievementType.SPEND_TIME)
                                .reward(AchievementReward.builder()
                                        .friendlyName("1x Kilof 6/3/3")
                                        .itemStack(ItemStackBuilder.of(Material.DIAMOND_PICKAXE)
                                                .enchantment(Enchantment.DIG_SPEED, 6)
                                                .enchantment(Enchantment.LOOT_BONUS_BLOCKS, 3)
                                                .enchantment(Enchantment.DURABILITY, 3)
                                                .asItemStack())
                                        .build())
                                .required(TimeUnit.HOURS.toMillis(24))
                                .build(),
                        Achievement.builder()
                                .id(7)
                                .type(AchievementType.SPEND_TIME)
                                .reward(AchievementReward.builder()
                                        .friendlyName("5x Magiczna skrzynka")
                                        .itemStack(ItemStackBuilder.of(CustomRecipe.MAGIC_CHEST.getItem().clone()).amount(5).asItemStack())
                                        .build())
                                .required(TimeUnit.HOURS.toMillis(32))
                                .build(),
                        Achievement.builder()
                                .id(8)
                                .type(AchievementType.SPEND_TIME)
                                .reward(AchievementReward.builder()
                                        .friendlyName("10x Rzucane TNT")
                                        .itemStack(ItemStackBuilder.of(CustomRecipe.THROWN_TNT.getItem().clone()).amount(10).asItemStack())
                                        .build())
                                .required(TimeUnit.HOURS.toMillis(40))
                                .build(),
                        Achievement.builder()
                                .id(9)
                                .type(AchievementType.SPEND_TIME)
                                .reward(AchievementReward.builder()
                                        .friendlyName("1x &6&lVoucher na SVIP na edycje")
                                        .itemStack(ItemStackBuilder.of(CustomRecipe.SVIP_VOUCHER.getItem()).asItemStack())
                                        .build())
                                .required(TimeUnit.HOURS.toMillis(48))
                                .build()
                )
        );
    }
}
