package com.gmail.xfrednet.filefighter.entity;

/**
 * Created by xFrednet on 07.02.2016.
 */
public abstract class EntityTask {
	
	protected int timer = 0;
	
	abstract public void update(Entity entity);
	
	protected void updateTimer() {
		timer++;
	}
	
}
