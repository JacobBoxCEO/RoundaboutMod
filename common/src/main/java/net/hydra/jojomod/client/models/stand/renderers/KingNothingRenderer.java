package net.hydra.jojomod.client.models.stand.renderers;

import net.hydra.jojomod.Roundabout;
import net.hydra.jojomod.client.models.layers.ModEntityRendererClient;
import net.hydra.jojomod.client.models.stand.KingNothingModel;
import net.hydra.jojomod.client.models.stand.StandModel;
import net.hydra.jojomod.entity.stand.KingNothingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class KingNothingRenderer extends StandRenderer<KingNothingEntity> {

    private static final ResourceLocation MANGA_SKIN = Roundabout.location("textures/stand/king_nothing/manga.png");

    public static ResourceLocation getSkin(byte bt) {
        return MANGA_SKIN;
    }

    /**
     * Stand renderers should all extend this, because it is used
     * to make sure stand lighting doesn't mess up when they clip through blocks.
     *
     * @param context
     */
    public KingNothingRenderer(EntityRendererProvider.Context context) {
        super(context, new KingNothingModel<>(context.bakeLayer(ModEntityRendererClient.KING_NOTHING_LAYER)), 0f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(KingNothingEntity entity) {
        return getSkin(entity.getSkin());
    }
}
