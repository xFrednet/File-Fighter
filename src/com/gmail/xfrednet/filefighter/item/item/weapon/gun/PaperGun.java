package com.gmail.xfrednet.filefighter.item.item.weapon.gun;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.projectiles.PaperProjectile;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.item.item.weapon.ShootingWeapon;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 14.02.2016.
 */
public class PaperGun extends ShootingWeapon {
	
	public PaperGun() {
		super();
	}
	
	@Override
	public boolean attack(Level level, LivingEntity executingEntity, double angle) {
		if (useTimer > 0) return false;
		
		if (executingEntity.useStamina(getStaminaUsage())) {
			level.spawn(new PaperProjectile(level, getModifiedAngle(angle), getProjectileSpeed(), getRange(), getDamage(executingEntity), executingEntity));
			useTimer += getShootSpeed();
			return true;
		}
		
		return false;		
	}
	
	@Override
	public Item clone() {
		return new PaperGun().setCount(count);
	}
	
	/*
	* abstract getters
	* */
	@Override
	public String getName() {
		return "Paper Gun";
	}
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.paper_gun;
	} 
	
	//weapon
	@Override
	public int getDamageType() {
		return Damage.PHYSICAL_DAMAGE;
	}
	
	//shootingWeapon 
	@Override
	public double getDamageAmount() {
		return 20;
	}
	
	@Override
	public int getShootSpeed() {
		return (int) (Main.UPS * 0.5);
	}
	
	@Override
	public double getRange() {
		return 250;
	}
	
	@Override
	public double getAccuracy() {
		return 60d;
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
