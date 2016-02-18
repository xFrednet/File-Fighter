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
	
	public static final double MAX_HEALTH = 10;
	public static final double PHYSICAL_DEFENCE = 1;
	public static final double MENTAL_DEFENCE = 1;
	public static final double STRENGTH = 1;
	public static final double INTELLIGENCE = 1;
	public static final double LUCK = 1;
	
	public TestEntity(int x, int y, Level level, String name) {
		super(level, name, new RandomMovement(1));
		super.setInfo(x, y, 16, 30, 8, 1);
		setAttributes(MAX_HEALTH, PHYSICAL_DEFENCE, MENTAL_DEFENCE, STRENGTH, INTELLIGENCE, LUCK);
	}
	
	@Override
	protected void updateCurrentSprite() {
		if (isStanding) {
			currentSprite = LeitsTestClass.testEntity_entity_sprite[STILL_STANDING_SPRITE_INDEX];
		} else {
			currentSprite = LeitsTestClass.testEntity_entity_sprite[(direction * LeitsTestClass.TEST_ENTITY_ANIMATED_SPRITE_COUNT) + ((int)(animation / LeitsTestClass.TEST_ENTITY_ANIMATION_SPEED) % LeitsTestClass.TEST_ENTITY_ANIMATED_SPRITE_COUNT)];
		}
	}
	
}
