package com.gmail.xfrednet.filefighter.level;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.entity.livingentitys.TestEntity;
import com.gmail.xfrednet.filefighter.entity.livingentitys.enemy.TextFileEntity;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.cameras.ControllableCamera;
import com.gmail.xfrednet.filefighter.util.Input;

import java.io.File;
import java.util.Random;

/**
 * Created by xFrednet on 08.02.2016.
 */
public class FileLevel extends Level {
	
	public File file;
	public Random random;
	
	public FileLevel(Player player, Input input, Screen screen, File file) {
		super(player, input, screen);
		
		random = new Random();
		
		if (!file.isDirectory()) {
			System.out.println("[ERROR] FileLevel: given File is a File not a directory: " + file.getPath());
			
			WIDTH = 25;
			HEIGHT = 25;
			tileIDs = new int[WIDTH * HEIGHT];
			return;
		}
		
		generate(file);
	}
	
	private void generate(File file) {
		this.file = file;
		WIDTH = 25;
		HEIGHT = 25;
		
		tileIDs = new int[WIDTH * HEIGHT];
		
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
		
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) 
				entityList.add(getFileEntity(files[i]));
		}
		
		
		
	}
	
	private Entity getFileEntity(File file) {
		String fileName = file.getName();
		String fileEnding = "";
		
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			fileEnding = fileName.substring(i+1);
		}
		
		int x = (random.nextInt(WIDTH - 2) + 1) * TILE_SIZE;
		int y = (random.nextInt(HEIGHT - 2) + 1) * TILE_SIZE;
		
		switch (fileEnding) {
			case "txt": return new TextFileEntity(x, y, getPlayer(), fileName);
		}
		
		return new TestEntity(x, y, "ERROR");
		
	}
	
}
