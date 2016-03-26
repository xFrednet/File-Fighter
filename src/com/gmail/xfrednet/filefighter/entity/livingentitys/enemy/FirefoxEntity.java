package com.gmail.xfrednet.filefighter.entity.livingentitys.enemy;

import com.gmail.xfrednet.filefighter.entity.EnemyEntity;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.ItemEntity;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.FirefoxFlameThrower;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.PaperGun;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.level.path.Path;

/**
 * Created by xFrednet on 26.03.2016 at 13:35.
 */
public class FirefoxEntity extends EnemyEntity {
	
	Path path;
	
	public FirefoxEntity(double x, double y, Level level, String name) {
		super(level, name, 0);
		setInfo(x, y);
		
		weapon = new FirefoxFlameThrower();
	}
	
	@Override
	public void update(Level level) {
		super.update(level);
		
		if (path == null || path.hasFinished()) {
			if (getDistance(level.getPlayer()) < getViewDistance()) {
				path = new Path(level, this, level.getPlayer(), 2);
			} else {
				moveRandom(level);
			}
		} else {
			path.followPath(this, level, getAttribute(ATTRIBUTE_SPEED));
		}
		
		if (getDistance(level.getPlayer()) <= weapon.getRange() && getWeapon().isUsable(this)) {
			weapon.attack(level, this, getAngleTo(level.getPlayer()));
		}
		
	}
	
	protected double getViewDistance() {
		return 10 * Level.TILE_SIZE;
	}
	
	@Override
	protected void died(Level level, Entity damageSource) {
		super.died(level, damageSource);
		
		if (random.nextInt(100) < getDropChance(damageSource, 25)) {
			ItemEntity e = new FirefoxFlameThrower().getItemEntity(level);
			e.setPosition((int)info.getCenterX(), (int)info.getCenterY());
			level.spawn(e);
		}
		
	}
}
