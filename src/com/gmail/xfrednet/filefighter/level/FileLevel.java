package com.gmail.xfrednet.filefighter.level;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.ItemEntity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.entity.livingentitys.Dummy;
import com.gmail.xfrednet.filefighter.entity.livingentitys.TestEntity;
import com.gmail.xfrednet.filefighter.entity.livingentitys.enemy.JPGFileEntity;
import com.gmail.xfrednet.filefighter.entity.livingentitys.enemy.TextFileEntity;
import com.gmail.xfrednet.filefighter.graphics.GUIManager;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.level.tileentity.Chest;
import com.gmail.xfrednet.filefighter.util.FileHelper;
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
		super(player, screen, guiManager);
		
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
			if (files[i].isFile()) {
				spawn(FileHelper.getFileEntity(this, files[i], random.nextInt(10) * 32 + 32, random.nextInt(10) * 32 + 32));
			}
		}
		
		spawn(new ItemEntity(50, 75, this, player.getWeapon()));
		
		tileEntities.add(new Chest(3, 2));
		tileEntities.add(new Chest(4, 2, "Chest 2"));
		
	}
	
}
