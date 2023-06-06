package at.petrak.minerslung.common.loot;

import at.petrak.minerslung.MinersLungMod;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLoot {
    public static DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MinersLungMod.MOD_ID);

    public static RegistryObject<Codec<AddItemUniformModifier>> ADD_ITEM_UNIFORM = LOOT_MODIFIERS.register("add_item_uniform", AddItemUniformModifier.CODEC);
    public static RegistryObject<Codec<AddItemModifier>> ADD_ITEM = LOOT_MODIFIERS.register("add_item", AddItemModifier.CODEC);
}
