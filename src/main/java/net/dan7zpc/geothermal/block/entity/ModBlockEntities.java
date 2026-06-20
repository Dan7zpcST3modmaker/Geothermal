package net.dan7zpc.geothermal.block.entity;

import net.dan7zpc.geothermal.Geothermal;
import net.dan7zpc.geothermal.block.ModBlocks;
import net.dan7zpc.geothermal.block.entity.heat_inputs.HeatExtractorBlockEntity;
import net.dan7zpc.geothermal.block.entity.heat_storages.HeatAccumulatorBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE_DEFERRED_REGISTER =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Geothermal.MOD_ID);

    public static final Supplier<BlockEntityType<HeatExtractorBlockEntity>> HEAT_EXTRACTOR_BE =
            BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("heat_extractor_be",() -> BlockEntityType.Builder.of(
                    HeatExtractorBlockEntity::new, ModBlocks.HEAT_EXTRACTOR.get()).build(null));
    public static final Supplier<BlockEntityType<HeatAccumulatorBlockEntity>> HEAT_ACCUMULATOR_BE =
            BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("heat_accumulator_be",() -> BlockEntityType.Builder.of(
                    HeatAccumulatorBlockEntity::new, ModBlocks.HEAT_ACCUMULATOR.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus);
    }
}
