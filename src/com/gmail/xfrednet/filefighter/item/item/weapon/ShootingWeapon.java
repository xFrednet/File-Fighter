package com.gmail.xfrednet.filefighter.item.item.weapon;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.item.item.Weapon;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 29.02.2016.
 */
public abstract class ShootingWeapon extends Weapon {
	
	protected ShootingWeapon(String name) {
		super(name);
	}
	
	/*
	* getters
	* */
	protected double getModifiedAngle(double angle) {
		double rad = ((100 - getAccuracy()) / 2000) * Math.PI / 180;
		return angle + ((rad * random.nextDouble()) - rad / 2);  
	}
	
	/*
	* abstract getters
	* */
	abstract public double getDamage();
	abstract public double getShootSpeed();
	abstract public double getRange();
	abstract public double getAccuracy();
	abstract public double getProjectileSpeed();
	abstract public double getStaminaUsage();
}
