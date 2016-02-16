package com.gmail.xfrednet.filefighter.graphics;

/**
 * Created by xFrednet on 06.02.2016.
 */
public class Sprite {
	
	public static final int TILE_SPRITE_SIZE = 32;
	public static final int ENTITY_SPRITE_SIZE = 32;
	public static final int PROJECTILE_SPRITE_SIZE = 15;
	public static final int ITEM_ENTITY_SPRITE_SIZE = 32;
	
	public static final int SPRITE_INVISIBLE_COLOR_1 = 0xffffbb0f;
	public static final int SPRITE_INVISIBLE_COLOR_2 = 0xffff0000;
	
	/*
	* Test Code
	* */
	
	public static Sprite null_sprite = new Sprite(16, 16, 0xffff01ff);
	/*
	* Static Sprites
	* */
	public static Sprite null_tile_sprite = new Sprite(0, 0, SpriteSheet.tiles, TILE_SPRITE_SIZE);
	public static Sprite space_tile_sprite = new Sprite(TILE_SPRITE_SIZE, 0, SpriteSheet.tiles, TILE_SPRITE_SIZE);
	
	public static Sprite[] wall_tile_sprite = loadConnectedTileSprites(0, TILE_SPRITE_SIZE * 4, SpriteSheet.tiles, TILE_SPRITE_SIZE);
	/*
	* Entity Sprites
	* */
	public static Sprite[] slime_entity_sprites = loadEntityAnimation(0, 0, SpriteSheet.entities, ENTITY_SPRITE_SIZE, 16);
	public static Sprite[] player_entity_sprites = loadEntityAnimation(ENTITY_SPRITE_SIZE * 2, 0, SpriteSheet.entities, ENTITY_SPRITE_SIZE, 16);
	
	//File Sprites
	public static Sprite[] textFile_entity_sprites = loadEntityAnimation(ENTITY_SPRITE_SIZE * 4, 0, SpriteSheet.entities, ENTITY_SPRITE_SIZE, 6);
	public static Sprite[] jpgFile_entity_sprites = loadEntityAnimation(ENTITY_SPRITE_SIZE * 6, 0, SpriteSheet.entities, ENTITY_SPRITE_SIZE, 16);
	
	/*
	* Projectiles
	* */
	public static Sprite paper_projectile_sprite = new Sprite(0, 0, SpriteSheet.projectiles, PROJECTILE_SPRITE_SIZE);
	public static Sprite[] paper_projectile_particles = loadParticlesFromSprites(paper_projectile_sprite);
	
	/*
	* Particles
	* */
	public static Sprite[] smoke_particles = loadSplitSprite(new Sprite(0,0, SpriteSheet.particles, 16), 3);
	
	
	
	/*
	* Class
	* */
	
	/*
	* Values
	* */
	
	public final int WIDTH;
	public final int HEIGHT;
	public int[] pixels;
	
	/*
	* Constructor
	* */
	private Sprite(int width, int height, int color) {
		this.HEIGHT = height;
		this.WIDTH = width;
		
		pixels = new int[WIDTH * HEIGHT];
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}
	private Sprite(int xOffset, int yOffset, SpriteSheet spriteSheet, int size) {
		this(xOffset, yOffset, spriteSheet, size, size);
	}
	private Sprite(int xOffset, int yOffset, Sprite sprite, int size) {
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[WIDTH * HEIGHT];
		
		load(xOffset, yOffset, sprite);
	}
	private Sprite(int xOffset, int yOffset, SpriteSheet spriteSheet, int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[WIDTH * HEIGHT];
		
		load(xOffset, yOffset, spriteSheet);
	}
	
	private void load(int xOffset, int yOffset, SpriteSheet spriteSheet) {
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
				color = spriteSheet.pixels[xa + ya * spriteSheet.WIDTH];
				
				if (color == SPRITE_INVISIBLE_COLOR_1) color = Screen.INVISIBLE_COLOR;
				if (color == SPRITE_INVISIBLE_COLOR_2) color = Screen.INVISIBLE_COLOR;
				pixels[xp + yp * WIDTH] = color;
			}
		}
	}
	private void load(int xOffset, int yOffset, Sprite sprite) {
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
	
	private static Sprite[] loadProjectileSprites(int x, int y, SpriteSheet sheet, int size) {
		Sprite[] sprites = new Sprite[2];
		
		sprites[0] = new Sprite(x, y, sheet, size);
		sprites[1] = new Sprite(x, size + y, sheet, size);
	
		return sprites;
	}
}
