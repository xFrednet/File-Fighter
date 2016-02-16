package com.gmail.xfrednet.filefighter.graphics;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.level.Level;

import java.util.Random;

/**
 * Created by xFrednet on 12.02.2016.
 */
public class Particle {
	
	public static final int COLLISION_NO_WERE = 0; 
	public static final int COLLISION_CORNER_1 = 1;
	public static final int COLLISION_CORNER_2 = 2;
	public static final int COLLISION_CORNER_3 = 3;
	public static final int COLLISION_CORNER_4 = 4;
	public static final int COLLISION_EVERY_WERE = 5;
	
	public static final int SPRITE_PARTICLE_SIZE = 2;
	public static final int DEFAULT_MAX_LIVE_TIME = Main.UPS * 3;
	public static final double DEFAULT_SPEED = 0.5;
	public static final double MINIMUM_LIVE_TIME_PERCENTAGE = 0.5;
	public static final Random random = new Random();
	private static final double PI = 3.141592653589793;
	
	final int WIDTH;
	final int HEIGHT;
	int liveTime;
	double x;
	double y;
	double angle;
	double speed;
	boolean removed = false;
	Sprite sprite;
	
	/*
	* Constructor
	* */
	public Particle(double x, double y, Level level, Sprite[] sprites) {
		this(x, y, level, DEFAULT_MAX_LIVE_TIME, PI * 2 * random.nextDouble() - PI, DEFAULT_SPEED, sprites);
	}
	public Particle(double x, double y, Level level, double angle, Sprite[] sprites) {
		this(x, y, level, DEFAULT_MAX_LIVE_TIME, angle, DEFAULT_SPEED, sprites);
	}
	public Particle(double x, double y, Level level, double angle, double speed, Sprite[] sprites) {
		this(x, y, level, DEFAULT_MAX_LIVE_TIME, angle, speed, sprites);
	}
	public Particle(double x, double y, Level level, int maxLiveTime, double angle, Sprite[] sprites) {
		this(x, y, level, maxLiveTime, angle, DEFAULT_SPEED, sprites);
	}
	
	public Particle(double x, double y, Level level, int maxLiveTime, double angle, double speed, Sprite[] sprites) {
		this(x, y, level, maxLiveTime, angle, speed, sprites[random.nextInt(sprites.length)]);
	}
	public Particle(double x, double y, Level level, int maxLiveTime, double angle, double speed, Sprite sprite) {
		
		this.liveTime = (int)(maxLiveTime * MINIMUM_LIVE_TIME_PERCENTAGE) + random.nextInt((int) (maxLiveTime * (1 - MINIMUM_LIVE_TIME_PERCENTAGE)));
		this.angle = angle;
		this.speed = speed * random.nextDouble();
		this.sprite = sprite;
		
		WIDTH = sprite.WIDTH;
		HEIGHT = sprite.HEIGHT;
		
		spawnPosition(level, x, y);
		
	}
	
	private void spawnPosition(Level level, double x, double y) {
		
		switch (spawnCollision(x, y, level)) {
			case COLLISION_EVERY_WERE:
				this.x = x - WIDTH / 2;
				this.y = y - HEIGHT / 2;
				liveTime = 0;
				return;
			case COLLISION_NO_WERE:
				this.x = x - WIDTH / 2;
				this.y = y - HEIGHT / 2;
				return;
			case COLLISION_CORNER_1:
				x -= WIDTH / 2;
				y -= HEIGHT / 2;
				break;
			case COLLISION_CORNER_2:
				x += WIDTH / 2;
				y -= HEIGHT / 2;
				break;
			case COLLISION_CORNER_3:
				x -= WIDTH / 2;
				y += HEIGHT / 2;
				break;
			case COLLISION_CORNER_4:
				x += WIDTH / 2;
				y += HEIGHT / 2;
				break;
		}
		
		if (spawnCollision(x, y, level) == COLLISION_NO_WERE) {
			this.x = x;
			this.y = y;
		} else {
			liveTime = 0;
		}
		
	}
	
	/*
	* Game loop Methods
	* */
	//update
	public void update(Level level) {
		if (--liveTime <= 0) {
			removed = true;
			return;
		}
		move(level);
	}
	public void move(Level level) {
		double xm = speed * Math.sin(angle);
		double ym = speed * Math.cos(angle);
		
		if (!levelCollision(xm, 0, level)) {
			x += xm;
		}
	
		if (!levelCollision(0, ym, level)) {
			y += ym;
		}
	}
	public boolean levelCollision(double xm, double ym, Level level) {
		
		// + -
		// - -
		int xt = (int) (x + xm) >> 5;
		int yt = (int) (y + ym) >> 5;
		if (level.isSolid(xt, yt)) return true;
		
		// - +
		// - -
		xt = (int) (x + WIDTH + xm) >> 5;
		yt = (int) (y + ym) >> 5;
		if (level.isSolid(xt, yt)) return true;
		
		// - -
		// + -
		xt = (int) (x + xm) >> 5;
		yt = (int) (y + HEIGHT + ym) >> 5;
		if (level.isSolid(xt, yt)) return true;
		
		// - -
		// - +
		xt = (int) (x + WIDTH + xm) >> 5;
		yt = (int) (y + HEIGHT + ym) >> 5;
		if (level.isSolid(xt, yt)) return true;
		
		return false;
	}
	
	//returns 0 if it does not collide
	public int spawnCollision(double x, double y, Level level) {
		
		int collision = COLLISION_EVERY_WERE;
		int freeSpots = 0;
		
		// + -
		// - -
		int xt = (int) (x) >> 5;
		int yt = (int) (x) >> 5;
		if (!level.isSolid(xt, yt)) {
			collision = COLLISION_CORNER_1;
			freeSpots++;
		}
		
		// - +
		// - -
		xt = (int) (x + WIDTH) >> 5;
		yt = (int) (y) >> 5;
		if (!level.isSolid(xt, yt)) {
			collision = COLLISION_CORNER_2;
			freeSpots++;
		}
		
		// - -
		// + -
		xt = (int) (x) >> 5;
		yt = (int) (y + HEIGHT) >> 5;
		if (!level.isSolid(xt, yt)) {
			collision = COLLISION_CORNER_3;
			freeSpots++;
		}
		
		// - -
		// - +
		xt = (int) (x + WIDTH) >> 5;
		yt = (int) (y + HEIGHT) >> 5;
		if (!level.isSolid(xt, yt)) {
			collision = COLLISION_CORNER_4;
			freeSpots++;
		}
		if (freeSpots == 4) {
			return COLLISION_NO_WERE;
		} else {
			return collision;
		}
	}
	
	//render
	public void render(Screen screen) {
		screen.drawSprite((int)x, (int)y, sprite, false);
	}
	
	/*
	* getters
	* */
	public boolean isRemoved() {
		return removed;
	}
	
}
