package io.github.crucible.grimoire.mixins.forge;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderManager.class)
public abstract class MixinRenderManager {

    @Shadow
    public static boolean debugBoundingBox;
    @Shadow
    public TextureManager renderEngine;

    @Shadow
    protected abstract void renderDebugBoundingBox(Entity p_85094_1_, double p_85094_2_, double p_renderDebugBoundingBox_4_, double p_85094_4_, float p_renderDebugBoundingBox_8_, float p_85094_6_);

    @Shadow
    public abstract Render getEntityRenderObject(Entity p_78713_1_);

    /**
     * @author juanmuscaria
     * @reason Evitar crashar ao reiderizar uma entidade.
     */
    @Overwrite
    @Debug(print = true)
    public boolean func_147939_a(Entity p_147939_1_, double p_147939_2_, double p_147939_4_, double p_147939_6_, float p_147939_8_, float p_147939_9_, boolean p_147939_10_) {
        Render render = null;
        try {
            render = this.getEntityRenderObject(p_147939_1_);
            if (render != null && this.renderEngine != null) {
                if (!render.isStaticEntity() || p_147939_10_) {
                    try {
                        render.doRender(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
                    } catch (Throwable var18) {
                        //System.out.println(CrashReport.makeCrashReport(var18, "Rendering entity in world").getCompleteReport());
                    }

                    try {
                        render.doRenderShadowAndFire(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
                    } catch (Throwable var17) {
                        //System.out.println(CrashReport.makeCrashReport(var17, "Post-rendering entity in world").getCompleteReport());
                    }

                    if (debugBoundingBox && !p_147939_1_.isInvisible() && !p_147939_10_) {
                        try {
                            this.renderDebugBoundingBox(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
                        } catch (Throwable var16) {
                            //System.out.println(CrashReport.makeCrashReport(var16, "Rendering entity hitbox in world").getCompleteReport());
                        }
                    }
                }
            } else return this.renderEngine == null;

            return true;
        } catch (Throwable var19) {
            CrashReport crashreport = CrashReport.makeCrashReport(var19, "Rendering entity in world");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being rendered");
            p_147939_1_.addEntityCrashInfo(crashreportcategory);
            CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Renderer details");
            crashreportcategory1.addCrashSection("Assigned renderer", render);
            crashreportcategory1.addCrashSection("Location", CrashReportCategory.func_85074_a(p_147939_2_, p_147939_4_, p_147939_6_));
            crashreportcategory1.addCrashSection("Rotation", p_147939_8_);
            crashreportcategory1.addCrashSection("Delta", p_147939_9_);
            // System.out.println(crashreport.getCompleteReport());
            return false;
        }
    }
}

