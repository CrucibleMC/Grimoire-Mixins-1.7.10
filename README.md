# Grimoire-Mixins!

Collection of mixins for the core-mod [Grimoire](https://github.com/CrucibleMC/Grimoire)

### What is it?

Grimoire-Mixins are coremods that will be injected inside other mods trying to fix and\or change their natural behavior.

Some modules may require [EventHelper-1.7.10-1.11.jar](https://github.com/CrucibleMC/Grimoire-Mixins/raw/master/libs/EventHelper-1.7.10-1.11.jar)

Some modules may require other modules.

##### What is Event helper?

EventHelper is one of the best mods to help other mods work well on thermos/crucible. It helps fire bukkit events inside forge, so, its easy to check for permissions against popular protection plugins like GriefPreventionPlus and WorldGuard

Check out more over here https://github.com/gamerforEA/EventHelper

### How to Install?

* 0 - Notice that the majority of these mixins are Server Side Only! The ones who have any Client Fix are pointed out in their own description below.
* 1 - Install [Grimoire](https://github.com/CrucibleMC/Grimoire) (Just download and put it inside mods folder)
* 2 - Start the server once then turn it off, a folder called grimoire will be generated at the side of the 'mods' and 'config' folders.
* 3 - Download any Grimoire-Mixins module you want from [here](https://github.com/CrucibleMC/Grimoire-Mixins/releases)
* 4 - Put all the Grimoire-Mixins you want to use inside the folder grimoire, remember to check out the mixins requirements for each one.
* 5 - Start the server again.

### Modules

There are several modules for different things... just use the ones you want :V
Seriously, don't throw all of them if you don't need them!

##### Forge-Mixin

* Add a few changes inside forge itself, to make things easier to integrate EventHelper inside other mods.
* Requirements
* None

###### ArmourersWorkshop-Mixin

* BlockMannequin - Add EventHelper support.
* Requirements
  * ArmourersWorkshop Mod
  * EventHelper

###### Avaritia-Mixin

* EntityGapingVoid - Disabled it.
* ItemSwordInfinity - Fix bug involving death of players.
* ToolHelper - Add EventHelper support.
* Requirements
  * Avaritia Mod
  * EventHelper
  
###### Baubles-Mixin

* MixinEventHandlerEntity - Add a check for SoulBounded items (EnderIO Enchant) to prevent them from being droped on death!
* Requirements:
  * Baubles Mod

###### Botania-Mixin

* EntityEnderAirBottle - Disabled impact.
* MixinEntityManaBurst - Add EventHelper support.
* MixinEntityThornChakram - Add EventHelper support.
* MixinEntityVineBall - Add EventHelper support.
* MixinItemExchangeRod - Add EventHelper support.
* MixinItemGrassHorn - Fix small bug and add EventHelper support.
* MixinItemGravityRod - Add EventHelper support.
* MixinItemLokiRing - Add EventHelper support.
* MixinItemRainbowRod - Add EventHelper support.
* MixinItemSmeltRod - Add EventHelper support.
* MixinItemTerraformRod - Add EventHelper support.
* MixinItemWorldSeed - Add extra check to prevent FakePlayers from using it.
* MixinTileCorporeaIndex - Add EventHelper support.
* MixinTileSpreader - Set owner of it, for use in EventHelper support.
* removeBlockWithDropsCheck - Add EventHelper support.
* Requirements:
  * Botania Mod
  * EventHelper
  * Forge-Mixin module.

###### CarpentersBlocks-Mixin

* MixinPacketEnrichPlant - Possible fix for a hack that exploits packets.
* MixinPacketHandler - Fix for a hack that spams your console.
* Requirements:
  * CarpentersBlocks Mod

###### CoFH-Mixin

* MixinSecurityHelper - Fix the most mother-fuc*** spam bug of all time.
* MixinThermalTile - Attempt to fix a hack exploit.
* Requirements:
  * CoFH Mod

###### DraconicEvolution-Mixin

* MixinEntityCustomDragon - Make the mod dragon fire the same event at the original minecraft dragon (it will open a portal!)
* Requirements:
  * DraconicEvolution Mod

###### EnderIO-Mixin

* MixinEntityCustomDragon - Make the mod dragon fire the same event at the original minecraft dragon (it will open a portal!)
* Requirements:
  * EnderIO Mod

###### ExtraUtilities-Mixin

* MixinTileEntityEnderQuarry - Add EventHelper support.
* MixinXUHelper - Extra check for block existency.
* TileEntityTransferNode - Disable Speed Upgrade Modifier.
* Requirements:
  * ExtraUtilities Mod
  * EventHelper
  * Forge-Mixin module.
  
###### IC2-Mixin

* MixinUtil - IndustrialCraft 2 uses methods that are old even for forge itself, because a few of them, IC2 is not compatible with CrucibleMC. This fix that! With this mixin you can run IC2 inside CrucibleMC.
* Requirements:
  * IC2 Mod

###### Mekanism-Mixin

* MixinBinRecipe - Disable bin recipes.
* MixinFlamethrower - Add EventHelper support.
* MixinStackUtils - Fix a few comparing methods.
* MixinTileEntityDigitalMiner - Add weak reference to the Digital miner fakeplayers.
* MixinContainers - Disable Shift-Click on All Mekanism Containers.
* Requirements:
  * Mekanism Mod
  * EventHelper
  * Forge-Mixin module.

###### MineFactoryReloaded-Mixin

* MixinBlockRubberSapling - Disable the big rubber trees, all of them, now they behave like normal rubber trees.
* Requirements:
  * MineFactoryReloaded Mod

###### NEI-Mixin

* NEIServerUtils - Fix exploit where hackers could forceop them. Add permission check for "nei.gamemode".
* Requirements:
  * NEI Mod
  * EventHelper

###### OpenBlocks-Mixin

* MixinItemCursor - Add EventHelper support.
* Requirements:
  * OpenBlocks Mod
  * EventHelper

###### Thaumcraft-Mixin

* MixinBlockTable - Add extra check for fakeplayers and enforce gui close, to prevent dups.
* EntityBottleTaint - Add EventHelper support.
* MixinEntityCultistPortal - Add EventHelper support.
* MixinEntityEldritchGolem - Add EventHelper support.
* MixinEntityPrimalOrb - Add EventHelper support.
* MixinItemFocusPortableHole - Add EventHelper support.
* MixinItemFocusPrimal - Add EventHelper support.
* MixinItemFocusTrade - Add EventHelper support.
* MixinThaumcraftCraftingManager - Fix a massive lag in certain conditions. We have created a cache for the CraftingManager, so stops rechecking the same recipe over and over again.
* MixinTileArcaneBore - Add EventHelper support.
* MixinTileNode - Disable hungry node grief.
* MixinWandManager - Add EventHelper support.
* MixinTileArcaneLampGrowth - Disable ArcaneLamp of Growth (Too much overpowered)
* Requirements:
  * Thaumcraft Mod
  * EventHelper
  * Forge-Mixin module.

###### ThaumicEnergistics-Mixin

* MixinThETileInventory - Attempt to fix a dupe.
* Requirements:
  * ThaumicEnergistics Mod
  
###### WorldEdit-Mixin

* MixinUtilityCommands - Disable command '//calc'.
* Requirements:
  * WorldEdit Mod