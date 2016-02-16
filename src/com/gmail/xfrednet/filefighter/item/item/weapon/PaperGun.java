package com.gmail.xfrednet.filefighter.item.item.weapon;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.projectiles.PaperProjectile;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.Weapon;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 14.02.2016.
 */
public class PaperGun extends Weapon {
	
	public static final String NAME = "Paper gun";
	public static final int USE_TIME = (int) (Main.UPS * 0.3);
	
	public PaperGun() {
		super(NAME);
	}
	
	@Override
	public Sprite getItemSprite() {
		return Sprite.null_sprite;
	}
	
	@Override
	public boolean attack(Level level, Entity executingEntity, double angle) {
		if (useTimer > 0) return false;
		
		level.spawn(new PaperProjectile(level, angle, executingEntity));
		
		useTimer += USE_TIME;
		
		return true;
	}
}
