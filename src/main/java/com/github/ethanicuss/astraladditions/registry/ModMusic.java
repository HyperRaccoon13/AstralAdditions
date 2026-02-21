package com.github.ethanicuss.astraladditions.registry;

import net.minecraft.sound.MusicSound;

import javax.annotation.Nullable;
import java.util.IdentityHashMap;
import java.util.Map;

public class ModMusic {
    private static final int GAME_MIN_DELAY = 12000;
    private static final int GAME_MAX_DELAY = 24000;//12000, 24000
    public static final MusicSound MOON               = new MusicSound(ModSounds.MUSIC_MOON, GAME_MIN_DELAY, GAME_MAX_DELAY, false);
    public static final MusicSound MENU               = new MusicSound(ModSounds.MUSIC_ASTRAL_LAKES, 0, 0, true);
    public static final MusicSound POST_MOON          = new MusicSound(ModSounds.MUSIC_POST_MOON, 0, 0, true);
    public static final MusicSound DAY                = new MusicSound(ModSounds.MUSIC_DAY, GAME_MIN_DELAY, GAME_MAX_DELAY, false);
    public static final MusicSound NIGHT              = new MusicSound(ModSounds.MUSIC_NIGHT, GAME_MIN_DELAY, GAME_MAX_DELAY, false);
    public static final MusicSound OW_DAY             = new MusicSound(ModSounds.MUSIC_OW_DAY, GAME_MIN_DELAY, GAME_MAX_DELAY, false);
    public static final MusicSound OW_NIGHT           = new MusicSound(ModSounds.MUSIC_OW_NIGHT, GAME_MIN_DELAY, GAME_MAX_DELAY, false);
    public static final MusicSound OW_CAVE            = new MusicSound(ModSounds.MUSIC_OW_CAVE, GAME_MIN_DELAY, GAME_MAX_DELAY, false);
    public static final MusicSound OW_SCARY           = new MusicSound(ModSounds.MUSIC_OW_SCARY, GAME_MIN_DELAY, GAME_MAX_DELAY, false);
    public static final MusicSound ORBIT              = new MusicSound(ModSounds.MUSIC_ORBIT, GAME_MIN_DELAY, GAME_MAX_DELAY, false);
    public static final MusicSound MERCURY            = new MusicSound(ModSounds.MUSIC_MERCURY, GAME_MIN_DELAY, GAME_MAX_DELAY, false);
    public static final MusicSound MARS               = new MusicSound(ModSounds.MUSIC_MARS, GAME_MIN_DELAY, GAME_MAX_DELAY, false);
    public static final MusicSound END                = new MusicSound(ModSounds.MUSIC_END, GAME_MIN_DELAY, GAME_MAX_DELAY, false);
    public static final MusicSound END_BOSS           = new MusicSound(ModSounds.MUSIC_END_BOSS, 0, 0, true);
    public static final MusicSound WITHER             = new MusicSound(ModSounds.MUSIC_WITHER, 0, 0, true);
    public static final MusicSound WITHER_PHASE2      = new MusicSound(ModSounds.MUSIC_WITHER_PHASE2, 0, 0, true);
    public static final MusicSound WITHER_SPAWN       = new MusicSound(ModSounds.MUSIC_WITHER_SPAWN, 0, 0, true);
    public static final MusicSound WITHER_DEATH       = new MusicSound(ModSounds.MUSIC_WITHER_DEATH, 0, 0, true);
    public static final MusicSound COMBAT             = new MusicSound(ModSounds.MUSIC_COMBAT, 0, 0, true);
    public static final MusicSound COMBAT_END         = new MusicSound(ModSounds.MUSIC_COMBAT_END, 0, 0, true);
    public static final MusicSound SHIMMER_BLAZE      = new MusicSound(ModSounds.MUSIC_SHIMMER_BLAZE, 0, 0, true);
    public static final MusicSound ASTRAL_LAKES_REMIX = new MusicSound(ModSounds.MUSIC_ASTRAL_LAKES_REMIX, 0, 0, true);


	private static final Map<MusicSound, String> TRACK_KEYS = new IdentityHashMap<>();

	static {
		register(MOON,              "moon");
		register(POST_MOON,         "post_moon");
		register(DAY,               "day");
		register(NIGHT,             "night");
		register(OW_DAY,            "ow_day");
		register(OW_NIGHT,          "ow_night");
		register(OW_CAVE,           "ow_cave");
		register(OW_SCARY,          "ow_scary");
		register(ORBIT,             "orbit");
		register(MERCURY,           "mercury");
		register(MARS,              "mars");
		register(END,               "end");
		register(END_BOSS,          "end_boss");
		register(WITHER,            "wither");
		register(WITHER_PHASE2,     "wither_phase2");
		register(WITHER_SPAWN,      "wither_spawn");
		register(WITHER_DEATH,      "wither_death");
		register(COMBAT,            "combat");
		register(COMBAT_END,        "combat_end");
		register(SHIMMER_BLAZE,     "shimmer_blaze");
		register(ASTRAL_LAKES_REMIX,"astral_lakes_remix");
	}

	private static void register(MusicSound sound, String key) {
		TRACK_KEYS.put(sound, key);
	}

	public static boolean isCustom(MusicSound sound) {
		return TRACK_KEYS.containsKey(sound);
	}

	@Nullable
	public static String getTrackKey(MusicSound sound) {
		return TRACK_KEYS.get(sound);
	}

}