package net.dan7zpc.geothermal.block;

import net.dan7zpc.geothermal.Geothermal;
import net.dan7zpc.geothermal.block.custom.heat_extractors.HeatAccumulatorBlock;
import net.dan7zpc.geothermal.block.custom.heat_extractors.HeatExtractorBlock;
import net.dan7zpc.geothermal.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Geothermal.MOD_ID);

    public static final DeferredBlock<Block> HEAT_EXTRACTOR = registerBlock("t1_heat_extractor",
            ()-> new HeatExtractorBlock(BlockBehaviour.Properties.of()
                    .strength(4)
            ));
    public static final DeferredBlock<Block> HEAT_ACCUMULATOR = registerBlock("t1_heat_accumulator",
            ()-> new HeatAccumulatorBlock(BlockBehaviour.Properties.of()
                    .strength(4)
            ));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name,block);
        registerBlockIteem(name,toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockIteem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
