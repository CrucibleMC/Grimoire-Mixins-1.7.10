package io.github.crucible.grimoire.mixins.thermaldynamics;

import cofh.thermaldynamics.duct.item.ItemGrid;
import cofh.thermaldynamics.duct.item.TileItemDuct;
import cofh.thermaldynamics.multiblock.IMultiBlockRoute;
import cofh.thermaldynamics.multiblock.Route;
import cofh.thermaldynamics.multiblock.RouteCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = {TileItemDuct.class}, remap = false)
public abstract class MixinTileItemDuct implements IMultiBlockRoute{

    @Shadow public abstract RouteCache getCache();

    @Shadow public ItemGrid internalGrid;

    /**
     * @author EverNife
     * @reason Somehow the ServoItem can tick on an ItemDuct that has no cached network.
     *
     * `cofh.thermaldynamics.duct.attachments.retriever.RetrieverItem.handleItemSending(RetrieverItem.java:156)
     * ...
     * cofh.thermaldynamics.duct.item.TileItemDuct.getCache(TileItemDuct.java:326)
     * `
     *
     * This will try to prevent NPE
     */

    @Overwrite
    public Route getRoute(IMultiBlockRoute itemDuct) {

        RouteCache routeCache = getCache();
        if (routeCache != null){ ///Add null check to prevent NPE
            for (Route outputRoute : routeCache.outputRoutes) {
                if (outputRoute.endPoint == itemDuct
                        || (outputRoute.endPoint.x() == itemDuct.x() && outputRoute.endPoint.y() == itemDuct.y() && outputRoute.endPoint.z() == itemDuct.z())) {
                    return outputRoute;
                }
            }
        }
        return null;
    }

    /**
     * @author EverNife
     * @reason Somehow the ServoItem can tick on an ItemDuct that has no cached network.
     *
     * This will try to prevent NPE
     */
    @Overwrite
    public RouteCache getCache(boolean urgent) {
        return internalGrid == null ? null : //Add null check to prevent NPE
                urgent ? internalGrid.getRoutesFromOutput(this) : internalGrid.getRoutesFromOutputNonUrgent(this);
    }
}
