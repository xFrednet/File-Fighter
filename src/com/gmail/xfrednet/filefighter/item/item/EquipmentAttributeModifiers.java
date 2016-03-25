package com.gmail.xfrednet.filefighter.item.item;

import static com.gmail.xfrednet.filefighter.entity.LivingEntity.*;

/**
 * Created by xFrednet on 08.03.2016.
 */
public class EquipmentAttributeModifiers {
	
	double maxHealth = 0;
	double maxStamina = 0;
	
	//defence
	double physicalDefence = 0;
	double mentalDefence = 0;
	
	//damage
	double physicalDamage = 0;
	double mentalDamage = 0;
	
	//luck
	double luck = 0;
	
	//regeneration
	double healthRegeneration = 0;
	double staminaRegeneration = 0;
	
	//speed
	double speed = 0;
	
	/*
	* Getters
	* */
	public double getAttributeModifier(int attribute) {
		switch (attribute) {
			case ATTRIBUTE_MAX_HEALTH: return maxHealth;
			case ATTRIBUTE_MAX_STAMINA: return maxStamina;
			//defence
			case ATTRIBUTE_PHYSICAL_DEFENCE: return physicalDefence;
			case ATTRIBUTE_MENTAL_DEFENCE: return mentalDefence;
			//damage
			case ATTRIBUTE_PHYSICAL_DAMAGE: return physicalDamage;
			case ATTRIBUTE_MENTAL_DAMAGE: return mentalDamage;
			//luck
			case ATTRIBUTE_LUCK: return luck;
			//regeneration
			case ATTRIBUTE_HEALTH_REGENERATION: return healthRegeneration;
			case ATTRIBUTE_STAMINA_REGENERATION: return staminaRegeneration;
			//speed
			case ATTRIBUTE_SPEED: return speed;
			//default
			default: return 0;
		}
	}
	
	/*
	* setters
	* */
	public EquipmentAttributeModifiers setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
		return this;
	}
	public EquipmentAttributeModifiers setMaxStamina(double maxStamina) {
		this.maxStamina = maxStamina;
		return this;
	}
	
	//defence
	public EquipmentAttributeModifiers setPhysicalDefence(double physicalDefence) {
		this.physicalDefence = physicalDefence;
		return this;
	}
	public EquipmentAttributeModifiers setMentalDefence(double mentalDefence) {
		this.mentalDefence = mentalDefence;
		return this;
	}
	
	//damage
	public EquipmentAttributeModifiers setPhysicalDamage(double physicalDamage) {
		this.physicalDamage = physicalDamage;
		return this;
	}
	public EquipmentAttributeModifiers setMentalDamage(double mentalDamage) {
		this.mentalDamage = mentalDamage;
		return this;
	}
	
	//luck
	public EquipmentAttributeModifiers setLuck(double luck) {
		this.luck = luck;
		return this;
	}
	
	//regeneration
	public EquipmentAttributeModifiers setHealthRegeneration(double healthRegeneration) {
		this.healthRegeneration = healthRegeneration;
		return this;
	}
	public EquipmentAttributeModifiers setStaminaRegeneration(double staminaRegeneration) {
		this.staminaRegeneration = staminaRegeneration;
		return this;
	}
	
	//speed
	public EquipmentAttributeModifiers setSpeed(double speed) {
		this.speed = speed;
		return this;
	}
}
