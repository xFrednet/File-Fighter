package com.gmail.xfrednet.filefighter.item.item.weapon.gun;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.projectiles.FireProjectile;
import com.gmail.xfrednet.filefighter.entity.projectiles.PaperProjectile;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.item.item.weapon.ShootingWeapon;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 26.03.2016 at 10:48.
 */
public class FirefoxFlameThrower extends ShootingWeapon {
	
	public FirefoxFlameThrower() {
		super();
	}
	
	@Override
	public boolean attack(Level level, LivingEntity executingEntity, double angle) {
		if (useTimer > 0) return false;
		
		if (executingEntity.useStamina(getStaminaUsage())) {
			level.spawn(new FireProjectile(level, getModifiedAngle(angle), getProjectileSpeed()      , getRange(), getDamage(executingEntity), executingEntity));
			level.spawn(new FireProjectile(level, getModifiedAngle(angle), getProjectileSpeed() * 0.9, getRange(), getDamage(executingEntity), executingEntity));
			level.spawn(new FireProjectile(level, getModifiedAngle(angle), getProjectileSpeed() * 0.8, getRange(), getDamage(executingEntity), executingEntity));
			level.spawn(new FireProjectile(level, getModifiedAngle(angle), getProjectileSpeed() * 0.7, getRange(), getDamage(executingEntity), executingEntity));
			level.spawn(new FireProjectile(level, getModifiedAngle(angle), getProjectileSpeed() * 0.6, getRange(), getDamage(executingEntity), executingEntity));
			useTimer += getShootSpeed();
			return true;
		}
		
		return false;
	}
	
	public boolean isUsable(LivingEntity executingEntity) {
		return executingEntity.hasEnoughStamina(getStaminaUsage());
	}
	
	/*
	* abstract getters
	* */
	@Override
	public String getName() {
		return "Firefox Flamethrower";
	}
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.fireFoxFlameThrower;
	}
	
	//weapon
	@Override
	public int getDamageType() {
		return Damage.MENTAL_DAMAGE;
	}
	
	//shootingWeapon 
	@Override
	public double getDamageAmount() {
		return 2;
	}
	
	@Override
	public double getShootSpeed() {
		return 0;
	}
	
	@Override
	public double getRange() {
		return 50;
	}
	
	@Override
	public double getAccuracy() {
		return 4d;
	}
	
	@Override
	public double getProjectileSpeed() {
		return 10;
	}
	
	@Override
	public double getStaminaUsage() {
		return 2;
	}
}
