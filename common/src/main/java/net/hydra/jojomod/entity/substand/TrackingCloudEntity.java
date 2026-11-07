package net.hydra.jojomod.entity.substand;

import net.hydra.jojomod.entity.stand.StandEntity;
import net.hydra.jojomod.event.ModParticles;
import net.hydra.jojomod.particles.PuzzleColorParticleOptions;
import net.hydra.jojomod.particles.PuzzleParticle;
import net.hydra.jojomod.stand.powers.PowersKingNothing;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class TrackingCloudEntity extends StandEntity {
    /**
     * Initialize Stands
     *
     * @param entityType
     * @param world
     */
    public TrackingCloudEntity(
            EntityType<? extends Mob> entityType,
            Level world) {
        super(entityType, world);
    }

    @Override
    public boolean needsActive() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (getUser().isAlive() && getUserData(getUser()).roundabout$getStandPowers() instanceof PowersKingNothing KN) {
            KN.sendParticlesIfPossible(this.level(),
                    PuzzleColorParticleOptions.fromPackedColor(ModParticles.PUZZLE_PARTICLE,
                            KN.getVisualInfo().color1()), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

}
