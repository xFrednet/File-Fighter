package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.entity.entitytask.Behavior;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 08.02.2016.
 */
public abstract class EnemyEntity extends LivingEntity {
	
	protected EnemyEntity(Level level, String name, int xp, Behavior behavior) {
		super(level, name, xp, behavior);
	}
	
}
