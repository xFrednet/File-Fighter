package com.gmail.xfrednet.filefighter.item;

import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.ItemEntity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.level.Level;

import java.util.Random;

/**
 * Created by xFrednet on 14.02.2016.
 */
public abstract class Item {
	
	protected static final Random random = new Random();
	
	protected String name;
	protected int count;
	protected int useTimer = 0;
	protected int maxStackSize = 1;
	
	/*
	* Constructor
	* */
	protected Item(String name) {
		this(name, 1);
	}
	protected Item(String name, int count) {
		this.name = name;
		this.count = count;
	}
	/*
	* abstract
	* */
	abstract public Sprite getItemSprite();
	
	/*
	* Use methods
	* */
	public boolean usePrimaryAction(Level level, LivingEntity executingEntity, double angle) {
		return true;
	}
	public boolean useSecondaryAction(Level level, LivingEntity executingEntity, double angle) {
		return true;
	}
	
	/*
	* Util
	* */
	//update
	public void updateFormEntity(Level level) {}
	public void update(Level level) {
		updateUseTimer();
	}
	public void updateUseTimer() {
		if (useTimer > 0) {
			useTimer--;
		}
	}
	
	/*
	* getters
	* */
	public ItemEntity getItemEntity(Level level) {
		return new ItemEntity(0, 0,level, this);
	}
	
	public String getName() {
		return name;
	}
	
	
}
