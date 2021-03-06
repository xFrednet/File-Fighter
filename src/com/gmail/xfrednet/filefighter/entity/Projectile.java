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
	
	public static final int PARTICLES_ON_DESTROY = 20;
 	
	protected double direction;
	protected double speed;
	protected double range = 0;
	protected double maxRange;
	protected Damage damage;
	protected int shootingEntityID;
	protected int team;
	
	/*
	* Constructor
	* */
	protected Projectile(Level level, String name, double direction, double speed, double maxRange, Damage damage, Entity shootingEntity) {
		super(level, name, false);
		this.direction = direction;
		this.speed = speed;
		this.damage = damage;
		this.maxRange = (maxRange * 3/4) + (random.nextDouble() * (maxRange / 1/4));
		this.shootingEntityID = shootingEntity.getID();
		team = shootingEntity.getTeam();
		
		this.sprite = getSprite();
	}
	
	protected void setInfo(Entity entity, int width, int height, int spriteXOffset, int spriteYOffset) {
		setInfo(entity.getInfo().getCenterX(), entity.getInfo().getCenterY(), width, height, spriteXOffset, spriteYOffset);
	}
	
	/*
	* Util
	* */
	public void update(Level level) {
		move(direction, level, speed);
	}
	public void render(Screen screen) {
		screen.drawProjectile(info.getSpriteX(), info.getSpriteY(), sprite, direction);
		if (showBoundingBoxes) drawBoundingBox(screen);
	}
	
	public void move(double angle, Level level, double speed) {
		
		if (speed > 1) {
			double xm1 = 1 * Math.sin(angle);
			double ym1 = 1 * Math.cos(angle);
			
			while (speed > 1) {
				if (!move(xm1, ym1, level)) return;
				projectileMoved(level);
				range++;
				speed--;
			}
			
		}
		
		move(speed * Math.sin(angle), speed * Math.cos(angle), level);
		range += speed;
	}
	private boolean move(double xm, double ym, Level level) {
		LivingEntity collidingEntity = null;
		if (!levelCollision(xm, ym, level) && ((collidingEntity = entityCollision(xm, ym, level)) == null || !hasEntityCollision()) && range < maxRange) {
			info.x += xm;
			info.y += ym;
			return true;
		} else {
			destroy(level);
			
			if (collidingEntity != null && hasEntityCollision()) {
				entityCollided(level, collidingEntity);
			}
			
			return false;
		}
	}
	protected boolean hasEntityCollision() {
		return true;
	}
	
	protected void entityCollided(Level level, LivingEntity entity) {
		Entity damageSource;
		
		if ((damageSource = level.getEntity(shootingEntityID)) == null)
			damageSource = this;
		
		entity.damage(level, damageSource, damage);
	}
	
	
	protected void projectileMoved(Level level) {}
	
	public LivingEntity entityCollision(double xm, double ym, Level level) {
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
		level.spawnParticles(info.getCenterX(), info.getCenterY(), getParticlesOnDestroy(), getParticleSprites());
	}
	
	/*
	* abstract
	* */
	abstract protected Sprite[] getParticleSprites();
	abstract public Sprite getSprite();
	
	protected int getParticlesOnDestroy() {
		return PARTICLES_ON_DESTROY;
	}
}
