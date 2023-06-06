package at.petrak.minerslung.common.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class AddItemUniformModifier extends LootModifier
{
    public static final Supplier<Codec<AddItemUniformModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst).and(
                            inst.group(
                                    ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter((m) -> m.addedItem),
                                    Codec.INT.fieldOf("min").forGetter((m) -> m.min),
                                    Codec.INT.fieldOf("max").forGetter((m) -> m.max)
                            )
                    )
                    .apply(inst, AddItemUniformModifier::new)));

    private final Item addedItem;
    private final int min, max;

    /**
     * This loot modifier adds an item to the loot table, given the conditions specified.
     */
    public AddItemUniformModifier(LootItemCondition[] conditionsIn, Item addedItemIn, int min, int max) {
        super(conditionsIn);
        this.addedItem = addedItemIn;
        this.min = min;
        this.max = max;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ItemStack addedStack = new ItemStack(addedItem);

        addedStack = SetItemCountFunction.setCount(UniformGenerator.between(min, max)).build().apply(addedStack, context);

        if (addedStack.getCount() < addedStack.getMaxStackSize()) {
            generatedLoot.add(addedStack);
        } else {
            int i = addedStack.getCount();

            while (i > 0) {
                ItemStack subStack = addedStack.copy();
                subStack.setCount(Math.min(addedStack.getMaxStackSize(), i));
                i -= subStack.getCount();
                generatedLoot.add(subStack);
            }
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}