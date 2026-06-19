package net.dan7zpc.geothermal.item;

import net.dan7zpc.geothermal.Geothermal;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Geothermal.MOD_ID);

    public static final DeferredItem<Item> COG = ITEMS.register("cog",
            ()-> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
