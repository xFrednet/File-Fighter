package com.gmail.xfrednet.filefighter.item.item;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.item.weapon.ShootingWeapon;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 14.02.2016.
 */
public abstract class Weapon extends Item {
	
	private boolean damageType;
	
	protected Weapon() {
		super();
	}
	
	public boolean usePrimaryAction(Level level, LivingEntity executingEntity, double angle) {
		return attack(level, executingEntity, angle);
	}
	
	public int getMaxStackSize() {
		return 1;
	}
	/*
	* abstract
	* */
	abstract public boolean attack(Level level, LivingEntity executingEntity, double angle);
	abstract public int getDamageType();
	abstract public boolean isUsable(LivingEntity executingEntity);
	
	public double getRange() {
		if (this instanceof ShootingWeapon) {
			ShootingWeapon w = (ShootingWeapon) this;
			return w.getRange();
		} else {
			return 0;
		}
	}
}
