package pl.kithard.core.enchant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum CustomEnchantType {
    SWORD(Collections.singletonList("SWORD")),
    ARMOR(Arrays.asList("CHESTPLATE", "HELMET", "LEGGINGS")),
    BOOTS(Collections.singletonList("BOOTS")),
    TOOLS(Arrays.asList("AXE", "PICKAXE", "SPADE")),
    BOW(Collections.singletonList("BOW"));

    private final List<String> mustContain;

    CustomEnchantType(List<String> mustContain) {
        this.mustContain = mustContain;
    }

    public List<String> getMustContain() {
        return mustContain;
    }
}
