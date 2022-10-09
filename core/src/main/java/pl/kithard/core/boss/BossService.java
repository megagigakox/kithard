package pl.kithard.core.boss;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;
import java.util.List;

public class BossService {

    private IronGolem boss;

    public void createBoss(Location location, int hp) {
        this.boss = (IronGolem) Bukkit.getWorld("world").spawnEntity(location, EntityType.IRON_GOLEM);
        boss.setCustomName(TextUtil.color("&b&lBOSS"));
        boss.setCustomNameVisible(true);
        boss.setMaxHealth(hp);
        boss.setHealth(hp);
        boss.setRemoveWhenFarAway(false);
        boss.setCanPickupItems(false);
//        boss.getEquipment()
//                .setArmorContents(new ItemStack[] {
//                        ItemStackBuilder.of(Material.DIAMOND_BOOTS)
//                                .enchantment(Enchantment.DURABILITY, 3)
//                                .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
//                                .asItemStack(),
//                        ItemStackBuilder.of(Material.DIAMOND_LEGGINGS)
//                                .enchantment(Enchantment.DURABILITY, 3)
//                                .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
//                                .asItemStack(),
//                        ItemStackBuilder.of(Material.DIAMOND_CHESTPLATE)
//                                .enchantment(Enchantment.DURABILITY, 3)
//                                .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
//                                .asItemStack(),
//                        ItemStackBuilder.of(Material.DIAMOND_HELMET)
//                                .enchantment(Enchantment.DURABILITY, 3)
//                                .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
//                                .asItemStack()
//                });
//
//        boss.getEquipment().setItemInHand(ItemStackBuilder.of(Material.DIAMOND_SWORD)
//                .enchantment(Enchantment.DAMAGE_ALL, 5)
//                .enchantment(Enchantment.DURABILITY, 3)
//                .enchantment(Enchantment.FIRE_ASPECT, 2)
//                .asItemStack());
//
//        boss.getEquipment().setBootsDropChance(0.0f);
//        boss.getEquipment().setChestplateDropChance(0.0f);
//        boss.getEquipment().setHelmetDropChance(0.0f);
//        boss.getEquipment().setLeggingsDropChance(0.0f);
//        boss.getEquipment().setItemInHandDropChance(0.0f);
        
        List<PotionEffect> potionEffectList = Arrays.asList(
                new PotionEffect(PotionEffectType.SPEED, 1999999, 3),
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1999999, 2),
                new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1999999, 2),
                new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1999999, 2)
        );
        potionEffectList.forEach(boss::addPotionEffect);
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(TextUtil.color("     &8&l&m--[&b&l&m---&b&l BOSSY &b&l&m---&8&l&m]--"));
        Bukkit.broadcastMessage(TextUtil.color("   &7Wlasnie zrespil sie nowy &3boss&7 na mapie!"));
        Bukkit.broadcastMessage(TextUtil.color("     &7Znajdziesz go na kordach&8: &7x&8: &f" + location.getBlockX() + " &7z&8: &f" + location.getBlockZ()));
        Bukkit.broadcastMessage(TextUtil.color("  &7Zbierz swoją &bekipe &7i wyrusz na &fogromną &7klepe!"));
        Bukkit.broadcastMessage("");

        System.out.println(boss.getUniqueId());

    }

    public IronGolem getBoss() {
        return boss;
    }

    public void setBoss(IronGolem boss) {
        this.boss = boss;
    }
}
