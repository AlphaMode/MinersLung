package at.petrak.minerslung.datagen;

import at.petrak.minerslung.MinersLungMod;
import at.petrak.minerslung.common.advancement.AirProtectionSource;
import at.petrak.minerslung.common.advancement.BreatheAirTrigger;
import at.petrak.minerslung.common.breath.AirQualityLevel;
import at.petrak.minerslung.common.items.ModItems;
import at.petrak.paucal.api.datagen.PaucalRecipeProvider;
import com.mojang.datafixers.util.Either;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;
import java.util.function.Consumer;

public class ModCraftingRecipes extends PaucalRecipeProvider {
    public ModCraftingRecipes(DataGenerator pGenerator) {
        super(pGenerator, MinersLungMod.MOD_ID);
    }

    @Override
    protected void makeRecipes(Consumer<FinishedRecipe> recipes) {
        var yellowTrigger = new BreatheAirTrigger.Instance(EntityPredicate.Composite.ANY,
            EnumSet.of(AirQualityLevel.YELLOW, AirQualityLevel.RED), null,
            Either.left(AirProtectionSource.NONE));
        var netherTrigger = ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(Level.NETHER);


        ShapedRecipeBuilder.shaped(ModItems.RESPIRATOR.get())
            .define('P', Items.PAPER)
            .define('S', Items.STRING)
            .define('C', Items.CHARCOAL)
            .pattern(" S ")
            .pattern("P P")
            .pattern("PCP")
            .unlockedBy("namesarehard",
                yellowTrigger)
            .save(recipes);

        ShapedRecipeBuilder.shaped(ModItems.AIR_BLADDER.get())
            .define('L', Items.LEATHER)
            .define('S', Items.STRING)
            .pattern(" LS")
            .pattern("L L")
            .pattern(" L ")
            .unlockedBy("namesarehard",
                yellowTrigger)
            .save(recipes);

        ringAll(ModItems.SAFETY_LANTERN.get(), 1, Ingredient.of(Tags.Items.INGOTS_COPPER),
            Ingredient.of(Items.REDSTONE_TORCH))
            .unlockedBy("namesarehard", yellowTrigger)
            .save(recipes);

        ShapedRecipeBuilder.shaped(ModItems.SOULFIRE_BOTTLE.get(), 3)
            .define('G', Ingredient.of(Tags.Items.GLASS))
            .define('S', Ingredient.of(Items.SOUL_SAND, Items.SOUL_SOIL))
            .define('C', Ingredient.of(Items.CHARCOAL, Items.COAL))
            .pattern(" C ")
            .pattern("GSG")
            .pattern(" G ")
            .unlockedBy("namesarehard", netherTrigger)
            .save(recipes, "soulfire_bottle_from_glass");

        ShapelessRecipeBuilder.shapeless(ModItems.SOULFIRE_BOTTLE.get(), 3)
            .requires(Items.GLASS_BOTTLE, 3)
            .requires(Ingredient.of(Items.SOUL_SAND, Items.SOUL_SOIL))
            .requires(Ingredient.of(Items.CHARCOAL, Items.COAL))
            .unlockedBy("namesarehard", netherTrigger)
            .save(recipes);

    }
}
