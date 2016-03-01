package com.gmail.xfrednet.filefighter.item.item;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;

/**
 * Created by xFrednet on 18.02.2016.
 */
public class Damage {
	
	public static final int PHYSICAL_DAMAGE = 1;
	public static final int MENTAL_DAMAGE = 2;
	
	int damageType;
	double damageAmount;
	
	public Damage(int damageType, double baseDamage, Entity shootingEntity) {
		this.damageType = damageType;
		this.damageAmount = baseDamage;
		if (shootingEntity instanceof LivingEntity) {
			if (damageType == PHYSICAL_DAMAGE) {
				this.damageAmount += ((LivingEntity)shootingEntity).getAttribute(LivingEntity.ATTRIBUTE_STRENGTH);
			} else {
				this.damageAmount += ((LivingEntity)shootingEntity).getAttribute(LivingEntity.ATTRIBUTE_INTELLIGENCE);
			}
		}
	}
	
	public Damage(int damageType, double damageAmount) {
		this.damageType = damageType;
		this.damageAmount = damageAmount;
	}
	
	public int getDamageType() {
		return damageType;
	}
	
	public double getDamageAmount() {
		return damageAmount;
	}
}