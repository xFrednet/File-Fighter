package com.gmail.xfrednet.filefighter.entity;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.entitytask.Behavior;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.item.item.Weapon;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 06.02.2016.
 */
public abstract class LivingEntity extends Entity {
	
	public static final int MAX_ANIMATION_VALUE = 10000;
	public static final int ANIMATION_SPEED =  ((int) (Main.UPS * 0.2) == 0) ? 1 : (int) (Main.UPS * 0.2);
	public static final int STILL_STANDING_SPRITE_INDEX = 0;
	
	public Weapon weapon;
	public Behavior behavior;
	
	protected int direction = 0;
	protected int animation = 0;
	public boolean isStanding = false;
	
	/*
	* Constructor
	* */
	
	protected LivingEntity(Level level, String name) {
		this(level, name, null);
	}
	protected LivingEntity(Level level, String name, Behavior behavior) {
		super(level, name);
		updateCurrentSprite();
		this.behavior = behavior;
	}
	
	/*
	* Util
	* */
	public void move(double angle, Level level, double speed) {
		double xm = speed * Math.sin(angle);
		double ym = speed * Math.cos(angle);
		
		move(xm, ym, level);
	}
	private void move(double xm, double ym, Level level) {
		if (ym < 0) direction = 1;
		if (ym > 0) direction = 0;
		
		if (xm != 0)
			if (!levelCollision(xm, 0, level)) {
				info.x += xm;
			}
		
		if (ym != 0)
			if (!levelCollision(0, ym, level)) {
				info.y += ym;
			}
		
	}
	
	protected void updateAnimation() {
		animation++;
		if (animation > MAX_ANIMATION_VALUE) {
			animation = animation % MAX_ANIMATION_VALUE;
		}
		
		updateCurrentSprite();
	}
	
	@Override
	public void update(Level level) {
		if (weapon != null) weapon.update(level);
		behavior.update(this, level);
		updateAnimation();
	}
	
	abstract protected void updateCurrentSprite();
	
	/*
	* getters
	* */
	public Weapon getWeapon() {
		return weapon;
	}
	
}
