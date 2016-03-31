package com.gmail.xfrednet.filefighter.item.item.weapon.gun;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.projectiles.CDProjectile;
import com.gmail.xfrednet.filefighter.entity.projectiles.PaperProjectile;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.item.Damage;
import com.gmail.xfrednet.filefighter.item.item.weapon.ShootingWeapon;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 31.03.2016.
 */
public class MP3Player extends ShootingWeapon {
	@Override
	public boolean attack(Level level, LivingEntity executingEntity, double angle) {
		if (useTimer > 0) return false;
		
		if (executingEntity.useStamina(getStaminaUsage())) {
			level.spawn(new CDProjectile(level, getModifiedAngle(angle), getProjectileSpeed(), getRange(), getDamage(executingEntity), executingEntity));
			useTimer += getShootSpeed();
			return true;
		}
		
		return false;
	}
	
	@Override
	public Item clone() {
		return new MP3Player();
	}
	
	@Override
	public String getName() {
		return "MP3 Player";
	}
	
	@Override
	public Sprite getItemSprite() {
		return Sprite.Item.mp3_player;
	}
	
	/*
	* else
	* */
	@Override
	public double getAccuracy() {
		return 60;
	}
	@Override
	public double getStaminaUsage() {
		return 30;
	}
	
	/*
	* Damage
	* */
	@Override
	public double getDamageAmount() {
		return 4;
	}
	@Override
	public int getDamageType() {
		return Damage.MENTAL_DAMAGE;
	}
	
	/*
	* projectile
	* */
	@Override
	public int getShootSpeed() {
		return Main.UPS * 5;
	}
	@Override
	public double getProjectileSpeed() {
		return 4;
	}
	@Override
	public double getRange() {
		return 500;
	}
	
	
	
	
}
