package com.gmail.xfrednet.filefighter.item.item.weapon;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.item.item.Weapon;
import com.gmail.xfrednet.filefighter.level.Level;

import java.util.List;

/**
 * Created by xFrednet on 30.03.2016 at 01:11.
 */
public abstract class AreaWeapon extends Weapon {
	
	protected AreaWeapon() {
		super();
	}
	
	@Override
	public boolean attack(Level level, LivingEntity executingEntity, double angle) {
		if (!isUsable(executingEntity) || useTimer > 0 ) return false;
		executingEntity.useStamina(getStaminaUsage());		
		
		List<LivingEntity> targets = level.getEnemies(executingEntity, getRange());
		
		for (int i = 0; i < targets.size(); i++) {
			targets.get(i).damage(level, executingEntity, getDamage());
		}
		
		useTimer += getUseDelay();
		return false;
	}
	
	/*
	* getters
	* */
	@Override
	public boolean isUsable(LivingEntity executingEntity) {
		return executingEntity.hasEnoughStamina(getStaminaUsage());
	}
	public Damage getDamage() {
		return new Damage(getDamageType(), getDamageAmount());
	}
	
	/*
	* abstract getters
	* */
	abstract public double getDamageAmount();
	abstract public double getRange();
	abstract public double getStaminaUsage();
	
	@Override
	public int getUseDelay() {
		return 10;
	}
	
}
