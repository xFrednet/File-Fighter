package com.gmail.xfrednet.filefighter.level.tile;

import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.level.Tile;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class SpaceTile extends Tile {
	@Override
	public void render(int x, int y, Screen screen, Level level) {
		screen.drawTile(x << 5, y << 5, Sprite.space_tile_sprite);
	}
	
	@Override
	public int getID() {
		return List.SPACE_TILE_ID;
	}
}
