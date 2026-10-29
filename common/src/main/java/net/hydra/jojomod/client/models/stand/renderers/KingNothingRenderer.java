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

    private static final ResourceLocation MANGA = Roundabout.location("textures/stand/king_nothing/manga.png");
    private static final ResourceLocation SPINE = Roundabout.location("textures/stand/king_nothing/spine.png");
    private static final ResourceLocation NOTE = Roundabout.location("textures/stand/king_nothing/note_block.png");
    private static final ResourceLocation BETA = Roundabout.location("textures/stand/king_nothing/beta.png");
    private static final ResourceLocation WARDEN = Roundabout.location("textures/stand/king_nothing/warden.png");

    public KingNothingRenderer(EntityRendererProvider.Context context) {
        super(context, new KingNothingModel<>(context.bakeLayer(ModEntityRendererClient.KING_NOTHING_LAYER)), 0f);
        this.addLayer(new KingNothingEmissiveLayer<>(this));
    }

    public static ResourceLocation getSkin(byte bt) {
        return switch (bt) {
            case 2 -> SPINE;
            case 3 -> NOTE;
            case 4 -> BETA;
            case 5 -> WARDEN;
            default -> MANGA;
        };
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(KingNothingEntity entity) {
        return getSkin(entity.getSkin());
    }
}
