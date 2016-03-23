package com.gmail.xfrednet.filefighter.entity.livingentitys.enemy;

import com.gmail.xfrednet.filefighter.entity.EnemyEntity;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.RandomMovement;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 15.03.2016.
 */
public class FileEntity extends EnemyEntity {
	public FileEntity(double x, double y, Level level, String name) {
		super(level, name, 0, new RandomMovement(1));
		super.setInfo(x, y);
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
