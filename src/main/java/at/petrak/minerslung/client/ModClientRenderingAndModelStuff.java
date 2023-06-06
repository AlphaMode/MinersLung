package at.petrak.minerslung.client;

import at.petrak.minerslung.common.breath.AirHelper;
import at.petrak.minerslung.common.items.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ModClientRenderingAndModelStuff {
    @SubscribeEvent
    public static void registerItemProperties(FMLClientSetupEvent evt) {
        ItemProperties.register(ModItems.SAFETY_LANTERN.get(), ModItems.SAFETY_LANTERN_AIR_QUALITY_PRED,
            (stack, level, maybeEntity, seed) -> {
                var entity = maybeEntity != null ? maybeEntity : stack.getEntityRepresentation();
                if (entity != null) {
                    return switch (AirHelper.getO2LevelFromLocation(entity.getEyePosition(), entity.getLevel())
                        .getFirst()) {
                        case RED -> 0;
                        case YELLOW -> 1;
                        case BLUE -> 2;
                        case GREEN -> 3;
                    };
                } else {
                    return 3; // just do green?
                }
            });
    }
}
