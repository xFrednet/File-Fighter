package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.entitytask.Behavior;
import com.gmail.xfrednet.filefighter.item.item.Weapon;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 06.02.2016.
 */
public abstract class LivingEntity extends Entity {
	
	public static final int MAX_ANIMATION_VALUE = 10000;
	public static final int ANIMATION_SPEED =  ((int) (Main.UPS * 0.2) == 0) ? 1 : (int) (Main.UPS * 0.2);
	public static final int STILL_STANDING_SPRITE_INDEX = 0;
	
	//attributes
	protected double maxHealth;
	protected double physicalDefence;
	protected double mentalDefence;
	protected double strength;
	protected double intelligence;
	protected double luck;
	
	//current attributes
	protected double health;
	
	public Weapon weapon;
	public Behavior behavior;
	
	//texture related
	protected int direction = 0;
	protected int animation = 0;
	public boolean isStanding = false;
	
	/*
	* Constructor
	* */
	
	protected LivingEntity(Level level, String name) {
		this(level, name, null);
	}
	protected LivingEntity(Level level, String name, Behavior behavior) {
		super(level, name);
		updateCurrentSprite();
		this.behavior = behavior;
	}
	
	public void setAttributes(double maxHealth, double physicalDefence, double mentalDefence, double intelligence, double strength, double luck) {
		this.maxHealth = maxHealth;
		this.physicalDefence = physicalDefence;
		this.mentalDefence = mentalDefence;
		this.strength = strength;
		this.intelligence = intelligence;
		this.luck = luck;
	}
	public void setHealth(double health) {
		this.health = health;
	}
	
	/*
	* Util
	* */
	public void move(double angle, Level level, double speed) {
		if (speed > 1) {
			double xm1 = 1 * Math.sin(angle);
			double ym1 = 1 * Math.cos(angle);
			
			while (speed > 1) {
				move(xm1, ym1, level);
				speed--;
			}
			
		}
		
		move(speed * Math.sin(angle), speed * Math.cos(angle), level);
	}
	private void move(double xm, double ym, Level level) {
		if (ym < 0) direction = 1;
		else if (ym > 0) direction = 0;

		if (!levelCollision(xm, 0, level)) {
			info.x += xm;
		}
		
		if (!levelCollision(0, ym, level)) {
			info.y += ym;
		}
		
	}
	
	protected void updateAnimation() {
		animation++;
		if (animation > MAX_ANIMATION_VALUE) {
			animation = animation % MAX_ANIMATION_VALUE;
		}
		
		updateCurrentSprite();
	}
	
	@Override
	public void update(Level level) {
		if (weapon != null) weapon.update(level);
		if (behavior != null) behavior.update(this, level);
		updateAnimation();
	}
	abstract protected void updateCurrentSprite();
	
	//damage
	public void damage(Entity damageSource, Damage damage) {
		health -= getCalculatedDamage(damage);
		if (health <= 0) {
			died(damageSource);
		}
	}
	private void died(Entity damageSource) {
		removed = true;
	}
	protected double getCalculatedDamage(Damage damage) {
		double resultingDamage = damage.getDamageAmount();
		
		if (damage.getDamageType() == Damage.PHYSICAL_DAMAGE) {
			resultingDamage -= getPhysicalDefence();
		} else {
			resultingDamage -= getMentalDefence();
		}
		
		if (resultingDamage >= 0) {
			return resultingDamage;
		} else {
			return 0;
		}
	}
	
	/*
	* getters
	* */
	public Weapon getWeapon() {
		return weapon;
	}
	
	//attributes
	public double getMaxHealth() {
		return maxHealth;
	}
	
	public double getPhysicalDefence() {
		return physicalDefence;
	}
	
	public double getMentalDefence() {
		return mentalDefence;
	}
	
	public double getStrength() {
		return strength;
	}
	
	public double getIntelligence() {
		return intelligence;
	}
	
	public double getLuck() {
		return luck;
	}
	
	public double getHealth() {
		return health;
	}
}
