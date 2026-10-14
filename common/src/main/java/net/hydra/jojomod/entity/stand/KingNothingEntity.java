package net.hydra.jojomod.entity.stand;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class KingNothingEntity extends FollowingStandEntity {
    /**
     * Initialize Stands
     *
     * @param entityType
     * @param world
     */
    public KingNothingEntity(EntityType<? extends Mob> entityType,
                                Level world) {
        super(entityType, world);
    }

    public static final byte MANGA = 1;
}
