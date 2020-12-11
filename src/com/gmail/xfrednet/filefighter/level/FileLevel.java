package com.gmail.xfrednet.filefighter.level;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.ItemEntity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.entity.areaeffects.ElectricArea;
import com.gmail.xfrednet.filefighter.entity.livingentitys.Dummy;
import com.gmail.xfrednet.filefighter.entity.livingentitys.TestEntity;
import com.gmail.xfrednet.filefighter.entity.livingentitys.enemy.JPGFileEntity;
import com.gmail.xfrednet.filefighter.entity.livingentitys.enemy.TextFileEntity;
import com.gmail.xfrednet.filefighter.graphics.GUIManager;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.item.potion.HealthPotion;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.FirefoxFlameThrower;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.PaperGun;
import com.gmail.xfrednet.filefighter.level.path.Path;
import com.gmail.xfrednet.filefighter.level.tile.WallTile;
import com.gmail.xfrednet.filefighter.level.tileentity.Chest;
import com.gmail.xfrednet.filefighter.level.tileentity.FolderTileEntity;
import com.gmail.xfrednet.filefighter.util.FileHelper;
import com.gmail.xfrednet.filefighter.util.Input;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xFrednet on 08.02.2016.
 */
public class FileLevel extends Level {
	
	final File FILE;
	final long SEED;
	
	public FileLevel(Player player, Screen screen, File file, GUIManager guiManager) {
		super(player, screen, guiManager);
		
		WIDTH = 49;
		HEIGHT = 49;
		tiles = new Tile[WIDTH * HEIGHT];
		
		
		if (file == null) {
			System.out.println("[ERROR] FileLevel: given File is null");
			
			SEED = 0;
			FILE = new File(System.getProperty("user.home"));
			
			return;
		}
		
		SEED = stringToLong(file.getName());
		FILE = file;
		
		System.out.println("[INFO] FileLevel: dir: " + file.getAbsolutePath());
		System.out.println("[INFO] FileLevel: Seed: " + SEED);
		
		if (!file.isDirectory()) {
			System.out.println("[ERROR] FileLevel: given File is a File not a directory: " + file.getPath());
			return;
		}
		
		generate();
		
		initClearProgress();
	}
	
	private void generate() {
		generateMaze();
		spawnFileEntities();
		spawnFolderTileEntities();
		
		spawn(new PaperGun().getItemEntity(this, 32 + 16, 32 + 16));
		spawn(new FirefoxFlameThrower().getItemEntity(this, 64 + 16, 32 + 16));
		spawn(HealthPotion.newSmallHealthPotion(50).getItemEntity(this, 3 * 32 + 16, 3 * 32 + 16));
		//spawn(new ElectricArea(this, 10 * 32 + 16, 10 * 32 + 16, 32 * 10));
	}
	
