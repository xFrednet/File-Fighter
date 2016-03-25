package com.gmail.xfrednet.filefighter.level.tile;

import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.level.Tile;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class WallTile extends Tile {
	
	int index = -1;
	
	@Override
	public void render(int x, int y, Screen screen, Level level) {
		if (index == -1) index = getSpriteArrayIndex(x, y, level);
		screen.drawTile(x << 5, y << 5, Sprite.Tiles.wall_tile_sprite[index]);
	}
	
	@Override
	public int getID() {
		return List.WALL_ID;
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
}
