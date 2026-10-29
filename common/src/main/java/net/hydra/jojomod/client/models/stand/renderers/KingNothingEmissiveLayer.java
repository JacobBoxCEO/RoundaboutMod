package net.hydra.jojomod.client.models.stand.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.access.IEntityAndData;
import net.hydra.jojomod.client.ClientUtil;
import net.hydra.jojomod.client.models.stand.StandModel;
import net.hydra.jojomod.entity.stand.KingNothingEntity;
import net.hydra.jojomod.entity.stand.SoftAndWetEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;

public class KingNothingEmissiveLayer <T extends KingNothingEntity, M extends StandModel<T>> extends EyesLayer<T, M> {
    private static final RenderType WARDEN =
            RenderType.eyes(Roundabout.location("textures/stand/king_nothing/warden_e.png"));

    public KingNothingEmissiveLayer(RenderLayerParent<T, M> $$0) {
        super($$0);
    }

    @Override
    public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T mobEntity, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
        LivingEntity User = mobEntity.getUser();

        if ((User != null && ((IEntityAndData)User).roundabout$getTrueInvisibility() > -1) ||
                ((IEntityAndData)mobEntity).roundabout$getTrueInvisibility() > -1){
            return;
        }
        if (ClientUtil.canSeeStands(Minecraft.getInstance().player) && mobEntity.getSkin() == KingNothingEntity.WARDEN
                && (mobEntity.getFadeOut() >= mobEntity.getMaxFade())){
            VertexConsumer $$10 = $$1.getBuffer(this.renderType());
            this.getParentModel().renderToBuffer($$0, $$10, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, mobEntity.fadePercent);

        }
    }

    @Override
    public RenderType renderType() {
        return WARDEN;
    }
}
