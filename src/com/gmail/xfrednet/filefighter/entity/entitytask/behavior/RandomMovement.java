package com.gmail.xfrednet.filefighter.entity.entitytask.behavior;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.entitytask.Behavior;
import com.gmail.xfrednet.filefighter.level.Level;

import java.util.Random;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class RandomMovement extends Behavior {
	
	private static final int NEW_DIRECTION_MAX_TIME = 2 * Main.UPS;
	
	int newDirectionTimer = 0;
	double direction = 0;
	double speed;
	
	public RandomMovement(double speed) {
		this.speed = speed;
	}
	
	@Override
	public void update(LivingEntity entity, Level level) {
		updateTimer();
		entity.move(direction, level, speed);
		if (timer < newDirectionTimer) return;
		
		direction = Math.PI * 2 * Entity.random.nextDouble() - Math.PI;
		newDirectionTimer += Entity.random.nextInt(NEW_DIRECTION_MAX_TIME);
	}
}