	/*
	* Entity spawning
	* */
	private void spawnFolderTileEntities() {
		File file = FILE.getParentFile();
		if (file != null) {
			tileEntities.add(new FolderTileEntity(2, 2, file));
		}
	}
	private void spawnFileEntities() {
		Random random = new Random(SEED);
		File[] files = FILE.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return !pathname.isDirectory();
			}
		});
		
		Node testPos = new Node(WIDTH / 2, HEIGHT / 2);
		if (isSolid(testPos.getX(), testPos.getY())) {
			testPos = new Node(WIDTH / 2 + 1, HEIGHT / 2 + 1);
		}
		
		Node spawnLocation;
		
		int x;
		int y;
		for (int i = 0; i < files.length; i++) {
			spawnLocation = getValidSpawnLocation(random, testPos);
			x = spawnLocation.getMapX() + TILE_SIZE / 2;
			y = spawnLocation.getMapY() + TILE_SIZE / 2;
			
			spawn(FileHelper.getFileEntity(this, files[i], x, y));
		}
		
	}
	private Node getValidSpawnLocation(Random random, Node testPos) {
		Node pos = new Node();
		do {
			pos.setX(random.nextInt(WIDTH));
			pos.setY(random.nextInt(HEIGHT));
		} while (isSolid(pos.getX(), pos.getY()) || !Path.isPossible(this, pos.getX(), pos.getY(), testPos.getX(), testPos.getY()));
		
		return pos;
	}
	
	/*
	* Maze generation
	* */
	private void generateMaze() {
		Random r = new Random(SEED);
		int distance = 4;
		
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = Tile.List.spaceTile;
		}
		
		//generation walls same will be remove
		for (int y = 0; y < HEIGHT; y += distance) {
			for (int x = 0; x < WIDTH; x++) {
				tiles[x + y * WIDTH] = new WallTile();
			}
		}
		for (int x = 0; x < WIDTH; x += distance) {
			for (int y = 0; y < HEIGHT; y++) {
				tiles[x + y * WIDTH] = new WallTile();
			}
		}
		
		//creating values
		int cNodeX = 2;
		int cNodeY = 2;
		Node cNode;
		int loopTimes;
		List<Node> neighbors;
		
		for (cNodeY = 2; cNodeY < HEIGHT; cNodeY += distance) {
			for (cNodeX = 2; cNodeX < WIDTH; cNodeX += distance) {
				
				cNode = new Node(cNodeX, cNodeY);
				neighbors = cNode.getNeighbors(distance);
				
				if (!neighbors.isEmpty()) {
					if (neighbors.size() > 1) {
						loopTimes = (int) (1 + (neighbors.size() * r.nextDouble()) / 2);
						
						for (; loopTimes > 0; loopTimes--) {
							clearTiles(neighbors.get(r.nextInt(neighbors.size())), cNode, distance);
						}
					} else {
						clearTiles(neighbors.get(0), cNode, distance);
					}
				}
			}
		}
		
		for (int y = 0; y < HEIGHT; y += 4) {
			for (int x = 0; x < WIDTH; x += 4) {
				if (!isAWallPart(x, y)) {
					tiles[x + y * WIDTH] = Tile.List.spaceTile;
				}
			}
		}
		
	}
	private boolean isAWallPart(int x, int y) {
		//    1
		//  2   3
		//    4
		return getTile(x, y - 1).getID() == Tile.List.WALL_ID  
				|| getTile(x - 1, y    ).getID() == Tile.List.WALL_ID 
				|| getTile(x + 1, y    ).getID() == Tile.List.WALL_ID
				|| getTile(x    , y + 1).getID() == Tile.List.WALL_ID;
	}
	private void clearTiles(Node node, Node parent, int distance) {
		//    1
		//  2 3 4
		//    5
		int xn = (node.x - parent.getX()) / distance;
		int yn = (node.y - parent.getY()) / distance;
		Node n = new Node(parent.x + xn * distance / 2, parent.y + yn * distance / 2);
		
		// 1
		int x = n.getX();
		int y = n.getY() - 1;
		tiles[x + y * WIDTH] = Tile.List.spaceTile;
		// 2
		x = n.getX() - 1;
		y = n.getY();
		tiles[x + y * WIDTH] = Tile.List.spaceTile;
		// 3
		x = n.getX();
		y = n.getY();
		tiles[x + y * WIDTH] = Tile.List.spaceTile;
		// 4
		x = n.getX() + 1;
		y = n.getY();
		tiles[x + y * WIDTH] = Tile.List.spaceTile;
		// 5
		x = n.getX();
		y = n.getY() + 1;
		tiles[x + y * WIDTH] = Tile.List.spaceTile;
		
	}
	
	/*
	* Util
	* */
	private long stringToLong(String string) {
		if (string.length() < 8) {
			string += "        ";
		}
		return ByteBuffer.wrap(string.getBytes()).getLong();
	}
	
	
	/*
	* Util Class
	* */
	private class Node {
		
		int x;
		int y;
		
		/*
		* Constructor
		* */
		public Node() {}
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		/*
		* Util
		* */
		public List<Node> getNeighbors(int distance) {
			List<Node> n = new ArrayList<>();
			
			//    1
			//  2   3
			//    4
			
			// 1
			Node node = new Node(x, y - distance);
			if (node.isValid(this, distance)) {
				n.add(node);
			}
			// 2
			node = new Node(x - distance, y);
			if (node.isValid(this, distance)) {
				n.add(node);
			}
			// 3
			node = new Node(x + distance, y);
			if (node.isValid(this, distance)) {
				n.add(node);
			}
			// 4
			node = new Node(x, y + distance);
			if (node.isValid(this, distance)) {
				n.add(node);
			}
			
			return n;
		}
		
		/*
		* setters
		* */
		public void setX(int x) {
			this.x = x;
		}
		public void setY(int y) {
			this.y = y;
		}
		
		/*
		* getters
		* */
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public int getMapX() {
			return getX() * TILE_SIZE;
		}
		public int getMapY() {
			return getY() * TILE_SIZE;
		}
		
		public boolean isValid(Node parent, int distance) {
			if (FileLevel.this.contains(x, y)) return false;
			
			int x = (this.x - parent.getX()) / distance;
			int y = (this.y - parent.getY()) / distance;
			
			return isSolid(parent.getX() + (x * distance / 2), parent.getY() + (y * distance / 2));
		}
		
	}
}


