package net.dan7zpc.geothermal.datagen;

import net.dan7zpc.geothermal.Geothermal;
import net.dan7zpc.geothermal.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Geothermal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.HEAT_EXTRACTOR.get())
                .add(ModBlocks.HEAT_ACCUMULATOR.get())
        ;
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.HEAT_EXTRACTOR.get())
                .add(ModBlocks.HEAT_ACCUMULATOR.get())
        ;
    }
}
