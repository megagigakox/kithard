package pl.kithard.core.deposit;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DepositItemSerdes implements ObjectSerializer<DepositItem> {
    @Override
    public boolean supports(@NotNull Class<? super DepositItem> type) {
        return DepositItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull DepositItem object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("limit", object.getLimit());
        data.add("withdrawAll", object.isWithdrawAll());
        data.add("slot", object.getSlot());
        data.add("message", object.getMessage());
        data.add("item", object.getItem());
    }

    @Override
    public DepositItem deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new DepositItem(
                data.get("name", String.class),
                data.get("limit", Integer.class),
                data.get("withdrawAll", Boolean.class),
                data.get("slot", Integer.class),
                data.get("message", String.class),
                data.get("item", ItemStack.class)
        );
    }
}
