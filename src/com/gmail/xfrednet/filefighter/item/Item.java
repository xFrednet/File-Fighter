package com.gmail.xfrednet.filefighter.item;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.Entity;
import com.gmail.xfrednet.filefighter.entity.ItemEntity;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.groups.GUIItemInfoFrame;
import com.gmail.xfrednet.filefighter.level.Level;

import java.util.Random;

/**
 * Created by xFrednet on 14.02.2016.
 */
public abstract class Item {
	
	protected static final Random random = new Random();
	
	protected int count;
	protected int useTimer = 0;
	protected int maxStackSize = 1;
	
	/*
	* Constructor
	* */
	protected Item() {
		this(1);
	}
	protected Item(int count) {
		this.count = count;
		maxStackSize = getMaxStackSize();
	}
	
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
	
	public void drop(Level level, Player player) {
		drop(level, player, count);
	}
	public void drop(Level level, Player player, int count) {
		level.spawn(getItemEntity(level, player.getInfo().getCenterX(), player.getInfo().getCenterY(), count));
	}
	
	@Override
	public boolean equals(Object object) {
		return object != null && (this.getClass().getSimpleName().equals(object.getClass().getSimpleName()));
	} 
	
	public boolean equals(Class obj) {
		//test id the item classes are the same 
		return this.getClass().getName().equals(obj.getName());
	}
	
	/*
	* getters
	* */
	public ItemEntity getItemEntity(Level level) {
		return getItemEntity(level, 0, 0);
	}
	public ItemEntity getItemEntity(Level level, double x, double y) {
		return getItemEntity(level, x, y, getCount());
	}
	public ItemEntity getItemEntity(Level level, double x, double y, int count) {
		if (count < this.count) {
			Item item = this.clone();
			item.setCount(count);
			setCount(this.count - count);
			
			return item.getItemEntity(level, x, y);
		}
		return new ItemEntity(x, y, level, this);
	}
	public GUIItemInfoFrame getGUIItemInfoFrame(GUIComponentGroup parent, int x, int y) {
		return new GUIItemInfoFrame(parent, x, y, this);
	}
	public int getCount() {
		return count;
	}
	
	/*
	* setters
	* */
	public void addToCount(int count) {
		this.count += count;
	}
	public Item setCount(int count) {
		this.count = count;
		return this;
	}
	
	/*
	* abstract
	* */
	abstract public String getName();
	abstract protected int getMaxStackSize();
	abstract public Sprite getItemSprite();
	abstract public Item clone();
	abstract public int getUseDelay();
	
}
