package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 08.02.2016.
 */
public abstract class EnemyEntity extends LivingEntity {
	
	protected EnemyEntity(Level level, String name, int xp) {
		super(level, name, xp);
		team = ENEMY_TEAM;
	}
	
}
