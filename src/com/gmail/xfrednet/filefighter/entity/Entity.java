package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIEntityNameTag;
import com.gmail.xfrednet.filefighter.level.Level;

import java.awt.Graphics;
import java.util.List;
import java.util.Random;

/**
 * Created by xFrednet on 06.02.2016.
 */
public abstract class Entity {
	
	public static final int BOUNDING_BOX_COLOR = 0xffff00ff;
	public static final int POSITION_COLOR = 0xffff0000;
	public static boolean showBoundingBoxes = false;
	protected static int currentID = Integer.MIN_VALUE;
	public static final int NAME_TAG_SPAWN_X = -100;
	public static final int NAME_TAG_SPAWN_Y = -100;
	
	protected final int entityID;
	protected EntityInfo info;
	protected Sprite currentSprite;
	protected String name;
	protected GUIEntityNameTag nameTag;
	protected boolean showNameTag = true;
	protected boolean removed = false;
	
	public final static Random random = new Random();
	
	/*
	* Constructor
	* */
	protected Entity(int x, int y, Level level, int width, int height, int spriteXOffset, int spriteYOffset, Sprite sprite, String name) {
		info = new EntityInfo(x, y, width, height, spriteXOffset, spriteYOffset);
		currentSprite = sprite;
		entityID = currentID++;
		this.name = name;
		
		if (level != null) {
			nameTag = new GUIEntityNameTag(level.getLevelGUI(), NAME_TAG_SPAWN_X, NAME_TAG_SPAWN_Y, name);
			level.getLevelGUI().addComponent(nameTag);
		}
		System.out.println("[INFO] New Entity with ID: " + currentID + ", Name: " + name);
	}
	protected Entity(Level level, String name) {
		entityID = currentID++;
		this.name = name;
		
		if (level != null) {
			nameTag = new GUIEntityNameTag(level.getLevelGUI(), NAME_TAG_SPAWN_X, NAME_TAG_SPAWN_Y, name);
			level.getLevelGUI().addComponent(nameTag);
		}
		System.out.println("[INFO] New Entity with ID: " + currentID + ", Name: " + name);
	}
	protected void setInfo(double x, double y, int width, int height, int spriteXOffset, int spriteYOffset) {
		info = new EntityInfo(x, y, width, height, spriteXOffset, spriteYOffset);
	}
	
	/*
	* Abstract Methods
	* */
	abstract public void update(Level level);
	public void endUpdate(Level level) {
		if (nameTag != null) {
			nameTag.setMapPosition((int)( info.getCenterX() - level.getCamera().getXOffset()), (int)(info.getMaxY() - level.getCamera().getYOffset()));
		}
	}
	public void render(Screen screen) {
		screen.drawSprite(info.getSpriteX(), info.getSpriteY(), currentSprite, false);
		if (showBoundingBoxes) drawBoundingBox(screen);
	}
	public void render(Graphics g) {
		if (showNameTag) {
			if (nameTag != null)
				nameTag.render(g);
		}
	}
	
	/*
	* Util
	* */
	public void drawBoundingBox(Screen screen) {
		screen.drawRectangle(info.getIntX(), info.getIntY(), info.width, info.height, BOUNDING_BOX_COLOR, false);
		screen.drawPixel(info.getIntX(), info.getIntY(), POSITION_COLOR, false);
		screen.drawPixel((int) info.getCenterX(), (int) info.getCenterY(), POSITION_COLOR, false);
	}
	public boolean levelCollision(double xm, double ym, Level level) {
		
		// + -
		// - -
		int xt = (int) (info.x + xm) >> 5;
		int yt = (int) (info.y + ym) >> 5;
		if (level.isSolid(xt, yt)) return true;
		
		// - +
		// - -
		xt = (int) (info.getMaxX() + xm) >> 5;
		yt = (int) (info.y + ym) >> 5;
		if (level.isSolid(xt, yt)) return true;
		
		// - -
		// + -
		xt = (int) (info.x + xm) >> 5;
		yt = (int) (info.getMaxY() + ym) >> 5;
		if (level.isSolid(xt, yt)) return true;
		
		// - -
		// - +
		xt = (int) (info.getMaxX() + xm) >> 5;
		yt = (int) (info.getMaxY() + ym) >> 5;
		if (level.isSolid(xt, yt)) return true;
		
		return false;
	}
	public List<Entity> entityCollision(double xm, double ym, Level level) {
		return level.entityMotionCollision(xm, ym, this);
	}
	public double getAngleTo(Entity entity) {
		return info.getAngle(entity.getInfo());
	}
	public double getAngleTo(double x, double y) {
		return info.getAngle(x, y);
	}
	/*
	* getters
	* */
	public EntityInfo getInfo() {
		return info;
	}
	public int getID() {
		return entityID;
	}
	
	/*
	* Setters
	* */
	public void setPosition(int x, int y) {
		info.x = x;
		info.y = y;
	}
	
	public double getDistance(double x, double y) {
		return info.getDistance(x, y);
	}
	
	public boolean isColliding(Entity entity) {
		if (entity.getID() == entityID) {
			return false;
		} else {
			return info.contains(entity.getInfo());
		}
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public GUIEntityNameTag getNameTag() {
		return nameTag;
	}
	
	
	/*
	* Class
	* */
	public static class EntityInfo {
		
		private EntityInfo(double x, double y, int width, int height, int spriteXOffset, int spriteYOffset) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.spriteXOffset = spriteXOffset;
			this.spriteYOffset = spriteYOffset;
		}
		
		//Bounding Box
		protected double x;
		protected double y;
		protected int width;
		protected int height;
		
		//Sprite Offset
		protected int spriteXOffset;
		protected int spriteYOffset;
		
		/*
		* Util
		* */
		public double getDistance(double x, double y) {
			double a = getCenterX() - x;
			double b = getCenterY() - y;
			
			//a^2 + b^2 = c^2
			return Math.sqrt((a * a) + (b * b));
		}
		
		/*
		* getters
		* */
		
		public double getX() {
			return x;
		}
		public double getY() {
			return y;
		}
		public int getIntX() {
			return (int) x;
		}
		public int getIntY() {
			return (int) y;
		}
		
		public int getSpriteX() {
			return getIntX() - spriteXOffset;
		}
		public int getSpriteY() {
			return getIntY() - spriteYOffset;
		}
		
		public double getMaxX() {
			return getIntX() + width - 1;
		}
		public double getMaxY() {
			return getIntY() + height - 1;
		}
		
		public double getCenterX() {
			return x + width / 2;
		}
		public double getCenterY() {
			return y + height / 2;
		}
		
		public double getAngle(EntityInfo info) {
			return Math.atan2(info.x - x, info.y - y);
		}
		public double getAngle(double x, double y) {
			return Math.atan2(x - this.x, y - this.y);
		}
		
		public boolean contains(EntityInfo info) {
			return contains(info.x, info.y)
					|| contains(info.getMaxX(), info.y)
					|| contains(info.x, info.getMaxY())
					|| contains(info.getMaxX(), info.getMaxY());
		}
		
		private boolean contains(double x, double y) {
			return (x >= this.x &&
					y >= this.y &&
					x < getMaxX() &&
					y < getMaxY());
		}
	}
	
}
