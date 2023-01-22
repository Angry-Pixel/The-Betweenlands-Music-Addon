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
import thebetweenlands.common.entity.mobs.EntityEmberlingShaman;
import thebetweenlands.common.sound.BLSoundEvent;

public class EmberlingShamanMusicProvider implements IEntityMusicProvider {

	@Override
	public IEntityMusic getEntityMusic(Entity entity) {
		if(entity instanceof EntityEmberlingShaman) {
			return new Music((EntityEmberlingShaman) entity);
		}
		return null;
	}

	private class Music implements IEntityMusic {

		private final EntityEmberlingShaman entity;

		public Music(EntityEmberlingShaman entity) {
			this.entity = entity;
		}

		@Override
		public BLSoundEvent getMusicFile(EntityPlayer listener) {
			return null;
		}

		@Override
		public IEntitySound getMusicSound(EntityPlayer listener) {
			return new EntityMusicSound<Entity>(TheBetweenlandsMusic.EMBERLING_SHAMAN_LOOP, SoundCategory.MUSIC, this.entity, this, 1, AttenuationType.NONE);
		}

		@Override
		public double getMusicRange(EntityPlayer listener) {
			return 32.0;
		}

		@Override
		public boolean isMusicActive(EntityPlayer listener) {
			return this.entity.isEntityAlive();
		}

		@Override
		public int getMusicLayer(EntityPlayer listener) {
			return EntityMusicLayers.BOSS;
		}
		
		@Override
		public boolean canInterruptOtherEntityMusic(EntityPlayer listener) {
			return false;
		}

	}

}
