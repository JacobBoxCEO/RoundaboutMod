package net.hydra.jojomod.event.powers.visagedata;

import net.minecraft.world.entity.LivingEntity;

public class NorisukeVisage extends VisageData {
    public NorisukeVisage(LivingEntity self) {
        super(self);
    }

    @Override
    public VisageData generateVisageData(LivingEntity entity) {
        return new NorisukeVisage(self);
    }

    @Override
    public String getSkinPath() {
        return "norisuke";
    }

    @Override
    public boolean rendersNorisukeHair() {
        return true;
    }
}
