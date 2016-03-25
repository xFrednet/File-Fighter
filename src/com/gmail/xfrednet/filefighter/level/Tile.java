package com.gmail.xfrednet.filefighter.level;

import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.tile.NullTile;
import com.gmail.xfrednet.filefighter.level.tile.SpaceTile;
import com.gmail.xfrednet.filefighter.level.tile.WallTile;

/**
 * Created by xFrednet on 06.02.2016.
 */
public abstract class Tile {
	
	protected Sprite[] sprites;
	
	/*
	* Constructor
	* */
	protected Tile() {}
	
	/*
	* Abstract Methods
	* */
	abstract public void render(int x, int y, Screen screen, Level level);
	abstract public int getID();
	
	/*
	* Overridden Methods
	* */
	public boolean isSolid() {
		return false;
	}
	
	/*
	* Util
	* */
	protected int setBit(int n, int pos) {
		return n | (1 << pos);
	}
	protected int getSpriteArrayIndex(int x, int y, Level level) {
		int tileID = getID();
		int index = 0;
		
		if (level.getTileID(x, y + 1) == tileID) index = setBit(index, 0);
		if (level.getTileID(x + 1, y) == tileID) index = setBit(index, 1);
		if (level.getTileID(x, y - 1) == tileID) index = setBit(index, 2);
		if (level.getTileID(x - 1, y) == tileID) index = setBit(index, 3);
		
		return index;
	}
	
	/*
	* List class
	* */
	public static class List {
		
		/*
		* IDs
		* */
		public static final int NULL_TILE_ID = 0;
		public static final int SPACE_TILE_ID = 1;
		public static final int WALL_ID = 2;
		
		/*
		* Tiles
		* */
		public static Tile nullTile = new NullTile();
		public static Tile wallTile = new WallTile();
		public static Tile spaceTile = new SpaceTile();
		
		/*
		* Methods
		* */
		private List() {}
		
		public static Tile getTileByID(int tileID) {
			switch (tileID) {
				case NULL_TILE_ID: return nullTile;
				case SPACE_TILE_ID: return spaceTile;
				case WALL_ID: return wallTile;
				default: return nullTile;
			}
		}
		
	} 
}
