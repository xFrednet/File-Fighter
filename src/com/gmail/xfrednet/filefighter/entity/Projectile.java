package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 11.02.2016.
 */
public abstract class Projectile extends Entity {
	
	double direction;
	double speed;
	double damage;
	int shootingEntityID;
	
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
	
	public void move(double angle, Level level, double speed) {
		double xm = speed * Math.sin(angle);
		double ym = speed * Math.cos(angle);
		
		move(xm, ym, level);
	}
	private void move(double xm, double ym, Level level) {
		info.x += xm;
		info.y += ym;
		
		if (levelCollision(xm, ym, level)) {
			destroy(level);
		}
		
	}
	
	public void destroy(Level level) {
		removed = true;
	}
	
}
