package com.gmail.xfrednet.filefighter.entity.livingentitys;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.item.item.weapon.gun.PaperGun;
import com.gmail.xfrednet.filefighter.level.Level;
import com.gmail.xfrednet.filefighter.level.path.Node;
import com.gmail.xfrednet.filefighter.level.path.Path;

import java.util.List;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class TestEntity extends LivingEntity {
	
	Path path;
	
	public TestEntity(double x, double y, Level level, String name) {
		super(level, name, 0);
		super.setInfo(x, y);
		path = new Path(level, this, level.getPlayer());
		setSprite(Sprite.Tiles.wall_tile_sprite[0]);
		weapon = new PaperGun();
		team = ENEMY_TEAM;
	}
	
	@Override
	public void update(Level level) {
		super.update(level);
		
		if (path.hasFinished()) {
			path = new Path(level, this, level.getPlayer());
		} else {
			path.followPath(this, level, 1);
		}
		if (getDistance(level.getPlayer()) <= 90 && getWeapon().isUsable(this)) {
			weapon.attack(level, this, getAngleTo(level.getPlayer()));
			System.out.println("You'll die");
		}
	}
	
}
