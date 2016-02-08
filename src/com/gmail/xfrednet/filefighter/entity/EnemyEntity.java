package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.entity.entitytask.Behavior;

/**
 * Created by xFrednet on 08.02.2016.
 */
public abstract class EnemyEntity extends LivingEntity {
	
	protected EnemyEntity(int x, int y, int width, int height, int spriteXOffset, int spriteYOffset, Behavior behavior, String name) {
		super(x, y, width, height, spriteXOffset, spriteYOffset, behavior, name);
	}
}
