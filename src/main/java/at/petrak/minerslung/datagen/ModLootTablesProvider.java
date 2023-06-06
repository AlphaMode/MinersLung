package at.petrak.minerslung.datagen;

import at.petrak.minerslung.common.blocks.ModBlocks;
import at.petrak.paucal.api.datagen.PaucalLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Map;

public class ModLootTablesProvider extends PaucalLootTableProvider {
    public ModLootTablesProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void makeLootTables(Map<Block, LootTable.Builder> blockTables,
                                  Map<ResourceLocation, LootTable.Builder> lootTables) {
        dropSelf(blockTables, ModBlocks.SAFETY_LANTERN, ModBlocks.SIGNAL_TORCH);

        dropThis(ModBlocks.WALL_SIGNAL_TORCH.get(), ModBlocks.SIGNAL_TORCH.get(), blockTables);
    }
}
