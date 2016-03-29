package com.gmail.xfrednet.filefighter.graphics;

import com.gmail.xfrednet.filefighter.graphics.gui.components.GUIItemFrame;

import java.awt.image.BufferedImage;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class Sprite {
	
	public static final int TILE_SPRITE_SIZE = 32;
	public static final int ENTITY_SPRITE_SIZE = 32;
	public static final int PROJECTILE_SPRITE_SIZE = 15;
	public static final int ITEM_ENTITY_SPRITE_SIZE = 16;
	
	public static final int SPRITE_INVISIBLE_COLOR_1 = 0xffffbb0f;
	public static final int SPRITE_INVISIBLE_COLOR_2 = 0xffff0000;
	public static final int ITEM_SPRITE_SIZE = 16;
	
	/*
	* Test Code
	* */
	public static Sprite null_sprite = new Sprite(16, 16, 0xffff03ff);
	
	/*
	* Entity Sprites
	* */
	public static Sprite[] player_entity_sprites = loadEntityAnimation(0, 0, SpriteSheet.player, ENTITY_SPRITE_SIZE, 8);
	
	/*
	* Projectiles
	* */
	public static class Projectiles {
		public static Sprite paper_projectile = new Sprite(0, 0, SpriteSheet.projectiles, PROJECTILE_SPRITE_SIZE);
		public static Sprite fire_ball = new Sprite(PROJECTILE_SPRITE_SIZE, 0, SpriteSheet.projectiles, PROJECTILE_SPRITE_SIZE);
	}
	/*
	* GUIComponents
	* */
	public static class GUI {
		public static Sprite selected_item_frame = new Sprite(GUIItemFrame.SPRITE_SIZE * GUIItemFrame.TYPE_COUNT, 0, SpriteSheet.guiComponents, GUIItemFrame.SPRITE_SIZE);
		public static Sprite[] itemFrame = loadItemFrames();
	}
	
	/*
	* Particles
	* */
	public static class Particles {
		public static Sprite[] smoke_particles = loadSplitSprite(new Sprite(0, 0, SpriteSheet.particles, 16), 3);
		/*
		* Projectiles particles
		* */
		public static Sprite[] paper_projectile_particles = loadParticlesFromSprites(Projectiles.paper_projectile);
		public static Sprite[] fire_ball_particles = loadParticlesFromSprites(Projectiles.fire_ball);
	}
	
	/*
	* Tiles
	* */
	public static class Tiles {
		public static Sprite null_tile_sprite = new Sprite(0, 0, SpriteSheet.tiles, TILE_SPRITE_SIZE);
		public static Sprite space_tile_sprite = new Sprite(TILE_SPRITE_SIZE, 0, SpriteSheet.tiles, TILE_SPRITE_SIZE);
		public static Sprite[] wall_tile_sprite = loadConnectedTileSprites(0, TILE_SPRITE_SIZE * 4, SpriteSheet.tiles, TILE_SPRITE_SIZE);
	}
	
	/*
	* Tile Entities
	* */
	public static class TileEntities {
		public static Sprite chest_sprite = new Sprite(0, 0, SpriteSheet.tileEntities, TILE_SPRITE_SIZE);
		public static Sprite folder_sprite = new Sprite(TILE_SPRITE_SIZE, 0, SpriteSheet.tileEntities, TILE_SPRITE_SIZE);
		public static Sprite back_folder_sprite = new Sprite(TILE_SPRITE_SIZE * 2, 0, SpriteSheet.tileEntities, TILE_SPRITE_SIZE);
	}
	
	/*
	* Items
	* */
	public static class Item {
		/*
		* Weapons
		* */
		public static Sprite paper_gun = new Sprite(0, 0, SpriteSheet.items, ITEM_SPRITE_SIZE);
		public static Sprite firefox_flamethrower = new Sprite(ITEM_SPRITE_SIZE, 0, SpriteSheet.items, ITEM_SPRITE_SIZE);
		/*
		* Potions
		* */
		public static Sprite health_potion = new Sprite(0, ITEM_SPRITE_SIZE * 4, SpriteSheet.items, ITEM_SPRITE_SIZE);
		public static Sprite stamina_potion = new Sprite(ITEM_SPRITE_SIZE, ITEM_SPRITE_SIZE * 4, SpriteSheet.items, ITEM_SPRITE_SIZE);
		/*
		* Armor
		* */
		public static Sprite leather_helmet = new Sprite(ITEM_SPRITE_SIZE * 0, ITEM_SPRITE_SIZE * 5, SpriteSheet.items, ITEM_SPRITE_SIZE);
		public static Sprite leather_chestplate = new Sprite(ITEM_SPRITE_SIZE * 1, ITEM_SPRITE_SIZE * 5, SpriteSheet.items, ITEM_SPRITE_SIZE);
		public static Sprite leather_pents = new Sprite(ITEM_SPRITE_SIZE * 2, ITEM_SPRITE_SIZE * 5, SpriteSheet.items, ITEM_SPRITE_SIZE);
		public static Sprite leather_boots = new Sprite(ITEM_SPRITE_SIZE * 3, ITEM_SPRITE_SIZE * 5, SpriteSheet.items, ITEM_SPRITE_SIZE);
		/*
		* Accessories
		* */
		public static Sprite gold_diamond_necklace = new Sprite(ITEM_SPRITE_SIZE * 0, ITEM_SPRITE_SIZE * 1, SpriteSheet.items, ITEM_SPRITE_SIZE);
		public static Sprite silver_diamond_ring = new Sprite(ITEM_SPRITE_SIZE * 0, ITEM_SPRITE_SIZE * 2, SpriteSheet.items, ITEM_SPRITE_SIZE);
		public static Sprite bronze_ring = new Sprite(ITEM_SPRITE_SIZE * 1, ITEM_SPRITE_SIZE * 2, SpriteSheet.items, ITEM_SPRITE_SIZE);
		public static Sprite gold_bracelet = new Sprite(ITEM_SPRITE_SIZE * 0, ITEM_SPRITE_SIZE * 3, SpriteSheet.items, ITEM_SPRITE_SIZE);
	}
	/*
	* Equipment
	* */
	public static class Equipment {
		//Basic Equipment
		public static Sprite[] leather_helmet_sprite = loadEntityAnimation(ENTITY_SPRITE_SIZE * 2, 0, SpriteSheet.player, ENTITY_SPRITE_SIZE, 8);
		public static Sprite[] leather_chestplate_sprite = loadEntityAnimation(ENTITY_SPRITE_SIZE * 4, 0, SpriteSheet.player, ENTITY_SPRITE_SIZE, 8);
		public static Sprite[] leather_pents_sprite = loadEntityAnimation(ENTITY_SPRITE_SIZE * 6, 0, SpriteSheet.player, ENTITY_SPRITE_SIZE, 8);
		public static Sprite[] leather_boots_sprite = loadEntityAnimation(ENTITY_SPRITE_SIZE * 8, 0, SpriteSheet.player, ENTITY_SPRITE_SIZE, 8);
	}
	/*
	* Class
	
	/*
	* Values
	* */
	public final int WIDTH;
	public final int HEIGHT;
	public int[] pixels;
	private SpriteSheet spriteSheet;
	private int x;
	private int y;
	
	/*
	* Constructor
	* */
	public Sprite(int width, int height, int color) {
		this.HEIGHT = height;
		this.WIDTH = width;
		
		pixels = new int[WIDTH * HEIGHT];
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}
	public Sprite(int[] pixels, int width, int height) {
		this.pixels = pixels;
		WIDTH = width;
		HEIGHT = height;
		
		spriteSheet = null;
		x = 0;
		y = 0;
	}
	public Sprite(int xOffset, int yOffset, SpriteSheet spriteSheet, int size) {
		this(xOffset, yOffset, spriteSheet, size, size);
	}
	public Sprite(int xOffset, int yOffset, Sprite sprite, int size) {
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[WIDTH * HEIGHT];
		
		load(xOffset, yOffset, sprite);
	}
	public Sprite(int xOffset, int yOffset, SpriteSheet spriteSheet, int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[WIDTH * HEIGHT];
		this.spriteSheet = spriteSheet;
		x = xOffset;
		y = yOffset;
		
		load(xOffset, yOffset, spriteSheet);
	}
	public Sprite(Sprite sprite) {
		WIDTH = sprite.WIDTH;
		HEIGHT = sprite.HEIGHT;
		pixels = new int[WIDTH * HEIGHT];
		this.spriteSheet = sprite.spriteSheet;
		x = sprite.getImageX();
		y = sprite.getImageY();
		
		System.arraycopy(sprite.pixels, 0, pixels, 0, pixels.length);
		
	}
	
	protected void load(int xOffset, int yOffset, SpriteSheet spriteSheet) {
		if (xOffset < 0 || xOffset + WIDTH -1 >= spriteSheet.WIDTH || yOffset < 0 || yOffset + HEIGHT - 1 >= spriteSheet.HEIGHT) {
			System.out.println("[ERROR] Sprite loads pixels outside the SpriteSheet ");
			return;
		}
		int ya, xa;
		int color;
		for (int yp = 0; yp < HEIGHT; yp++) {
			ya = yp + yOffset;
			for (int xp = 0; xp < WIDTH; xp++) {
				xa = xp + xOffset;
				
				pixels[xp + yp * WIDTH] = spriteSheet.pixels[xa + ya * spriteSheet.WIDTH];
			}
		}
	}
	protected void load(int xOffset, int yOffset, Sprite sprite) {
		if (xOffset < 0 || xOffset + WIDTH -1 >= sprite.WIDTH || yOffset < 0 || yOffset + HEIGHT - 1 >= sprite.HEIGHT) {
			System.out.println("[ERROR] Sprite loads pixels outside the SpriteSheet ");
			return;
		}
		int ya, xa;
		int color;
		for (int yp = 0; yp < HEIGHT; yp++) {
			ya = yp + yOffset;
			for (int xp = 0; xp < WIDTH; xp++) {
				xa = xp + xOffset;
				color = sprite.pixels[xa + ya * sprite.WIDTH];
				
				if (color == SPRITE_INVISIBLE_COLOR_1) color = Screen.INVISIBLE_COLOR;
				if (color == SPRITE_INVISIBLE_COLOR_2) color = Screen.INVISIBLE_COLOR;
				pixels[xp + yp * WIDTH] = color;
			}
		}
	}
	
	/*
	* Util
	* */
	public int getImageX() {
		return x;
	}
	public int getImageY() {
		return y;
	}
	public int getImageMaxX() {
		return x + WIDTH;
	}
	public int getImageMaxY() {
		return y + HEIGHT;
	}
	
	public BufferedImage getImage() {
		return spriteSheet.getImage();
	}
	
	public void add(Sprite sprite) {
		if (sprite == null ||WIDTH != sprite.WIDTH || HEIGHT != sprite.HEIGHT) {
			System.out.println("[ERR] can't add the sprite do to different Sizes");
			return;
		}
		
		int color;
		for (int i = 0; i < pixels.length; i++) {
			color = sprite.pixels[i];
			
			if (color != Screen.INVISIBLE_COLOR) {
				pixels[i] = color;
			}
		}
		
	}
	
	/*
	* Static Util
	* */
	private static Sprite[] loadConnectedTileSprites(int x, int y, SpriteSheet sheet, int size) {
		Sprite[] sprites = new Sprite[16];
		sprites[0] = new Sprite(size * 0 + x, size * 0 + y, sheet, size);
		sprites[1] = new Sprite(size * 0 + x, size * 1 + y, sheet, size);
		sprites[2] = new Sprite(size * 1 + x, size * 0 + y, sheet, size);
		sprites[3] = new Sprite(size * 1 + x, size * 1 + y, sheet, size);
		sprites[4] = new Sprite(size * 0 + x, size * 3 + y, sheet, size);
		sprites[5] = new Sprite(size * 0 + x, size * 2 + y, sheet, size);
		sprites[6] = new Sprite(size * 1 + x, size * 3 + y, sheet, size);
		sprites[7] = new Sprite(size * 1 + x, size * 2 + y, sheet, size);
		sprites[8] = new Sprite(size * 3 + x, size * 0 + y, sheet, size);
		sprites[9] = new Sprite(size * 3 + x, size * 1 + y, sheet, size);
		sprites[10] = new Sprite(size * 2 + x, size * 0 + y, sheet, size);
		sprites[11] = new Sprite(size * 2 + x, size * 1 + y, sheet, size);
		sprites[12] = new Sprite(size * 3 + x, size * 3 + y, sheet, size);
		sprites[13] = new Sprite(size * 3 + x, size * 2 + y, sheet, size);
		sprites[14] = new Sprite(size * 2 + x, size * 3 + y, sheet, size);
		sprites[15] = new Sprite(size * 2 + x, size * 2 + y, sheet, size);
		
		return sprites;
	}
	
	public static Sprite[] loadEntityAnimation(int x, int y, SpriteSheet sheet, int size, int animationSprites) {
		Sprite[] sprites = new Sprite[4 * animationSprites];
		
		/*
		* Lade pattern
		* 
		*  0    4   8   12
		*  1    5   9   13
		*  2    6   10  14
		*  3    7   11  15
		* */
		
		
		int i = 0;
		for (int xp = 0; xp < 2; xp++) {
			for (int yp = 0; yp < animationSprites; yp++) {
				sprites[i++] = new Sprite(size * xp + x, size * yp + y, sheet, size);
			}
		}
		return sprites;
	}
	
	private static Sprite[] loadSplitSprite(Sprite sprite, int size) {
		int widthParticleCount = sprite.WIDTH / size;
		int heightParticleCount = sprite.HEIGHT / size;
		Sprite[] sprites = new Sprite[widthParticleCount * heightParticleCount];
		
		int i = 0;
		int xs, xa, ya;
		for (int ys = 0; ys < heightParticleCount; ys++) {
			ya = ys * size;
			for (xs = 0; xs < widthParticleCount; xs++) {
				xa = xs * size;
				
				sprites[i++] = new Sprite(xa, ya, sprite, size);
				
			}
		}
		
		return sprites;
	}
	private static Sprite[] loadParticlesFromSprites(Sprite sprite) {
		int widthParticleCount = sprite.WIDTH / Particle.SPRITE_PARTICLE_SIZE;
		int heightParticleCount = sprite.HEIGHT / Particle.SPRITE_PARTICLE_SIZE;
		Sprite[] particles = new Sprite[widthParticleCount * heightParticleCount];
		
		int i = 0;
		int xs, xa, ya;
		for (int ys = 0; ys < heightParticleCount; ys++) {
			ya = ys * Particle.SPRITE_PARTICLE_SIZE;
			for (xs = 0; xs < widthParticleCount; xs++) {
				xa = xs * Particle.SPRITE_PARTICLE_SIZE;
				
				particles[i++] = new Sprite(xa, ya, sprite, Particle.SPRITE_PARTICLE_SIZE);
				
			}
		}
		
		return particles;
	}
	
	private static Sprite[] loadItemFrames() {
		Sprite[] itemFrames = new Sprite[GUIItemFrame.TYPE_COUNT];
		
		int x = 0;
		int y = 0;
		for (int i = 0; i < GUIItemFrame.TYPE_COUNT; i++) {
			itemFrames[i] = new Sprite(x, y, SpriteSheet.guiComponents, GUIItemFrame.SPRITE_SIZE);
			x += GUIItemFrame.SPRITE_SIZE;
		}
		
		return itemFrames;
	}
	
}
