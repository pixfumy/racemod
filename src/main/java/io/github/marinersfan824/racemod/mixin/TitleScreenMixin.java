package io.github.marinersfan824.racemod.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {
    @Shadow
    private String splashText;
    @Inject(method = "<init>()V", at = @At("RETURN"))
    public void changeSplashText(CallbackInfo ci) {
        String[] splashes = {"Don't bridge with sand!",
                "Doug has plot armor!",
                "Shh! Illumina doesn't know ledge yet!",
                "Fall tower is not better!",
                "Boat is meta.",
                "One quadrant that I can dig in!",
                "Fruit portal!",
                "Is Msushi on peaceful?",
                "Leave your horse here, the stronghold's on that coast!",
                "Anthony those are pearls not food!",
                "Show us your pokemon cards if you have downtime!",
                "I believe in The Nation and The Nation believes in me.",
                "Judges, do you see it?",
                "Why do Doug The Pig have 4 mods?",
                "They did it! They broke the record live!",
                "Why is Silliest in creative mode?",
                "and Illumina finds a pearl on the floor!",
                "@Skyrota can you get rid of caves?",
                "There's baby chickens at spawn!",
                "Don't forget the spider protection!",
                "He did it! He broke the record live!",
                "viewbotzach is my favorite twitch chatter.",
                "when yoy the minecraft speedrun the world hahahah rnaodm seed lmao glitched",
                "When is couriway playing?",
                "Do a safety bed!",
                "Serious Dedication.",
                "That's a perch.",
                "Make a shield!",
                "Battle of the children!",
                "Why isn't sharpie getting leaves?",
                "Team Brzęczyszczykiewicz",
                "He did it! He broke the record live!",
                "WH OMEGALUL are you",
                "There shouldn't be iron above Y64!",
                "Danger Perch!",
                "Subscribe to Cscuile!",
                "Taro7",
                "ILLUMINA1337在MINECRAFT1.7戰役中F3得到Scandal",
                "Ma porko dio di monio!",
                "Che cazzo se dici, Crafterdark?",
                "Bad strategy.",
                "Regardless of who wins, we need to beat Illumina.",
                "Find the EDSA!",
                "Forge beds do more damage!",
                "Max is doing all versions!",
                "The korean runner got a 23:42!",
                "Sub 1 Temple is not possible.",
                "After this match, let's do Snow Seed.",
                "Hi Cubex!",
                "And guess what? A 3 eye.",
                "illU"
        };
        this.splashText = splashes[(new Random()).nextInt(splashes.length)];
    }
}