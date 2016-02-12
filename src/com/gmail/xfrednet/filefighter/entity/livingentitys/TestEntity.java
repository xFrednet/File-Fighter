package com.gmail.xfrednet.filefighter.entity.livingentitys;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.MoveToTarget;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.RandomMovement;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class TestEntity extends LivingEntity {
	
	public static final int ANIMATION_SPRITES = 16;
	int targetID;
	
	public TestEntity(int x, int y, Level level, String name) {
		super(level, name, new RandomMovement(1));
		super.setInfo(x, y, 16, 30, 8, 1);
	}
	public TestEntity(int x, int y, Level level, Entity target, String name) {
		super(level, name, new MoveToTarget(target.getID(), 1, 30));
		super.setInfo(x, y, 16, 30, 8, 1);
	}
	
	@Override
	protected void updateCurrentSprite() {
		if (isStanding) {
			currentSprite = Sprite.testEntity_entity_sprite[STILL_STANDING_SPRITE_INDEX];
		} else {
			currentSprite = Sprite.testEntity_entity_sprite[(direction * Sprite.TEST_ENTITY_ANIMATED_SPRITE_COUNT) + ((int)(animation / Sprite.TEST_ENTITY_ANIMATION_SPEED) % Sprite.TEST_ENTITY_ANIMATED_SPRITE_COUNT)];
		}
	}
	
}
