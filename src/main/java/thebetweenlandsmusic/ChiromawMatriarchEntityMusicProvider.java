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
import thebetweenlands.common.entity.mobs.EntityChiromawMatriarch;
import thebetweenlands.common.sound.BLSoundEvent;

public class ChiromawMatriarchEntityMusicProvider implements IEntityMusicProvider {

	@Override
	public IEntityMusic getEntityMusic(Entity entity) {
		if(entity instanceof EntityChiromawMatriarch) {
			return new Music((EntityChiromawMatriarch) entity);
		}
		return null;
	}

	private class Music implements IEntityMusic {

		private final EntityChiromawMatriarch entity;

		private int lastTargetTime = -1;

		public Music(EntityChiromawMatriarch entity) {
			this.entity = entity;
		}

		@Override
		public BLSoundEvent getMusicFile(EntityPlayer listener) {
			return null;
		}

		@Override
		public IEntitySound getMusicSound(EntityPlayer listener) {
			return new EntityMusicSound<Entity>(TheBetweenlandsMusic.CHIROMAW_MATRIARCH_LOOP, SoundCategory.MUSIC, this.entity, this, 1, AttenuationType.NONE);
		}

		@Override
		public double getMusicRange(EntityPlayer listener) {
			return 64.0;
		}

		@Override
		public boolean isMusicActive(EntityPlayer listener) {
			if(!this.entity.isEntityAlive()) {
				return false;
			}
			if(this.entity.getAttackTarget() == listener) {
				this.lastTargetTime = this.entity.ticksExisted;
				return true;
			}
			return this.lastTargetTime >= 0 && this.entity.ticksExisted - this.lastTargetTime < 30*20;
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
