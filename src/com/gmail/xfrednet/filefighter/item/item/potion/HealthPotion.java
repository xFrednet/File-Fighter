package com.gmail.xfrednet.filefighter.item.item.potion;

import com.gmail.xfrednet.filefighter.Main;
import com.gmail.xfrednet.filefighter.entity.LivingEntity;
import com.gmail.xfrednet.filefighter.entity.Player;
import com.gmail.xfrednet.filefighter.graphics.Sprite;
import com.gmail.xfrednet.filefighter.graphics.gui.GUIComponentGroup;
import com.gmail.xfrednet.filefighter.graphics.gui.groups.GUIItemInfoFrame;
import com.gmail.xfrednet.filefighter.item.Item;
import com.gmail.xfrednet.filefighter.level.Level;

/**
 * Created by xFrednet on 27.03.2016 at 14:04.
 */
public class HealthPotion extends Item {
	
	double healAmount;
	String name;
	Sprite sprite;
	
	private HealthPotion(int count, double healAmount, String name, Sprite sprite) {
		super(count);
		this.healAmount = healAmount;
		this.name = name;
		this.sprite = sprite;
	}
	
	@Override
	public Item clone() {
		return new HealthPotion(count, healAmount, name, sprite);
	}
	
	/*
	* Types
	* */
	public static HealthPotion newSmallHealthPotion(int count) {
		return new HealthPotion(count, 30, "small health potion", Sprite.Item.health_potion);
	}
	
	/*
	* util
	* */
	@Override
	public void update(Level level) {
		Player player = level.getPlayer();
		useTimer = player.getHealthPotionDelayTimer();
	}
	
	/*
	* getters
	* */
	@Override
	public boolean equals(Object object) {
		if (super.equals(object)) {
			HealthPotion healthPotion = (HealthPotion) object;
			
			return healthPotion.getHealAmount() == getHealAmount();
		} else {
			return false;
		}
	}
	
	@Override
	public GUIItemInfoFrame getGUIItemInfoFrame(GUIComponentGroup parent, int x, int y) {
		GUIItemInfoFrame info = super.getGUIItemInfoFrame(parent, x, y);
		info.addItemInfo(new GUIItemInfoFrame.GUItemNumberInfo(info, "Health", "+ " + getHealAmount()));
		return info;
	}
	
	@Override
	public boolean usePrimaryAction(Level level, LivingEntity executingEntity, double angle) {
		if (useTimer > 0 || !(executingEntity instanceof Player)) return false;
		Player player = (Player) executingEntity;
		player.heal(healAmount);
		
		count--;
		player.setHealthPotionDelayTimer(getUseDelay());
		useTimer = player.getHealthPotionDelayTimer();
		return true;
	}
	
	@Override
	public Sprite getItemSprite() {
		return sprite;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	protected int getMaxStackSize() {
		return 20;
	}
	
	public double getHealAmount() {
		return healAmount;
	}
	
	@Override
	public int getUseDelay() {
		return Main.UPS * 10;
	}
}
