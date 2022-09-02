package pl.kithard.core.effect;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

public class CustomEffectCache {

    private final CorePlugin plugin;

    private final List<CustomEffect> effects = new ArrayList<>();

    public CustomEffectCache(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        this.effects.clear();
        Configuration configuration = this.plugin.getCustomEffectConfiguration().getCustomConfig();

        for (String effect : configuration.getConfigurationSection("effects").getKeys(false)) {

            int cost = configuration.getInt("effects." + effect + ".cost");
            int time = configuration.getInt("effects." + effect + ".time");
            int slot = configuration.getInt("effects." + effect + ".slot");

            List<String> replacedLore = new ArrayList<>();
            for (String toReplace : configuration.getStringList("effects." + effect + ".icon.lore")) {
                replacedLore.add(toReplace.replace("{COST}", String.valueOf(cost)).replace("{TIME}", String.valueOf(time)));
            }

            CustomEffect customEffect = new CustomEffect(
                    effect,
                    ItemBuilder.from(Material.valueOf(configuration.getString("effects." + effect + ".icon.type")))
                            .name(TextUtil.component(configuration.getString("effects." + effect + ".icon.name")))
                            .lore(TextUtil.component(replacedLore))
                            .amount(configuration.getInt("effects." + effect + ".icon.amount"))
                            .build(),
                    cost,
                    time,
                    slot
            );

            List<String> potionEffectsList = configuration.getStringList("effects." + effect + ".effects");
            if (potionEffectsList.size() > 0) {
                for (String enchant : potionEffectsList) {

                    String[] potionEffectArray = enchant.split(":");
                    String potionEffect = potionEffectArray[0];
                    int potionEffectMultiplier = Integer.parseInt(potionEffectArray[1]);

                    customEffect.getPotionEffects().add(new PotionEffect(PotionEffectType.getByName(potionEffect), time * 20, potionEffectMultiplier));
                }
            }

            this.effects.add(customEffect);
        }
    }

    public List<CustomEffect> getEffects() {
        return effects;
    }
}
