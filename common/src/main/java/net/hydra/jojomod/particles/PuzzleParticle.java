package net.hydra.jojomod.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;

public class PuzzleParticle extends TextureSheetParticle {

    protected PuzzleParticle(ClientLevel level, double xPos, double yPos, double zPos, SpriteSet set,
                             double xd, double yd, double zd) {
        super(level, xPos, yPos, zPos, xd, yd, zd);

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.lifetime = 10;
        setSpriteFromAge(set);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<PuzzleColorParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(PuzzleColorParticleOptions options, ClientLevel level,
                                       double x, double y, double z, double xd, double yd, double zd) {
            PuzzleParticle particle = new PuzzleParticle(level, x, y, z, sprites, xd, yd, zd);
            particle.setColor(options.getR(), options.getG(), options.getB());
            return particle;
        }
    }
}
