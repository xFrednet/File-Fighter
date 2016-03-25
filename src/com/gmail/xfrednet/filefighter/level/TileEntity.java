package com.gmail.xfrednet.filefighter.level;

import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.util.Input;

import java.util.Random;

/**
 * Created by xFrednet on 06.03.2016.
 */
public abstract class TileEntity {
	
	public static final int MAIN_INTERACTION_BUTTON = Input.LEFT_MOUSE_BUTTON;
	protected static Random random = new Random();
			
	protected int x;
	protected int y;
	protected boolean removed = false;
	private boolean containsPlayer = false;
	
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
	* Util
	* */
	public void test(int tileX, int tileY, Level level, Player player) {
		if (tileX == x && tileY == y) {
			if (!containsPlayer) {
				playerEntered((int)player.getInfo().getCenterX(), (int)player.getInfo().getCenterY(), level, player);
				containsPlayer = true;
			}
			
			containsPlayer((int)player.getInfo().getCenterX(), (int)player.getInfo().getCenterY(), level, player);
		} else {
			if (containsPlayer) {
				playerExited((int)player.getInfo().getCenterX(), (int)player.getInfo().getCenterY(), level, player);
				containsPlayer = false;
			}
		}
	}
	
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
	public void containsPlayer(int x, int y, Level level, Player player) {}
	public void playerEntered(int x, int y, Level level, Player player) {}
	public void playerExited(int x, int y, Level level, Player player) {}
	public void mouseInteraction(int x, int y, int button, Level level, Player player) {}
	public void levelCleared(Level level, Player player) {}
	
}
