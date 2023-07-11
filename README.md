# racemod
A mod for 1.7 ranked that makes major sources of RNG consistent between different players playing on the same seed.

## Features 

- Endermen, blaze, spider, sheep, cow, pig, and chicken rates are all standardized.
- Player spawns are standardized on new worlds
- Blaze spawns are standardized
- Eye breaks are standardized
- Flint RNG is standardized
- Eyes of ender always point towards the portal room regardless of relogs
- All ravines are removed
- Mobs will not spawn in temples during the daytime
- All water, lava pools, and flowing lava sources below y60 are removed (this is different from lava *lakes*, which still occur at y10).
- Surface lava pools are more common
- Desert Hills are replaced by normal desert to make terrain flatter
- Ghast spawns are disabled
- Animal weights are tweaked to make chickens more common
- Temple loot is modified so that each temple has at minimum 4 iron (1 per chest) 

## Commands for seedfinders

If you have cheats enabled, there are two seedfinder commands available:

`/rates` will tell you the current rates on the world you're in in the following output: 

![rates](https://user-images.githubusercontent.com/95588510/186546483-557a7da0-8168-4bb9-98e0-1effe08bc629.png)

`/resetRNGSeed [blazeRodSeed|blazeSpawnSeed|enderEyeSeed|featherSeed|enderEyeSeed|flintSeed|stringSeed|woolSeed|porkChopSeed|beefSeed]` will allow you to reset the seed for one source of RNG. For example, if `/rates` tells you you have bad blaze rates, you can do `/rates blazeRodSeed reset` and then check again with `/rates`. Repeat until you're happy with the RNG. Note that the seeds are auto-completable and you do not need to memorize them.

Note that these two commands were previously part of [Seedfinder's Mod](https://github.com/pixfumy/seedfinders-mod), but have since been added to racemod instead.

This mod requires Legacy Fabric 0.12.12+.
