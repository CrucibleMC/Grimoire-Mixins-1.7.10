package io.github.crucible.grimoire.mixins.tinkersdefense;

import cpw.mods.fml.common.FMLLog;
import lance5057.tDefense.TinkersDefense;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = TinkersDefense.class, remap = true)
public abstract class MixinTinkersDefense {

    /**
     * @author EverNife
     * @reason The mod should not register client commands on the server side
     * @return
     */
    @Redirect(method = "preInit", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ClientCommandHandler;registerCommand(Lnet/minecraft/command/ICommand;)Lnet/minecraft/command/ICommand;")) //ClientCommandHandler
    public ICommand preInit(ClientCommandHandler instance, ICommand iCommand) {
        FMLLog.warning("Ignoring registraion of the command [" + iCommand.getCommandName() +"] as it is ClientSide!");
        return iCommand;
    }

}