package net.dan7zpc.geothermal.datagen;

import net.dan7zpc.geothermal.Geothermal;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Geothermal.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
