package com.gmail.xfrednet.filefighter.entity.livingentitys;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.MoveToTarget;
import com.gmail.xfrednet.filefighter.entity.entitytask.behavior.RandomMovement;
import com.gmail.xfrednet.filefighter.graphics.Sprite;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class TestEntity extends LivingEntity {
	
	public static final int ANIMATION_SPRITES = 16;
	int targetID;
	
	public TestEntity(int x, int y) {
		super(x, y, 16, 30, 8, 1, new RandomMovement(1));
	}
	public TestEntity(int x, int y, Entity target) {
		super(x, y, 16, 30, 8, 1, new MoveToTarget(target.getID(), 1, 30));
	}
	
	@Override
	protected void updateCurrentSprite() {
		if (isStanding) {
			currentSprite = Sprite.slime_entity_sprite[STILL_STANDING_SPRITE_INDEX];
		} else {
			currentSprite = Sprite.slime_entity_sprite[(direction * ANIMATION_SPRITES) + ((int)(animation / ANIMATION_SPEED) % ANIMATION_SPRITES)];
		}
	}
	
}
