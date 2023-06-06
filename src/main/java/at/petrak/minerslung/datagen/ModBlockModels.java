package at.petrak.minerslung.datagen;

import at.petrak.minerslung.MinersLungMod;
import at.petrak.minerslung.common.blocks.ModBlocks;
import at.petrak.minerslung.common.blocks.SafetyLanternBlock;
import at.petrak.paucal.api.forge.datagen.PaucalBlockStateAndModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Locale;

public class ModBlockModels extends PaucalBlockStateAndModelProvider {
    public ModBlockModels(DataGenerator gen, ExistingFileHelper efh) {
        super(gen, MinersLungMod.MOD_ID, efh);
    }

    @Override
    protected void registerStatesAndModels() {
        var torchTex = modLoc("block/signal_torch");
        simpleBlock(ModBlocks.SIGNAL_TORCH.get(), models().torch("signal_torch", torchTex).renderType("cutout"));
        horizontalBlock(ModBlocks.WALL_SIGNAL_TORCH.get(), models().torchWall("wall_signal_torch", torchTex).renderType("cutout"), 90);


        getVariantBuilder(ModBlocks.SAFETY_LANTERN.get()).forAllStates(bs -> {
            var quality = bs.getValue(SafetyLanternBlock.AIR_QUALITY);
            var templatePath = bs.getValue(LanternBlock.HANGING) ? "template_hanging_lantern" : "template_lantern";
            var name = "lantern_" + quality.name().toLowerCase(Locale.ROOT);
            var texPath = modLoc("block/" + name);
            var model = models().withExistingParent(
                    name + (bs.getValue(LanternBlock.HANGING) ? "_hanging" : ""),
                    "minecraft:" + templatePath)
                .texture("lantern", texPath).renderType("cutout");
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }
}
