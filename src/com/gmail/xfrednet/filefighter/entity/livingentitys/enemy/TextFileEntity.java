package com.gmail.xfrednet.filefighter.entity.livingentitys.enemy;

import com.gmail.xfrednet.filefighter.entity.EnemyEntity;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.PaperGun;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.level.path.Path;

/**
 * Created by xFrednet on 08.02.2016.
 */
public class TextFileEntity extends EnemyEntity {
	
	Path path;
	
	public TextFileEntity(int x, int y, Level level, String name) {
		super(level, name, 0);
		super.setInfo(x, y);
		
		path = new Path(level, this, level.getPlayer());
		team = ENEMY_TEAM;
		weapon = new PaperGun();
	}
	
	@Override
	public void update(Level level) {
		super.update(level);
		
		if (path.hasFinished()) {
			path = new Path(level, this, level.getPlayer());
		} else {
			path.followPath(this, level, getAttribute(ATTRIBUTE_SPEED));
		}
		if (getDistance(level.getPlayer()) <= weapon.getRange() && getWeapon().isUsable(this)) {
			weapon.attack(level, this, getAngleTo(level.getPlayer()));
		}
		
	}
}
