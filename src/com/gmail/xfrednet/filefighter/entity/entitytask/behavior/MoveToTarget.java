package com.gmail.xfrednet.filefighter.entity.entitytask.behavior;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.entitytask.Behavior;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 07.02.2016.
 */
public class MoveToTarget extends Behavior {
	
	int targetID;
	double speed;
	int distance;
	
	public MoveToTarget(int targetID, double speed, int distance) {
		this.targetID = targetID;
		this.speed = speed;
		this.distance = distance;
	}
	
	
	@Override
	public void update(LivingEntity entity, Level level) {
		Entity target = level.getEntity(targetID);
		if (target == null) return;
		
		int currentDistance = (int) entity.getInfo().getDistance(target.getInfo().getCenterX(), target.getInfo().getCenterY());
		
		if (currentDistance > distance) {
			entity.move(entity.getAngleTo(target), level, speed);
		}
		
	}
}
