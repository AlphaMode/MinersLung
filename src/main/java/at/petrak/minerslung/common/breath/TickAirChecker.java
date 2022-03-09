package at.petrak.minerslung.common.breath;

import at.petrak.minerslung.common.advancement.ModAdvancementTriggers;
import at.petrak.minerslung.common.capability.ModCapabilities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;
import java.util.Random;

public class TickAirChecker {
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void modifyEntityAir(LivingEvent.LivingUpdateEvent evt) {
        var e = evt.getEntityLiving();
        var world = e.level;

        var o2Level = AirHelper.getO2LevelFromLocation(e.getEyePosition(), e.level);
        var deltaO2 = 0;
        var wasProtected = false;
        switch (o2Level) {
            case GREEN -> deltaO2 = 4;
            case BLUE -> deltaO2 = 0;
            case YELLOW -> {
                Optional<ItemStack> yellowProt = AirHelper.getProtectionFromYellow(e);
                wasProtected = yellowProt != null;
                if (yellowProt == null) {
                    if (world.getGameTime() % 4 == 0) {
                        deltaO2 = -1;
                    }
                } else if (yellowProt.isPresent() && world.getGameTime() % (20 * 15) == 0) {
                    ServerPlayer maybeSplayer = null;
                    if (e instanceof ServerPlayer splayer) {
                        maybeSplayer = splayer;
                    }
                    yellowProt.get().hurt(1, RANDOM, maybeSplayer);
                }
            }
            case RED -> {
                var redProt = AirHelper.isProtectedFromRed(e);
                wasProtected = redProt;
                if (!redProt) {
                    deltaO2 = -1;
                }
            }
        }

        if (e instanceof ServerPlayer splayer) {
            var usedBladder = false;
            var cap = splayer.getCapability(ModCapabilities.IS_PROTECTED_FROM_AIR).resolve();
            if (cap.isPresent() && cap.get().isUsingBladder) {
                usedBladder = true;
            }
            ModAdvancementTriggers.BREATHE_AIR.trigger(splayer,
                o2Level, wasProtected, AirHelper.isUnderwater(splayer.getEyePosition(), splayer.level), usedBladder);
        }

        if (deltaO2 != 0) {
            boolean skipAirLossDueToRespiration = false;
            if (deltaO2 < 0) {
                int respirationLevel = EnchantmentHelper.getRespiration(e);
                if (respirationLevel != 0 && RANDOM.nextInt(respirationLevel + 1) > 0) {
                    skipAirLossDueToRespiration = true;
                }
            }
            if (!skipAirLossDueToRespiration) {
                var newO2 = Math.min(e.getAirSupply() + deltaO2, e.getMaxAirSupply());
                e.setAirSupply(newO2);

                if (newO2 <= -20) {
                    e.setAirSupply(newO2 + 20);
                    // copy code from pristine
                    var vel = e.getDeltaMovement();

                    for (int i = 0; i < 8; i++) {
                        double dx = RANDOM.nextDouble() - RANDOM.nextDouble();
                        double dy = RANDOM.nextDouble() - RANDOM.nextDouble();
                        double dz = RANDOM.nextDouble() - RANDOM.nextDouble();
                        world.addParticle(ParticleTypes.BUBBLE, e.getX() + dx, e.getY() + dy, e.getZ() + dz, vel.x,
                            vel.y, vel.z);
                    }

                    e.hurt(DamageSource.DROWN, 2.0F);
                }
            }
        }
    }
}
