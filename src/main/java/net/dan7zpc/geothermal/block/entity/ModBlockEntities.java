package net.dan7zpc.geothermal.block.entity;

import net.dan7zpc.geothermal.Geothermal;
import net.dan7zpc.geothermal.block.ModBlocks;
import net.dan7zpc.geothermal.block.entity.heat_extractors.TierOneHeatExtractorBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE_DEFERRED_REGISTER =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Geothermal.MOD_ID);

    public static final Supplier<BlockEntityType<TierOneHeatExtractorBlockEntity>> TIER_ONE_HEAT_EXTRACTOR_BE =
            BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("t1_heat_extractor_be",() -> BlockEntityType.Builder.of(
                    TierOneHeatExtractorBlockEntity::new, ModBlocks.TIER_ONE_HEAT_EXTRACTOR.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus);
    }
}
