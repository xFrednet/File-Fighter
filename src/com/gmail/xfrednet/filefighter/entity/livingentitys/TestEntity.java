package com.gmail.xfrednet.filefighter.entity.livingentitys;

import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.RandomMovement;
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
		super(level, name, 0, new RandomMovement(1));
		super.setInfo(x, y, 16, 30, 8, 1);
	}
	
	public TestEntity(double x, double y, Level level, String name, int i, int i1) {
		super(level, name, 0, null);
		super.setInfo(x, y);
		path = new Path(level, this, level.getPlayer());
		setSprite(Sprite.wall_tile_sprite[0]);
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
	
	@Override
	protected double getBaseAttribute(int attribute) {
		switch (attribute) {
			case ATTRIBUTE_MAX_HEALTH: return 10;
			case ATTRIBUTE_MAX_STAMINA: return 25;
			case ATTRIBUTE_PHYSICAL_DEFENCE: return 1;
			case ATTRIBUTE_MENTAL_DEFENCE: return 1;
			case ATTRIBUTE_PHYSICAL_DAMAGE: return 1;
			case ATTRIBUTE_MENTAL_DAMAGE: return 1;
			case ATTRIBUTE_LUCK: return 1;
			case ATTRIBUTE_HEALTH_REGENERATION: return 0;
			case ATTRIBUTE_STAMINA_REGENERATION: return 0.2;
			default: return 0;
		}
	}
	
}
