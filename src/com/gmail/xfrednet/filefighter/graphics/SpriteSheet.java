package com.gmail.xfrednet.filefighter.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class SpriteSheet {
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/tiles.png");
	public static SpriteSheet entities = new SpriteSheet("/textures/entities.png");
	public static SpriteSheet projectiles = new SpriteSheet("/textures/projectiles.png");
	public static SpriteSheet particles = new SpriteSheet("/textures/particles.png");
	
	public static SpriteSheet player = new SpriteSheet("/textures/player/bluze.png");
	
	public int WIDTH;
	public int HEIGHT;
	public int[] pixels;
	
	public SpriteSheet(String path) {
		load(path);
	}
	
	private void load(String path) {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			WIDTH = image.getWidth();
			HEIGHT = image.getHeight();
			
			pixels = new int[WIDTH * HEIGHT];
			
			pixels = image.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
