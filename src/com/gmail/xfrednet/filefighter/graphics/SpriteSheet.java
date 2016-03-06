package com.gmail.xfrednet.filefighter.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.IOException;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class SpriteSheet {
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/tiles.png");
	public static SpriteSheet entities = new SpriteSheet("/textures/entities.png");
	public static SpriteSheet projectiles = new SpriteSheet("/textures/projectiles.png");
	public static SpriteSheet particles = new SpriteSheet("/textures/particles.png");
	
	public static SpriteSheet guiComponents = new SpriteSheet("/textures/guiComponents.png");
	public static SpriteSheet player = new SpriteSheet("/textures/player/bluze.png");
	public static SpriteSheet items = new SpriteSheet("/textures/items.png", true);
	
	public int WIDTH;
	public int HEIGHT;
	public int[] pixels;
	private BufferedImage image;
	
	public SpriteSheet(String path) {
		load(path);
	}
	public SpriteSheet(String path, boolean loadAlpha) {
		load(path, loadAlpha);
	}
	
	private void load(String path) {
		load(path, false);
	}
	private void load(String path, boolean loadAlpha) {
		try {
			image = ImageIO.read(SpriteSheet.class.getResource(path));
			WIDTH = image.getWidth();
			HEIGHT = image.getHeight();
			
			pixels = new int[WIDTH * HEIGHT];
			
			pixels = image.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
			
			if (!loadAlpha) {
				System.out.println("[INFO] SpriteSheet: loaded " + path);
				return;
			}
			
			WritableRaster alphaRaster = image.getAlphaRaster();
			if (alphaRaster == null) {
				System.out.println("[ERROR] SpriteSheet: could not load alphaRaster " + path);
				return;
			}
			
			int[] alpha = alphaRaster.getSamples(0, 0, alphaRaster.getWidth(), alphaRaster.getHeight(), 0, (int[]) null);
			
			for (int i = 0; i < alpha.length; i++) {
				if (alpha[i] != 255) {
					pixels[i] = Screen.INVISIBLE_COLOR;
				}
			}
			
			System.out.println("[INFO] SpriteSheet: loaded " + path + "             /w alpha");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
