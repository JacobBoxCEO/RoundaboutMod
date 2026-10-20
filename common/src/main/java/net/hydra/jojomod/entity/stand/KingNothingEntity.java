package net.hydra.jojomod.entity.stand;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
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

    @Override
    public void setupAnimationStates() {
        super.setupAnimationStates();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot $$0) {
        if ($$0.equals(EquipmentSlot.MAINHAND)) {
            return getHeldItem();
        }
        return super.getItemBySlot($$0);
    }

    public static AttributeSupplier.Builder createStandAttributes() {
        return StandEntity.createStandAttributes().add(Attributes.ATTACK_SPEED);
    }
}
