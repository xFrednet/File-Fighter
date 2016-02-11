package com.gmail.xfrednet.filefighter.entity.entitytask;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.EntityTask;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 07.02.2016.
 */
public abstract class Behavior extends EntityTask {
	
	
	abstract public void update(LivingEntity entity, Level level);
	
	@Override
	public void update(Entity entity, Level level) {
		System.out.println("[ERROR] Behavior: wrong method call: update(Entity enitity)");
	}
}
