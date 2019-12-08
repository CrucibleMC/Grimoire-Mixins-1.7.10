package io.github.crucible.grimoire.mixins.industrialcraft;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ic2.core.util.Util.class)
public abstract class MixinUtil {

    /**
     * @author Brunoxkk0
     * @reason Corrigir um bug com o industrial craft que ao pegar uma chunk retorna null, quebrando todas as tile entities do mod.
     */
    @Overwrite
    public static Chunk getLoadedChunk(World world, int chunkX, int chunkZ){
        Chunk chunk = null;

        if (world.getChunkProvider() instanceof ChunkProviderServer) {
            ChunkProviderServer cps = (ChunkProviderServer)world.getChunkProvider();

            try {
                if (cps.chunkExists(chunkX, chunkZ)) {
                    chunk = cps.provideChunk(chunkX, chunkZ);
                }
            } catch (NoSuchFieldError e) {
                System.err.println("[Crucible] IC2 ~ Error on getLoadedChunk" + e.getLocalizedMessage());
            }

        } else {
            chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
        }

        if (chunk instanceof net.minecraft.world.chunk.EmptyChunk) {
            return null;
        }
        return chunk;
    }

}