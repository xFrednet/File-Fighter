package com.gmail.xfrednet.filefighter.level;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.entity.livingentitys.TestEntity;
import com.gmail.xfrednet.filefighter.entity.livingentitys.enemy.Slime;
import com.gmail.xfrednet.filefighter.graphics.Camera;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.cameras.ControllableCamera;
import com.gmail.xfrednet.filefighter.util.Input;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class Level {
	
	public static final int TILE_SIZE = 32;
	public int WIDTH;
	public int HEIGHT;
	public int[] tileIDs;
	
	public List<Entity> entityList = new ArrayList<>();
	
	private Player player;
	private Camera camera;
	
	public Level(int width, int height, Player player, Input input, Screen screen) {
		WIDTH = width;
		HEIGHT = height;
		
		tileIDs = new int[WIDTH * HEIGHT];
		
		camera = new ControllableCamera(this, screen, input);
		
		//player
		this.player = player;
		player.setCamera(camera);
		
		generate();
	}
	public Level(Player player, Input input, Screen screen) {
		camera = new ControllableCamera(this, screen, input);
		
		//player
		this.player = player;
		player.setCamera(camera);
		player.setPosition(60, 60);

	}
	
	private void generate() {
		for (int i = 0; i < tileIDs.length; i++) {
			tileIDs[i] = Tile.List.SPACE_TILE_ID;
		}
		
		//Walls
		for (int y = 0; y < HEIGHT; y++) {
			tileIDs[y * WIDTH] = Tile.List.WALL_ID;
			tileIDs[WIDTH - 1 + y * WIDTH] = Tile.List.WALL_ID;
		}
		for (int x = 0; x < WIDTH; x++) {
			tileIDs[x] = Tile.List.WALL_ID;
			tileIDs[x + (HEIGHT - 1) * WIDTH] = Tile.List.WALL_ID;
		}
		
		entityList.add(new Slime(32 * 4, 32 * 4, player, "xFrednet"));
		
		entityList.add(new TestEntity(32 * 10, 32 * 10, "TestEntity"));
		entityList.add(new TestEntity(32 * 20, 32 * 10, "TestEntity"));
		entityList.add(new TestEntity(32 * 10, 32 * 20, "TestEntity"));
		entityList.add(new TestEntity(32 * 20, 32 * 20, "TestEntity"));
		
	}
	
	/*
	* Getters
	* */
	//tiles
	public int getTileID(int x, int y) {
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return 0;
		return tileIDs[x + y * WIDTH];
	}
	public Tile getTile(int x, int y) {
		return Tile.List.getTileByID(getTileID(x, y));
	}
	public boolean isSolid(int x, int y) {
		return getTile(x, y).isSolid();
	}
	
	//entities
	public Entity getEntity(int entityID) {
		if (entityID == player.getID()) return player;
		
		for (int i = 0; i < entityList.size(); i++) {
			if (entityList.get(i).getID() == entityID) 
				return entityList.get(i);
		}
		
		return null;
	}
	
	public Player getPlayer() {
		return player;
	}
	public Camera getCamera() {
		return camera;
	}
	
	/*
	* Game loop util 
	* */
	public void render(Screen screen) {
		int xScroll = camera.getXOffset();
		int yScroll = camera.getYOffset();
		screen.setOffset(xScroll, yScroll);
		
		int x0 = xScroll >> 5;
		int x1 = (xScroll + screen.WIDTH + 32) >> 5;
		int y0 = yScroll >> 5;
		int y1 = (yScroll + screen.HEIGHT + 32) >> 5;
		
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) {
					Tile.List.nullTile.render(x, y, screen, this);
				} else {
					Tile.List.getTileByID(tileIDs[x + y * WIDTH]).render(x, y, screen, this);
				}
			}
		}
		
		for (int i = 0; i < entityList.size(); i++) {
			entityList.get(i).render(screen);
		}
		
	}
	public void update() {
		for (int i = 0; i < entityList.size(); i++) {
			entityList.get(i).update(this);
		}
	}
	
}
