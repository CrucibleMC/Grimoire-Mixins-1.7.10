package io.github.crucible.grimoire.mixins.thaumcraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import thaumcraft.common.tiles.TileArcaneLampGrowth;

@Pseudo
@Mixin(value = TileArcaneLampGrowth.class, remap = false)
public class MixinTileArcaneLampGrowth {

    /**
     * @author EverNife
     * @reason Desativa esse item imundo...
     *
     * 49 lampadas dessa fazem o trabalho de 1750 sprinklers
     *
     * https://media.discordapp.net/attachments/211161697421885441/671403055568388096/Growth_Lamp.gif?
     */
    @Overwrite()
    public void func_145845_h() {

    }

    /**
     * @author EverNife
     * @reason Desativa esse item imundo...
     *
     * 49 lampadas dessa fazem o trabalho de 1750 sprinklers
     *
     * https://media.discordapp.net/attachments/211161697421885441/671403055568388096/Growth_Lamp.gif?
     */
    @Overwrite()
    private void updatePlant(){

    }

    /**
     * @author EverNife
     * @reason Desativa esse item imundo...
     *
     * 49 lampadas dessa fazem o trabalho de 1750 sprinklers
     *
     * https://media.discordapp.net/attachments/211161697421885441/671403055568388096/Growth_Lamp.gif?
     */
    @Overwrite()
    boolean drawEssentia(){
        return false;
    }

    /**
     * @author EverNife
     * @reason Desativa esse item imundo...
     *
     * 49 lampadas dessa fazem o trabalho de 1750 sprinklers
     *
     * https://media.discordapp.net/attachments/211161697421885441/671403055568388096/Growth_Lamp.gif?
     */
    @Overwrite()
    public boolean canUpdate(){
        return false;
    }

}
