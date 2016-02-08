package com.gmail.xfrednet.filefighter.graphics;

import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 06.02.2016.
 */
public abstract class Camera {
	
	protected int x;
	protected int y;
	protected Level level;
	protected Screen screen;
	
	/*
	* Constructors
	* */
	protected Camera(Level level, Screen screen) {
		this(0, 0, level, screen);
	}
	protected Camera(int x, int y, Level level, Screen screen) {
		this.x = x;
		this.y = y;
		
		if (level == null) System.out.println("[ERROR] Camera: level = null");
		if (screen == null) System.out.println("[ERROR] Camera: screen = null");
		
		this.level = level;
		this.screen = screen;
	}
	
	/*
	* Abstract Methods
	* */
	abstract public void update();
	
	/*
	* Util
	* */
	protected void moveCamera(int x, int y) {
		setXOffset(x);
		setYOffset(y);
	}
	
	/*
	* getters
	* */
	public int getXOffset() {
		return x;
	}
	public int getYOffset() {
		return y;
	}
	
	/*
	* setters
	* */
	public void setXOffset(int xOffset) {
		if (xOffset < 0) {
			x = 0;
			return;
		}
		if (xOffset > level.WIDTH * Sprite.TILE_SPRITE_SIZE - screen.WIDTH) {
			x = level.WIDTH * Sprite.TILE_SPRITE_SIZE - screen.WIDTH;
			return;
		}
		
		x = xOffset;
	}
	public void setYOffset(int yOffset) {
		if (yOffset < 0) {
			y = 0;
			return;
		}
		if (yOffset > level.HEIGHT * Sprite.TILE_SPRITE_SIZE - screen.HEIGHT) {
			y = level.HEIGHT * Sprite.TILE_SPRITE_SIZE - screen.HEIGHT;
			return;
		}
		
		y = yOffset;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
	
}
