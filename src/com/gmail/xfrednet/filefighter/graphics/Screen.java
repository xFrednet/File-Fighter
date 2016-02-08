package com.gmail.xfrednet.filefighter.graphics;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class Screen {
	
	public static final int CLEAR_COLOR = 0xffff00ff;
	public static final int INVISIBLE_COLOR = 0xffff00ff;
	public final int WIDTH;
	public final int HEIGHT;
	
	public int[] pixels;
	public int xOffset = 0;
	public int yOffset = 0;

	/*
	* Constructor
	* */
	
	public Screen(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[WIDTH * HEIGHT];
	}
	
	/*
	* Util
	* */
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = CLEAR_COLOR;
		}
	}
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	/*
	* draw Methods
	* */
	public void drawSprite(int x, int y, Sprite sprite, boolean fixed) {
		if (!fixed) {
			x -= xOffset;
			y -= yOffset;
		}
		
		int ya;
		int xa;
		int color;
		for (int yp = 0; yp < sprite.HEIGHT; yp++) {
			ya = yp + y;
			if (ya < 0 || ya >= HEIGHT) continue;
			
			for (int xp = 0; xp < sprite.WIDTH; xp++) {
				xa = xp + x;
				if (xa < 0 || xa >= WIDTH) continue;
				
				color = sprite.pixels[xp + yp * sprite.WIDTH];
				
				if (color != INVISIBLE_COLOR) 
					pixels[xa + ya * WIDTH] = color;
			}
		}
	}
	public void drawTile(int x, int y, Sprite sprite) {
		x -= xOffset;
		y -= yOffset;
		
		int ya;
		int xa;
		for (int yp = 0; yp < sprite.HEIGHT; yp++) {
			ya = yp + y;
			if (ya < 0 || ya >= HEIGHT) continue;
			
			for (int xp = 0; xp < sprite.WIDTH; xp++) {
				xa = xp + x;
				if (xa < 0 || xa >= WIDTH) continue;
				
				pixels[xa + ya * WIDTH] = sprite.pixels[xp + yp * sprite.WIDTH];
			}
		}
	}
	public void drawRectangle(int x, int y, int width, int height, int color, boolean fixed) {
		if (!fixed) {
			x -= xOffset;
			y -= yOffset;
		}
		
		int ya;
		int xa;
		for (int yp = 0; yp < height; yp++) {
			ya = yp + y;
			if (ya < 0 || ya >= HEIGHT) continue; 
			
			xa = x;
			if (!(xa < 0 || xa >= WIDTH)) {
				pixels[xa + ya * WIDTH] = color;
			}
			xa = x + width - 1;
			if (!(xa < 0 || xa >= WIDTH)) {
				pixels[xa + ya * WIDTH] = color;
			}
		}
		
		for (int xp = 0; xp < width; xp++) {
			xa = xp + x;
			if (xa < 0 || xa >= WIDTH) continue;
			
			ya = y;
			if (!(ya < 0 || ya >= HEIGHT)) {
				pixels[xa + ya * WIDTH] = color;
			}
			
			ya = y + height - 1;
			if (!(ya < 0 || ya >= HEIGHT)) {
				pixels[xa + ya * WIDTH] = color;
			}
			
		}
	}
	public void drawPixel(int x, int y, int color, boolean fixed) {
		if (!fixed) {
			x -= xOffset;
			y -= yOffset;
		}
		
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) return;
		pixels[x + y * WIDTH] = color;
	}
}
