package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.graphics.Screen;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.level.Level;

import java.util.List;

/**
 * Created by xFrednet on 11.02.2016.
 */
public abstract class Projectile extends Entity {
	
	public static final int PARTICLES_ON_DESTROY = 10;
 	
	protected double direction;
	protected double speed;
	protected Damage damage;
	protected int shootingEntityID;
	protected int team;
	
	/*
	* Constructor
	* */
	protected Projectile(Level level, String name, double direction, double speed, Damage damage, Entity shootingEntity, Sprite sprite) {
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
			double xm1 = 1 * Math.sin(angle);
			double ym1 = 1 * Math.cos(angle);
			
			while (speed > 1) {
				if (!move(xm1, ym1, level)) return;
				projectileMoved(level);
				speed--;
			}
			
		}
		
		move(speed * Math.sin(angle), speed * Math.cos(angle), level);
		
	}
	
	protected void projectileMoved(Level level) {}
	
	private boolean move(double xm, double ym, Level level) {
		LivingEntity collidingEntities = null;
		if (!levelCollision(xm, ym, level) && (collidingEntities = entityCollision(xm, ym, level)) == null) {
			info.x += xm;
			info.y += ym;
			return true;
		} else {
			destroy(level);
			
			if (collidingEntities != null) 
				collidingEntities.damage(this, damage);
			
			return false;
		}
	}
	
	public LivingEntity  entityCollision(double xm, double ym, Level level) {
		List<LivingEntity> entities = level.livingEntityMotionCollision(xm ,ym, this);
		
		if (entities.size() == 0) return null;
		
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getTeam() != team) return entities.get(i);
		}
		
		return null;
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
