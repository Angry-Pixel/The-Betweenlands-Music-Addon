package thebetweenlandsmusic;

import net.minecraft.client.audio.ISound.AttenuationType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import thebetweenlands.api.audio.IEntitySound;
import thebetweenlands.api.entity.IEntityMusic;
import thebetweenlands.api.entity.IEntityMusicProvider;
import thebetweenlands.client.audio.EntityMusicLayers;
import thebetweenlands.client.audio.EntityMusicSound;
import thebetweenlands.common.entity.mobs.EntitySpiritTreeFaceLarge;
import thebetweenlands.common.registries.BiomeRegistry;
import thebetweenlands.common.sound.BLSoundEvent;

public class SpiritTreeEntityMusicProvider implements IEntityMusicProvider {

	@Override
	public IEntityMusic getEntityMusic(Entity entity) {
		if(entity instanceof EntitySpiritTreeFaceLarge) {
			return new Music((EntitySpiritTreeFaceLarge) entity);
		}
		return null;
	}

	private class Music implements IEntityMusic {

		private final EntitySpiritTreeFaceLarge entity;

		public Music(EntitySpiritTreeFaceLarge entity) {
			this.entity = entity;
		}

		@Override
		public BLSoundEvent getMusicFile(EntityPlayer listener) {
			return null;
		}

		@Override
		public IEntitySound getMusicSound(EntityPlayer listener) {
			return new EntityMusicSound<Entity>(TheBetweenlandsMusic.SPIRIT_TREE_LOOP, SoundCategory.MUSIC, this.entity, this, 1, AttenuationType.NONE);
		}

		@Override
		public double getMusicRange(EntityPlayer listener) {
			return 42.0;
		}

		@Override
		public boolean isMusicActive(EntityPlayer listener) {
			return this.entity.isEntityAlive() && this.entity.world.getBiome(listener.getPosition()) == BiomeRegistry.SWAMPLANDS_CLEARING;
		}

		@Override
		public int getMusicLayer(EntityPlayer listener) {
			return EntityMusicLayers.BOSS;
		}

	}

}
