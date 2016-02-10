package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.entity.entitytask.Behavior;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 08.02.2016.
 */
public abstract class EnemyEntity extends LivingEntity {
	
	protected EnemyEntity(int x, int y, Level level, int width, int height, int spriteXOffset, int spriteYOffset, Behavior behavior, String name) {
		super(x, y, level, width, height, spriteXOffset, spriteYOffset, behavior, name);
	}
}
