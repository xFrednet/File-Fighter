package com.gmail.xfrednet.filefighter.level.tileentity;

import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.GUIManager;
import com.gmail.xfrednet.filefighter.graphics.Particle;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.FileLevel;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.level.TileEntity;

import java.io.File;

/**
 * Created by xFrednet on 23.03.2016 at 18:44.
 */
public class FolderTileEntity extends TileEntity {
	
	File file;
	int timer;
	boolean locked = true;
	
	public FolderTileEntity(int x, int y, File file) {
		super(x, y);
		this.file = file;
		
		if (file == null) {
			remove();
		}
	}
	
	@Override
	protected Sprite getSprite() {
		return Sprite.TileEntities.folder_sprite;
	}
	
	@Override
	public void containsPlayer(int x, int y, Level level, Player player) {
		timer++;
	}
	@Override
	public void playerExited(int x, int y, Level level, Player player) {
		timer = 0;
	}
	
	@Override
	public void levelCleared(Level level, Player player) {
		locked = false;
		timer = 0;
	}
	
	@Override
	public void update(Level level) {
		if (!locked || random.nextInt(10) == 0) {
			double angle = ((Math.PI * 2) * random.nextDouble()) - Math.PI;
			double speed = 16;
			double mx = speed * Math.sin(angle);
			double my = speed * Math.cos(angle);
			double sx = x * Level.TILE_SIZE - mx + (Level.TILE_SIZE - 4) / 2 + 2;
			double sy = y * Level.TILE_SIZE - my + (Level.TILE_SIZE - 4) / 2 + 2;
			
			level.addParticle(new Particle(sx, sy, level, 20, angle, speed / 10, Sprite.Particles.smoke_particles));
		}
		
		if (timer >= 30 && !locked) {
			level.changeLevel(new FileLevel(level.getPlayer(), level.getScreen(), file, (GUIManager) level.getLevelGUI().getParent()));
		}
		
	}
}
