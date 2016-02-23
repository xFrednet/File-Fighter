package com.gmail.xfrednet.filefighter.graphics;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class Screen {
	
	public static final int CLEAR_COLOR = 0xff00C2ff;
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
	
	//rotate sprite
	private int[] rotateSprite(double angle, Sprite sprite) {
		int[] pixels = new int[sprite.pixels.length];
		
		double nx_x = rot_x(angle, 1.0, 0.0);
		double nx_y = rot_y(angle, 1.0, 0.0);
		double ny_x = rot_x(angle, 0.0, 1.0);
		double ny_y = rot_y(angle, 0.0, 1.0);
		
		double x0 = rot_x(angle, -sprite.WIDTH / 2.0, -sprite.HEIGHT / 2.0) + sprite.WIDTH / 2.0;
		double y0 = rot_y(angle, -sprite.WIDTH / 2.0, -sprite.HEIGHT / 2.0) + sprite.HEIGHT / 2.0;
		
		double x1, y1;
		int xa, ya;
		int color;
		for (int yp = 0; yp < sprite.HEIGHT; yp++) {
			x1 = x0;
			y1 = y0;
			
			for (int xp = 0; xp < sprite.WIDTH; xp++) {
				xa = (int) x1;
				ya = (int) y1;
				
				//setting color
				if (xa < 0 || xa >= sprite.WIDTH || ya < 0 || ya >= sprite.HEIGHT)
					color = INVISIBLE_COLOR;
				else
					color = sprite.pixels[xa + ya * sprite.WIDTH];
				pixels[xp + yp * sprite.WIDTH] = color;
				
				//adding rotation
				x1 += nx_x;
				y1 += nx_y;
			}
			//adding rotation
			x0 += ny_x;
			y0 += ny_y;
		}
		
		return pixels;
	}
	private double rot_x(double angle, double x, double y) {
		return x * Math.cos(angle) + y * -Math.sin(angle);
	}
	private double rot_y(double angle, double x, double y) {
		return x * Math.sin(angle) + y * Math.cos(angle);
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
	public void drawSprite(int x, int y, Sprite sprite, boolean fixed, int scale) {
		if (scale == 1) drawSprite(x, y, sprite, fixed);
		if (!fixed) {
			x -= xOffset;
			y -= yOffset;
		}
		
		int ya;
		int xa;
		int xaa;
		int yaa;
		int yap;
		int xap;
		int color;
		for (int yp = 0; yp < sprite.HEIGHT; yp++) {
			ya = y + yp * scale;
			if (ya < -scale || ya >= HEIGHT) continue;
			
			for (int xp = 0; xp < sprite.WIDTH; xp++) {
				xa = x + xp * scale;
				if (xa < -scale || xa >= WIDTH) continue;
				
				color = sprite.pixels[xp + yp * sprite.WIDTH];
				
				if (color != INVISIBLE_COLOR){
					pixels[xa + ya * WIDTH] = color;
					for (yap = 0; yap < scale; yap++) {
						yaa = ya + yap;
						if (yaa < 0 || yaa >= HEIGHT) continue;
						
						for (xap = 0; xap < scale; xap++) {
							xaa = xa + xap;
							if (xaa < 0 || xaa >= WIDTH) continue;
							
							pixels[xaa + yaa * WIDTH] = color;
							
						}
					}
				}
			}
		}
	}
	
	//shapes
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
	public void drawFilledRectangle(int x, int y, int width, int height, int color, boolean fixed) {
		if (!fixed) {
			x -= xOffset;
			y -= yOffset;
		}
		
		int ya;
		int xa;
		int xp;
		for (int yp = 0; yp < width; yp++) {
			ya = y + yp;
			if (ya < 0 || ya >= HEIGHT) continue;
			
			for (xp = 0; xp < width; xp++) {
				xa = x + xp;
				if (xa < 0 || xa >= WIDTH) continue;
				
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
	//specific draw Methods
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
	public void drawProjectile(int x, int y, Sprite sprite, double angle) {
		
		x -= xOffset;
		y -= yOffset;
		
		int[] rotatedPixels = rotateSprite(angle, sprite);
		int ya;
		int xa;
		int color;
		for (int yp = 0; yp < sprite.HEIGHT; yp++) {
			ya = yp + y;
			if (ya < 0 || ya >= HEIGHT) continue;
			
			for (int xp = 0; xp < sprite.WIDTH; xp++) {
				xa = xp + x;
				if (xa < 0 || xa >= WIDTH) continue;
				
				color = rotatedPixels[xp + yp * sprite.WIDTH];
				
				if (color != INVISIBLE_COLOR)
					pixels[xa + ya * WIDTH] = color;
			}
		}
	}
	
	
}
