package com.gmail.xfrednet.filefighter.item.item.weapon.gun;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.projectiles.PaperProjectile;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.weapon.ShootingWeapon;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 14.02.2016.
 */
public class PaperGun extends ShootingWeapon {
	
	public static final String NAME = "Paper gun";
	
	public PaperGun() {
		super(NAME);
	}
	
	@Override
	public boolean attack(Level level, LivingEntity executingEntity, double angle) {
		if (useTimer > 0) return false;
		
		if (executingEntity.useStamina(getStaminaUsage())) {
			level.spawn(new PaperProjectile(level, getModifiedAngle(angle), getProjectileSpeed(), getRange(), getDamage(), executingEntity));
		}
		
		useTimer += getShootSpeed();
		
		return true;
	}
	
	/*
	* abstract getters
	* */
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.paperGun;
	}
	@Override
	public double getDamage() {
		return 3.5;
	}
	
	@Override
	public double getShootSpeed() {
		return (Main.UPS * 0.5);
	}
	
	@Override
	public double getRange() {
		return 250;
	}
	
	@Override
	public double getAccuracy() {
		return 40d;
	}
	
	@Override
	public int getDamageType() {
		return PaperProjectile.DAMAGE_TYPE;
	}
	
	@Override
	public double getProjectileSpeed() {
		return 7.5;
	}
	
	@Override
	public double getStaminaUsage() {
		return 4;
	}
}
