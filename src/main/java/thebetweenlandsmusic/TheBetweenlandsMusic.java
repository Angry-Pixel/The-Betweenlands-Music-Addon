package thebetweenlandsmusic;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.InstanceFactory;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import thebetweenlands.client.audio.ambience.AmbienceManager;
import thebetweenlands.client.audio.ambience.list.LocationAmbienceType;
import thebetweenlands.common.BetweenlandsAPI;
import thebetweenlands.common.entity.mobs.EntityChiromawMatriarch;
import thebetweenlands.common.entity.mobs.EntityEmberlingShaman;
import thebetweenlands.common.entity.mobs.EntitySpiritTreeFaceLarge;
import thebetweenlands.common.world.storage.location.LocationAmbience;
import thebetweenlands.common.world.storage.location.LocationAmbience.EnumLocationAmbience;
import thebetweenlands.common.world.storage.location.LocationSludgeWormDungeon;
import thebetweenlands.common.world.storage.location.LocationStorage;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, acceptedMinecraftVersions = ModInfo.MC_VERSIONS, dependencies = ModInfo.DEPENDENCIES, clientSideOnly = true)
public class TheBetweenlandsMusic {
	@Instance(ModInfo.ID)
	public static TheBetweenlandsMusic instance;

	@InstanceFactory
	public static TheBetweenlandsMusic createInstance() {
		return new TheBetweenlandsMusic();
	}


	// Location music
	public static final SoundEvent WIGHT_FORTRESS = new SoundEvent(new ResourceLocation(ModInfo.ID, "wight_fortress"));
	public static final SoundEvent LABYRINTHINE_VAULTS = new SoundEvent(new ResourceLocation(ModInfo.ID, "labyrinthine_vaults"));

	// Mob music
	public static final SoundEvent CHIROMAW_MATRIARCH_LOOP = new SoundEvent(new ResourceLocation(ModInfo.ID, "chiromaw_matriarch_loop"));
	public static final SoundEvent SPIRIT_TREE_LOOP = new SoundEvent(new ResourceLocation(ModInfo.ID, "spirit_tree_loop"));
	public static final SoundEvent EMBERLING_SHAMAN_LOOP = new SoundEvent(new ResourceLocation(ModInfo.ID, "emberling_shaman_loop"));

	private static final List<SoundEvent> SOUNDS = new ArrayList<>();

	static {
		SOUNDS.add(CHIROMAW_MATRIARCH_LOOP);
		SOUNDS.add(SPIRIT_TREE_LOOP);
		SOUNDS.add(EMBERLING_SHAMAN_LOOP);
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		BetweenlandsAPI.getInstance().registerEntityMusicProvider(EntityChiromawMatriarch.class, new ChiromawMatriarchEntityMusicProvider());
		BetweenlandsAPI.getInstance().registerEntityMusicProvider(EntitySpiritTreeFaceLarge.class, new SpiritTreeEntityMusicProvider());
		BetweenlandsAPI.getInstance().registerEntityMusicProvider(EntityEmberlingShaman.class, new EmberlingShamanMusicProvider());

		AmbienceManager.INSTANCE.registerAmbience(new LocationAmbienceType(EnumLocationAmbience.WIGHT_TOWER, WIGHT_FORTRESS) {
			@Override
			public int getPriority() {
				return 6;
			}

			@Override
			public boolean isActive() {
				return super.isActive() && !this.getAmbience().getLocation().getName().equals("wight_tower_boss");
			}

			@Override
			public SoundCategory getCategory() {
				return SoundCategory.MUSIC;
			}
		});

		AmbienceManager.INSTANCE.registerAmbience(new LocationAmbienceType(EnumLocationAmbience.SLUDGE_WORM_DUNGEON, LABYRINTHINE_VAULTS) {
			@Override
			public int getPriority() {
				return 6;
			}

			@Override
			public boolean isActive() {
				if(super.isActive()) {
					LocationAmbience ambience = this.getAmbience();
					if(ambience != null) {
						LocationStorage location = ambience.getLocation();
						if(location instanceof LocationSludgeWormDungeon) {
							return this.getPlayer().getPositionEyes(1).y < ((LocationSludgeWormDungeon) location).getStructurePos().getY();
						}
					}
				}
				return false;
			}

			@Override
			public SoundCategory getCategory() {
				return SoundCategory.MUSIC;
			}
		});
	}

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		IForgeRegistry<SoundEvent> registry = event.getRegistry();

		for(SoundEvent sound : SOUNDS) {
			sound.setRegistryName(sound.getSoundName());
			registry.register(sound);
		}
	}
}
