package com.gmail.xfrednet.filefighter.level;

import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.util.Input;

/**
 * Created by xFrednet on 06.03.2016.
 */
public abstract class TileEntity {
	
	public static final int MAIN_INTERACTION_BUTTON = Input.LEFT_MOUSE_BUTTON;
	
	int x;
	int y;
	protected boolean removed = false;
	
	public TileEntity(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	* Game loop Util
	* */
	public void render(Screen screen) {
		screen.drawTileEntity(x << 5, y << 5, getSprite());
	}
	public void update(Level level) {}
	
	/*
	* getters
	* */
	public boolean isRemoved() {
		return removed;
	}
	public void remove() {
		this.removed = true;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	/*
			* abstract & Overridden Methods
			* */
	protected abstract Sprite getSprite();
	public void playerEntered(Level level, Player player) {}
	public void mouseInteraction(int x, int y, int button, Level level, Player player) {}
	
}
