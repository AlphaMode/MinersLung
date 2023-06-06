package at.petrak.minerslung.datagen;

import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModDataGenerators {
    @SubscribeEvent
    public static void generateData(GatherDataEvent evt) {
        var gen = evt.getGenerator();
        var efh = evt.getExistingFileHelper();

        gen.addProvider(evt.includeClient(), new ModItemModels(gen, efh));
        gen.addProvider(evt.includeClient(), new ModBlockModels(gen, efh));

        gen.addProvider(evt.includeServer(), new ModBlockTagsProvider(gen, efh));
        gen.addProvider(evt.includeServer(), new ModLootTablesProvider(gen));
        gen.addProvider(evt.includeServer(), new ModLootMods(gen));
        gen.addProvider(evt.includeServer(), new ModAdvancementProvider(gen, efh));
        gen.addProvider(evt.includeServer(), new ModCraftingRecipes(gen));
    }
}
