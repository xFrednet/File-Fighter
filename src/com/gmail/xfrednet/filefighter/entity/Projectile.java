package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;

import java.util.List;

/**
 * Created by xFrednet on 11.02.2016.
 */
public abstract class Projectile extends Entity {
	
	public static final int PARTICLES_ON_DESTROY = 10;
 	
	protected double direction;
	protected int spriteDirection = 0;
	protected double speed;
	protected double damage;
	protected int shootingEntityID;
	protected int team;
	
	/*
	* Constructor
	* */
	protected Projectile(Level level, String name, double direction, double speed, double damage, Entity shootingEntity, Sprite sprite) {
		super(level, name, false);
		this.direction = direction;
		this.speed = speed;
		this.damage = damage;
		this.shootingEntityID = shootingEntity.getID();
		team = shootingEntity.getTeam();
		
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
		if (speed > 1) {
			move(angle, level, speed - 1);
			speed = 1;
		}
		double xm = speed * Math.sin(angle);
		double ym = speed * Math.cos(angle);
		
		move(xm, ym, level);
	}
	private void move(double xm, double ym, Level level) {
		
		if (!levelCollision(xm, ym, level) && !entityCollision(xm, ym, level)) {
			info.x += xm;
			info.y += ym;
		} else {
			destroy(level);
		}
	}
	
	public boolean entityCollision(double xm, double ym, Level level) {
		List<LivingEntity> entities = level.livingEntityMotionCollision(xm ,ym, this);
		
		if (entities.size() == 0) return false;
		
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getTeam() != team) return true;
		}
		
		
		return false;
	}
	
	//destroy
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
