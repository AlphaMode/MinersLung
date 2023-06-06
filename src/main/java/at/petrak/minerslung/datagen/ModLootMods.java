package at.petrak.minerslung.datagen;

import at.petrak.minerslung.MinersLungMod;
import at.petrak.minerslung.common.items.ModItems;
import at.petrak.minerslung.common.loot.AddItemModifier;
import at.petrak.minerslung.common.loot.AddItemUniformModifier;
import at.petrak.minerslung.common.loot.ModLoot;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModLootMods extends GlobalLootModifierProvider {
    public ModLootMods(DataGenerator gen) {
        super(gen, MinersLungMod.MOD_ID);
    }

    @Override
    protected void start() {
        add("soulfire_bottle_buried", new AddItemUniformModifier(
                new LootItemCondition[]{LootTableIdCondition.builder(new ResourceLocation("minecraft:chests/buried_treasure")).build()},
            ModItems.SOULFIRE_BOTTLE.get(), 3, 10
        ));
        add("soulfire_bottle_shipwreck", new AddItemUniformModifier(
                new LootItemCondition[]{LootTableIdCondition.builder(
                        new ResourceLocation("minecraft:chests/shipwreck_treasure")).build(),
                        LootItemRandomChanceCondition.randomChance(0.5f).build(),
                },
            ModItems.SOULFIRE_BOTTLE.get(), 1, 5
        ));
        add("soulfire_bottle_big_ruin", new AddItemUniformModifier(
                new LootItemCondition[]{LootTableIdCondition.builder(
                        new ResourceLocation("minecraft:chests/underwater_ruin_big")).build(),
                        LootItemRandomChanceCondition.randomChance(0.8f).build(),
                },
            ModItems.SOULFIRE_BOTTLE.get(), 3, 8
        ));
        add("soulfire_bottle_big_ruin", new AddItemUniformModifier(
                new LootItemCondition[]{LootTableIdCondition.builder(
                        new ResourceLocation("minecraft:chests/underwater_ruin_small")).build(),
                        LootItemRandomChanceCondition.randomChance(0.3f).build(),
                },
            ModItems.SOULFIRE_BOTTLE.get(), 1, 3
        ));
        add("safety_lantern_dungeon", new AddItemModifier(
                new LootItemCondition[]{LootTableIdCondition.builder(
                        new ResourceLocation("minecraft:chests/simple_dungeon")).build(),
                        LootItemRandomChanceCondition.randomChance(0.3f).build(),
                },
            ModItems.SAFETY_LANTERN.get(), 1
        ));
        add("safety_lantern_mineshaft", new AddItemUniformModifier(
                new LootItemCondition[]{LootTableIdCondition.builder(
                        new ResourceLocation("minecraft:chests/abandoned_mineshaft")).build(),
                        LootItemRandomChanceCondition.randomChance(0.7f).build(),
                },
            ModItems.SAFETY_LANTERN.get(), 1, 2
        ));
        add("safety_lantern_stronghold", new AddItemUniformModifier(
                new LootItemCondition[]{LootTableIdCondition.builder(
                        new ResourceLocation("minecraft:chests/stronghold_corridor")).build(),
                        LootItemRandomChanceCondition.randomChance(0.5f).build(),
                },
            ModItems.SAFETY_LANTERN.get(), 1, 2
        ));
    }
}
