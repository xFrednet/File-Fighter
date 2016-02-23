package com.gmail.xfrednet.filefighter.item.item;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 14.02.2016.
 */
public abstract class Weapon extends Item {
	
	private boolean damageType;
	
	protected Weapon(String name) {
		super(name);
		maxStackSize = 1;
	}
	
	public boolean usePrimaryAction(Level level, Entity executingEntity, double angle) {
		return attack(level, executingEntity, angle);
	}
	
	abstract public boolean attack(Level level, Entity executingEntity, double angle);
	
	abstract public int getDamageType();
}
