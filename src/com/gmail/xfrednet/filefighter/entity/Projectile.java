package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 11.02.2016.
 */
public abstract class Projectile extends Entity {
	
	public static final int PARTICLES_ON_DESTROY = 10;
	
	public static final double PI = 3.14159265358979323846264;
	public static final double PI_1_eighth = (1 / 8.0) * PI;
	public static final double PI_3_eighth = (3 / 8.0) * PI;
	public static final double PI_5_eighth = (5 / 8.0) * PI;
	public static final double PI_7_eighth = (7 / 8.0) * PI;
 	
	protected double direction;
	protected int spriteDirection = 0;
	protected double speed;
	protected double damage;
	protected int shootingEntityID;
	
	/*
	* Constructor
	* */
	protected Projectile(Level level, String name, double direction, double speed, double damage, Entity shootingEntity, Sprite sprite) {
		super(level, name, false);
		this.direction = direction;
		this.speed = speed;
		this.damage = damage;
		this.shootingEntityID = shootingEntity.getID();
		
		this.currentSprite = sprite;
	}
	
	
	/*
	* Util
	* */
	public void update(Level level) {
		move(direction, level, speed);
	}
	public void render(Screen screen) {
		screen.drawProjectile(info.getSpriteX(), info.getSpriteY(), currentSprite, direction);
		if (showBoundingBoxes) drawBoundingBox(screen);
	}
	
	public void move(double angle, Level level, double speed) {
		double xm = speed * Math.sin(angle);
		double ym = speed * Math.cos(angle);
		
		if (xm > 1 || ym > 1) {
			
			int loopTime = (xm > ym) ? ((int) xm) : ((int) ym);
			double loop_xm = xm / loopTime;
			double loop_ym = ym / loopTime;
			for (int i = 0; i < loopTime; i++) {
				move(loop_xm, 0, level);
				xm -= loop_xm;
				
				move(0, loop_ym, level);
				ym -= loop_ym;
			}
		}
		move(xm, ym, level);
	}
	private void move(double xm, double ym, Level level) {
		
		if (!levelCollision(xm, ym, level)) {
			info.x += xm;
			info.y += ym;
		} else {
			destroy(level);
		}
		
	}
	
	public void destroy(Level level) {
		removed = true;
		level.spawnParticles(info.getCenterX(), info.getCenterY(), PARTICLES_ON_DESTROY, getParticleSprites());
	}
	
	/*
	* abstract
	* */
	abstract protected Sprite[] getParticleSprites();
	abstract protected Sprite getSprite();
}
