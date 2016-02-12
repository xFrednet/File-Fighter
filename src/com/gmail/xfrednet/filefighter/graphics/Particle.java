package com.gmail.xfrednet.filefighter.graphics;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.level.Level;

import java.util.Random;

/**
 * Created by xFrednet on 12.02.2016.
 */
public class Particle {
	
	public static final int SPRITE_PARTICLE_SIZE = 2;
	public static final int DEFAULT_MAX_LIVE_TIME = Main.UPS * 3;
	public static final double DEFAULT_SPEED = 0.3;
	public static final double MINIMUM_LIVE_TIME_PERCENTAGE = 0.5;
	public static final Random random = new Random();
	private static final double PI = 3.141592653589793;
	
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
	public Particle(double x, double y, Sprite[] sprites) {
		this(x, y, DEFAULT_MAX_LIVE_TIME, PI * 2 * random.nextDouble() - PI, DEFAULT_SPEED, sprites);
	}
	public Particle(double x, double y, double angle, Sprite[] sprites) {
		this(x, y, DEFAULT_MAX_LIVE_TIME, angle, DEFAULT_SPEED, sprites);
	}
	public Particle(double x, double y, double angle, double speed, Sprite[] sprites) {
		this(x, y, DEFAULT_MAX_LIVE_TIME, angle, speed, sprites);
	}
	public Particle(double x, double y, int maxLiveTime, double angle, Sprite[] sprites) {
		this(x, y, maxLiveTime, angle, DEFAULT_SPEED, sprites);
	}
	
	public Particle(double x, double y, int maxLiveTime, double angle, double speed, Sprite[] sprites) {
		this(x, y, maxLiveTime, angle, speed, sprites[random.nextInt(sprites.length)]);
	}
	public Particle(double x, double y, int maxLiveTime, double angle, double speed, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.liveTime = (int)(maxLiveTime * MINIMUM_LIVE_TIME_PERCENTAGE) + random.nextInt((int) (maxLiveTime * (1 - MINIMUM_LIVE_TIME_PERCENTAGE)));
		this.angle = angle;
		this.speed = speed;
		this.sprite = sprite;
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
		xt = (int) (x + SPRITE_PARTICLE_SIZE + xm) >> 5;
		yt = (int) (y + ym) >> 5;
		if (level.isSolid(xt, yt)) return true;
		
		// - -
		// + -
		xt = (int) (x + xm) >> 5;
		yt = (int) (y + SPRITE_PARTICLE_SIZE + ym) >> 5;
		if (level.isSolid(xt, yt)) return true;
		
		// - -
		// - +
		xt = (int) (x + SPRITE_PARTICLE_SIZE + xm) >> 5;
		yt = (int) (x + SPRITE_PARTICLE_SIZE + ym) >> 5;
		if (level.isSolid(xt, yt)) return true;
		
		return false;
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
