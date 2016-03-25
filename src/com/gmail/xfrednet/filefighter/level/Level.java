package com.gmail.xfrednet.filefighter.level;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.*;
import com.gmail.xfrednet.filefighter.entity.livingentitys.TestEntity;
import com.gmail.xfrednet.filefighter.graphics.*;
import com.gmail.xfrednet.filefighter.graphics.cameras.ControllableCamera;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponent;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIProgressBar;
import com.gmail.xfrednet.filefighter.util.Input;
import com.gmail.xfrednet.filefighter.level.path.Node;
import com.sun.javafx.beans.annotations.NonNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xFrednet on 06.02.2016.
 */
public abstract class Level {
	
	public static final int TILE_SIZE = 32;
	public static final int SPAWN_PARTICLE_COUNT = 60;
	
	public int WIDTH;
	public int HEIGHT;
	protected boolean cleared = false;
	protected Tile[] tiles;
	
	public List<Entity> entityList = new ArrayList<>();
	public List<TileEntity> tileEntities = new ArrayList<>(); 
	public List<Particle> particles = new ArrayList<>();
	
	protected Player player;
	protected Camera camera;
	protected GUIComponentGroup levelGUI;
	protected GUIProgressBar clearProgress;
	protected Level returnLevel = null;
	
	public Level(Player player, Screen screen, GUIManager guiManager) {
		camera = new ControllableCamera(this, screen, player.getInput());
		
		//player
		this.player = player;
		player.setCamera(camera);
		player.setPosition(160, 160);
		levelGUI = new GUIComponentGroup(guiManager, 0, 0);
		guiManager.addComponent(levelGUI);
		
	}
	
	protected void initClearProgress() {
		clearProgress = new GUIProgressBar(levelGUI, 0, 0, Main.WIDTH * Main.scale, 3);
		clearProgress.setProgressColor(new Color(0xff0094ff, true));
		
		int enemies = 0;
		for (int i = 0; i < entityList.size(); i++) {
			if (entityList.get(i) instanceof EnemyEntity) {
				enemies++;
			}
		}
		
		clearProgress.setMaxProgress(enemies);
		clearProgress.setProgress(enemies);
		
		levelGUI.addComponent(clearProgress);
		
	}
	
	public void delete() {
		levelGUI.remove();
	}
	
	/*
	* spawning
	* */
	public void spawn(Entity entity) {
		spawn(entity, true);
	}
	public void spawn(ItemEntity entity) {
		spawn(entity, false);
	}
	public void spawn(Projectile entity) {
		spawn(entity, false);
	}
	public void spawn(LivingEntity entity) {
		spawn(entity, true);
	}
	public void spawn(Entity entity, boolean showSpawnParticles) {
		entityList.add(entity);
		if (showSpawnParticles) {
			spawnParticles(entity.getInfo().getCenterX(), entity.getInfo().getCenterY(), SPAWN_PARTICLE_COUNT, Sprite.Particles.smoke_particles);
		}
	}
	public void addParticle(Particle particle) {
		particles.add(particle);
	}
	public void spawnParticles(double x, double y, int count, Sprite[] sprites) {
		for (int i = 0; i < count; i++) {
			addParticle(new Particle(x, y, this, sprites));
		}
	}
	
	/*
	* Util
	* */
	public void changeLevel(Level level) {
		returnLevel = level;
	}
	
	public void clicked(int mouseX, int mouseY, int button, Player player) {
		int tileX = (camera.getXOffset() + mouseX / Main.scale) >> 5;
		int tileY = (camera.getYOffset() + mouseY / Main.scale) >> 5;
		
		for (int i = 0; i < tileEntities.size(); i++) {
			if (tileEntities.get(i).getX() == tileX && tileEntities.get(i).getY() == tileY) {
				tileEntities.get(i).mouseInteraction(mouseX, mouseY, button, this, player);
			}
		}
	}
	
	private void sendLevelClearedMessage() {
		cleared = true;
		for (int i = 0; i < tileEntities.size(); i++) {
			tileEntities.get(i).levelCleared(this, player);
		}
	}
	/*
	* Game loop util 
	* */
	
	/*
	* Render
	* */
	public void render(Screen screen) {
		screen.setOffset(camera.getXOffset(), camera.getYOffset());
		
		renderLevel(screen);
		renderEntities(screen);
		renderParticles(screen);
		
		player.render(screen);
		
	}
	
	private void renderParticles(Screen screen) {
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
	}
	private void renderEntities(Screen screen) {
		for (int i = 0; i < entityList.size(); i++) {
			entityList.get(i).render(screen);
		}
	}
	private void renderLevel(Screen screen) {
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
					tiles[x + y * WIDTH].render(x, y, screen, this);
				}
			}
		}
		
		for (int i = 0; i < tileEntities.size(); i++) {
			if (tileEntities.get(i).getX() < x0 || tileEntities.get(i).getX() >= x1 || tileEntities.get(i).getY() < y0 || tileEntities.get(i).getY() >= y1) continue;
			tileEntities.get(i).render(screen);
		}
	}
	
	/*
	* Update
	* */
	public Level update() {
		updateTileEntities();
		updateEntities();
		updateParticles();
		
		if (returnLevel == null) {
			return this;
		} else {
			delete();
			return returnLevel;
		}
	}
	
	private void updateEntities() {
		for (int i = 0; i < entityList.size(); i++) {
			entityList.get(i).update(this);
			entityList.get(i).endUpdate(this);
		}
		
		int enemyCount = 0;
		for (int i = 0; i < entityList.size(); i++) {
			if (entityList.get(i).isRemoved()) {
				levelGUI.removeComponent(entityList.get(i).getNameTag());
				entityList.remove(i);
				i--;
			} else {
				if (!cleared && (entityList.get(i) instanceof EnemyEntity)) {
					enemyCount++;
				}
			}
		}
		
		if (enemyCount == 0 && !cleared) {
			sendLevelClearedMessage();
		}
		clearProgress.setProgress(enemyCount);
	}
	private void updateParticles() {
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update(this);
			
			if (particles.get(i).isRemoved()) {
				particles.remove(i);
				i--;
			}
		}
	}
	private void updateTileEntities() {
		int tileX = ((int) player.getInfo().getCenterX()) >> 5;
		int tileY = ((int) player.getInfo().getCenterY()) >> 5;
		for (TileEntity entity : tileEntities) {
			entity.update(this);
			
			entity.test(tileX, tileY, this, player);
			
		}
		
		for (int i = 0; i < tileEntities.size(); i++) {
			if (tileEntities.get(i).isRemoved()) {
				tileEntities.remove(i);
				i--;
			}
		}
		
	}
	/*
	* Getters
	* */
	public boolean contains(int x, int y) {
		return (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT);
	}
	
	//tiles
	public int getTileID(int x, int y) {
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return 0;
		return tiles[x + y * WIDTH].getID();
	}
	public Tile getTile(int x, int y) {
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return Tile.List.nullTile;
		return tiles[x + y * WIDTH];
	}
	public boolean isSolid(int x, int y) {
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return true; 
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
	
	public List<Entity> getItemEntities(double x, double y, double maxDistance) {
		List<Entity> returnEntities = new ArrayList<>();
		
		for (int i = 0; i < entityList.size(); i++) {
			if (entityList.get(i) instanceof ItemEntity && entityList.get(i).getDistance(x, y) < maxDistance) {
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
	public Screen getScreen() {
		return camera.getScreen();
	}
	
}
