package com.gmail.xfrednet.filefighter.level;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.entity.Projectile;
import com.gmail.xfrednet.filefighter.entity.livingentitys.TestEntity;
import com.gmail.xfrednet.filefighter.entity.livingentitys.enemy.Slime;
import com.gmail.xfrednet.filefighter.graphics.*;
import com.gmail.xfrednet.filefighter.graphics.cameras.ControllableCamera;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.util.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class Level {
	
	public static final int TILE_SIZE = 32;
	public static final int SPAWN_PARTICLE_COUNT = 60;
	
	public int WIDTH;
	public int HEIGHT;
	public int[] tileIDs;
	
	public List<Entity> entityList = new ArrayList<>();
	public List<Particle> particles = new ArrayList<>();
	
	private Player player;
	private Camera camera;
	private GUIComponentGroup levelGUI;
	
	public Level(int width, int height, Player player, Input input, Screen screen, GUIManager guiManager) {
		WIDTH = width;
		HEIGHT = height;
		
		tileIDs = new int[WIDTH * HEIGHT];
		
		camera = new ControllableCamera(this, screen, input);
		
		//player
		this.player = player;
		player.setCamera(camera);
		levelGUI = new GUIComponentGroup(guiManager, 0, 0);
		
		generate();
	}
	public Level(Player player, Input input, Screen screen, GUIManager guiManager) {
		camera = new ControllableCamera(this, screen, input);
		
		//player
		this.player = player;
		player.setCamera(camera);
		player.setPosition(160, 160);
		levelGUI = new GUIComponentGroup(guiManager, 0, 0);
		guiManager.addComponent(levelGUI);
		
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
		
		add(new Slime(32 * 4, 32 * 4, this, player, "xFrednet"));
		
		add(new TestEntity(32 * 10, 32 * 10, this, "TestEntity"));
		add(new TestEntity(32 * 20, 32 * 10, this, "TestEntity"));
		add(new TestEntity(32 * 10, 32 * 20, this, "TestEntity"));
		add(new TestEntity(32 * 20, 32 * 20, this, "TestEntity"));
		
	}
	
	/*
	* spawning
	* */
	public void add(Entity entity) {
		add(entity, true);
	}
	public void add(Projectile entity) {
		add(entity, false);
	}
	public void add(LivingEntity entity) {
		add(entity, true);
	}
	public void add(Entity entity, boolean showSpawnParticles) {
		entityList.add(entity);
		if (showSpawnParticles) {
			spawnParticles(entity.getInfo().getCenterX(), entity.getInfo().getCenterY(), SPAWN_PARTICLE_COUNT, Sprite.smoke_particles);
		}
	}
	public void addParticle(Particle particle) {
		particles.add(particle);
	}
	public void spawnParticles(double x, double y, int count, Sprite[] sprites) {
		for (int i = 0; i < count; i++) {
			addParticle(new Particle(x, y, sprites));
		}
	}
	
	/*
	* Game loop util 
	* */
	
	//Update
	public void render(Screen screen) {
		screen.setOffset(camera.getXOffset(), camera.getYOffset());
		
		renderLevel(screen);
		renderEntities(screen);
		renderParticles(screen);
		
		player.render(screen);
		
	}
	
	public void renderParticles(Screen screen) {
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
	}
	public void renderEntities(Screen screen) {
		for (int i = 0; i < entityList.size(); i++) {
			entityList.get(i).render(screen);
		}
	}
	public void renderLevel(Screen screen) {
		int xScroll = camera.getXOffset();
		int yScroll = camera.getYOffset();
		
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
	}
	
	//Render
	public void update() {
		updateEntities();
		updateParticles();
	}
	
	public void updateEntities() {
		for (Entity entity : entityList) {
			entity.update(this);
			entity.endUpdate(this);
		}
		for (int i = 0; i < entityList.size(); i++) {
			if (entityList.get(i).isRemoved()) {
				levelGUI.removeComponent(entityList.get(i).getNameTag());
				entityList.remove(i);
			}
		}
	}
	public void updateParticles() {
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update(this);
			
			if (particles.get(i).isRemoved()) {
				particles.remove(i);
			}
		}
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
	
	public List<Entity> getEntities() {
		return entityList;
	}
	public List<Entity> getEntities(double x, double y, double maxDistance) {
		List<Entity> returnEntities = new ArrayList<>();
		
		for (int i = 0; i < entityList.size(); i++) {
			if (entityList.get(i).getDistance(x, y) < maxDistance) {
				returnEntities.add(entityList.get(i));
			}
		}
		
		return returnEntities;
	}
	public List<LivingEntity> livingEntityMotionCollision(double xm, double ym, Entity entity) {
		List<LivingEntity> collidingEntities = new ArrayList<>();
		
		if (player.isColliding(entity)) {
			collidingEntities.add(player);
		}
		
		for (int i = 0; i < entityList.size(); i++) {
			if (entityList.get(i) instanceof LivingEntity && entityList.get(i).isColliding(entity) && entityList.get(i).getID() != entity.getID()) {
				collidingEntities.add((LivingEntity) entityList.get(i));
			}
		}
		
		return collidingEntities;
	}
	
	//else
	public GUIComponentGroup getLevelGUI() {
		return levelGUI;
	}
	public Camera getCamera() {
		return camera;
	}
	
}
