package com.gmail.xfrednet.filefighter.entity.livingentitys;

import com.gmail.xfrednet.filefighter.LeitsTestClass;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.MoveToTarget;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.RandomMovement;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class TestEntity extends LivingEntity {
	
	public TestEntity(double x, double y, Level level, String name) {
		super(level, name, 0, new RandomMovement(1));
		super.setInfo(x, y, 16, 30, 8, 1);
	}
	
	@Override
	protected void updateCurrentSprite() {
		if (isStanding) {
			currentSprite = LeitsTestClass.testEntity_entity_sprite[STILL_STANDING_SPRITE_INDEX];
		} else {
			currentSprite = LeitsTestClass.testEntity_entity_sprite[(direction * LeitsTestClass.TEST_ENTITY_ANIMATED_SPRITE_COUNT) + ((int)(animation / LeitsTestClass.TEST_ENTITY_ANIMATION_SPEED) % LeitsTestClass.TEST_ENTITY_ANIMATED_SPRITE_COUNT)];
		}
	}
	
	@Override
	protected double getBaseAttribute(int attribute) {
		switch (attribute) {
			case ATTRIBUTE_MAX_HEALTH: return 10;
			case ATTRIBUTE_MAX_STAMINA: return 25;
			case ATTRIBUTE_PHYSICAL_DEFENCE: return 1;
			case ATTRIBUTE_MENTAL_DEFENCE: return 1;
			case ATTRIBUTE_STRENGTH: return 1;
			case ATTRIBUTE_INTELLIGENCE: return 1;
			case ATTRIBUTE_LUCK: return 1;
			case ATTRIBUTE_HEALTH_REGENERATION: return 0;
			case ATTRIBUTE_STAMINA_REGENERATION: return 0.2;
			default: return 0;
		}
	}
	
}
