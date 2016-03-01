package com.gmail.xfrednet.filefighter.level;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.entity.livingentitys.Dummy;
import com.gmail.xfrednet.filefighter.entity.livingentitys.TestEntity;
import com.gmail.xfrednet.filefighter.entity.livingentitys.enemy.JPGFileEntity;
import com.gmail.xfrednet.filefighter.entity.livingentitys.enemy.TextFileEntity;
import com.gmail.xfrednet.filefighter.graphics.GUIManager;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.util.Input;

import java.io.File;
import java.util.Random;

/**
 * Created by xFrednet on 08.02.2016.
 */
public class FileLevel extends Level {
	
	public File file;
	public Random random;
	
	public FileLevel(Player player, Input input, Screen screen, File file, GUIManager guiManager) {
		super(player, input, screen, guiManager);
		
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
				spawn(getFileEntity(files[i]));
		}
		
		
		
	}
	
	private Entity getFileEntity(File file) {
		String fileName = file.getName();
		
		//file ending
		String fileEnding = "";
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			fileEnding = fileName.substring(i + 1);
		}
		fileEnding = fileEnding.toLowerCase();
		
		//spawn location
		int x = (random.nextInt(WIDTH - 2) + 1) * TILE_SIZE + TILE_SIZE / 2;
		int y = (random.nextInt(HEIGHT - 2) + 1) * TILE_SIZE + TILE_SIZE / 2;
		
		//entity choice
		Entity fileEntity = null;
		switch (fileEnding) {
			case "lnk": fileEntity = getEntityByName(x, y, fileName); break;
			//text files
			case "txt": return new TextFileEntity(x, y, this, getPlayer(), fileName);
			
			//JGP
			case "jpg": return new JPGFileEntity(x, y, this, getPlayer(), fileName);
			case "jpeg": return new JPGFileEntity(x, y, this, getPlayer(), fileName);
			case "jpe": return new JPGFileEntity(x, y, this, getPlayer(), fileName);
			
			//Test code
			case "dummy": return new Dummy(250, 250, this, "dummy");
		}
		if (fileEntity == null) {
			return new TestEntity(x, y, this, "KNOWN ERROR");
		} else {
			return fileEntity;
		}
		
	}
	
	private Entity getEntityByName(double x, double y, String fileName) {
		fileName.toLowerCase();
		
		if (fileName.contains("mozilla firefox")) {
			return new TestEntity(x, y, this, "Mozilla Firefox");
		}
		return null;
	}
	
}
