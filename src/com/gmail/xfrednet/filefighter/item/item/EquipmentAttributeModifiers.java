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
	
	public double getAttributeModifiers(int attribute) {
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
			//default
			default: return 0;
		}
	}
	
	/*
	* setters
	* */
	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}
	public void setMaxStamina(double maxStamina) {
		this.maxStamina = maxStamina;
	}
	
	//defence
	public void setPhysicalDefence(double physicalDefence) {
		this.physicalDefence = physicalDefence;
	}
	public void setMentalDefence(double mentalDefence) {
		this.mentalDefence = mentalDefence;
	}
	
	//damage
	public void setPhysicalDamage(double physicalDamage) {
		this.physicalDamage = physicalDamage;
	}
	public void setMentalDamage(double mentalDamage) {
		this.mentalDamage = mentalDamage;
	}
	
	//luck
	public void setLuck(double luck) {
		this.luck = luck;
	}
	
	//regeneration
	public void setHealthRegeneration(double healthRegeneration) {
		this.healthRegeneration = healthRegeneration;
	}
	public void setStaminaRegeneration(double staminaRegeneration) {
		this.staminaRegeneration = staminaRegeneration;
	}
}
